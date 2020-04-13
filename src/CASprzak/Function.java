package CASprzak;

import CASprzak.SpecialFunctions.*;
import CASprzak.CommutativeFunctions.*;
import CASprzak.BinaryFunctions.*;
import CASprzak.UnitaryFunctions.*;

import java.util.HashMap;

public abstract class Function implements Evaluable, Differentiable, Simplifiable, Substitutable, Comparable<Function> {
	protected HashMap<Integer, Function> derivatives = new HashMap<>();

	protected static final Class<?>[] sortOrder = {Constant.class, Variable.class, Pow.class, Logb.class, Multiply.class, UnitaryFunction.class, Add.class};

	public abstract String toString();

	public abstract Function clone();

	public Function simplifyTimes(int times) {
		Function newFunction, curFunction = this;
		for (int i = 0; i < times; i++) {
			newFunction = curFunction.simplify();
			if (newFunction.toString().equals(curFunction.toString()))
				return newFunction;
			curFunction = newFunction;
		}
		return curFunction;
	}

	public Function getSimplifiedDerivative(int varID) {
		if (Settings.cacheDerivatives && derivatives.containsKey(varID))
			return derivatives.get(varID);
		Function derivative = getDerivative(varID).simplify();
		if (Settings.cacheDerivatives)
			derivatives.put(varID, derivative);
		return derivative;
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
		else if (this.getClass().equals(that.getClass()))
			return compareSelf(that);
		else
			return (ArrLib.findClassValue(this) - ArrLib.findClassValue(that));
	}
}
