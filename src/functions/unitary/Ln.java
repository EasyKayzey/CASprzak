package functions.unitary;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Multiply;
import functions.special.Constant;

public class Ln extends UnitaryFunction {
    public Ln(Function function) {
        super(function);
    }

    @Override
    public double evaluate(double... variableValues) {
        return Math.log(function.evaluate(variableValues));
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), function));
    }

    public UnitaryFunction me(Function operand) {
        return new Ln(operand);
    }

}
