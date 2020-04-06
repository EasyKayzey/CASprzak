package CASprzak.BinaryFunctions;

import CASprzak.CommutativeFunctions.Add;
import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
import CASprzak.UnitaryFunctions.Ln;

public class Pow extends BinaryFunction {

	public Pow(Function function1, Function function2) {
		super(function1, function2);
	}

	@Override
	public String toString() {
		return "(" + function2.toString() + ")^(" + function1.toString() + ")";
	}

	@Override
	public Function getDerivative(int varID) {
		return  new Multiply(new Pow(function1, function2), new Add( new Multiply(function1.getSimplifiedDerivative(varID), new Ln(function2)), new Multiply(new Multiply(function1, function2.getSimplifiedDerivative(varID)), new Pow(new Constant(-1), function2))));
	}

	@Override
	public double evaluate(double... variableValues) {
		return Math.pow(function2.evaluate(variableValues), function1.evaluate(variableValues));
	}

	public Function clone() {
		return new Pow(function1.clone(), function2.clone());
	}

	public Function simplify() {
		return (new Pow(function1.simplify(), function2.simplify())).multiplyExponents().simplifyObviousExponents();
	}

	protected Function simplifyObviousExponents() {
		if(function1 instanceof Constant) {
			if (((Constant) function1).constant == 0)
				return new Constant(1);
			if (((Constant) function1).constant == 1)
				return function2.simplify();
		}
		return this;
	}

	protected Pow multiplyExponents() {
		if (function2 instanceof Pow) {
			return new Pow(new Multiply(((Pow) function2).function1, function1), ((Pow) function2).function2);
		}
		return (Pow) clone();
	}

	public int compareTo(Function f) {
		return 0;
	}
}
