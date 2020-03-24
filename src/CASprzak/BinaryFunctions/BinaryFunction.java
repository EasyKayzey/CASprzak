package CASprzak.BinaryFunctions;
import CASprzak.Differentiable;
import CASprzak.Function;
public abstract class BinaryFunction extends Function {
    protected final Function function1;
    protected final Function function2;

    public BinaryFunction(Function function1, Function function2) {
        this.function1 = function1;
        this.function2 = function2;
    }

    public int compareTo( Function f) {
        return 0;
    }

    public Function getFunction1() {
        return function1;
    }

    public Function getFunction2() {
        return function2;
    }
}
