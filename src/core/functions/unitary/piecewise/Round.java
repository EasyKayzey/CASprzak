package core.functions.unitary.piecewise;

import core.functions.GeneralFunction;
import core.functions.unitary.UnitaryFunction;
import core.tools.exceptions.DerivativeDoesNotExistException;

import java.util.Map;

public class Round extends PiecewiseFunction{
    /**
     * Constructs a new {@link Round}
     * @param operand The function which the round is operating on
     */
    public Round(GeneralFunction operand) {
        super(operand);
    }

    @Override
    public UnitaryFunction getInstance(GeneralFunction operand) {
        return new Round(operand);
    }

    @Override
    public GeneralFunction getDerivative(String varID) {
        throw new DerivativeDoesNotExistException(this);
    }

    @Override
    public double evaluate(Map<String, Double> variableValues) {
        return Math.round(operand.evaluate(variableValues));
    }
}
