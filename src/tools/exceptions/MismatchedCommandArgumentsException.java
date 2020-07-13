package tools.exceptions;

public class MismatchedCommandArgumentsException extends IllegalArgumentException{
    /**
     * To be thrown when an input is not the required length in KeywordInterface
     * @param expectedLength the expected length of the input
     * @param actualLength the actual length of the input
     */
    public MismatchedCommandArgumentsException(String expectedLength, int actualLength) {
        super("Expected " + expectedLength +  " arguments and received " + actualLength + ".");
    }

    /**
     * To be thrown when an input is not the required length in KeywordInterface
     * @param message the error message
     */
    public MismatchedCommandArgumentsException(String message) {
        super(message);
    }
}
