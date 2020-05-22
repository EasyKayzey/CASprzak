package functions.unitary.integer.combo;

import config.Settings;
import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

/**
 * {@link Factorial} is the general class extended in all implementations of factorial. To conform with {@link Settings}, factorial should always be instantiated with {@link #defaultFactorial(GeneralFunction)}.
 */
public abstract class Factorial extends UnitaryFunction {

	/**
	 * Constructs a new {@link Factorial}
	 * @param operand the argument of the factorial
	 */
	public Factorial(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns this representation of factorial in the form of a composition of other raw classes
	 * @return the representation as a composition of other raw classes
	 * @throws UnsupportedOperationException if no such representation exists, such as in the case of recursive factorial {@link RFactorial}
	 */
	public abstract GeneralFunction classForm() throws UnsupportedOperationException;

	/**
	 * Returns a {@link Factorial} of the {@link Settings#defaultFactorial} type
	 * @param input The {@link UnitaryFunction#operand} of the new {@link Factorial}
	 * @return a {@link Factorial} of the {@link Settings#defaultFactorial} type
	 */
	public static Factorial defaultFactorial(GeneralFunction input) {
		return switch (Settings.defaultFactorial) {
			case STIRLING -> new SFactorial(input);
			case RECURSIVE -> new RFactorial(input);
		};
	}

	public String toString() {
		return operand + "!";
	}
}
