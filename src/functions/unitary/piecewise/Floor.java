package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;
import output.OutputFunction;
import output.OutputUnitary;
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
    public GeneralFunction getDerivative(String varID) {
        throw new DerivativeDoesNotExistException(this);
    }

    @Override
    public double evaluate(Map<String, Double> variableValues) {
        return Math.floor(operand.evaluate(variableValues));
    }

    public OutputFunction toOutputFunction() {
        return new OutputFloor(operand.toOutputFunction());
    }

    private static class OutputFloor extends OutputUnitary {

        public OutputFloor(OutputFunction operand) {
            super("floor", operand);
        }

        @Override
        public String toString() {
            return "⌊" + operand + "⌋";
        }

        @Override
        public String toLatex() {
            return "\\left \\lfloor " + operand.toLatex() + " \\right \\rfloor";
        }

    }
}
