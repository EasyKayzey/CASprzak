package show.ezkz.casprzak.core.functions.unitary.piecewise;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.output.OutputFunction;
import show.ezkz.casprzak.core.output.OutputUnitary;
import show.ezkz.casprzak.core.tools.exceptions.DerivativeDoesNotExistException;

import java.util.Map;

public class Floor extends PiecewiseFunction {

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
