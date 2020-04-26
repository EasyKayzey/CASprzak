package parsing;

public class ConstantEvaluator {

	private ConstantEvaluator(){}

	/**
	 * Evaluates infix corresponding to a constant, like {@code pi/3}
	 * @param infix infix string of constant
	 * @return a double corresponding to the evaluated constant to be evaluated
	 */
	public static double getConstant(String infix) {
		return Parser.parse(infix).evaluate(null);
	}
}
