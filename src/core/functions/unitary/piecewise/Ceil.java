package core.functions.unitary.piecewise;

import core.functions.GeneralFunction;
import core.functions.unitary.UnitaryFunction;
import core.output.OutputFunction;
import core.output.OutputUnitary;
import core.tools.exceptions.DerivativeDoesNotExistException;

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
