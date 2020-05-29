package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;
import tools.exceptions.DerivativeDoesNotExistException;

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
