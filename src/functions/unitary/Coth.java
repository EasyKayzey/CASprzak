package functions.unitary;

import functions.Function;

public class Coth extends UnitaryFunction{
    public Coth(Function function) {
        super(function);
    }

    @Override
    public Function getDerivative(int varID) {
        return null;
    }

    @Override
    public double evaluate(double... variableValues) {
        return 0;
    }

    @Override
    public UnitaryFunction me(Function operand) {
        return new Coth(operand);
    }
}
