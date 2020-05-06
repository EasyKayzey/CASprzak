package functions;

public interface Differentiable {

	/**
	 * Returns the derivative of a {@link GeneralFunction} with respect to a variable. Should not be used in general; {@link GeneralFunction#getSimplifiedDerivative(char)} is strongly preferred.
	 * @param varID the ID of the variable that is differentiated against
	 * @return the derivative of the function with respect to varID
	 */
	GeneralFunction getDerivative(char varID);

	/**
	 * Returns the Nth derivative of a {@link GeneralFunction} with respect to a variable
	 * @param varID the ID of the variable that is differentiated against
	 * @param N the amount of times to be differentiated
	 * @return the derivative of the function with respect to varID
	 */
	@SuppressWarnings("unused")
	GeneralFunction getNthDerivative(char varID, int N);
}
