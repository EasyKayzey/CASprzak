package functions;

import output.OutputFunction;

public interface Outputable {

	/**
	 * Converts this function into an {@link OutputFunction} for use in string conversion
	 * @return this function as an {@link OutputFunction}
	 */
	OutputFunction toOutputFunction();
}
