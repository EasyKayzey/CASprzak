package parsing;

public class ConstantEvaluator {

	private ConstantEvaluator(){}

	public static double getConstant(String infix) {
		return Parser.parse(infix).evaluate();
	}
}
