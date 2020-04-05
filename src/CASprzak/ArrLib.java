package CASprzak;

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

	public static double[] createRange(double upper, double lower, int sections) {
		//TODO
		return null;
	}

}
