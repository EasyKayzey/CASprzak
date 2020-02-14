package CASprzak.CommutativeFunctions;
import CASprzak.Function;

import javax.naming.CommunicationException;

public abstract class CommutativeFunction extends Function {
    protected Function[] functions;

    public CommutativeFunction(Function[] functions) {
        this.functions = functions;
    }

    public CommutativeFunction(Function f1, Function f2) {
        this.functions = new Function[] {f1, f2};
    }
}
