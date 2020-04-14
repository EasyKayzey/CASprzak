package functions.unitary;

import functions.Function;

public class Csch extends UnitaryFunction{
    public Csch(Function function) {
        super(function);
    }

    @Override
    public Function getDerivative(int varID) {
        return null;
    }

    @Override
    public double evaluate(double... variableValues) {
        return 1/Math.sinh(function.evaluate(variableValues));
    }

    @Override
    public UnitaryFunction me(Function operand) {
        return new Csch(operand);
    }
}
