package CASprzak;

import java.util.ArrayList;
import java.util.LinkedList;

public class ArrLib {
	public static Function[] removeFunctionAt(Function[] functionArray, int index) {
		Function[] fout = new Function[functionArray.length-1];
		for (int i = 0; i < fout.length; i++)
				fout[i] = functionArray[(i<index?i:i+1)];
		return fout;
	}

	public static Function[] deepClone(Function[] functionArray) {
		Function[] fout = new Function[functionArray.length];
		for (int i = 0; i < fout.length; i++)
			fout[i] = functionArray[i].clone();
		return fout;
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
		for (int i = 0; i < indexInFirst; i++)
			an[i] = outer[i];
		for (int i = indexInFirst + 1; i < outer.length; i++)
			an[i - 1] = outer[i];
		for (int i = 0; i < inner.length; i++)
			an[outer.length + i - 1] = inner[i];
		return an;
	}

	public static int findClassValue(Function function) {
		for (int i = 0; i < Function.sortOrder.length; i++) {
			if (function.getClass().equals(Function.sortOrder[i]))
				return i;
		}
		return -1;
	}

	public static LinkedList<Double> createRange(double upper, double lower, int sections) {
		LinkedList<Double> range = new LinkedList<>();
		for (int i = 0; i < sections + 1; i++)
			range.add( lower + i * (upper - lower) / sections);
		return range;
	}

	public static void nanRemover(LinkedList<Double> values) {
		for (int i = 0; i < values.size(); i++) {
			if ((values.get(i)).isNaN()) {
				values.remove(i);
				i--;
			}
		}
	}

	public static void removeRepeats(LinkedList<Double> values) {
		for (int j = 0; j <= values.size(); j++) {
			for (int i = 0; i < values.size() -1; i++) {
				if(values.get(i) < values.get(i+1)+1E-15 && values.get(i) > values.get(i+1) - 1E-15) {
					values.remove(i+1);
				}
			}
		}
	}


}
