package core.functions;

import core.output.OutputFunction;

/**
 * The {@link Outputable} interface allows objects, mainly {@link GeneralFunction}s, to be converted to an {@link OutputFunction} for processing.
 */
public interface Outputable {

	/**
	 * Converts this function into an {@link OutputFunction} for use in string conversion
	 * @return this function as an {@link OutputFunction}
	 */
	OutputFunction toOutputFunction();
}
