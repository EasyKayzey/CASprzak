package functions.commutative;

import core.Settings;
import functions.Function;
import functions.binary.Pow;
import functions.special.Constant;
import functions.special.Variable;
import tools.FunctionTools;

import java.util.Arrays;
import java.util.Map;

public class Multiply extends CommutativeFunction {
	/**
	 * Constructs a new Multiply
	 * @param functions The terms being multiplied together
	 */
	public Multiply(Function... functions) {
		super(functions);
		identityValue = 1;
	}

	public double oldEvaluate(Map<Character, Double> variableValues) {
		double accumulator = identityValue;
		for (Function f : functions)
			accumulator *= f.oldEvaluate(variableValues);
		return accumulator;
	}


	public String toString() {
		if (functions.length < 1)
			return "(empty product)";
		StringBuilder string = new StringBuilder("(");
		for (int i = 0; i < functions.length - 1; i++) {
			string.append(functions[i].toString());
			string.append(" * ");
		}
		string.append(functions[functions.length - 1].toString());
		string.append(")");
		return string.toString();
	}

	@Override
	public Function getDerivative(char varID) {
		Function[] toAdd = new Function[functions.length];

		for (int i = 0; i < toAdd.length; i++) {
			Function[] toMultiply = new Function[functions.length];
			for (int j = 0; j < functions.length; j++) {
				toMultiply = Arrays.copyOf(functions, functions.length);
				toMultiply[i] = functions[i].getSimplifiedDerivative(varID);
			}
			toAdd[i] = new Multiply(toMultiply);
		}

		return new Add(toAdd);
	}

	public Multiply clone() {
		Function[] toMultiply = new Function[functions.length];
		for (int i = 0; i < functions.length; i++)
			toMultiply[i] = functions[i].clone();
		return new Multiply(toMultiply);
	}


	public Function simplify() {
		Multiply currentFunction = simplifyInternal();
		if (currentFunction.isTimesZero())
			return new Constant((0));
		else if (currentFunction.getFunctions().length <= 1)
			return currentFunction.simplifyOneElement();
		else
			return currentFunction.distributeAll();
	}

	public Multiply simplifyInternal() {
		Multiply current = (Multiply) super.simplifyInternal();
		current = current.addExponents();
		return current;
	}

	@Override
	public Multiply simplifyElements() {
		Function[] toMultiply = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toMultiply[i] = functions[i].simplify();
		return new Multiply(toMultiply);
	}

	@Override
	public Multiply simplifyConstants() {
		for (int i = 1; i < functions.length; i++) {
			for (int j = 0; j < i; j++) {
				if (functions[i] instanceof Constant first && functions[j] instanceof Constant second) {
					Function[] toMultiply = FunctionTools.deepClone(functions);
					toMultiply[i] = new Constant(first.constant * second.constant);
					toMultiply = FunctionTools.removeFunctionAt(toMultiply, j);
					return (new Multiply(toMultiply)).simplifyConstants();
				}
			}
		}
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}

	/**
	 * Returns true if the {@link Multiply} contains a 0 {@link Constant}
	 * @return true if the {@link Multiply} contains a 0 {@link Constant}
	 */
	public boolean isTimesZero() {
		for (Function function : functions) {
			if (function instanceof Constant constant) {
				if (constant.constant == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * If {@link #functions} contains multiple of the same {@link Variable} multiplied by each other (e.g. x*x^3) then the exponents will be added and the terms will be combined into one element of {@link #functions}
	 * @return A new {@link Multiply} with all variable combined with added exponents
	 */
	public Multiply addExponents() {
		Function[] simplifiedTerms = FunctionTools.deepClone(functions);
		for (int a = 0; a < simplifiedTerms.length; a++) {
			if (simplifiedTerms[a] instanceof Variable)
				simplifiedTerms[a] = new Pow(new Constant(1), simplifiedTerms[a]);
		}
		for (int i = 1; i < simplifiedTerms.length; i++) {
			for (int j = 0; j < i; j++) {
				if (simplifiedTerms[i] instanceof Pow first && simplifiedTerms[j] instanceof Pow second) {
					if (first.getFunction2().equals(second.getFunction2())) {
						simplifiedTerms[j] = new Pow(new Add(first.getFunction1(), second.getFunction1()), first.getFunction2());
						simplifiedTerms = FunctionTools.removeFunctionAt(simplifiedTerms, i);
						return (new Multiply(simplifiedTerms)).simplifyInternal();
					}
				}
			}
		}
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}

	public CommutativeFunction me(Function... functions) {
		return new Multiply(functions);
	}

	/**
	 * Returns a {@link Function} where the rest of the multiple has been distributed to any {@link Add}. Example: {@code sin(y)*(x+2) = x*sin(y) + 2*sin(y)}
	 * @return a {@link Function} where the rest of the multiple has been distributed to any {@link Add}
	 */
	public Function distributeAll() { //TODO this doesn't distribute completely
		Function[] multiplyTerms = getFunctions();
		Function[] addTerms;
		for (int i = 0; i < multiplyTerms.length; i++) {
			if (multiplyTerms[i] instanceof Add add) {
				addTerms = add.getFunctions();
				multiplyTerms = FunctionTools.removeFunctionAt(multiplyTerms, i);
				return new Add(FunctionTools.distribute(multiplyTerms, addTerms)).simplify();
			}
		}
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}
}
