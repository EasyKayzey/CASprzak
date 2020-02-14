package CASprzak.UnitaryFunctions;

public class Tan extends UnitaryFunction {
    @Override
    public String toString() {
        return "tan(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 0;
    }
}
