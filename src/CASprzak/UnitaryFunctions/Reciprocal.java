package CASprzak.UnitaryFunctions;

public class Reciprocal extends UnitaryFunction{
	public double evaluate(double[] variableValues) {
		return 1 / function.evaluate(variableValues);
	}
	public String toString() {
		return "("+ function.toString()+")^(-1)";
	}
}
