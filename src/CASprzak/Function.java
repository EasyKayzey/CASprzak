package CASprzak;

public abstract class Function implements Evaluatable, Differentiable, Simplifiable, Comparable<Function> {
	protected String functionName;

	public abstract String toString();

	public abstract Function clone();

	public Function simplifyTimes(int times) {
		Function temp = this;
		for (int i = 0; i < times; i++) temp = temp.simplify();
		return temp;
	}

	public boolean equals(Function f) {
		return this.toString().equals(f.toString());
	}
}
