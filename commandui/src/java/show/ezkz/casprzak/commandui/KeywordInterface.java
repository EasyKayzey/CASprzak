package show.ezkz.casprzak.commandui;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import show.ezkz.casprzak.core.functions.unitary.transforms.Integral;
import show.ezkz.casprzak.core.output.OutputFunction;
import show.ezkz.casprzak.core.tools.MiscTools;
import show.ezkz.casprzak.core.tools.ParsingTools;
import show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings;
import show.ezkz.casprzak.core.tools.exceptions.*;
import show.ezkz.casprzak.core.tools.singlevariable.Extrema;
import show.ezkz.casprzak.core.tools.singlevariable.NumericalIntegration;
import show.ezkz.casprzak.core.tools.singlevariable.Solver;
import show.ezkz.casprzak.core.tools.singlevariable.TaylorSeries;
import show.ezkz.casprzak.parsing.FunctionParser;
import show.ezkz.casprzak.parsing.LatexReplacer;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings.minimal;
import static show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings.user;

/**
 * {@link KeywordInterface} is the backend for {@link CommandUI}, providing support for parsing user input to commands and functions.
 */
@SuppressWarnings("SpellCheckingInspection")
public class KeywordInterface {
	private static final String version = "v0.3.0-DEV";
	private static final Pattern keywordSplitter = Pattern.compile("\\s+(?=[^\"]*(\"[^\"]*\"[^\"]*)*$)");
	private static final Pattern spaces = Pattern.compile("\\s+");
	private static final Pattern equals = Pattern.compile("=");
	private static final HashMap<String, GeneralFunction> storedFunctions = new HashMap<>();
	private static RuntimeException prevException = null;

	/**
	 * The previous output of {@link #useKeywords(String)}
	 */
	public static Object prev;

	private KeywordInterface(){}

	/**
	 * Takes input as a string in the format {@code "command arguments..."}
	 * @param input a string that contains the command and arguments
	 * @return the Object requested
	 * @throws UserExitException if the input prompts an exit from the program
	 */
	public static Object useKeywords(String input) throws UserExitException {
		input = stripQuotes(input);
		input = input.strip(); // Strips whitespace
		if ("_".equals(input))
			return prev;
		if (input != null && input.isEmpty())
			return "No input was given.";
		String[] splitInput = spaces.split(input, 2);
		Object ret;
		try {
			ret = switch (splitInput[0]) {
				case "demo" -> demo();
				case "pd", "pdiff", "partial", "pdifferentiate" 									-> partialDiff(splitInput[1]);
				case "pdn", "pdiffn", "partialn", "pdifferentiaten" 								-> partialDiffNth(splitInput[1]);
				case "eval", "evaluate" 															-> evaluate(splitInput[1]);
				case "simp", "simplify" 															-> simplify(splitInput[1]);
				case "sub", "substitute" 															-> substitute(splitInput[1], false);
				case "subs", "substitutesimplify" 													-> substitute(splitInput[1], true);
				case "sa", "suball" 																-> substituteAllInput(splitInput[1]);
				case "sol", "solve" 																-> solve(splitInput[1]);
				case "ext", "extrema" 																-> extrema(splitInput[1]);
				case "tay", "taylor" 																-> taylor(splitInput[1]);
				case "intn", "intnumeric" 															-> integrateNumeric(splitInput[1]);
				case "intne", "intnumericerror" 													-> integrateNumericError(splitInput[1]);
				case "def", "deffunction" 															-> defineFunction(splitInput[1], false);
				case "defs", "deffunctions", "deffunctionsimplify" 									-> defineFunction(splitInput[1], true);
				case "defc", "defcon", "defconstant" 												-> defineConstant(splitInput[1]);
				case "rmf", "rmfun", "removefun", "removefunction" 									-> removeFunction(splitInput[1]);
				case "rmc", "rmconstant", "removeconstant" 											-> removeConstant(splitInput[1]);
				case "pf", "printfun", "printfunction", "printfunctions" 							-> splitInput.length == 1 ? getFunctions() : getFunction(splitInput[1]);
				case "pc", "printc", "printconstants" 												-> getConstants();
				case "clearfun", "clearfunctions" 													-> clearFunctions();
				case "ss", "sset", "sets", "setsetting" 											-> setSettings(splitInput[1]);
				case "ps", "settings", "printsettings" 												-> printSettings();
				case "int", "integral" 																-> integral(splitInput[1]);
				case "ai", "index", "arrayindex" 													-> arrayIndex(splitInput[1]);
				case "debug" 																		-> debug(splitInput[1]);
				case "version", "v" 																		-> version;
				case "reset" 																		-> reset();
				case "err", "error" 																-> printError();
				case "help" 																		-> splitInput.length == 1 ? help() : help(splitInput[1]);
				case "exit", "!" 																	-> throw new UserExitException();
				default 																			-> null;
			};
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new MismatchedCommandArgumentsException("Command " + splitInput[0] + " expected an argument, but no argument was given.");
		}
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
				prevException = new CommandNotFoundException(splitInput[0] + " is not a command supported by KeywordInterface, and raw-function parsing failed to interpret the input.", parserException);
				throw prevException;
			}
		} else if (ret instanceof Double) {
			ret = new Constant((Double) ret);
		}
		prev = ret;
		return ret;
	}

	/**
	 * Runs {@link #useKeywords(String)} assuming that the input is not an exit argument, removing the checked exception
	 * @param input the input for {@link #useKeywords(String)}
	 * @return the output of {@link #useKeywords(String)}
	 * @throws IllegalArgumentException if the input passed throws an {@link UserExitException}
	 */
	public static Object safeKeywords(String input) {
		try {
			return useKeywords(input);
		} catch (UserExitException ignored) {
			throw new IllegalArgumentException("Exit argument was illegally passed to safeKeywords.");
		}
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
		input = scanner.nextLine();
		while (!"exit".equals(input) && !"!".equals(input)) {
			debug.accept(input);
			input = scanner.nextLine();
		}
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
			return FunctionParser.toFunction(prev);

		if (Constant.isSpecialConstant(input))
			return new Constant(input);
		else try {
			return (GeneralFunction) useKeywords(input);
		} catch (UserExitException ignored) {
			throw new IllegalArgumentException("The input passed to parseStored was interpreted as a call to exit, but parseStored cannot induce a program exit.");
		}
	}

	/**
	 * Substitutes everything stored in {@link #storedFunctions} into {@code function} in an unspecified order
	 * @param function the function to be substituted into
	 * @return {@code input} with all substitutions
	 */
	public static GeneralFunction substituteAll(GeneralFunction function) {
		//noinspection unchecked
		return function.substituteVariables(
					Map.ofEntries(
							storedFunctions.entrySet().stream()
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
			else
				throw new IllegalArgumentException("Nested quotes are not supported.");
		}
		return input;
	}

	private static double toDouble(Object object) {
		if (object instanceof Constant constant)
			return constant.constant;
		else
			return (double) object;
	}

	private static String demo() {
		CASDemo.reset();
		return CASDemo.runDemo();
	}

	private static GeneralFunction partialDiff(String input) {
		String[] splitInput = spaces.split(input, 2);
		if (splitInput.length != 2)
			throw new MismatchedCommandArgumentsException("2", splitInput.length);
		return parseStored(splitInput[1]).getSimplifiedDerivative(LatexReplacer.encodeAll(splitInput[0]));
	}

	private static GeneralFunction partialDiffNth(String input) {
		String[] splitInput = spaces.split(input, 3);
		if (splitInput.length != 3)
			throw new MismatchedCommandArgumentsException("3", splitInput.length);
		return parseStored(splitInput[2]).getNthDerivative(LatexReplacer.encodeAll(splitInput[0]), Integer.parseInt(splitInput[1]));
	}

	private static double evaluate(String input) {
		String[] splitInput = keywordSplitter.split(input, 2);
		if (splitInput.length == 0)
			throw new MismatchedCommandArgumentsException("1 or more", splitInput.length);
		if (splitInput.length == 1)
			return parseStored(splitInput[0]).evaluate();
		else {
			Object lastPrev = prev;
			return parseStored(splitInput[0]).evaluate(
					Arrays.stream(keywordSplitter.split(splitInput[1]))
							.map(equals::split)
							.collect(Collectors.toMap(
									e -> LatexReplacer.encodeAll(e[0]),
									e -> "_".equals(e[1]) ? toDouble(lastPrev) : FunctionParser.getConstant(e[1]))
							)
			);
		}
	}


	private static GeneralFunction simplify(String input) {
		return parseStored(input).simplify(user);
	}

	private static GeneralFunction substitute(String input, boolean simplify) {
		String[] splitInput = keywordSplitter.split(input, 2);
		GeneralFunction current = parseStored(splitInput[0]).substituteVariables(
				Arrays.stream(keywordSplitter.split(splitInput[1]))
						.map(equals::split)
						.collect(Collectors.toMap(e -> e[0], e -> parseStored(e[1])))
		);
		if (simplify)
			return current.simplify(user);
		else
			return current.simplify(minimal);
	}

	private static GeneralFunction substituteAllInput(String input) {
		return substituteAll(parseStored(input));
	}

	private static List<Double> solve(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return Solver.getSolutionsRange(parseStored(splitInput[0]), FunctionParser.getConstant(splitInput[1]), FunctionParser.getConstant(splitInput[2]));
	}

	private static Object extrema(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return switch (splitInput[0]) {
			case "min", "minima"					-> Extrema.findLocalMinimum(parseStored(splitInput[1]), FunctionParser.getConstant(splitInput[2]), FunctionParser.getConstant(splitInput[3]));
			case "max", "maxima"					-> Extrema.findLocalMaximum(parseStored(splitInput[1]), FunctionParser.getConstant(splitInput[2]), FunctionParser.getConstant(splitInput[3]));
			case "anymin", "anyminima"				-> Extrema.findLocalMinima(parseStored(splitInput[1]), FunctionParser.getConstant(splitInput[2]), FunctionParser.getConstant(splitInput[3]));
			case "anymax", "anymaxima"				-> Extrema.findLocalMaxima(parseStored(splitInput[1]), FunctionParser.getConstant(splitInput[2]), FunctionParser.getConstant(splitInput[3]));
			case "inflect", "inflection"			-> Extrema.findInflectionPoints(parseStored(splitInput[1]), FunctionParser.getConstant(splitInput[2]), FunctionParser.getConstant(splitInput[3]));
			default 								-> throw new SettingNotFoundException(splitInput[0], "extrema");
		};
	}

	private static GeneralFunction taylor(String input) {
		String[] splitInput = keywordSplitter.split(input);
		return TaylorSeries.makeTaylorSeries(parseStored(splitInput[0]), ParsingTools.toInteger(FunctionParser.getConstant(splitInput[1])), FunctionParser.getConstant(splitInput[2]));
	}

	private static Object defineFunction(String input, boolean simplify) {
		String[] splitInput = spaces.split(input, 2);
		if (splitInput.length != 2)
			throw new MismatchedCommandArgumentsException("2", splitInput.length);
		// A try-catch used to be here and was removed
		GeneralFunction toPut = parseStored(splitInput[1]);
		if (Settings.enforcePatternMatchingNames && !ParsingTools.validNames.matcher(splitInput[0]).matches())
			throw new IllegalNameException(splitInput[0]);
		if (simplify)
			toPut = toPut.simplify(user);
		storedFunctions.put(splitInput[0], toPut);
		return toPut;
	}


	private static Object defineConstant(String input) {
		String[] splitInput = spaces.split(input, 2);
		if (splitInput.length != 2)
			throw new MismatchedCommandArgumentsException("2", splitInput.length);
		try {
			return Constant.addSpecialConstant(LatexReplacer.encodeAll(splitInput[0]), ((GeneralFunction) KeywordInterface.useKeywords(splitInput[1])).evaluate());
		} catch (Exception e) {
			return Constant.addSpecialConstant(LatexReplacer.encodeAll(splitInput[0]), parseStored(splitInput[1]).evaluate());
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
		if (splitInput.length != 3)
			throw new MismatchedCommandArgumentsException("3", splitInput.length);
		return NumericalIntegration.simpsonsRule(parseStored(splitInput[0]), FunctionParser.getConstant(splitInput[1]), FunctionParser.getConstant(splitInput[2]));
	}

	private static double[] integrateNumericError(String input) {
		String[] splitInput = keywordSplitter.split(input);
		if (splitInput.length != 3)
			throw new MismatchedCommandArgumentsException("3", splitInput.length);
		return NumericalIntegration.simpsonsRuleWithError(parseStored(splitInput[0]), FunctionParser.getConstant(splitInput[1]), FunctionParser.getConstant(splitInput[2]));
	}

	private static String setSettings(String input) {
		String[] splitInput = keywordSplitter.split(input);
		if (splitInput.length != 2)
			throw new MismatchedCommandArgumentsException("2", splitInput.length);
		Settings.parseSingleSetting(splitInput[0], splitInput[1]);
		return splitInput[0] + " = " + splitInput[1];
	}

	private static String printSettings() {
		Field[] settings = Settings.class.getDeclaredFields();
		StringBuilder stringBuilder = new StringBuilder(30 * settings.length);
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
		if (splitInput.length != 2)
			throw new MismatchedCommandArgumentsException("2", splitInput.length);
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

	private static Double arrayIndex(String input) {
		if (prev instanceof List<?> list) {
			return (Double) list.get(Integer.parseInt(input));
		} else
			throw new IllegalArgumentException("The previous output was not a list of numbers.");
	}

	@SuppressWarnings("SameReturnValue")
	private static String reset() {
		clearFunctions();
		Constant.resetConstants();
		return "Reset done.";
	}

	private static String printError() {
		if (prevException == null)
			return "No error was found.";
		else
			return prevException.getCause().toString();
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
					"pf (name)";
			case "pc", "printc", "printconstants"                       			-> "Prints the list of constants.\n" +
					"pc";
			case "clearfun", "clearfunctions"                           			-> "Clears the list of functions.\n" +
					"clearfun";
			case "ss", "sets", "setsetting"                    			 			-> "Sets a setting.\n" +
					"ss [setting] [value]";
			case "ps", "prints", "printsettings"                    	  			-> "Prints all settings.\n" +
					"ps";
			case "int", "integral"                                      			-> "Symbolically integrates [function] with respect to [variable].\n" +
					"int [function] d[variable]";
			case "ai", "index", "arrayindex"										-> "Assuming that the output of the previous command was a list, returns the value at index [index] in the list.\n" +
					"ai [index]";
			case "version", "v"														-> "Prints the version of CASprzak which is currently being run. \n" +
					"version";
			case "reset"															-> "Resets stored functions and constants to their initial state. \n" +
					"reset";
			case "err", "error"														-> "Prints the details of the previously encountered error. \n" +
					"err";
			case "help"				                                      			-> "Gives more information about a command. [argument] denotes a necessary argument, (argument) denotes an optional argument, and (argument)* denotes zero or more instances of argument.\n" +
					"help (command)";
			case "exit", "!"														-> "Exits the program.\n" +
					"exit";
			default -> throw new IllegalArgumentException("Invalid command: " + input + "");
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
				ai, index, arrayindex                                  returns a value from an array
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
				version, v:                                            prints version
				reset:                                                 resets stored functions and constants
				err, error:                                            prints the details of the previous error
				exit, !:                                               exits the interface
				Execute `help [command]` to get more info on that command, and `help help` for more info on the help menu.
				""";
	}
}
