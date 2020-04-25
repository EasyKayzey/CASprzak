package parsing;

import functions.Function;
import functions.special.Variable;
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
	public static HashMap<String, Function> storedFunctions = new HashMap<>();
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
			case "eval", "evaluate" -> evaluate(splitInput[1]);
			case "simp", "simplify" -> simplify(splitInput[1]);
			case "sub", "substitute" -> substitute(splitInput[1]);
			case "sol", "solve" -> solve(splitInput[1]);
			case "ext", "extrema" -> extrema(splitInput[1]);
			case "tay", "taylor" -> taylor(splitInput[1]);
			case "sto", "store", "new", "def" -> store(splitInput[1]);
			case "addvar", "addvars" -> addvars(splitInput[1]);
			case "vars", "printvars" -> printvars();
			case "clearvars" -> clearvars();
			case "printfun", "printfunctions" -> printfun();
			case "clearfun", "clearfunctions" -> clearfun();
			case "intn", "intnumeric" -> integrateNumeric(splitInput[1]);
			case "intne", "intnumericerror" -> integrateNumericError(splitInput[1]);
			default -> null;
		};
		if (ret == null) {
			if (storedFunctions.containsKey(input)) {
				prev = storedFunctions.get(input);
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
		else
			return (Function) useKeywords(input);
	}

	public static Function substituteAll(Function function) {
		for (Map.Entry<String, Function>  entry : storedFunctions.entrySet())
			function = function.substitute(Variable.getVarID(entry.getKey().charAt(0)), entry.getValue());
		return function;
	}


	/**
	 * pd [variable] [function]
	 */
	public static Function partialDiff(String input) {
		String[] splitInput = keywordSplitter.split(input, 2);
		return parseStored(splitInput[1]).getSimplifiedDerivative(Variable.getVarID(splitInput[0].charAt(0)));
	}

	/**
	 * eval [function] [values]
	 */
	public static double evaluate(String input) {
		String[] splitInput = keywordSplitter.split(input, 2);
		double[] values = Arrays.stream(keywordSplitter.split(splitInput[1])).mapToDouble(ConstantEvaluator::getConstant).toArray();
		return parseStored(splitInput[0]).evaluate(values);
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
			throw new IllegalArgumentException("Variables are one character, so " + splitInput[1] + " is not valid.");
		return parseStored(splitInput[0]).substitute(Variable.getVarID(splitInput[1].charAt(0)), parseStored(splitInput[2]));
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
			case "min", "minima" -> Extrema.findLocalMinima(parseStored(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]), ConstantEvaluator.getConstant(splitInput[3]));
			case "max", "maxima" -> Extrema.findLocalMaxima(parseStored(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]), ConstantEvaluator.getConstant(splitInput[3]));
			case "anymin", "anyminima" -> Extrema.findAnyMinima(parseStored(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]), ConstantEvaluator.getConstant(splitInput[3]));
			case "anymax", "anymaxima" -> Extrema.findAnyMaxima(parseStored(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]), ConstantEvaluator.getConstant(splitInput[3]));
			case "inflect", "inflection" -> Extrema.findAnyInflectionPoints(parseStored(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]), ConstantEvaluator.getConstant(splitInput[3]));
			default -> throw new IllegalArgumentException("Invalid setting for extrema:" + splitInput[0]);
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
	public static Object store(String input) {
		String[] splitInput = keywordSplitter.split(input, 2);
		if (splitInput[0].length() != 1)
			throw new IllegalArgumentException("Functions should be one character.");
		if (!storedFunctions.containsKey(splitInput[0]))
			Variable.addFunctionVariable(splitInput[0].charAt(0));
		try {
			storedFunctions.put(splitInput[0], (Function) KeywordInterface.useKeywords(splitInput[1]));
		} catch (IllegalArgumentException e) {
			storedFunctions.put(splitInput[0], parseStored(splitInput[1]));
		}
		return storedFunctions.get(splitInput[0]);
	}

	/**
	 * var [variablename]
	 */
	public static String addvars(String input) {
		String[] splitInput = keywordSplitter.split(input);
		for (String var : splitInput) {
			if (var.length() > 1)
				throw new IllegalArgumentException("Variables should be one character.");
			Variable.addVariable(var.charAt(0));
		}
		return printvars();
	}

	/**
	 * printvars
	 */
	public static String printvars() {
		return String.valueOf(Variable.variables);
	}

	/**
	 * clearvars
	 */
	public static String clearvars() {
		Variable.clearVariables();
		storedFunctions.clear();
		return String.valueOf(Variable.variables);
	}

	/**
	 * printfun
	 */
	public static String printfun() {
		return String.valueOf(storedFunctions);
	}

	/**
	 * clearfun
	 */
	public static String clearfun() {
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
}
