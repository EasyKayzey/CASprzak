package CASprzak.CommutativeFunctions;
import CASprzak.ArrLib;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

public abstract class CommutativeFunction extends Function {
    protected double identityValue;

    protected final Function[] functions;

    public CommutativeFunction(Function... functions) {
        this.functions = functions;
    }

    public Function simplify() {
        return this.simplifyInternal().simplifyOneElement();
    }

    public CommutativeFunction simplifyInternal() {
        return this.simplifyElements().simplifyPull().simplifyIdentity().simplifyConstants();
    }

    protected abstract CommutativeFunction simplifyElements();

    protected abstract CommutativeFunction simplifyIdentity();

    protected abstract CommutativeFunction simplifyConstants();

    protected Function simplifyOneElement() {
        if (functions.length == 0)
            return new Constant(identityValue);
        if (functions.length == 1)
            return functions[0].simplify();
        return this;
    }

    protected CommutativeFunction simplifyPull() {
        for (int i = 0; i < functions.length; i++) {
            if (this.getClass().equals(functions[i].getClass())) {
                return (new Add(ArrLib.pullUp(functions, ((CommutativeFunction) functions[i]).getFunctions(), i))).simplifyInternal();
            }
        }
        return this;
    }

    public Function[] getFunctions() {
        return ArrLib.deepClone(functions);
    }


    public boolean equals(Function that) {
        if (this.getClass().equals(that.getClass()))
            return ArrLib.deepEquals(this.getFunctions(), ((CommutativeFunction)that).getFunctions());
        return false;
    }
}
