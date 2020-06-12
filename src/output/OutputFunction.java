package output;

import java.util.Collection;

interface OutputFunction {

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
	 * Returns an ASCII representation of the function tree
	 * @return an ASCII representation of the function tree
	 */
	String toAscii();

}
