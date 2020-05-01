package functions.unitary.combo;

import config.Settings;
import functions.Function;
import functions.unitary.UnitaryFunction;
import tools.exceptions.NotYetImplementedException;

public abstract class Factorial extends UnitaryFunction {

	public Factorial(Function function) {
		super(function);
	}

	/**
	 * Returns the specific approximation used for this factorial in the form of a {@link Function}
	 * @return a {@link Function} representing the specific approximation
	 */
	public abstract Function classForm();

	public static Function defaultFactorial(Function input) {
		return switch (Settings.defaultFactorial) {
			case STIRLING -> new SFactorial(input);
			case LANCZOS -> throw new NotYetImplementedException("LFactorial not implemented");
			case RECURSIVE -> new RFactorial(input);
		};
	}

	public String toString() {
		return operand + "!";
	}
}
