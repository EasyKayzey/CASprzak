package CASprzak;

import CASprzak.SpecialFunctions.*;
import CASprzak.CommutativeFunctions.*;
import CASprzak.BinaryFunctions.*;
import CASprzak.UnitaryFunctions.*;

public abstract class Function implements Evaluable, Differentiable, Simplifiable, Comparable<Function> {
	protected static final Class<?>[] sortOrder = {Negative.class, Constant.class, Variable.class, Pow.class, Logb.class, Multiply.class, UnitaryFunction.class, Add.class, Reciprocal.class};

	public abstract String toString();

	public abstract Function clone();

	public Function simplifyTimes(int times) {
		Function temp = this;
		for (int i = 0; i < times; i++) temp = temp.simplify();
		return temp;
	}

	public abstract boolean equals(Function f);

	public boolean equals(Object o) {
		if (!(o instanceof Function))
			return false;
		return this.equals((Function) o);
	}
}
