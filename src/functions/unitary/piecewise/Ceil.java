package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

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
    public GeneralFunction getDerivative(char varID) {
        return DefaultFunctions.ZERO;
    }

    @Override
    public double evaluate(Map<Character, Double> variableValues) {
        return Math.ceil(operand.evaluate(variableValues));
    }
}
