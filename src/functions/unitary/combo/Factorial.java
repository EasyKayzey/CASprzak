package functions.unitary.combo;

import config.Settings;
import functions.Function;
import functions.unitary.UnitaryFunction;
import tools.exceptions.NotYetImplementedException;

public abstract class Factorial extends UnitaryFunction {

	public Factorial(Function operand) {
		super(operand);
	}

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
