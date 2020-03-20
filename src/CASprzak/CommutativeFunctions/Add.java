package CASprzak.CommutativeFunctions;

import CASprzak.ArrLib;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
public class Add extends CommutativeFunction{
	double identity = 0;

	public Add(Function[] functions) {
		super(functions);
	}

	public Add(Function function1, Function function2) {
		super(function1, function2);
	}

	public double evaluate(double[] variableValues) {
		double accumulator = 0;
		for (Function f : functions) accumulator += f.evaluate(variableValues);
		return accumulator;
	}

	public String toString() {
		StringBuilder temp = new StringBuilder("(");
		for (int i = 0; i < functions.length - 1; i++) {
			temp.append(functions[i].toString());
			temp.append(" + ");
		}
		temp.append(functions[functions.length-1].toString());
		temp.append(")");
		return temp.toString();
	}

	@Override
	public Function getDerivative(int varID) {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) {
			toAdd[i] = functions[i].getDerivative(varID);
		}
		return new Add(toAdd);
	}

	public Function clone() {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toAdd[i] = functions[i].clone();
		return new Add(toAdd);
	}

	public Function simplify() {
		return super.simplifyInternal().simplifyOneElement();
	}


	@Override
	protected CommutativeFunction simplifyElements() {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toAdd[i] = functions[i].simplify();
		return new Add(toAdd);
	}

	@Override
	protected CommutativeFunction simplifyIdentity() {
		for (int i = 0; i < functions.length; i++) {
			if (functions[i] instanceof Constant) {
				if (((Constant) functions[i]).getConstant() == 0) {
					return (new Add(ArrLib.removeFunctionAt(functions, i))).simplifyIdentity();
				}
			}
		}
		return this;
	}

	protected CommutativeFunction simplifyConstants() {
		for (int i = 1; i < functions.length; i++){
			for (int j = 0; j < i; j++){
				if (functions[i] instanceof Constant && functions[j] instanceof Constant) {
					Constant c = new Constant(((Constant) functions[i]).getConstant() + ((Constant) functions[j]).getConstant());
					Function[] toAdd = ArrLib.removeFunctionAt(functions, j);
					toAdd[i] = c;
					return (new Add(toAdd)).simplifyConstants();
				}
			}
		}
		return this;
	}

	protected Function simplifyOneElement() {
		if (functions.length == 1) return functions[0].simplify();
		return this;
	}

	public int compareTo(Function f) {
		return 0;
	}
}
