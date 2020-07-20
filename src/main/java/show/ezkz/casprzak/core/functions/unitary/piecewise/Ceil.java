package show.ezkz.casprzak.core.functions.unitary.piecewise;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.output.OutputFunction;
import show.ezkz.casprzak.core.output.OutputUnitary;
import show.ezkz.casprzak.core.tools.exceptions.DerivativeDoesNotExistException;

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

    public OutputFunction toOutputFunction() {
        return new OutputCeil(operand.toOutputFunction());
    }

    private static class OutputCeil extends OutputUnitary {

        public OutputCeil(OutputFunction operand) {
            super("ceil", operand);
        }

        @Override
        public String toString() {
            return "⌈" + operand + "⌉";
        }

        @Override
        public String toLatex() {
            return "\\left \\lceil " + operand.toLatex() + " \\right \\rceil";
        }

    }
}
