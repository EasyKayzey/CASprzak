package parsing;

import functions.Function;
import functions.special.Variable;
import tools.singlevariable.Extrema;
import tools.singlevariable.Solver;
import tools.singlevariable.TaylorSeries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

@SuppressWarnings("SpellCheckingInspection")
public class KeywordInterface {
	public static final Pattern spacesOutsideQuotes = Pattern.compile("\"\\s\"|\"\\s|\\s\"|\"$|\\s+(?=[^\"]*(\"[^\"]*\"[^\"]*)*$)");
	public static HashMap<String, Function> storedFunctions = new HashMap<>();
	/**
	 * A list of sets of keywords corresponding to operations
	 */
	public static String[][] keywordSets = {
			{"pd", "pdiff", "partial", "pdifferentiate"},
			{"eval", "evaluate"},
			{"simp", "simplify"},
			{"sub", "substitute"},
			{"sol", "solve"},
			{"ext", "extrema"},
			{"tay", "taylor"},
			{"sto", "store"},
	};

	/**
	 * Takes input as a string with command, arguments...
	 * @param input contains the command and arguments
	 * @return the Object requested
	 */
	public static Object useKeywords(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input, 2);
		return switch (splitInput[0]) {
			case "pd", "pdiff", "partial", "pdifferentiate" -> pd(splitInput[1]);
			case "eval", "evaluate" -> eval(splitInput[1]);
			case "simp", "simplify" -> simp(splitInput[1]);
			case "sub", "substitute" -> sub(splitInput[1]);
			case "sol", "solve" -> sol(splitInput[1]);
			case "ext", "extrema" -> ext(splitInput[1]);
			case "tay", "taylor" -> tay(splitInput[1]);
			case "sto", "store" -> sto(splitInput[1]);
			default -> throw new IllegalArgumentException(splitInput[0] + " is not supported by KeywordInterface");
		};
	}

	public static Function parseStored(String input) {
		if (storedFunctions.containsKey(input))
			return storedFunctions.get(input);
		else
			return Parser.parse(input);
	}
	
	/**
	 * pd [variable] [function]
	 */
	public static Function pd(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return parseStored(splitInput[1]).getSimplifiedDerivative(Variable.getVarID(splitInput[0].charAt(0)));
	}

	/**
	 * eval [function] [values]
	 */
	public static double eval(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input, 2);
		double[] values = Arrays.stream(spacesOutsideQuotes.split(splitInput[1])).mapToDouble(ConstantEvaluator::getConstant).toArray();
		return parseStored(splitInput[0]).evaluate(values);
	}

	/**
	 * simp [function]
	 */
	public static Function simp(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return parseStored(splitInput[0]).simplify();
	}

	/**
	 * sub [function] [variable] [replacementfunction]
	 */
	public static Function sub(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		if (splitInput[1].length() > 1)
			throw new IllegalArgumentException("Variables are one character, so " + splitInput[1] + " is not valid.");
		return parseStored(splitInput[0]).substitute(Variable.getVarID(splitInput[1].charAt(0)), parseStored(splitInput[2]));
	}

	/**
	 * sol [function] [startrange] [endrange]
	 */
	public static double[] sol(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return Solver.getSolutionsRange(parseStored(splitInput[0]), ConstantEvaluator.getConstant(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]));
	}

	/**
	 * ext ["min(ima)"/"max(ima)"/"anymin(ima)"/"anymax(ima)"/"inflect(ion)"] [function] [startrange] [endrange]
	 */
	public static Object ext(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
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
	public static Function tay(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return TaylorSeries.makeTaylorSeries(parseStored(splitInput[0]), (int) ConstantEvaluator.getConstant(splitInput[1]), ConstantEvaluator.getConstant(splitInput[2]));
	}

	/**
	 * sto [uppercaselocationstring] [function]
	 */
	public static Object sto(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		//TODO make this actually use user input
		return null;
	}
}
