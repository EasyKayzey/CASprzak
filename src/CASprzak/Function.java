package CASprzak;

import CASprzak.SpecialFunctions.*;
import CASprzak.CommutativeFunctions.*;
import CASprzak.BinaryFunctions.*;
import CASprzak.UnitaryFunctions.*;

public abstract class Function implements Evaluatable, Differentiable, Simplifiable, Comparable<Function> {
	protected static final Class[] sortOrder = {Negative.class, Constant.class, Variable.class, Pow.class, Logb.class, Multiply.class, UnitaryFunction.class, Add.class, Reciprocal.class};

	protected String functionName;

	public abstract String toString();

	public abstract Function clone();

	public Function simplifyTimes(int times) {
		Function temp = this;
		for (int i = 0; i < times; i++) temp = temp.simplify();
		return temp;
	}

	public boolean equals(Function f) {
		return this.toString().equals(f.toString());
	}
}
