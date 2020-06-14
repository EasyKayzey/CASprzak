package output;

import java.util.Collection;

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
	 * Returns a string representation of the function tree
	 * @return a string representation of the function tree
	 */
	String toString();

}
