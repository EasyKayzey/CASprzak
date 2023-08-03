package core.functions.unitary.piecewise;

import core.functions.GeneralFunction;
import core.functions.unitary.UnitaryFunction;
import core.output.OutputFunction;
import core.output.OutputUnitary;
import core.tools.exceptions.DerivativeDoesNotExistException;

import java.util.Map;


public class Dirac extends PiecewiseFunction {
	/**
	 * Constructs a new {@link Dirac}
	 * @param operand The function which the Dirac delta function is operating on
	 */
	public Dirac(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public double evaluate(Map<String, Double> variableValues) {
		if (operand.evaluate(variableValues) == 0)
			return Double.POSITIVE_INFINITY;
		else
			return 0;
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		throw new DerivativeDoesNotExistException(this);
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Dirac(operand);
	}

	public OutputFunction toOutputFunction() {
		return new OutputDirac(operand.toOutputFunction());
	}

	private static class OutputDirac extends OutputUnitary {

		public OutputDirac(OutputFunction operand) {
			super("dirac", operand);
		}

		@Override
		public String toString() {
			return "δ(" + operand + ")";
		}

		@Override
		public String toLatex() {
			return "\\delta \\left( " + operand.toLatex() + "\\right)";
		}

	}
}

