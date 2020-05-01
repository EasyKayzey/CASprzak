package parsing;

import config.Settings;
import functions.Function;
import functions.special.Constant;
import functions.special.Variable;
import tools.MiscTools;
import functions.unitary.Integral;
import tools.singlevariable.Extrema;
import tools.singlevariable.NumericalIntegration;
import tools.singlevariable.Solver;
import tools.singlevariable.TaylorSeries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings("SpellCheckingInspection")
public class KeywordInterface {
	public static final Pattern keywordSplitter = Pattern.compile("(?<=^d/d)(?=[a-zA-Z])|\"\\s+\"|\"\\s+|\\s+\"|\"$|\\s+(?=[^\"]*(\"[^\"]*\"[^\"]*)*$)");
	public static final HashMap<String, Function> storedFunctions = new HashMap<>();
	public static Object prev;


	/**
	 * Takes input as a string with command, arguments...
	 * @param input contains the command and arguments
	 * @return the Object requested
	 */
	public static Object useKeywords(String input) {
		if ("_".equals(input))
			return prev;
		String[] splitInput = keywordSplitter.split(input, 2);
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
	 * @return a {@link Function}
	 */
	public static Function parseStored(String input) {
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
			return (Function) useKeywords(input);
	}

	/**
	 * Substitutes everything stored in {@link #storedFunctions} into the input {@link functions.Function}
	 * @param function the function to be substituted into
	 * @return input with all substitutions
	 */
	public static Function substituteAll(Function function) {
		for (Map.Entry<String, Function>  entry : storedFunctions.entrySet())
			function = function.substitute(entry.getKey().charAt(0), entry.getValue());
		return function;
	}


	/**
	 * pd [variable] [function]
	 */
	public static Function partialDiff(String input) {
		String[] splitInput = keywordSplitter.split(input, 2);
		return parseStored(splitInput[1]).getSimplifiedDerivative(splitInput[0].charAt(0));
	}
	/**
	 * pdn [variable] [times] [function]
	 */
	private static Function partialDiffNth(String input) {
		String[] splitInput = keywordSplitter.split(input, 3);
		return parseStored(splitInput[2]).getNthDerivative(splitInput[0].charAt(0), Integer.parseInt(splitInput[1]));
	}

	/**
	 * eval [function] [values]
	 */
	public static double evaluate(String input) {
		String[] splitInput = keywordSplitter.split(input, 2);
		double[] values = Arrays.stream(keywordSplitter.split(splitInput[1])).mapToDouble(ConstantEvaluator::getConstant).toArray();
		Map<Character, Double> map = new HashMap<>();
		for (int i = 0; i < values.length; i++)
			map.put(Variable.variables.get(i), values[i]);
		return parseStored(splitInput[0]).evaluate(map);
	}

	/**
	 * simp [function]
	 */
	public static Function simplify(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return parseStored(splitInput[0]).simplify();
	}

	/**
	 * sub [function] [variable] [replacementfunction]
	 */
	public static Function substitute(String input) {
		String[] splitInput = keywordSplitter.split(input);
		if (splitInput[1].length() > 1)
			throw new IllegalArgumentException("Variables should be one character.");
		return parseStored(splitInput[0]).substitute(splitInput[1].charAt(0), parseStored(splitInput[2]));
	}

	/**
	 * sol [function] [startrange] [endrange]
	 */
	public static double[] solve(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return Solver.getSolutionsRange(parseStored(splitInput[0]), ConstantEvaluator.getConstant(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]));
	}

	/**
	 * ext ["min(ima)"/"max(ima)"/"anymin(ima)"/"anymax(ima)"/"inflect(ion)"] [function] [startrange] [endrange]
	 */
	public static Object extrema(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return switch (splitInput[0]) {
			case "min", "minima" -> Extrema.findLocalMinimum(parseStored(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]), ConstantEvaluator.getConstant(splitInput[3]));
			case "max", "maxima" -> Extrema.findLocalMaximum(parseStored(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]), ConstantEvaluator.getConstant(splitInput[3]));
			case "anymin", "anyminima" -> Extrema.findAnyMinima(parseStored(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]), ConstantEvaluator.getConstant(splitInput[3]));
			case "anymax", "anymaxima" -> Extrema.findAnyMaxima(parseStored(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]), ConstantEvaluator.getConstant(splitInput[3]));
			case "inflect", "inflection" -> Extrema.findAnyInflectionPoints(parseStored(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]), ConstantEvaluator.getConstant(splitInput[3]));
			default -> throw new IllegalStateException("Invalid setting for extrema:" + splitInput[0]);
		};
	}

	/**
	 * tay [function] [terms] [center]
	 */
	public static Function taylor(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return TaylorSeries.makeTaylorSeries(parseStored(splitInput[0]), (int) ConstantEvaluator.getConstant(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]));
	}

	/**
	 * sto [locationstring] [input]
	 */
	public static Object storeFunction(String input) {
		String[] splitInput = keywordSplitter.split(input, 2);
		if (splitInput[0].length() != 1)
			throw new IllegalArgumentException("Functions should be one character.");
		if (!storedFunctions.containsKey(splitInput[0]))
			Variable.addFunctionVariable(splitInput[0].charAt(0));
		try {
			storedFunctions.put(splitInput[0], (Function) KeywordInterface.useKeywords(splitInput[1]));
		} catch (RuntimeException e) {
			storedFunctions.put(splitInput[0], parseStored(splitInput[1]));
		}
		return storedFunctions.get(splitInput[0]);
	}

	/**
	 * rmfun [functionname]
	 */
	private static Object removeFunction(String input) {
		if (input.length() > 1)
			throw new IllegalArgumentException("Variables should be one character.");
		Variable.removeFunctionVariable(input.charAt(0));
		return printVariables();
	}


	/**
	 * var(s) [variablename(s)]
	 */
	public static String addVariables(String input) {
		String[] splitInput = keywordSplitter.split(input);
		for (String var : splitInput) {
			if (var.length() > 1)
				throw new IllegalArgumentException("Variables should be one character.");
			Variable.addVariable(var.charAt(0));
		}
		return printVariables();
	}

	/**
	 * rmvar(s) [variablenames]
	 */
	private static Object removeVariables(String input) {
		String[] splitInput = keywordSplitter.split(input);
		for (String var : splitInput) {
			if (var.length() > 1)
				throw new IllegalArgumentException("Variables should be one character.");
			Variable.removeVariable(var.charAt(0));
		}
		return printVariables();
	}

	/**
	 * defcon [constantstring] [value]
	 */
	private static Object defineConstant(String input) {
		String[] splitInput = keywordSplitter.split(input, 2);
		try {
			return Constant.addSpecialConstant(splitInput[0], ((Function) KeywordInterface.useKeywords(splitInput[1])).evaluate(null));
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
		return NumericalIntegration.simpsonsRule(parseStored(splitInput[0]), ConstantEvaluator.getConstant(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]));
	}

	/**
	 * intne [function] [startvalue] [endvalue]
	 */
	private static double[] integrateNumericError(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return NumericalIntegration.simpsonsRuleWithError(parseStored(splitInput[0]), ConstantEvaluator.getConstant(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]));
	}

	/**
	 * setsetting [setting] [value]
	 */
	private static String setSettings(String input) {
		String[] splitInput = keywordSplitter.split(input);
		switch (splitInput[0]) {
			case "defaultSolverIterations" -> Settings.defaultSolverIterations = Integer.parseInt(splitInput[1]);
			case "defaultRangeSections" -> Settings.defaultRangeSections = Integer.parseInt(splitInput[1]);
			case "simpsonsSegments" -> Settings.simpsonsSegments = Integer.parseInt(splitInput[1]);
			case "zeroMargin" -> Settings.zeroMargin = Double.parseDouble(splitInput[1]);
			case "integerMargin" -> Settings.integerMargin = Double.parseDouble(splitInput[1]);
			case "simplifyFunctionsOfConstants" -> Settings.simplifyFunctionsOfConstants = MiscTools.parseBoolean(splitInput[1]);
			case "distributeExponents" -> Settings.distributeExponents = MiscTools.parseBoolean(splitInput[1]);
			case "cacheDerivatives" -> Settings.cacheDerivatives = MiscTools.parseBoolean(splitInput[1]);
			case "trustImmutability" -> Settings.trustImmutability = MiscTools.parseBoolean(splitInput[1]);
			case "enforceIntegerOperations" -> Settings.enforceIntegerOperations = MiscTools.parseBoolean(splitInput[1]);
			case "singleVariableDefault" -> {
				if (splitInput[1].length() == 1)
					Settings.singleVariableDefault = splitInput[1].charAt(0);
				else
					throw new IllegalArgumentException("This setting should only be one character");
			}
			default -> throw new IllegalArgumentException("Setting " + splitInput[0] + " does not exist");
			//TODO implement Enum setting parsing
		}
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
	 * integral [function] d[variable]
	 */
	private static Function integral(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return (new Integral(parseStored(splitInput[0]), splitInput[1].charAt(1)).integrate().simplify());
	}
}
