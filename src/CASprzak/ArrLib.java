package CASprzak;

public class ArrLib {
	public static Function[] removeFunctionAt(Function[] farr, int index) {
		Function[] fout = new Function[farr.length-1];
		for (int i = 0; i < fout.length; i++)
				fout[i] = farr[(i<index?i:i+1)];
		return fout;
	}
	public static double[] createRange(double upper, double lower, int sections) {
		return null;
	}
}
