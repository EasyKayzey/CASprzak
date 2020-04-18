package tools;

import java.util.LinkedList;
import java.util.List;

public class SolverTools {

	private SolverTools(){}

	/**
	 * Returns a list of Doubles which is a range slit into a certain amount of sections
	 *
	 * @param upper    the upper bound of the range
	 * @param lower    the lower bound of the range
	 * @param sections the amount of sections that the range is split into
	 * @return the specified range
	 */
	public static List<Double> createRange(double upper, double lower, int sections) {
		List<Double> range = new LinkedList<>();
		for (int i = 0; i < sections + 1; i++)
			range.add(lower + i * (upper - lower) / sections);
		return range;
	}

	/**
	 * Removes all NaNs from a specified List
	 *
	 * @param values the List from which the NaNs will be removed
	 */
	public static void nanRemover(List<Double> values) {
		for (int i = 0; i < values.size(); i++) {
			if ((values.get(i)).isNaN()) {
				values.remove(i);
				i--;
			}
		}
	}

	/**
	 * Removes a number from a List if that number is adjacent to the same number within a tolerance of 1E-15
	 *
	 * @param values the List from which the repeated values are removed
	 */
	public static void removeRepeatsInOrder(List<Double> values) {
		for (int i = 0; i < values.size() - 1; i++) {
			if (values.get(i).equals(values.get(i + 1)) || (values.get(i) < values.get(i + 1) + 1E-15 && values.get(i) > values.get(i + 1) - 1E-15)) {
				values.remove(i + 1);
				i--;
			}
		}
	}
}
