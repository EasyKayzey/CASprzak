package core;

import functions.Function;
import functions.commutative.Multiply;

import java.util.LinkedList;
import java.util.List;

public class ArrLib {

	/**
	 * Removes a {@link Function} from a Function[] and returns the new array (does not modify)
	 * @param functionArray the array of Functions
	 * @param index index of the Function to be removed
	 * @return the new array
	 */
	public static Function[] removeFunctionAt(Function[] functionArray, int index) {
		Function[] newArray = new Function[functionArray.length-1];
		for (int i = 0; i < newArray.length; i++)
				newArray[i] = functionArray[(i<index ? i : i+1)];
		return newArray;
	}

	/**
	 * Deep-clones an array of Functions
	 * @param functionArray array of Functions
	 * @return new Function[]
	 */
	public static Function[] deepClone(Function[] functionArray) {
		Function[] newArray = new Function[functionArray.length];
		for (int i = 0; i < newArray.length; i++)
			newArray[i] = functionArray[i].clone();
		return newArray;
	}

	/**
	 * Checks if two {@link Function} arrays have equal Functions at each index (assumes sorted)
	 * @param functionArray1 first array
	 * @param functionArray2 second array
	 * @return true if equal
	 */
	public static boolean deepEquals(Function[] functionArray1, Function[] functionArray2) {
		if (functionArray1.length != functionArray2.length)
			return false;
		for (int i = 0; i < functionArray1.length; i++) {
			if (!functionArray1[i].equals(functionArray2[i]))
				return false;
		}
		return true;
	}

	/**
	 * Creates a new {@link Function}[] out of two Function arrays, including all elements from both except for one in the first.
	 * @param outer first Function[]
	 * @param inner second Function[]
	 * @param indexInOuter index to not include in the first Function[]
	 * @return new Function[] with elements from both
	 */
	public static Function[] pullUp(Function[] outer, Function[] inner, int indexInOuter) {
		Function[] an = new Function[outer.length + inner.length - 1];
		if (indexInOuter > 0)
			System.arraycopy(outer, 0, an, 0, indexInOuter);
		if (indexInOuter < outer.length - 1)
			System.arraycopy(outer, indexInOuter + 1, an, indexInOuter, outer.length - indexInOuter - 1);
		System.arraycopy(inner, 0, an, outer.length - 1, inner.length);
		return an;
	}

	/**
	 * Returns the location of a {@link Function} in its class-based sort order (see {@link Function#sortOrder})
	 * @param function the function whose class order is to be found
	 * @return location in {@link Function#sortOrder}
	 */
	public static int findClassValue(Function function) {
		Class<?> functionClass = function.getClass();
		for (int i = 0; i < Function.sortOrder.length; i++) {
			if (Function.sortOrder[i].isAssignableFrom(functionClass))
				return i;
		}
		throw new IllegalArgumentException("Class " + function.getClass().getSimpleName() + " not supported.");
	}

	/**
	 * Returns a list of Doubles which is a range slit into a certain amount of sections
	 * @param upper the upper bound of the range
	 * @param lower the lower bound of the range
	 * @param sections the amount of sections that the range is split into
	 * @return the specified range
	 */
	public static List<Double> createRange(double upper, double lower, int sections) {
		List<Double> range = new LinkedList<>();
		for (int i = 0; i < sections + 1; i++)
			range.add( lower + i * (upper - lower) / sections);
		return range;
	}

	/**
	 * Removes all NaNs from a specified List
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

	/**
	 * Returns a Function[] where every element in add is now a {@link Multiply} of multiply and the function that was previously there in add
	 * @param multiply the Function[] which is distributed to every element in add
	 * @param add the Function[] which is being distributed to
	 * @return Function[] where the multiply has been distributed to the add
	 */
	public static Function[] distribute(Function[] multiply, Function[] add) {
		Function[] finalAdd = new Function[add.length];
		for (int i = 0; i < finalAdd.length; i++) {
			finalAdd[i] = new Multiply(append(multiply, add[i]));
		}
		return finalAdd;
 	}

	/**
	 * Appends two {@link Function} arrays
	 * @param first first array
	 * @param second second array
	 * @return new combined array
	 */
	public static Function[] append(Function[] first, Function second) {
		Function[] finalFunctionArray = new Function[first.length + 1];
		System.arraycopy(first, 0, finalFunctionArray, 0, first.length);
		finalFunctionArray[first.length] = second;
		return finalFunctionArray;
	}


}
