package functions.binary;

import core.Settings;
import functions.Function;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.Ln;

import java.util.Arrays;
import java.util.Map;

public class Pow extends BinaryFunction {
	/**
	 * Constructs a new Pow
	 * @param exponent The exponent of the exponent
	 * @param base The base of the exponent
	 */
	public Pow(Function exponent, Function base) {
		super(exponent, base);
	}

	@Override
	public Function getDerivative(char varID) {
		if (function1 instanceof Constant exponent)
			return new Multiply(new Constant(exponent.constant), new Pow(new Constant(exponent.constant - 1), function2), function2.getSimplifiedDerivative(varID));
		else
			return new Multiply(new Pow(function1, function2), new Add(new Multiply(function1.getSimplifiedDerivative(varID), new Ln(function2)), new Multiply(new Multiply(function1, function2.getSimplifiedDerivative(varID)), new Pow(new Constant(-1), function2))));
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.pow(function2.evaluate(variableValues), function1.evaluate(variableValues));
	}

	public Function clone() {
		return new Pow(function1.clone(), function2.clone());
	}

	public Function simplify() {
		Pow currentPow = (new Pow(function1.simplify(), function2.simplify()));
		currentPow = currentPow.multiplyExponents();
		Function current = currentPow.simplifyObviousExponentsAndFOC();
		if (current instanceof Pow pow && pow.function2 instanceof Multiply && Settings.distributeExponents)
			current = pow.distributeExponents();
		return current;
	}

	/**
	 * Returns a {@link Function} where obvious exponents (ex: {@code (x+1)^1 or (x-1)^0}) have been simplified and {@link Constant} bases and {@link Constant} exponents (ex: {@code 2^7}) are simplified
	 * @return a {@link Function} where obvious exponents and Functions of Constants are simplified
	 */
	public Function simplifyObviousExponentsAndFOC() { //FOC means Functions of Constants
		if (function1 instanceof Constant constant) {
			if (constant.constant == 0)
				return new Constant(1);
			if (constant.constant == 1)
				return function2.simplify();
			if (Settings.simplifyFunctionsOfConstants && function2 instanceof Constant)
				return new Constant(this.evaluate(null));
		}
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}

	/**
	 * Returns a {@link Pow} where an exponent to an exponent has been simplified. Example: {@code (x^2)^3 = x^6}
	 * @return a {@link Pow} where the exponents are multiplied
	 */
	public Pow multiplyExponents() {
		if (function2 instanceof Pow base)
			return new Pow(new Multiply(base.function1, function1).simplify(), base.function2);
		if (Settings.trustImmutability)
			return this;
		else
			return (Pow) clone();
	}

	/**
	 * Returns a {@link Multiply} where the exponent is on each term. Example: {@code (xy)^2 = (x^2)(y^2) }
	 * @return a {@link Multiply} with the exponent distributed
	 */
	public Multiply distributeExponents() {
		return new Multiply(distributeExponentsArray());
	}

	private Function[] distributeExponentsArray() {
		if (function2 instanceof Multiply multiply) {
			Function[] oldFunctions = multiply.getFunctions();
			Function[] toMultiply = new Function[oldFunctions.length];
			for (int i = 0; i < toMultiply.length; i++) {
				toMultiply[i] = new Pow(function1, oldFunctions[i]).simplify();
			}
			return toMultiply;
		} else {
			throw new IllegalArgumentException("Method should not be called if base is not a Multiply");
		}
	}

	private Function unwrapIntegerPower() {
		if (function1 instanceof Constant constant) {
			if (constant.constant % 1 == 0) {
				Function[] toMultiply = new Function[(int)constant.constant];
				Arrays.fill(toMultiply, function2);
				return new Multiply(toMultiply);
			}
		}
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}


	public BinaryFunction me(Function function1, Function function2) {
		return new Pow(function1, function2);
	}


	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		boolean parenF1 = function1 instanceof BinaryFunction;
		boolean parenF2 = function2 instanceof BinaryFunction;
		if (parenF2)
			out.append("(");
		out.append(function2.toString());
		if (parenF2)
			out.append(")");
		out.append("^");
		if (parenF1)
			out.append("(");
		out.append(function1.toString());
		if (parenF1)
			out.append(")");
		return out.toString();
	}
}
