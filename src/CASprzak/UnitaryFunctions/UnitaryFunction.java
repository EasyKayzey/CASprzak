package CASprzak.UnitaryFunctions;
import CASprzak.Function;

public abstract class UnitaryFunction extends Function {
    protected Function function;

    public UnitaryFunction(Function function) {
        this.function = function;
    }

    public UnitaryFunction() throws Exception {
        throw new Exception("Cannot instantiate empty function");
    }
}
