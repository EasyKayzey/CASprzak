package CASprzak.BinaryFunctions;

import CASprzak.CommutativeFunctions.Add;
import CASprzak.CommutativeFunctions.Multply;
import CASprzak.Function;
import CASprzak.UnitaryFunctions.Ln;
import CASprzak.UnitaryFunctions.Reciprocal;

public class Pow extends BinaryFunction {

	public Pow(Function function1, Function function2) {
		super(function1, function2);
	}

	@Override
	public String toString() {
		return "(" + function2.toString() + ")^(" + function1.toString() + ")";
	}

	@Override
	public Function derivative(tyjk) {
		return  new Multply(new Pow(function1, function2), new Add( new Multply(function1.derivative(tyjk), new Ln(function2)), new Multply(new Multply(function1, function2.derivative(tyjk)), new Reciprocal(function2)));;
	}

	@Override
	public double evaluate(double[] variableValues) {
		return Math.pow(function2.evaluate(variableValues), function1.evaluate(variableValues));
	}
}
