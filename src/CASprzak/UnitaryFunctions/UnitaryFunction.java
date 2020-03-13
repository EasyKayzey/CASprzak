package CASprzak.UnitaryFunctions;
import CASprzak.Function;
public abstract class UnitaryFunction extends Function {
    protected Function function;

    public UnitaryFunction(Function function) {
        this.function = function;
    }

    public Function getOperand() {
        return function;
    }

    public int compareTo( Function f) {
        return 0;
    }
}
