package CASprzak.UnitaryFunctions;

public class Tanh extends UnitaryFunction {
    @Override
    public String toString() {
        return "tanh(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 0;
    }
}
