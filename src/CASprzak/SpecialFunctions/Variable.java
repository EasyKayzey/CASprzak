package CASprzak.SpecialFunctions;

import CASprzak.CAS;
import CASprzak.Function;

public class Variable extends Function {
	protected char[] varNames;

	private int varID;

	public Variable(int varID, char[] varNames) {
		this.varID = varID;
		this.varNames = varNames;
	}

	public String toString() {
		return "" + varNames[varID];
	}

	@Override
	public Function derivative() {
		return new Constant(1);
	}

	public double evaluate(double[] variableValues) {
		return variableValues[varID];
	}
}
