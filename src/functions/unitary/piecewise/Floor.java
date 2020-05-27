package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;
import tools.exceptions.DerivativeDoesNotExistException;

import java.util.Map;

public class Floor extends PiecewiseFunction{
    /**
     * Constructs a new {@link Floor}
     * @param operand The function which signum is operating on
     */
    public Floor(GeneralFunction operand) {
        super(operand);
    }

    @Override
    public UnitaryFunction getInstance(GeneralFunction operand) {
        return new Floor(operand);
    }

    @Override
    public GeneralFunction getDerivative(char varID) {
        throw new DerivativeDoesNotExistException(this);
    }

    @Override
    public double evaluate(Map<Character, Double> variableValues) {
        return Math.floor(operand.evaluate(variableValues));
    }
}
