package CASprzak;

public interface Differentiable {

    /**
     * Returns the derivative of a function with respect to a variable
     * @param varID the ID of the variable that is differentiated against
     * @return the derivative of the function with respect to varID
     */
    Function getDerivative(int varID);
}
