package tools.singlevariable;

import core.Settings;
import functions.Function;
import functions.special.Constant;
import tools.SolverTools;

import java.util.List;
import java.util.ListIterator;

public class Solver {

	private Solver(){}

	/**
	 * Does one iteration of Newton's method for a given {@link Function} at an given point
	 * @param expression the function that is iterated on
	 * @param value      the initial approximation of the root
	 * @return a better approximate of the root based on the value provided
	 */
	private static double newtonsMethod(Function expression, double value) {
		return value - expression.evaluate(value) / expression.getSimplifiedDerivative(0).evaluate(value);
	}

	/**
	 * Gives an approximate root of a {@link Function} using {@link #newtonsMethod} for the initialPoint after a specified amount of runs
	 * @param expression   the function whose root is being found
	 * @param initialPoint the initial approximation of the root
	 * @param runs         the amount of times that {@link #newtonsMethod} is ran recursively
	 * @return the approximate solution for a root of the function
	 */
	public static double getSolutionPoint(Function expression, double initialPoint, int runs) {
		if (expression.evaluate(initialPoint) == 0)
			return initialPoint;
		if (expression instanceof Constant)
			return Double.NaN;
		for (int i = 0; i < runs; i++) {
			double nextPoint = newtonsMethod(expression, initialPoint);
			if (Double.isNaN(nextPoint))
				return initialPoint;
			initialPoint = nextPoint;
			if (i % 25 == 0)
				if (initialPoint < 1E-10 && initialPoint > -1E-10)
					return 0;
		}
		if (expression.evaluate(initialPoint) < Settings.zeroMargin && expression.evaluate(initialPoint) > -Settings.zeroMargin)
			return initialPoint;
		return Double.NaN;
	}

	/**
	 * Gives an approximate root of a {@link Function} using {@link #newtonsMethod} for the initialPoint after 100 runs
	 * @param expression   the function whose root is being found
	 * @param initialPoint the initial approximation of the root
	 * @return the approximate solution for a root of the function
	 */
	public static double getSolutionPoint(Function expression, double initialPoint) {
		return getSolutionPoint(expression, initialPoint, Settings.defaultSolverIterations);
	}

	/**
	 * Gives approximate roots of a {@link Function} using {@link #newtonsMethod} in a range of values after a specified amount of runs
	 * @param expression the function whose roots are being found
	 * @param lower      the lower bound of the values that will be searched for roots
	 * @param upper      the upper bound of the values that will be searched for roots
	 * @param runs       the amount of times that {@link #newtonsMethod} is ran recursively
	 * @return an array of all the approximate roots found
	 */
	public static double[] getSolutionsRange(Function expression, double lower, double upper, int runs) {
		List<Double> solutions = SolverTools.createRange(upper, lower, Settings.defaultRangeSections);
		ListIterator<Double> iter = solutions.listIterator();
		while (iter.hasNext()) {
			double nextLocation = getSolutionPoint(expression, iter.next(), runs);
			if (!(expression.evaluate(nextLocation) < Settings.zeroMargin && expression.evaluate(nextLocation) > -Settings.zeroMargin))
				iter.remove();
			else
				iter.set(nextLocation);
		}
		SolverTools.nanRemover(solutions);
		SolverTools.removeRepeatsInOrder(solutions);
		return solutions.stream().mapToDouble(i -> i).toArray();
	}

	/**
	 * Gives approximate roots of a {@link Function} using {@link #newtonsMethod} in a range of values after 1000 runs
	 * @param expression the function whose roots are being found
	 * @param lower      the lower bound of the values that will be searched for roots
	 * @param upper      the upper bound of the values that will be searched for roots
	 * @return an array of all the approximate roots found
	 */
	public static double[] getSolutionsRange(Function expression, double lower, double upper) {
		return getSolutionsRange(expression, lower, upper, Settings.defaultSolverIterations);
	}
}
