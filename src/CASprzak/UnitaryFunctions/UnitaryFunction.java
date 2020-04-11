package CASprzak.UnitaryFunctions;
import CASprzak.CASUI;
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
        if (CASUI.simplifyFunctionsOfConstants && newFunction.getOperand() instanceof Constant)
            return new Constant(newFunction.evaluate());
        return newFunction;
    }

    public abstract UnitaryFunction me(Function function);

    public UnitaryFunction clone() {
        return me(function.clone());
    }

    public UnitaryFunction simplifyInternal() {
        return me(function.simplify());
    }

    public UnitaryFunction substitute(int varID, Function toReplace) {
        return me(function.substitute(varID, toReplace));
    }

    public boolean equals(Function that) {
        return this.getClass().equals(that.getClass()) && this.function.equals(((UnitaryFunction)that).function);
    }

    public int compareSelf(Function that) {
        return (this.getOperand().compareTo(((UnitaryFunction)that).getOperand()));
    }
}
