package CASprzak.CommutativeFunctions;
import CASprzak.ArrLib;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

public abstract class CommutativeFunction extends Function {
    double identity;

    protected final Function[] functions;

    public CommutativeFunction(Function... functions) {
        this.functions = functions;
    }

    public CommutativeFunction simplifyInternal() {
        return this.simplifyElements().simplifyIdentity().simplifyConstants();
    }

    protected abstract CommutativeFunction simplifyElements();

    protected abstract CommutativeFunction simplifyIdentity();

    protected abstract CommutativeFunction simplifyConstants();

    protected Function simplifyOneElement() {
        if (functions.length == 0)
            return new Constant(identity);
        if (functions.length == 1)
            return functions[0].simplify();
        return this;
    }


    public Function[] getFunctions() {
        return ArrLib.deepClone(functions);
    }
}
