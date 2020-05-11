package parsing;

import config.Settings;
import config.SettingsParser;
import functions.GeneralFunction;
import functions.special.Constant;
import functions.unitary.transforms.Integral;
import tools.ParsingTools;
import tools.exceptions.TransformFailedException;
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
	public static final Pattern keywordSplitter = Pattern.compile("\\s+(?=[^\"]*(\"[^\"]*\"[^\"]*)*$)");
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
		input = stripQuotes(input);
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
			case "sa", "suball"													-> substituteAllInput(splitInput[1]);
			case "sol", "solve"													-> solve(splitInput[1]);
			case "ext", "extrema"												-> extrema(splitInput[1]);
			case "tay", "taylor"												-> taylor(splitInput[1]);
			case "intn", "intnumeric"											-> integrateNumeric(splitInput[1]);
			case "intne", "intnumericerror"										-> integrateNumericError(splitInput[1]);
			case "def", "deffunction"											-> defineFunction(splitInput[1]);
			case "defc", "defcon", "defconstant"								-> defineConstant(splitInput[1]);
			case "rmf", "rmfun", "removefun", "removefunction"					-> removeFunction(splitInput[1]);
			case "rmc", "rmconstant", "removeconstant"							-> removeConstant(splitInput[1]);
			case "pf", "printfun", "printfunction", "printfunctions"			-> splitInput.length == 1 ? getFunctions() : getFunction(splitInput[1]);
			case "pc", "printc", "printconstants"								-> getConstants();
			case "clearfun", "clearfunctions"									-> clearFunctions();
			case "ss", "sset", "sets", "setsetting"								-> setSettings(splitInput[1]);
			case "ps", "settings", "printsettings"								-> printSettings();
			case "int", "integral"												-> integral(splitInput[1]);
			case "help"															-> splitInput.length == 1 ? help() : help(splitInput[1]);
			default 															-> null;
		};
		if (ret == null) {
			if (storedFunctions.containsKey(input)) {
				prev = storedFunctions.get(input);
				return prev;
			} else if (Constant.isSpecialConstant(input)) {
				prev = Constant.getSpecialConstant(input);
				return prev;
			} else try {
				 prev = FunctionParser.parseInfix(input);
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

		if (Constant.isSpecialConstant(input))
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

	private static String stripQuotes(String input) {
		if (input.charAt(0) == '"' && input.charAt(input.length() - 1) == '"') {
			String stripped = input.substring(1, input.length() - 1);
			if (!stripped.contains("\""))
				return stripped;
		}
		return input;
	}

	public static GeneralFunction partialDiff(String input) {
		String[] splitInput = spaces.split(input, 2);
		return parseStored(splitInput[1]).getSimplifiedDerivative(ParsingTools.getCharacter(splitInput[0]));
	}

	private static GeneralFunction partialDiffNth(String input) {
		String[] splitInput = spaces.split(input, 3);
		return parseStored(splitInput[2]).getNthDerivative(ParsingTools.getCharacter(splitInput[0]), Integer.parseInt(splitInput[1]));
	}

	public static double evaluate(String input) {
		String[] splitInput = spaces.split(input, 2);
		return parseStored(splitInput[0]).evaluate(
				Arrays.stream(keywordSplitter.split(splitInput[1]))
				.map(equals::split)
				.collect(Collectors.toMap(e -> ParsingTools.getCharacter(e[0]), e -> ParsingTools.getConstant(e[1])))
		);
	}

	public static GeneralFunction simplify(String input) {
		return parseStored(input).simplify();
	}

	public static GeneralFunction substitute(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return parseStored(splitInput[0]).substituteVariable(ParsingTools.getCharacter(splitInput[1]), parseStored(splitInput[2]));
	}

	public static GeneralFunction substituteAllInput(String input) {
		return substituteAll(parseStored(input));
	}

	public static List<Double> solve(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return Solver.getSolutionsRange(parseStored(splitInput[0]), ParsingTools.getConstant(splitInput[1]), ParsingTools.getConstant(splitInput[2]));
	}

	public static Object extrema(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return switch (splitInput[0]) {
			case "min", "minima"					-> Extrema.findLocalMinimum(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			case "max", "maxima"					-> Extrema.findLocalMaximum(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			case "anymin", "anyminima"				-> Extrema.findLocalMinima(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			case "anymax", "anymaxima"				-> Extrema.findLocalMaxima(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			case "inflect", "inflection"			-> Extrema.findInflectionPoints(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			default 								-> throw new IllegalStateException("Invalid setting for extrema: " + splitInput[0]);
		};
	}

	public static GeneralFunction taylor(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return TaylorSeries.makeTaylorSeries(parseStored(splitInput[0]), ParsingTools.toInteger(ParsingTools.getConstant(splitInput[1])), ParsingTools.getConstant(splitInput[2]));
	}

	public static Object defineFunction(String input) {
		String[] splitInput = spaces.split(input, 2);
		try {
			storedFunctions.put(splitInput[0], (GeneralFunction) KeywordInterface.useKeywords(splitInput[1]));
		} catch (RuntimeException e) {
			storedFunctions.put(splitInput[0], parseStored(splitInput[1]));
		}
		return storedFunctions.get(splitInput[0]);
	}

	private static Object defineConstant(String input) {
		String[] splitInput = spaces.split(input, 2);
		try {
			return Constant.addSpecialConstant(splitInput[0], ((GeneralFunction) KeywordInterface.useKeywords(splitInput[1])).evaluate(null));
		} catch (Exception e) {
			return Constant.addSpecialConstant(splitInput[0], parseStored(splitInput[1]).evaluate(null));
		}
	}

	private static GeneralFunction removeFunction(String input) {
		return storedFunctions.remove(input);
	}

	private static double removeConstant(String input) {
		return Constant.removeSpecialConstant(input);
	}

	@SuppressWarnings("SameReturnValue")
	public static Map<String, GeneralFunction> getFunctions() {
		return storedFunctions;
	}

	public static GeneralFunction getFunction(String function) {
		return storedFunctions.get(function);
	}

	@SuppressWarnings("SameReturnValue")
	private static Map<String, Double> getConstants() {
		return Constant.specialConstants;
	}

	@SuppressWarnings("SameReturnValue")
	public static Map<String, GeneralFunction> clearFunctions() {
		storedFunctions.clear();
		return storedFunctions;
	}

	public static double integrateNumeric(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return NumericalIntegration.simpsonsRule(parseStored(splitInput[0]), ParsingTools.getConstant(splitInput[1]), ParsingTools.getConstant(splitInput[2]));
	}

	private static double[] integrateNumericError(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return NumericalIntegration.simpsonsRuleWithError(parseStored(splitInput[0]), ParsingTools.getConstant(splitInput[1]), ParsingTools.getConstant(splitInput[2]));
	}

	private static String setSettings(String input) {
		String[] splitInput = keywordSplitter.split(input);
		SettingsParser.parseSingleSetting(splitInput[0], splitInput[1]);
		return splitInput[0] + " = " + splitInput[1];
	}

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

	private static GeneralFunction integral(String input) {
		String[] splitInput = keywordSplitter.split(input);
		Integral integral = new Integral(parseStored(splitInput[0]), splitInput[1].charAt(1));
		try {
			return integral.execute();
		} catch (TransformFailedException e) {
			System.out.println("Integration failed: " + e.toString());
			return integral;
		}
	}

	private static String help(String input) {
		return switch (input) {
			case "demo"																-> "Runs the demo.\n" +
					"demo";
			case "pd", "pdiff", "partial", "pdifferentiate"             			-> "Returns the partial derivative of [function] with respect to [variable].\n" +
					"pd [variable] [function]";
			case "pdn", "pdiffn", "partialn", "pdifferentiaten"         			-> "Executes 'pd' [times] times.\n" +
					"pdn [variable] [times] [function]";
			case "eval", "evaluate"                                     			-> "Evaluates [function] at the values specified.\n" +
					"eval [function] [var=val]*";
			case "simp", "simplify"                                     			-> "Simplifies [function].\n" +
					"simp [function]";
			case "sub", "substitute"                                    			-> "Substitutes [replacementfunction] into every instance of [variable] in [function].\n" +
					"sub [function] [variable] [replacementfunction]";
			case "sa", "suball"														-> "Substites every pre-defined function into [function] in accordance with its name.\n" +
					"sa [function]";
			case "sol", "solve"                                         			-> "Solves [function] in one variable on a range.\n" +
					"sol [function] [startrange] [endrange]";
			case "ext", "extrema"                                       			-> "Finds the specified extrema of [function] on a range. 'min'/'max' return one coordinate, and 'anymin'/'anymax'/'inflect' return a list of coordinates.\n" +
					"ext ['min(ima)'/'max(ima)'/'anymin(ima)'/'anymax(ima)'/'inflect(ion)'] [function] [startrange] [endrange]";
			case "tay", "taylor"                                        			-> "Find the [degree]-degree taylor series of [function] around [center].\n" +
					"tay [function] [degree] [center]";
			case "intn", "intnumeric"                                   			-> "Integrates [function] numerically on a range.\n" +
					"intn [function] [startvalue] [endvalue]";
			case "intne", "intnumericerror"                             			-> "Integrates [function] numerically on a range, returning an array whose first value is the result, and whose second value is the maximum error of the approximation.\n" +
					"intne [function] [startvalue] [endvalue]";
			case "def", "deffunction"    											-> "Defines a function with name [name] to be [value]. [name] can be a LaTeX-escaped Greek letter.\n" +
					"def [name] [value]";
			case "defc", "defcon", "defconstant" 									-> "Defines a constant with name [name] to be [value]. [name] can be a LaTeX-escaped Greek letter.\n" +
					"defc [name] [value]";
			case "rmf", "rmfun", "removefun", "removefunction"          			-> "Removes a defined function.\n" +
					"rmf [name]";
			case "rmc", "rmconstant", "removeconstant"                  			-> "Removes a defined constant.\n" +
					"rmc [name]";
			case "pf", "printfun", "printfunctions"                     			-> "Prints the list of functions, or the contents of one function.\n" +
					"printfun (name)";
			case "pc", "printc", "printconstants"                       			-> "Prints the list of constants.\n" +
					"printconstants";
			case "clearfun", "clearfunctions"                           			-> "Clears the list of functions.\n" +
					"clearfun";
			case "ss", "sets", "setsetting"                    			 			-> "Sets a setting.\n" +
					"setsetting [setting] [value]";
			case "ps", "prints", "printsettings"                    	  			-> "Prints all settings.\n" +
					"printsettings";
			case "int", "integral"                                      			-> "Symbolically integrates [function] with respect to [variable].\n" +
					"integral [function] d[variable]";
			default -> throw new IllegalArgumentException("Invalid keyword: " + input);
		};
	}

	@SuppressWarnings("SameReturnValue")
	private static String help() {
		return """
				demo:                                              runs the demo
                eval, evaluate:                                    evaluates
				simp, simplify:                                    simplifies
				sub, substitute: 								   substitutes
				sa, suball:										   substitutes all functions variables
				pd, pdiff, partial, pdifferentiate:				   takes the partial derivative
				pdn pdiffn partialn pdifferentiaten:               takes the partial derivative n times
				int, integral:                                     integrates a function
				intn, intnumeric:                                  performs numerical integration
				intne, intnumericerror:                            performs numerical integration with error
				tay, taylor:                                       takes a taylor series
				sol, solve:                                        solves for roots
				ext, extrema:                                      finds extrema
				def, deffunction:                                  defines a function
				defcon, defconstant:                               defines a constant
				rmf, rmfun, removefun, removefunction:             removes a function
				rmc, rmconstant, removeconstant:                   removes a constant
				pf, printfun, printfunctions:                      prints all stored functions
				pc, printc, printconstants:                        prints all stored constants
				ss, sets, setsetting:                        	   sets a setting
				ps, prints, printsettings:						   prints all settings
				clearfun, clearfunctions:                          clears functions
				""";
	}
}
