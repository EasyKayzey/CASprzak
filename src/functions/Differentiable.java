package functions;

public interface Differentiable {

    /**
     * Returns the derivative of a {@link Function} with respect to a variable
     * @param varID the ID of the variable that is differentiated against
     * @return the derivative of the function with respect to varID
     */
    Function getDerivative(int varID);
}
