package functions;

import java.util.Map;

public interface Evaluable {

	/**
	 * Evaluates a {@link Function} at a point
	 * @param variableValues the values of the variables of the {@link Function} at the point
	 * @return the value of the {@link Function} at the point
	 */
	double oldEvaluate(Map<Character, Double> variableValues);
}
