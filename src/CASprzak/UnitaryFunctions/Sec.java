package CASprzak.UnitaryFunctions;

public class Sec extends UnitaryFunction {
    @Override
    public String toString() {
        return "sec(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 0;
    }
}
