package functions;

import java.util.Map;

public interface Evaluable {

	/**
	 * Evaluates a {@link GeneralFunction} at a point denoted by a hashmap
	 * @param variableValues the values of the variables in the {@link GeneralFunction} at the point
	 * @return the value of the {@link GeneralFunction} at the point
	 */
	double evaluate(Map<Character, Double> variableValues);
}
