package parsing;

import config.Settings;
import config.SettingsParser;
import functions.GeneralFunction;
import functions.special.Constant;
import functions.special.Variable;
import functions.unitary.transforms.Integral;
import tools.ParsingTools;
import tools.singlevariable.Extrema;
import tools.singlevariable.NumericalIntegration;
import tools.singlevariable.Solver;
import tools.singlevariable.TaylorSeries;
import ui.CASDemo;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuppressWarnings("SpellCheckingInspection")
public class KeywordInterface {
	public static final Pattern keywordSplitter = Pattern.compile("\"\\s+\"|\"\\s+|\\s+\"|\"$|\\s+(?=[^\"]*(\"[^\"]*\"[^\"]*)*$)");
	public static final Pattern spaces = Pattern.compile("\\s+");
	public static final Pattern equals = Pattern.compile("=");
	public static final HashMap<String, GeneralFunction> storedFunctions = new HashMap<>();
	public static Object prev;

	/**
	 * Takes input as a string with command, arguments...
	 * @param input contains the command and arguments
	 * @return the Object requested
	 */
	public static Object useKeywords(String input) {
		if ("_".equals(input))
			return prev;
		String[] splitInput = spaces.split(input, 2);
		Object ret = switch (splitInput[0]) {
			case "demo"															-> CASDemo.runDemo();
			case "pd", "pdiff", "partial", "pdifferentiate"						-> partialDiff(splitInput[1]);
			case "pdn", "pdiffn", "partialn", "pdifferentiaten"					-> partialDiffNth(splitInput[1]);
			case "eval", "evaluate"												-> evaluate(splitInput[1]);
			case "simp", "simplify"												-> simplify(splitInput[1]);
			case "sub", "substitute"											-> substitute(splitInput[1]);
			case "sol", "solve"													-> solve(splitInput[1]);
			case "ext", "extrema"												-> extrema(splitInput[1]);
			case "tay", "taylor"												-> taylor(splitInput[1]);
			case "intn", "intnumeric"											-> integrateNumeric(splitInput[1]);
			case "intne", "intnumericerror"										-> integrateNumericError(splitInput[1]);
			case "addf", "sto", "store", "new", "def", "addfunction"			-> storeFunction(splitInput[1]);
			case "addv", "addvar", "addvars"									-> addVariables(splitInput[1]);
			case "addc", "addconstant", "defc", "defcon", "defconstant"			-> defineConstant(splitInput[1]);
			case "rmf", "rmfun", "removefun", "removefunction"					-> removeFunction(splitInput[1]);
			case "rmv", "rmvar", "removevar", "removevariable"					-> removeVariables(splitInput[1]);
			case "rmc", "rmconstant", "removeconstant"							-> removeConstant(splitInput[1]);
			case "pf", "printfun", "printfunctions"								-> printFunctions();
			case "pv", "vars", "printvars"										-> printVariables();
			case "pc", "printc", "printconstants"								-> printConstants();
			case "clearfun", "clearfunctions"									-> clearFunctions();
			case "clearvars", "clearvariables"									-> clearVariables();
			case "ss", "sset", "sets", "setsetting"								-> setSettings(splitInput[1]);
			case "ps", "settings", "printsettings"								-> printSettings();
			case "int", "integral"												-> integral(splitInput[1]);
			case "help"															-> splitInput.length == 1 ? helpNoInput() : helpWithInput(splitInput[1]);
			default 															-> null; //TODO add "help" and "demo"
		};
		if (ret == null) {
			if (storedFunctions.containsKey(input)) {
				prev = storedFunctions.get(input);
				return prev;
			} else if (Constant.isSpecialConstant(input)) {
				prev = Constant.getSpecialConstant(input);
				return prev;
			} else try {
				 prev = substituteAll(FunctionParser.parseInfix(input));
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
			return ParsingTools.toFunction(prev);

		if (input.chars().filter(ch -> ch == '\"').count() % 2 == 1) // this is a really janky fix TODO fix this by adding quotes-stripper and removing quotes from keywordsplitter
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
			function = function.substituteVariable(ParsingTools.getCharacter(entry.getKey()), entry.getValue());
		return function;
	}

	/**
	 * pd [variable] [function]
	 */
	public static GeneralFunction partialDiff(String input) {
		String[] splitInput = spaces.split(input, 2);
		return parseStored(splitInput[1]).getSimplifiedDerivative(ParsingTools.getCharacter(splitInput[0]));
	}

	/**
	 * pdn [variable] [times] [function]
	 */
	private static GeneralFunction partialDiffNth(String input) {
		String[] splitInput = keywordSplitter.split(input, 3);
		return parseStored(splitInput[2]).getNthDerivative(ParsingTools.getCharacter(splitInput[0]), Integer.parseInt(splitInput[1]));
	}

	/**
	 * eval [function] [var=val]*
	 */
	public static double evaluate(String input) {
		String[] splitInput = spaces.split(input, 2);
		return parseStored(splitInput[0]).evaluate(
				Arrays.stream(keywordSplitter.split(splitInput[1]))
				.map(equals::split)
				.collect(Collectors.toMap(e -> ParsingTools.getCharacter(e[0]), e -> ParsingTools.getConstant(e[1])))
		);
	}

	/**
	 * simp [function]
	 */
	public static GeneralFunction simplify(String input) {
		return parseStored(input).simplify();
	}

	/**
	 * sub [function] [variable] [replacementfunction]
	 */
	public static GeneralFunction substitute(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return parseStored(splitInput[0]).substituteVariable(ParsingTools.getCharacter(splitInput[1]), parseStored(splitInput[2]));
	}

	/**
	 * sol [function] [startrange] [endrange]
	 */
	public static List<Double> solve(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return Solver.getSolutionsRange(parseStored(splitInput[0]), ParsingTools.getConstant(splitInput[1]), ParsingTools.getConstant(splitInput[2]));
	}

	/**
	 * ext ["min(ima)"/"max(ima)"/"anymin(ima)"/"anymax(ima)"/"inflect(ion)"] [function] [startrange] [endrange]
	 */
	public static Object extrema(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return switch (splitInput[0]) {
			case "min", "minima"					-> Extrema.findLocalMinimum(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			case "max", "maxima"					-> Extrema.findLocalMaximum(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			case "anymin", "anyminima"				-> Extrema.findAnyMinima(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			case "anymax", "anymaxima"				-> Extrema.findAnyMaxima(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			case "inflect", "inflection"			-> Extrema.findAnyInflectionPoints(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			default 								-> throw new IllegalStateException("Invalid setting for extrema: " + splitInput[0]);
		};
	}

	/**
	 * tay [function] [terms] [center]
	 */
	public static GeneralFunction taylor(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return TaylorSeries.makeTaylorSeries(parseStored(splitInput[0]), ParsingTools.toInteger(ParsingTools.getConstant(splitInput[1])), ParsingTools.getConstant(splitInput[2]));
	}

	/**
	 * sto [locationstring] [input]
	 */
	public static Object storeFunction(String input) {
		String[] splitInput = spaces.split(input, 2);
		if (!storedFunctions.containsKey(splitInput[0])) // TODO Make Variable.isFunctionVariable or something
			Variable.addFunctionVariable(ParsingTools.getCharacter(splitInput[0]));
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
		Variable.removeFunctionVariable(ParsingTools.getCharacter(input));
		return printVariables();
	}

	/**
	 * var(s) [variablename(s)]
	 */
	public static String addVariables(String input) {
		String[] splitInput = keywordSplitter.split(input);
		for (String var : splitInput)
			Variable.addVariable(ParsingTools.getCharacter(var));
		return printVariables();
	}

	/**
	 * rmvar(s) [variablenames]
	 */
	private static Object removeVariables(String input) {
		String[] splitInput = keywordSplitter.split(input);
		for (String var : splitInput)
			Variable.removeVariable(ParsingTools.getCharacter(var));
		return printVariables();
	}

	/**
	 * defcon [constantstring] [value]
	 */
	private static Object defineConstant(String input) {
		String[] splitInput = spaces.split(input, 2);
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
	private static String printConstants() { //TODO maybe replace all print___ functions with get___ and have them have flexible return types
		return String.valueOf(Constant.specialConstants);
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
		return NumericalIntegration.simpsonsRule(parseStored(splitInput[0]), ParsingTools.getConstant(splitInput[1]), ParsingTools.getConstant(splitInput[2]));
	}

	/**
	 * intne [function] [startvalue] [endvalue]
	 */
	private static double[] integrateNumericError(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return NumericalIntegration.simpsonsRuleWithError(parseStored(splitInput[0]), ParsingTools.getConstant(splitInput[1]), ParsingTools.getConstant(splitInput[2]));
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
		Field[] settings = Settings.class.getDeclaredFields();
		StringBuilder stringBuilder = new StringBuilder();
		for (Field setting : settings) {
			stringBuilder.append(setting.getName());
			stringBuilder.append(" = ");
			try {
				stringBuilder.append(setting.get(null));
			} catch (IllegalAccessException e) {
				stringBuilder.append(e.toString());
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	/**
	 * integral [function] d[variable]
	 */
	private static GeneralFunction integral(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return new Integral(parseStored(splitInput[0]), splitInput[1].charAt(1)).execute();
	}

	private static String helpWithInput(String input) {
		return switch (input) {
			case "demo"																-> "implement";
			case "pd", "pdiff", "partial", "pdifferentiate"             			-> "implement";
			case "pdn", "pdiffn", "partialn", "pdifferentiaten"         			-> "implement";
			case "eval", "evaluate"                                     			-> "implement";
			case "simp", "simplify"                                     			-> "implement";
			case "sub", "substitute"                                    			-> "implement";
			case "sol", "solve"                                         			-> "implement";
			case "ext", "extrema"                                       			-> "implement";
			case "tay", "taylor"                                        			-> "implement";
			case "intn", "intnumeric"                                   			-> "implement";
			case "intne", "intnumericerror"                             			-> "implement";
			case "addf", "sto", "store", "new", "def", "addfunction"    			-> "implement";
			case "addv", "addvar", "addvars"                            			-> "implement";
			case "addc", "addconstant", "defc", "defcon", "defconstant" 			-> "implement";
			case "rmf", "rmfun", "removefun", "removefunction"          			-> "implement";
			case "rmv", "rmvar", "removevar", "removevariable"          			-> "implement";
			case "rmc", "rmconstant", "removeconstant"                  			-> "implement";
			case "pf", "printfun", "printfunctions"                     			-> "implement";
			case "pv", "vars", "printvars"                              			-> "implement";
			case "pc", "printc", "printconstants"                       			-> "implement";
			case "clearfun", "clearfunctions"                           			-> "implement";
			case "clearvars", "clearvariables"                          			-> "implement";
			case "ss", "sset", "sets", "setsetting"                     			-> "implement";
			case "ps", "settings", "printsettings"                      			-> "implement";
			case "int", "integral"                                      			-> "implement";
			case "help"                                           					-> "implement";
			default -> throw new IllegalArgumentException("Invalid keyword: " + input);
		};
	}

	private static String helpNoInput() {
		return """
				demo: runs the demo
				eval, evaluate:  evaluate
				simp, simplify: simplifies
				sub, substitute: substitutespd, pdiff, partial, pdifferentiate: takes the partial derivative
				pdn pdiffn partialn pdifferentiaten: takes the partial derivative n times
				int, integral: integrates a function
				intn, intnumeric: performs numerical integration
				intne, intnumericerror: performs numerical integration with error
				tay, taylor: takes a taylor series
				sol, solve: solves for roots
				ext, extrema: finds extrema
				ss, sset, sets, setsetting: sets a setting
				addf, sto, store, new, def, addfunction: stores a function
				addv, addvar, addvars: adds a variable
				addc, addconstant, defc, defcon, defconstant: defines a constant
				rmf, rmfun, removefun, removefunction: removes a function
				rmv, rmvar, removevar, removevariable: removes a function
				rmc, rmconstant, removeconstant: removes a constant
				pf, printfun, printfunctions: prints all stored functions
				pv, vars, printvars: prints all variables
				pc, printc, printconstants: prints all stored constants
				pv, vars, printvars: prints all variables
				clearfun, clearfunctions: clears functions
				clearvars, clearvariables: clears variables""";
	}
}
