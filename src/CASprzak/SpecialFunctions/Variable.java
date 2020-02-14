package CASprzak.SpecialFunctions;

import CASprzak.CAS;
import CASprzak.Function;
import org.jetbrains.annotations.NotNull;

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


	public Function getDerivative(int varID) {
		return new Constant(1);
	}

	public double evaluate(double[] variableValues) {
		return variableValues[varID];
	}

	public Function clone() {
		return new Variable(varID, varNames);
	}

	public Function simplify() {
		return clone();
	}

	public int compareTo(@NotNull Function f) {
		return 0;
	}
}
