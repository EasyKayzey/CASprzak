package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multply;
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
	public Function derivative(int varID) {
		return new Multply(new Constant(-1), function.derivative(varID));
	}
}
