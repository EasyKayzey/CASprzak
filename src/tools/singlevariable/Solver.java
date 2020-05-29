package tools.singlevariable;

import config.Settings;
import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.piecewise.Abs;
import tools.DefaultFunctions;
import tools.SolverTools;
import tools.VariableTools;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * The {@link Solver} class solves for zeros of {@link GeneralFunction}s using <a href="https://en.wikipedia.org/wiki/Newton%27s_method">Newton's method</a> or <a href="https://en.wikipedia.org/wiki/Halley%27s_method">Halley's method</a> of root finding.
 */
public class Solver {

	private Solver(){}

	/**
	 * Performs one iteration of Newton's method for a given {@link GeneralFunction} at a given point
	 * @param expression the function that is iterated on
	 * @param value      initial approximation of the root
	 * @return a better approximation of the root based on the value provided
	 */
	private static double newtonsMethod(GeneralFunction expression, double value) {
		String var = VariableTools.getSingleVariable(expression);
		return value - expression.evaluate(Map.of(var, value)) / expression.getSimplifiedDerivative(var).evaluate(Map.of(var, value));
	}

	/**
	 * Gives an approximate root of a {@link GeneralFunction} using {@link #newtonsMethod} on the initialPoint for a specified amount of runs
	 * @param expression   the function whose root is being found
	 * @param initialPoint initial approximation of the root
	 * @param runs         the amount of times that {@link #newtonsMethod} is iterated
	 * @return the approximate solution for a root of the function
	 */
	public static double getSolutionPointNewton(GeneralFunction expression, double initialPoint, int runs) {
		String var = VariableTools.getSingleVariable(expression);
		if (expression.evaluate(Map.of(var, initialPoint)) == 0)
			return initialPoint;
		if (expression instanceof Constant)
			return Double.NaN;
		for (int i = 0; i < runs; i++) {
			double nextPoint = newtonsMethod(expression, initialPoint);
			if (Double.isNaN(nextPoint))
				return initialPoint;
			initialPoint = nextPoint;
			if (i % 25 == 0 && initialPoint < 1E-10 && initialPoint > -1E-10)
				return 0;
			if (Settings.exitSolverOnProximity && i % (runs / 20) == 0 && Math.abs(expression.evaluate(Map.of(var, nextPoint))) < Settings.equalsMargin)
				return nextPoint;
		}
		if (Math.abs(expression.evaluate(Map.of(var, initialPoint))) < Settings.zeroMargin)
			if (Math.abs(initialPoint) < Settings.equalsMargin)
				return 0;
			else
				return initialPoint;
		return Double.NaN;
	}

	/**
	 * Gives an approximate root of a {@link GeneralFunction} using {@link #newtonsMethod} on the initialPoint for the amount of runs specified in {@link Settings#defaultSolverIterations}
	 * @param expression   the function whose root is being found
	 * @param initialPoint initial approximation of the root
	 * @return the approximate solution for a root of the function
	 */
	public static double getSolutionPointNewton(GeneralFunction expression, double initialPoint) {
		return getSolutionPointNewton(expression, initialPoint, Settings.defaultSolverIterations);
	}

	/**
	 * Gives approximate roots of a {@link GeneralFunction} in a range of values using the solver specified in {@link Settings#defaultSolverType}
	 * @param expression the function whose roots are being found
	 * @param lower      the lower bound of the values that will be searched
	 * @param upper      the upper bound of the values that will be searched
	 * @return a List of all the approximate roots found
	 */
	@SuppressWarnings("UnnecessaryDefault")
	public static List<Double> getSolutionsRange(GeneralFunction expression, double lower, double upper) {
		return switch (Settings.defaultSolverType) {
			case NEWTON -> getSolutionsRangeNewton(expression, lower, upper);
			case HALLEY -> getSolutionsRangeHalley(expression, lower, upper);
			default -> throw new IllegalStateException("Solver type unspecified!");
		};
	}

	/**
	 * Gives approximate roots of a {@link GeneralFunction} using {@link #newtonsMethod} in a range of values for a specified amount of runs
	 * @param expression the function whose roots are being found
	 * @param lower      the lower bound of the values that will be searched
	 * @param upper      the upper bound of the values that will be searched
	 * @param runs       the amount of times that {@link #newtonsMethod} is iterated
	 * @return a List of all the approximate roots found
	 */
	public static List<Double> getSolutionsRangeNewton(GeneralFunction expression, double lower, double upper, int runs) {
		String var = VariableTools.getSingleVariable(expression);
		List<Double> solutions = SolverTools.createRange(upper, lower, Settings.defaultRangeSections);
		ListIterator<Double> iter = solutions.listIterator();
		while (iter.hasNext()) {
			double nextLocation = getSolutionPointNewton(expression, iter.next(), runs);
			double nextResult = expression.evaluate(Map.of(var, nextLocation));
			if (Double.isNaN(nextResult) || Math.abs(nextResult) > Settings.zeroMargin)
				iter.remove();
			else
				iter.set(nextLocation);
		}
		SolverTools.removeNotInRange(solutions, lower, upper);
		SolverTools.removeRepeatsSort(solutions);
		return solutions;
	}

	/**
	 * Gives approximate roots of a {@link GeneralFunction} using Halley's method in a range of values for the amount of runs specified in {@link Settings}
	 * @param expression the function whose roots are being found
	 * @param lower      the lower bound of the values that will be searched
	 * @param upper      the upper bound of the values that will be searched
	 * @return a List of all the approximate roots found
	 */
	public static List<Double> getSolutionsRangeHalley(GeneralFunction expression, double lower, double upper) {
		String var = VariableTools.getSingleVariable(expression);
		return getSolutionsRangeNewton(
				(new Product(expression, new Pow(DefaultFunctions.NEGATIVE_HALF, new Abs(expression.getDerivative(var))))).simplify(),
				lower, upper
		);
	}

	/**
	 * Gives approximate roots of a {@link GeneralFunction} using {@link #newtonsMethod} in a range of values for the amount of runs specified in {@link Settings}
	 * @param expression the function whose roots are being found
	 * @param lower      the lower bound of the values that will be searched
	 * @param upper      the upper bound of the values that will be searched
	 * @return a List of all the approximate roots found
	 */
	public static List<Double> getSolutionsRangeNewton(GeneralFunction expression, double lower, double upper) {
		return getSolutionsRangeNewton(expression, lower, upper, Settings.defaultSolverIterations);
	}
}
