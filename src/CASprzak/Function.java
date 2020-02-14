package CASprzak;

public abstract class Function implements Evaluatable {
	protected String functionName;
	public abstract String toString();
	public abstract Function derivative();
}
