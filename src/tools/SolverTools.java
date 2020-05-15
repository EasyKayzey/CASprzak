package tools;

import config.Settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * TODO explain
 */
public class SolverTools {

	private SolverTools(){}

	/**
	 * Returns a list of Doubles that represents a range split into a certain amount of sections by storing the endpoints of the sections
	 * @param upper    the upper bound of the range
	 * @param lower    the lower bound of the range
	 * @param sections the amount of sections that the range is split into
	 * @return the specified List
	 */
	public static List<Double> createRange(double upper, double lower, int sections) {
		List<Double> range = new ArrayList<>(sections + 1);
		for (int i = 0; i <= sections; i++)
			range.add(lower + i * (upper - lower) / sections);
		return range;
	}

	/**
	 * Removes a number from a List if that number is adjacent to the same number within a tolerance of {@link Settings#equalsMargin}
	 * @param values the List from which the repeated values are removed
	 */
	public static void removeRepeatsSort(List<Double> values) {
		Collections.sort(values);
		if (values.size() <= 1)
			return;
		ListIterator<Double> iter = values.listIterator();
		double current;
		double previous;
		current = iter.next();
		while (iter.hasNext()) {
			previous = current;
			current = iter.next();
			if (current == previous || Math.abs(current - previous) < Settings.equalsMargin)
				iter.remove();
		}
	}

	/**
	 * Removes values in a list which are not in the given range
	 * @param values the list from which values are being removed
	 * @param lowerBound the lower bound of the range
	 * @param upperBound the upper bound of the range
	 */
	public static void removeNotInRange(List<Double> values, double lowerBound, double upperBound) {
		values.removeIf(v -> v > upperBound || v < lowerBound);
	}


	/**
	 * Returns [value] if it is in the range, and NaN otherwise
	 * @param value the value to be checked
	 * @param lowerBound the lower bound of the range
	 * @param upperBound the upper bound of the range
	 * @return [value] if it is the range, and NaN otherwise
	 */
	public static double strictToRange(double value, double lowerBound, double upperBound) {
		if (value > upperBound || value < lowerBound)
			return Double.NaN;
		else
			return value;
	}
}
