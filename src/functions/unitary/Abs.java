package functions.unitary;

import functions.Function;

public class Abs extends UnitaryFunction {
    public Abs(Function function) {
        super(function);
    }

    @Override
    public double evaluate(double... variableValues) {
        return Math.abs(function.evaluate(variableValues));
    }

}
