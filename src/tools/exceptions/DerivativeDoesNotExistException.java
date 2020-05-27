package tools.exceptions;

import functions.GeneralFunction;

public class DerivativeDoesNotExistException extends UnsupportedOperationException {

    private final GeneralFunction function;

    /**
     * To be thrown when a derivative of a function does not exist
     * @param function the non-differentiable function
     */
    public DerivativeDoesNotExistException(GeneralFunction function) {
        super("The derivative of " + function.getClass().getSimpleName() + " does not exist.");
        this.function = function;
    }

    /**
     * Returns the non-differentiable function
     * @return the non-differentiable function
     */
    public GeneralFunction getFunction() {
        return function;
    }
}
