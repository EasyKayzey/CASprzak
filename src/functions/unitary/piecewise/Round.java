package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

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
    public GeneralFunction getDerivative(char varID) {
        throw new UnsupportedOperationException("Round has no derivative.");
    }

    @Override
    public double evaluate(Map<Character, Double> variableValues) {
        return Math.round(operand.evaluate(variableValues));
    }
}
