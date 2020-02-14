package CASprzak;

public abstract class Function implements Evaluatable, Differentiable, Simplifiable, Comparable<Function> {
	protected String functionName;
	public abstract String toString();
	public abstract Function clone();
}
