package tools.exceptions;

import functions.GeneralFunction;

public class IntegrationFailedException extends TransformFailedException {

	private final GeneralFunction function;

	/**
	 * To be thrown when the integral of a function cannot be found
	 * @param function the non-integrable function
	 */
	public IntegrationFailedException(GeneralFunction function) {
		super("The integral of " + function.getClass().getSimpleName() + " could not be found.");
		this.function = function;
	}

	/**
	 * Returns the non-integrable function
	 * @return the non-integrable function
	 */
	public GeneralFunction getFunction() {
		return function;
	}
}
