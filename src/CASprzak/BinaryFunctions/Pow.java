package CASprzak.BinaryFunctions;

import CASprzak.CommutativeFunctions.Add;
import CASprzak.CommutativeFunctions.CommutativeFunction;
import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.Settings;
import CASprzak.SpecialFunctions.Constant;
import CASprzak.SpecialFunctions.Variable;
import CASprzak.UnitaryFunctions.Ln;

public class Pow extends BinaryFunction {

	public Pow(Function exponent, Function base) {
		super(exponent, base);
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
		return (new Pow(function1.simplify(), function2.simplify())).multiplyExponents().simplifyObviousExponentsAndFOC();
	}

	public Function simplifyObviousExponentsAndFOC() { //FOC means Functions of Constants
		if(function1 instanceof Constant) {
			if (((Constant) function1).constant == 0)
				return new Constant(1);
			if (((Constant) function1).constant == 1)
				return function2.simplify();
			if (Settings.simplifyFunctionsOfConstants && function2 instanceof Constant)
				return new Constant(this.evaluate());
		}
		return this;
	}

	public Pow multiplyExponents() {
		if (function2 instanceof Pow) {
			return new Pow(new Multiply(((Pow) function2).function1, function1), ((Pow) function2).function2);
		}
		return (Pow) clone();
	}

	public Multiply distributeExponents() {
		return new Multiply(distributeExponentsArray());
	}

	public Function[] distributeExponentsArray() {
		if (!(function2 instanceof Multiply))
			throw new IllegalArgumentException("Method should not be called if base is not a Multiply");
		Function[] oldFunctions = ((Multiply)function2).getFunctions();
		Function[] toMultiply = new Function[oldFunctions.length];
		for (int i = 0; i < toMultiply.length; i++) {
			toMultiply[i] = new Pow(function1, oldFunctions[i]);
		}
		return toMultiply;
	}


	public BinaryFunction me(Function function1, Function function2) {
		return new Pow(function1, function2);
	}


	@Override
	public String toString() { //TODO this should probably be using StringBuilder
		boolean parenF1 = !((function1 instanceof Constant) || (function1 instanceof Variable) || (function1 instanceof CommutativeFunction));
		boolean parenF2 = !((function2 instanceof Constant) || (function2 instanceof Variable) || (function2 instanceof CommutativeFunction));
		return (parenF2 ? "(" : "") + function2.toString() + (parenF2 ? ")" : "") + "^" + (parenF1 ? "(" : "") + function1.toString() + (parenF1 ? ")" : "");
	}
}
