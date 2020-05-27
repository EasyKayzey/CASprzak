package tools.exceptions;

import javax.naming.OperationNotSupportedException;

public class DerivativeDoesNotExistException extends OperationNotSupportedException {
    /**
     * To be thrown when a derivative of a function does not exist
     * @param message the message describing the problem
     */
    public DerivativeDoesNotExistException(String message) {
        super(message);
    }
}
