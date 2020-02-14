package CASprzak.UnitaryFunctions;

public class Cos extends UnitaryFunction {
    @Override
    public String toString() {
        return "cos(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 0;
    }
}
