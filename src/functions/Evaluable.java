package functions;

import java.util.Map;

/**
 * The {@link Evaluable} interface provides an interface for the evaluation of functions.
 */
public interface Evaluable {

	/**
	 * Evaluates a {@link GeneralFunction} at a point denoted by a {@code Map}
	 * @param variableValues the values of the variables in the {@link GeneralFunction} at the point
	 * @return the value of the {@link GeneralFunction} at the point
	 */
	double evaluate(Map<Character, Double> variableValues);
}
