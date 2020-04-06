package CASprzak;

import CASprzak.SpecialFunctions.*;
import CASprzak.CommutativeFunctions.*;
import CASprzak.BinaryFunctions.*;
import CASprzak.UnitaryFunctions.*;

public abstract class Function implements Evaluable, Differentiable, Simplifiable, Comparable<Function> {
	protected static final Class<?>[] sortOrder = {Constant.class, Variable.class, Pow.class, Logb.class, Multiply.class, UnitaryFunction.class, Add.class};

	public abstract String toString();

	public abstract Function clone();

	public Function simplifyTimes(int times) {
		Function temp = this;
		for (int i = 0; i < times; i++) temp = temp.simplify();
		return temp;
	}

	public Function getSimplifiedDerivative(int varID) {
		return getDerivative(varID).simplify();
	}

	public abstract boolean equals(Function that);

	public boolean equals(Object o) {
		if (!(o instanceof Function))
			return false;
		return this.simplify().equals(((Function) o).simplify());
	}

	public abstract int compareSelf(Function that);

	public int compareTo(Function that) {
		if (this.equals(that))
			return 0;
		if (this.getClass().equals(that.getClass()))
			return compareSelf(that);
		return (ArrLib.findClassValue(this) - ArrLib.findClassValue(that));
	}
}
