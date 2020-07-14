package output;

import java.util.Collection;

/**
 * The {@link OutputFunction} is the core of the system for outputting function trees in a readable way
 */
public interface OutputFunction {

	/**
	 * Returns the name of this function
	 * @return the name of this function
	 */
	String getName();

	/**
	 * Returns the operands of this function (its children in the function tree)
	 * @return a collection of operands
	 */
	Collection<OutputFunction> getOperands();

	/**
	 * Returns a text representation of the function tree
	 * @return a text representation of the function tree
	 */
	String toString();

	/**
	 * Returns a {@code LaTeX} representation of the function tree
	 * @return a {@code LaTeX} representation of the function tree
	 */
	String toLatex();

}
