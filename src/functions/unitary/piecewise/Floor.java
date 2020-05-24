package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

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
        return null;
    }

    @Override
    public GeneralFunction getDerivative(char varID) {
        return null;
    }

    @Override
    public double evaluate(Map<Character, Double> variableValues) {
        return 0;
    }
}
