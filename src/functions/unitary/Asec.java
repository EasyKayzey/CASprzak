package functions.unitary;

import functions.Function;

public class Asec extends UnitaryFunction {
    public Asec(Function function) {
        super(function);
    }

    @Override
    public UnitaryFunction me(Function operand) {
        return new Asec(operand);
    }

    @Override
    public Function getDerivative(int varID) {
        return null;
    }

    @Override
    public double evaluate(double... variableValues) {
        return 0;
    }
}
