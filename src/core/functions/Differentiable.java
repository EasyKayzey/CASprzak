package core.functions;

/**
 * The {@link Differentiable} interface provides an interface for the differentiation of functions.
 */
public interface Differentiable {

	/**
	 * Returns the derivative of a {@link GeneralFunction} with respect to a variable. Should not be used in general; {@link GeneralFunction#getSimplifiedDerivative} is strongly preferred.
	 * @param varID the ID of the variable that is differentiated against
	 * @return the derivative of the function with respect to {@code varID}
	 */
	GeneralFunction getDerivative(String varID);

	/**
	 * Returns the {@code N}th derivative of a {@link GeneralFunction} with respect to a variable
	 * @param varID the ID of the variable that is differentiated against
	 * @param N the amount of times to differentiate
	 * @return the {@code N}th derivative of the function with respect to {@code varID}
	 */
	@SuppressWarnings("unused")
	GeneralFunction getNthDerivative(String varID, int N);
}
