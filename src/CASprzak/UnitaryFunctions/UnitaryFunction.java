package CASprzak.UnitaryFunctions;
import CASprzak.Function;
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

    public boolean equals(Function that) {
        return this.getClass().equals(that.getClass()) && this.function.equals(((UnitaryFunction)that).function);
    }

    public int compareSelf(Function that) {
        return (this.getOperand().compareTo(((UnitaryFunction)that).getOperand()));
    }
}
