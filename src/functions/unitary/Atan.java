package functions.unitary;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;

public class Atan extends UnitaryFunction {
    public Atan(Function function) {
        super(function);
    }

    @Override
    public double evaluate(double... variableValues) {
        return Math.atan(function.evaluate(variableValues));
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Add(new Constant(1), new Pow(new Constant(2), function))));
    }

    public UnitaryFunction me(Function operand) {
        return new Atan(operand);
    }

}
