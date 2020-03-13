package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
public class Negative extends UnitaryFunction{
	public Negative(Function function) {
		super(function);
	}

	public double evaluate(double[] variableValues) {
		return -1 * function.evaluate(variableValues);
	}
	public String toString() {
		return "-" + function.toString();
	}

	@Override
	public Function getDerivative(int varID) {
		return new Multiply(new Constant(-1), function.getDerivative(varID));
	}

	public Function clone() {
		return new Negative(function.clone());
	}

	public Function simplify() {
		return new Negative(function.simplify());
	}

	public int compareTo( Function f) {
		return 0;
	}
}
