package CASprzak.UnitaryFunctions;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

public abstract class UnitaryFunction extends Function {
    protected final Function function;
    public UnitaryFunction(Function function) {
        this.function = function;
    }

    public Function getOperand() {
        return function;
    }

    public String toString() {
        return this.getClass().getSimpleName().toLowerCase() + "(" + function.toString() + ")";
    }

    public Function simplify() {
        UnitaryFunction newFunction = this.simplifyInternal();
        if (newFunction.getOperand() instanceof Constant)
            return new Constant(newFunction.evaluate(0));
        return newFunction;
    }

    public abstract UnitaryFunction simplifyInternal();

    public boolean equals(Function that) {
        return this.getClass().equals(that.getClass()) && this.function.equals(((UnitaryFunction)that).function);
    }

    public int compareSelf(Function that) {
        return (this.getOperand().compareTo(((UnitaryFunction)that).getOperand()));
    }
}
