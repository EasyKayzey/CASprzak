package CASprzak;

public class ArrLib {
	public static Function[] removeFunctionAt(Function[] functionArray, int index) {
		Function[] newArray = new Function[functionArray.length-1];
		for (int i = 0; i < newArray.length; i++)
				newArray[i] = functionArray[(i<index?i:i+1)];
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
			if (!functionArray1[i].equals(functionArray2[i])) {
				return false;
			}
		}
		return true;
	}

	public static Function[] pullUp(Function[] outer, Function[] inner, int indexInFirst) {
		Function[] newArray = new Function[outer.length + inner.length - 1];
		for (int i = 0; i < indexInFirst; i++)
			newArray[i] = outer[i];
		for (int i = indexInFirst + 1; i < outer.length; i++)
			newArray[i - 1] = outer[i];
		for (int i = 0; i < inner.length; i++)
			newArray[outer.length + i - 1] = inner[i];
		return newArray;
	}

	public static double[] createRange(double upper, double lower, int sections) {
		double[] range = new double[sections+1];
		for (int i = 0; i < range.length; i++)
			range[i] = lower + i * (upper-lower) / sections;
		return range;
	}

}
