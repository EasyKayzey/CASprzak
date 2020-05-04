package tools;

import config.Settings;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class SolverTools {

	private SolverTools(){}

	/**
	 * Returns a list of Doubles which is a range split into a certain amount of sections
	 * @param upper    the upper bound of the range
	 * @param lower    the lower bound of the range
	 * @param sections the amount of sections that the range is split into
	 * @return the specified list
	 */
	public static List<Double> createRange(double upper, double lower, int sections) {
		List<Double> range = new LinkedList<>();
		for (int i = 0; i <= sections; i++)
			range.add(lower + i * (upper - lower) / sections);
		return range;
	}

	/**
	 * Removes all NaNs from a specified List
	 * @param list the List from which the NaNs will be removed
	 */
	public static void nanRemover(List<Double> list) {
		list.removeIf(value -> value.isNaN());
	}

	/**
	 * Removes a number from a List if that number is adjacent to the same number within a tolerance of 1E-10
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
		ListIterator<Double> iter = values.listIterator();
		double current;
		while (iter.hasNext()) {
			current = iter.next();
			if (current > upperBound || current < lowerBound)
				iter.remove();
		}
	}
}
