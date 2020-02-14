package CASprzak;

import CASprzak.BinaryFunctions.Derivative;

public abstract class Function implements Evaluatable, Derivative {
	protected String functionName;
	public abstract String toString();
}
