package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;
import tools.exceptions.DerivativeDoesNotExistException;

import java.util.Map;

public class Ceil extends PiecewiseFunction{
    /**
     * Constructs a new {@link Ceil}
     * @param operand The function which ceiling is operating on
     */
    public Ceil(GeneralFunction operand) {
        super(operand);
    }

    @Override
    public UnitaryFunction getInstance(GeneralFunction operand) {
        return new Ceil(operand);
    }

    @Override
    public GeneralFunction getDerivative(String varID) {
        throw new DerivativeDoesNotExistException(this);
    }

    @Override
    public double evaluate(Map<String, Double> variableValues) {
        return Math.ceil(operand.evaluate(variableValues));
    }
}
