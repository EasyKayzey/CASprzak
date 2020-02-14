package CASprzak.UnitaryFunctions;

public class Sin extends UnitaryFunction {
    @Override
    public String toString() {
        return "sin(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 0;
    }
}
