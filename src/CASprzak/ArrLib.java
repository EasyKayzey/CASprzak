package CASprzak;

import java.util.*;

public class ArrLib {
	public static Function[] removeFunctionAt(Function[] functionArray, int index) {
		Function[] newArray = new Function[functionArray.length-1];
		for (int i = 0; i < newArray.length; i++)
				newArray[i] = functionArray[(i<index ? i : i+1)];
		return newArray;
	}

	public static Function[] deepClone(Function[] functionArray) {
		Function[] newArray = new Function[functionArray.length];
		for (int i = 0; i < newArray.length; i++)
			newArray[i] = functionArray[i].clone();
		return newArray;
	}

	public static boolean deepEquals(Function[] functionArray1, Function[] functionArray2) {
		if (functionArray1.length != functionArray2.length)
			return false;
		for (int i = 0; i < functionArray1.length; i++) {
			if (!functionArray1[i].equals(functionArray2[i]))
				return false;
		}
		return true;
	}

	public static Function[] pullUp(Function[] outer, Function[] inner, int indexInFirst) {
		Function[] an = new Function[outer.length + inner.length - 1];
		if (indexInFirst > 0)
			System.arraycopy(outer, 0, an, 0, indexInFirst);
		if (indexInFirst < outer.length - 1)
			System.arraycopy(outer, indexInFirst + 1, an, indexInFirst, outer.length - indexInFirst - 1);
		System.arraycopy(inner, 0, an, outer.length - 1, inner.length);
		return an;
	}

	public static int findClassValue(Function function) {
		for (int i = 0; i < Function.sortOrder.length; i++) {
			if (function.getClass().equals(Function.sortOrder[i]))
				return i;
		}
		return -1;
	}

	public static List<Double> createRange(double upper, double lower, int sections) {
		List<Double> range = new LinkedList<>();
		for (int i = 0; i < sections + 1; i++)
			range.add( lower + i * (upper - lower) / sections);
		return range;
	}

	public static void nanRemover(List<Double> values) {
		for (int i = 0; i < values.size(); i++) {
			if ((values.get(i)).isNaN()) {
				values.remove(i);
				i--;
			}
		}
	}

	public static void removeRepeatsInOrder(List<Double> values) {
		for (int i = 0; i < values.size() - 1; i++) {
			if (values.get(i).equals(values.get(i + 1)) || (values.get(i) < values.get(i + 1) + 1E-15 && values.get(i) > values.get(i + 1) - 1E-15)) {
				values.remove(i + 1);
				i--;
			}
		}
	}


}
