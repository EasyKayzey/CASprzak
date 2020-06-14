package parsing;

import config.Settings;
import config.SettingsParser;
import functions.GeneralFunction;
import functions.special.Constant;
import functions.unitary.transforms.Integral;
import output.OutputFunction;
import tools.MiscTools;
import tools.ParsingTools;
import tools.exceptions.CommandNotFoundException;
import tools.exceptions.SettingNotFoundException;
import tools.exceptions.TransformFailedException;
import tools.singlevariable.Extrema;
import tools.singlevariable.NumericalIntegration;
import tools.singlevariable.Solver;
import tools.singlevariable.TaylorSeries;
import ui.CASDemo;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * {@link KeywordInterface} is the backend for {@link ui.CommandUI}, providing support for parsing user input to commands and functions.
 */
@SuppressWarnings("SpellCheckingInspection")
public class KeywordInterface {
	private static final Pattern keywordSplitter = Pattern.compile("\\s+(?=[^\"]*(\"[^\"]*\"[^\"]*)*$)");
	private static final Pattern spaces = Pattern.compile("\\s+");
	private static final Pattern equals = Pattern.compile("=");
	private static final HashMap<String, GeneralFunction> storedFunctions = new HashMap<>();

	/**
	 * The previous output of {@link #useKeywords(String)}
	 */
	public static Object prev;

	private KeywordInterface(){}

	/**
	 * Takes input as a string in the format {@code "command arguments..."}
	 * @param input a string that contains the command and arguments
	 * @return the Object requested
	 */
	public static Object useKeywords(String input) {
		input = stripQuotes(input);
		input = input.strip(); // Strips whitespace
		if ("_".equals(input))
			return prev;
		if (input != null && input.isEmpty())
			return "No input was given.";
		String[] splitInput = spaces.split(input, 2);
		Object ret = switch (splitInput[0]) {
			case "demo"															-> demo();
			case "pd", "pdiff", "partial", "pdifferentiate"						-> partialDiff(splitInput[1]);
			case "pdn", "pdiffn", "partialn", "pdifferentiaten"					-> partialDiffNth(splitInput[1]);
			case "eval", "evaluate"												-> evaluate(splitInput[1]);
			case "simp", "simplify"												-> simplify(splitInput[1]);
			case "sub", "substitute"											-> substitute(splitInput[1], false);
			case "subs", "substitutesimplify"									-> substitute(splitInput[1], true);
			case "sa", "suball"													-> substituteAllInput(splitInput[1]);
			case "sol", "solve"													-> solve(splitInput[1]);
			case "ext", "extrema"												-> extrema(splitInput[1]);
			case "tay", "taylor"												-> taylor(splitInput[1]);
			case "intn", "intnumeric"											-> integrateNumeric(splitInput[1]);
			case "intne", "intnumericerror"										-> integrateNumericError(splitInput[1]);
			case "def", "deffunction"											-> defineFunction(splitInput[1], false);
			case "defs", "deffunctions", "deffunctionsimplify"					-> defineFunction(splitInput[1], true);
			case "defc", "defcon", "defconstant"								-> defineConstant(splitInput[1]);
			case "rmf", "rmfun", "removefun", "removefunction"					-> removeFunction(splitInput[1]);
			case "rmc", "rmconstant", "removeconstant"							-> removeConstant(splitInput[1]);
			case "pf", "printfun", "printfunction", "printfunctions"			-> splitInput.length == 1 ? getFunctions() : getFunction(splitInput[1]);
			case "pc", "printc", "printconstants"								-> getConstants();
			case "clearfun", "clearfunctions"									-> clearFunctions();
			case "ss", "sset", "sets", "setsetting"								-> setSettings(splitInput[1]);
			case "ps", "settings", "printsettings"								-> printSettings();
			case "int", "integral"												-> integral(splitInput[1]);
			case "debug"														-> debug(splitInput[1]);
			case "help"															-> splitInput.length == 1 ? help() : help(splitInput[1]);
			case "exit", "!"													-> throw new IllegalArgumentException("Exit command should never be passed directly to KeywordInterface. Please report this bug to the developers.");
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
				 prev = FunctionParser.parseSimplified(input);
				 return prev;
			} catch (Exception parserException) {
				prev = parserException;
				throw new CommandNotFoundException(splitInput[0] + " is not a command supported by KeywordInterface, so raw-function parsing was attempted.");
			}
		}
		prev = ret;
		return ret;
	}

	@SuppressWarnings("SameReturnValue")
	private static Object debug(String input) {
		Scanner scanner = new Scanner(System.in);
		Consumer<String> debug = switch (input.toLowerCase()) {
			case "fp", "parse", "parser" -> string -> {
				try {
					System.out.println("PSto: " + parseStored(string));
				} catch (Exception e) {
					e.printStackTrace();
				} try {
					System.out.println("PSim: " + FunctionParser.parseSimplified(string));
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			case "o", "out" -> string -> {
				GeneralFunction parsed = parseStored(string);
				OutputFunction out = parsed.toOutputFunction();
				try {
					System.out.println("FTS: " + parsed);
				} catch (Exception e) {
					e.printStackTrace();
				} try {
					System.out.println("OTS: " + out);
				} catch (Exception e) {
					e.printStackTrace();
				} try {
					System.out.println("OL: " + out.toLatex());
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			default -> throw new SettingNotFoundException(input, "debug");
		};
		while (!"exit".equals(input) && !"!".equals(input))
			//noinspection NestedAssignment
			debug.accept(input = scanner.nextLine());
		return "Done.";
	}

	/**
	 * Parses input using {@link #useKeywords(String)} and {@link #storedFunctions}
	 * @param input input string
	 * @return a {@link GeneralFunction}
	 */
	public static GeneralFunction parseStored(String input) {
		input = stripQuotes(input);

		if ("_".equals(input))
			return ParsingTools.toFunction(prev);

		if (Constant.isSpecialConstant(input))
			return new Constant(input);
		else
			return (GeneralFunction) useKeywords(input);
	}

	/**
	 * Substitutes everything stored in {@link #storedFunctions} into {@code function} in an unspecified order
	 * @param function the function to be substituted into
	 * @return {@code input} with all substitutions
	 */
	public static GeneralFunction substituteAll(GeneralFunction function) {
		//noinspection unchecked
		return function.substituteVariables(
					Map.ofEntries(storedFunctions.entrySet().stream()
							.map(e -> Map.entry(LatexReplacer.encodeAll(e.getKey()), e.getValue()))
							.toArray(Map.Entry[]::new)
					)
		);
	}

	private static String stripQuotes(String input) {
		if (!input.isBlank() && input.charAt(0) == '"' && input.charAt(input.length() - 1) == '"') {
			String stripped = input.substring(1, input.length() - 1);
			if (!stripped.contains("\""))
				return stripped;
		}
		return input;
	}

	private static String demo() {
		CASDemo.reset();
		return CASDemo.runDemo();
	}

	private static GeneralFunction partialDiff(String input) {
		String[] splitInput = spaces.split(input, 2);
		return parseStored(splitInput[1]).getSimplifiedDerivative(LatexReplacer.encodeAll(splitInput[0]));
	}

	private static GeneralFunction partialDiffNth(String input) {
		String[] splitInput = spaces.split(input, 3);
		return parseStored(splitInput[2]).getNthDerivative(LatexReplacer.encodeAll(splitInput[0]), Integer.parseInt(splitInput[1]));
	}

	private static double evaluate(String input) {
		String[] splitInput = spaces.split(input, 2);
		if (splitInput.length == 1)
			return parseStored(splitInput[0]).evaluate(new HashMap<>());
		else
			return parseStored(splitInput[0]).evaluate(
					Arrays.stream(keywordSplitter.split(splitInput[1]))
					.map(equals::split)
					.collect(Collectors.toMap(e -> LatexReplacer.encodeAll(e[0]), e -> ParsingTools.getConstant(e[1])))
			);
	}


	private static GeneralFunction simplify(String input) {
		return parseStored(input).simplify();
	}

	private static GeneralFunction substitute(String input, boolean simplify) {
		String[] splitInput = keywordSplitter.split(input, 2);
		GeneralFunction current = parseStored(splitInput[0]).substituteVariables(Arrays.stream(keywordSplitter.split(splitInput[1])).map(equals::split).collect(Collectors.toMap(e -> e[0], e -> parseStored(e[1]))));
		if (simplify)
			return current.simplify();
		else
			return MiscTools.minimalSimplify(current);
	}

	private static GeneralFunction substituteAllInput(String input) {
		return substituteAll(parseStored(input));
	}

	private static List<Double> solve(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return Solver.getSolutionsRange(parseStored(splitInput[0]), ParsingTools.getConstant(splitInput[1]), ParsingTools.getConstant(splitInput[2]));
	}

	private static Object extrema(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return switch (splitInput[0]) {
			case "min", "minima"					-> Extrema.findLocalMinimum(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			case "max", "maxima"					-> Extrema.findLocalMaximum(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			case "anymin", "anyminima"				-> Extrema.findLocalMinima(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			case "anymax", "anymaxima"				-> Extrema.findLocalMaxima(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			case "inflect", "inflection"			-> Extrema.findInflectionPoints(parseStored(splitInput[1]), ParsingTools.getConstant(splitInput[2]), ParsingTools.getConstant(splitInput[3]));
			default 								-> throw new SettingNotFoundException(splitInput[0], "extrema");
		};
	}

	private static GeneralFunction taylor(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return TaylorSeries.makeTaylorSeries(parseStored(splitInput[0]), ParsingTools.toInteger(ParsingTools.getConstant(splitInput[1])), ParsingTools.getConstant(splitInput[2]));
	}

	private static Object defineFunction(String input, boolean simplify) {
		String[] splitInput = spaces.split(input, 2);
		//A try-catch used to be here and was removed
		GeneralFunction toPut = (GeneralFunction) KeywordInterface.useKeywords(splitInput[1]);
		if (simplify)
			toPut = toPut.simplify();
		storedFunctions.put(splitInput[0], toPut);
		return toPut;
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
	private static Map<String, GeneralFunction> getFunctions() {
		return storedFunctions;
	}

	private static GeneralFunction getFunction(String function) {
		return storedFunctions.get(function);
	}

	@SuppressWarnings("SameReturnValue")
	private static Map<String, Double> getConstants() {
		return Constant.specialConstants;
	}

	@SuppressWarnings("SameReturnValue")
	private static Map<String, GeneralFunction> clearFunctions() {
		storedFunctions.clear();
		return storedFunctions;
	}

	private static double integrateNumeric(String input) {
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
		if (splitInput[1].charAt(0) != 'd')
			throw new IllegalArgumentException(splitInput[1] + " is not a differential (does not start with d).");
		Integral integral = new Integral(parseStored(splitInput[0]), splitInput[1].substring(1));
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
					"eval [function] ([variable]=[value]])*";
			case "simp", "simplify"                                     			-> "Simplifies [function].\n" +
					"simp [function]";
			case "sub", "substitute"                                    			-> "Substitutes [replacementfunction] into every instance of [variable] in [function].\n" +
					"sub [function] ([variable]=[replacementfunction])+";
			case "subs", "substitutesimplify"                                    	-> "Substitutes [replacementfunction] into every instance of [variable] in [function] and then simplifies.\n" +
					"subs [function] ([variable]=[replacementfunction])+";
			case "sa", "suball"														-> "Substites every pre-defined function into [function] in accordance with its name.\n" +
					"sa [function]";
			case "sol", "solve"                                         			-> "Solves [function] in one variable on a range.\n" +
					"sol [function] [startrange] [endrange]";
			case "ext", "extrema"                                       			-> "Finds the specified extrema of [function] on a range. 'min'/'max' return one coordinate, and 'anymin'/'anymax'/'inflect' return a list of coordinates.\n" +
					"ext ['min(ima)'/'max(ima)'/'anymin(ima)'/'anymax(ima)'/'inflect(ion)'] [function] [startrange] [endrange]";
			case "tay", "taylor"                                        			-> "Finds the [degree]-degree taylor series of [function] around [center].\n" +
					"tay [function] [degree] [center]";
			case "intn", "intnumeric"                                   			-> "Integrates [function] numerically on a range.\n" +
					"intn [function] [startvalue] [endvalue]";
			case "intne", "intnumericerror"                             			-> "Integrates [function] numerically on a range, returning an array whose first value is the result, and whose second value is the maximum error of the approximation.\n" +
					"intne [function] [startvalue] [endvalue]";
			case "def", "deffunction"    											-> "Defines a function with name [name] to be [value]. [name] can be a LaTeX-escaped Greek letter.\n" +
					"def [name] [value]";
			case "defs", "deffunctions", "deffunctionsimplify"    					-> "Defines a simplified function with name [name] to be [value]. [name] can be a LaTeX-escaped Greek letter.\n" +
					"defs [name] [value]";
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
			case "help"				                                      			-> "Gives more information about a command. [argument] denotes a necessary argument, (argument) denotes an optional argument, and (argument)* denotes zero or more instances of argument.\n" +
					"help (command)";
			case "exit", "!"														-> "Exits the program.\n" +
					"exit";
			default -> throw new IllegalArgumentException("Invalid command: " + input);
		};
	}

	@SuppressWarnings("SameReturnValue")
	private static String help() {
		return """
				demo:                                                  runs the demo
				eval, evaluate:                                        evaluates
				simp, simplify:                                        simplifies
				sub, substitute:                                       substitutes
				subs, substitutesimplify                               substitutes and simplifies
				sa, suball:                                            substitutes all functions variables
				pd, pdiff, partial, pdifferentiate:                    takes the partial derivative
				pdn pdiffn partialn pdifferentiaten:                   takes the partial derivative n times
				int, integral:                                         integrates a function
				intn, intnumeric:                                      performs numerical integration
				intne, intnumericerror:                                performs numerical integration with error
				tay, taylor:                                           takes a taylor series
				sol, solve:                                            solves for roots
				ext, extrema:                                          finds extrema
				def, deffunction:                                      defines a function
				defs, deffunctions, deffeunctionsimplify               defines a simplified function
				defcon, defconstant:                                   defines a constant
				rmf, rmfun, removefun, removefunction:                 removes a function
				rmc, rmconstant, removeconstant:                       removes a constant
				pf, printfun, printfunctions:                          prints all stored functions
				pc, printc, printconstants:                            prints all stored constants
				ss, sets, setsetting:                                  sets a setting
				ps, prints, printsettings:                             prints all settings
				clearfun, clearfunctions:                              clears functions
				exit, !:                                               exits the interface
				Execute `help [command]` to get more info on that command, and `help help` for more info on the help menu.
				""";
	}
}
