package parsing;

import config.Settings;
import config.SettingsParser;
import functions.GeneralFunction;
import functions.special.Constant;
import functions.special.Variable;
import functions.unitary.transforms.Integral;
import tools.MiscTools;
import tools.singlevariable.Extrema;
import tools.singlevariable.NumericalIntegration;
import tools.singlevariable.Solver;
import tools.singlevariable.TaylorSeries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuppressWarnings("SpellCheckingInspection")
public class KeywordInterface {
	public static final Pattern keywordSplitter = Pattern.compile("\"\\s+\"|\"\\s+|\\s+\"|\"$|\\s+(?=[^\"]*(\"[^\"]*\"[^\"]*)*$)");
	public static final Pattern spacesAndDdx = Pattern.compile("\\s+|(?<=^d/d)(?=[a-zA-Z])");
	public static final Pattern equals = Pattern.compile("=");
	public static final HashMap<String, GeneralFunction> storedFunctions = new HashMap<>();
	public static Object prev;

	/**
	 * Takes input as a string with command, arguments...
	 * @param input contains the command and arguments
	 * @return the Object requested
	 */
	public static Object useKeywords(String input) { // TODO Erez and Michael need to go over this
		if ("_".equals(input))
			return prev;
		String[] splitInput = spacesAndDdx.split(input, 2);
		Object ret = switch (splitInput[0]) {
			case "pd", "pdiff", "partial", "pdifferentiate", "d/d" -> partialDiff(splitInput[1]);
			case "pdn", "pdiffn", "partialn", "pdifferentiaten" -> partialDiffNth(splitInput[1]);
			case "eval", "evaluate" -> evaluate(splitInput[1]);
			case "simp", "simplify" -> simplify(splitInput[1]);
			case "sub", "substitute" -> substitute(splitInput[1]);
			case "sol", "solve" -> solve(splitInput[1]);
			case "ext", "extrema" -> extrema(splitInput[1]);
			case "tay", "taylor" -> taylor(splitInput[1]);
			case "intn", "intnumeric" -> integrateNumeric(splitInput[1]);
			case "intne", "intnumericerror" -> integrateNumericError(splitInput[1]);
			case "addf", "sto", "store", "new", "def", "addfunction" -> storeFunction(splitInput[1]);
			case "addv", "addvar", "addvars" -> addVariables(splitInput[1]);
			case "addc", "addconstant", "defc", "defcon", "defconstant" -> defineConstant(splitInput[1]);
			case "rmf", "rmfun", "removefun", "removefunction" -> removeFunction(splitInput[1]);
			case "rmv", "rmvar", "removevar", "removevariable" -> removeVariables(splitInput[1]);
			case "rmc", "rmconstant", "removeconstant" -> removeConstant(splitInput[1]);
			case "pf", "printfun", "printfunctions" -> printFunctions();
			case "pv", "vars", "printvars" -> printVariables();
			case "pc", "printc", "printconstants" -> printConstants();
			case "cf", "clearfun", "clearfunctions" -> clearFunctions();
			case "cv", "clearvars" -> clearVariables();
			case "ss", "sset", "sets", "setsetting" -> setSettings(splitInput[1]);
			case "ps", "settings", "printsettings" -> printSettings();
			case "svt", "setvarsto", "setvariablesto" -> setVariablesTo(splitInput[1]);
			case "int", "integral" -> integral(splitInput[1]);
			default -> null;
		};
		if (ret == null) {
			if (storedFunctions.containsKey(input)) {
				prev = storedFunctions.get(input);
				return prev;
			} else if (Constant.isSpecialConstant(input)) {
				prev = Constant.getSpecialConstant(input);
				return prev;
			} else try {
				 prev = substituteAll(Parser.parse(input));
				 return prev;
			} catch (Exception ignored) {
				throw new IllegalArgumentException(splitInput[0] + " is not supported by KeywordInterface");
			}
		}
		prev = ret;
		return ret;
	}

	/**
	 * Parses input using {@link #useKeywords(String)} and {@link #storedFunctions}
	 * @param input input string
	 * @return a {@link GeneralFunction}
	 */
	public static GeneralFunction parseStored(String input) {
		if ("_".equals(input))
			return Parser.toFunction(prev);
		if (input.chars().filter(ch -> ch == '\"').count() % 2 == 1) // this is a really janky fix
			if (input.charAt(input.length() - 1) == '\"')
				input = input.substring(0, input.length() - 1);
			else
				throw new IllegalArgumentException("Unmatched quotes in " + input);
		if (storedFunctions.containsKey(input))
			return storedFunctions.get(input);
		else if (Constant.isSpecialConstant(input))
			return new Constant(input);
		else
			return (GeneralFunction) useKeywords(input);
	}

	/**
	 * Substitutes everything stored in {@link #storedFunctions} into the input {@link GeneralFunction}
	 * @param function the function to be substituted into
	 * @return input with all substitutions
	 */
	public static GeneralFunction substituteAll(GeneralFunction function) {
		for (Map.Entry<String, GeneralFunction>  entry : storedFunctions.entrySet())
			function = function.substituteVariable(MiscTools.getCharacter(entry.getKey()), entry.getValue());
		return function;
	}

	/**
	 * pd [variable] [function]
	 */
	public static GeneralFunction partialDiff(String input) {
		String[] splitInput = keywordSplitter.split(input, 2);
		return parseStored(splitInput[1]).getSimplifiedDerivative(MiscTools.getCharacter(splitInput[0]));
	}

	/**
	 * pdn [variable] [times] [function]
	 */
	private static GeneralFunction partialDiffNth(String input) {
		String[] splitInput = keywordSplitter.split(input, 3);
		return parseStored(splitInput[2]).getNthDerivative(MiscTools.getCharacter(splitInput[0]), Integer.parseInt(splitInput[1]));
	}

	/**
	 * eval [function] [values]
	 */
	public static double evaluate(String input) {
		String[] splitInput = keywordSplitter.split(input, 2);
		double[] values = Arrays.stream(keywordSplitter.split(splitInput[1])).mapToDouble(Parser::getConstant).toArray();
		Map<Character, Double> map = new HashMap<>();
		for (int i = 0; i < values.length; i++)
			map.put(Variable.variables.get(i), values[i]);
		return parseStored(splitInput[0]).evaluate(map);
	}

	/**
	 * simp [function]
	 */
	public static GeneralFunction simplify(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return parseStored(splitInput[0]).simplify();
	}

	/**
	 * sub [function] [variable] [replacementfunction]
	 */
	public static GeneralFunction substitute(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return parseStored(splitInput[0]).substituteVariable(MiscTools.getCharacter(splitInput[1]), parseStored(splitInput[2]));
	}

	/**
	 * sol [function] [startrange] [endrange]
	 */
	public static double[] solve(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return Solver.getSolutionsRange(parseStored(splitInput[0]), Parser.getConstant(splitInput[1]), Parser.getConstant(splitInput[2]));
	}

	/**
	 * ext ["min(ima)"/"max(ima)"/"anymin(ima)"/"anymax(ima)"/"inflect(ion)"] [function] [startrange] [endrange]
	 */
	public static Object extrema(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return switch (splitInput[0]) {
			case "min", "minima" -> Extrema.findLocalMinimum(parseStored(splitInput[1]), Parser.getConstant(splitInput[2]), Parser.getConstant(splitInput[3]));
			case "max", "maxima" -> Extrema.findLocalMaximum(parseStored(splitInput[1]), Parser.getConstant(splitInput[2]), Parser.getConstant(splitInput[3]));
			case "anymin", "anyminima" -> Extrema.findAnyMinima(parseStored(splitInput[1]), Parser.getConstant(splitInput[2]), Parser.getConstant(splitInput[3]));
			case "anymax", "anymaxima" -> Extrema.findAnyMaxima(parseStored(splitInput[1]), Parser.getConstant(splitInput[2]), Parser.getConstant(splitInput[3]));
			case "inflect", "inflection" -> Extrema.findAnyInflectionPoints(parseStored(splitInput[1]), Parser.getConstant(splitInput[2]), Parser.getConstant(splitInput[3]));
			default -> throw new IllegalStateException("Invalid setting for extrema:" + splitInput[0]);
		};
	}

	/**
	 * tay [function] [terms] [center]
	 */
	public static GeneralFunction taylor(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return TaylorSeries.makeTaylorSeries(parseStored(splitInput[0]), (int) Parser.getConstant(splitInput[1]), Parser.getConstant(splitInput[2]));
	}

	/**
	 * sto [locationstring] [input]
	 */
	public static Object storeFunction(String input) {
		String[] splitInput = keywordSplitter.split(input, 2);
		if (!storedFunctions.containsKey(splitInput[0]))
			Variable.addFunctionVariable(MiscTools.getCharacter(splitInput[0]));
		try {
			storedFunctions.put(splitInput[0], (GeneralFunction) KeywordInterface.useKeywords(splitInput[1]));
		} catch (RuntimeException e) {
			storedFunctions.put(splitInput[0], parseStored(splitInput[1]));
		}
		return storedFunctions.get(splitInput[0]);
	}

	/**
	 * rmfun [functionname]
	 */
	private static Object removeFunction(String input) {
		Variable.removeFunctionVariable(MiscTools.getCharacter(input));
		return printVariables();
	}

	/**
	 * var(s) [variablename(s)]
	 */
	public static String addVariables(String input) {
		String[] splitInput = keywordSplitter.split(input);
		for (String var : splitInput)
			Variable.addVariable(MiscTools.getCharacter(var));
		return printVariables();
	}

	/**
	 * rmvar(s) [variablenames]
	 */
	private static Object removeVariables(String input) {
		String[] splitInput = keywordSplitter.split(input);
		for (String var : splitInput)
			Variable.removeVariable(MiscTools.getCharacter(var));
		return printVariables();
	}

	/**
	 * defcon [constantstring] [value]
	 */
	private static Object defineConstant(String input) {
		String[] splitInput = keywordSplitter.split(input, 2);
		try {
			return Constant.addSpecialConstant(splitInput[0], ((GeneralFunction) KeywordInterface.useKeywords(splitInput[1])).evaluate(null));
		} catch (Exception e) {
			return Constant.addSpecialConstant(splitInput[0], parseStored(splitInput[1]).evaluate(null));
		}
	}

	/**
	 * rmc [constantstring]
	 */
	private static double removeConstant(String input) {
		return Constant.removeSpecialConstant(input);

	}

	/**
	 * printconstants
	 */
	@SuppressWarnings("SameReturnValue")
	private static Object printConstants() {
		return Constant.specialConstants;
	}

	/**
	 * printvars
	 */
	public static String printVariables() {
		return String.valueOf(Variable.variables);
	}

	/**
	 * clearvars
	 */
	public static String clearVariables() {
		Variable.clearVariables();
		storedFunctions.clear();
		return String.valueOf(Variable.variables);
	}

	/**
	 * printfun
	 */
	public static String printFunctions() {
		return String.valueOf(storedFunctions);
	}

	/**
	 * clearfun
	 */
	public static String clearFunctions() {
		Variable.clearFunctionVariables();
		storedFunctions.clear();
		return String.valueOf(Variable.variables);
	}

	/**
	 * intn [function] [startvalue] [endvalue]
	 */
	public static double integrateNumeric(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return NumericalIntegration.simpsonsRule(parseStored(splitInput[0]), Parser.getConstant(splitInput[1]), Parser.getConstant(splitInput[2]));
	}

	/**
	 * intne [function] [startvalue] [endvalue]
	 */
	private static double[] integrateNumericError(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return NumericalIntegration.simpsonsRuleWithError(parseStored(splitInput[0]), Parser.getConstant(splitInput[1]), Parser.getConstant(splitInput[2]));
	}

	/**
	 * setsetting [setting] [value]
	 */
	private static String setSettings(String input) {
		String[] splitInput = keywordSplitter.split(input);
		SettingsParser.parseSingleSetting(splitInput[0], splitInput[1]);
		return splitInput[0] + " = " + splitInput[1];
	}

	/**
	 * settings
	 */
	private static String printSettings() {
		return "simpsonsSegments = " + Settings.simpsonsSegments + "\n"
		+ "defaultSolverIterations = " + Settings.defaultSolverIterations + "\n"
		+ "defaultRangeSections = " + Settings.defaultRangeSections + "\n"
		+ "zeroMargin = " + Settings.zeroMargin + "\n"
		+ "simplifyFunctionsOfConstants = " + Settings.simplifyFunctionsOfConstants + "\n"
		+ "distributeExponents = " + Settings.distributeExponents + "\n"
		+ "cacheDerivatives = " + Settings.cacheDerivatives + "\n"
		+ "trustImmutability = " + Settings.trustImmutability + "\n"
		+ "singleVariableDefault = " + Settings.singleVariableDefault + "\n"
		+ "defaultSolverType = " + Settings.defaultSolverType;
	}

	/**
	 * svt [function] ([char]=[value])*
	 */
	private static Object setVariablesTo(String input) {
		String[] splitInput = keywordSplitter.split(input, 2);
		return parseStored(splitInput[0]).setVariables(Arrays.stream(keywordSplitter.split(splitInput[1])).map(equals::split).collect(Collectors.toMap(e -> MiscTools.getCharacter(e[0]), e -> Parser.getConstant(e[1]))));
	}

	/**
	 * integral [function] d[variable]
	 */
	private static GeneralFunction integral(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return (new Integral(parseStored(splitInput[0]), splitInput[1].charAt(1)).execute().simplify());
	}
}
