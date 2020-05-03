package functions;

import java.util.Map;

public interface Evaluable {

	/**
	 * Evaluates a {@link GeneralFunction} at a point
	 * @param variableValues the values of the variables of the {@link GeneralFunction} at the point
	 * @return the value of the {@link GeneralFunction} at the point
	 */
	double evaluate(Map<Character, Double> variableValues);
}
