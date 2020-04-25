package functions;

public interface Differentiable {

	/**
	 * Returns the derivative of a {@link Function} with respect to a variable
	 * @param varID the ID of the variable that is differentiated against
	 * @return the derivative of the function with respect to varID
	 */
	Function getDerivative(char varID);

	/**
	 * Returns the Nth derivative of a {@link Function} with respect to a variable
	 * @param varID the ID of the variable that is differentiated against
	 * @param N the amount of times to be differentiated
	 * @return the derivative of the function with respect to varID
	 */
	Function getNthDerivative(char varID, int N);
}
