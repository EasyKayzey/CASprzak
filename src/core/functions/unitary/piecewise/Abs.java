package core.functions.unitary.piecewise;

import core.functions.GeneralFunction;
import core.functions.commutative.Product;
import core.functions.unitary.UnitaryFunction;
import core.output.OutputFunction;
import core.output.OutputUnitary;

import java.util.Map;


public class Abs extends PiecewiseFunction {
	/**
	 * Constructs a new {@link Abs}
	 * @param operand The function which absolute value is operating on
	 */
	public Abs(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return Math.abs(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Sign(operand));
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Abs(operand);
	}

	public String toString() {
		return "|" + operand + "|";
	}

	public OutputFunction toOutputFunction() {
		return new OutputAbs(operand.toOutputFunction());
	}

	private static class OutputAbs extends OutputUnitary {

		public OutputAbs(OutputFunction operand) {
			super("abs", operand);
		}

		@Override
		public String toString() {
			return "|" + operand + "|";
		}

		@Override
		public String toLatex() {
			return " \\left| " + operand.toLatex() + " \\right| ";
		}

	}

}
