package CASprzak.BinaryFunctions;
import CASprzak.CommutativeFunctions.CommutativeFunction;
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

    public abstract BinaryFunction me(Function function1, Function function2);

    public Function substitute(int varID, Function toReplace) {
        return me(function1.substitute(varID, toReplace), function2.substitute(varID, toReplace));
    }


    public boolean equals(Function that) {
        return this.getClass().equals(that.getClass()) && this.function1.equals(((BinaryFunction)that).function1) && this.function2.equals(((BinaryFunction)that).function2);
    }

    public int compareSelf(Function that) {
        if (!this.function1.equals(((BinaryFunction)that).function1))
            return this.function1.compareSelf(((BinaryFunction)that).function1);
        if (!this.function2.equals(((BinaryFunction)that).function2))
            return this.function2.compareSelf(((BinaryFunction)that).function2);
        System.out.println("This is never supposed to happen, binaryFunction compareSelf");
        return 0;
    }
}
