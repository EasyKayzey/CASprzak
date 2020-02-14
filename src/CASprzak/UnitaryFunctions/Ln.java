package CASprzak.UnitaryFunctions;

public class Ln extends UnitaryFunction {
    @Override
    public String toString() {
        return "ln(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 0;
    }
}
