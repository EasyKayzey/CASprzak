package CASprzak;

public class ConstantEvaluator {
	public static double getConstant(String infix) {
		Parser parser = new Parser();
		return parser.parse(infix).evaluate();
	}
}
