package parsing;

public class ConstantEvaluator {
	public static double getConstant(String infix) {
		return Parser.parse(infix).evaluate();
	}
}
