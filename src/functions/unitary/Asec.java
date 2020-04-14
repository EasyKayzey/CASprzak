package functions.unitary;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;

public class Asec extends UnitaryFunction {
    public Asec(Function function) {
        super(function);
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Multiply(new Abs(function), new Pow(new Constant(0.5), new Add(new Pow(new Constant(2), function), new Constant(-1))))));
    }

    @Override
    public double evaluate(double... variableValues) {
        return 0;
    }

    @Override
    public UnitaryFunction me(Function operand) {
        return new Asec(operand);
    }
}
