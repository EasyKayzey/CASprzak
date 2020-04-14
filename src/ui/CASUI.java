package ui;

import core.ConstantEvaluator;
import core.Parser;
import core.PreProcessor;
import core.SingleVariableSolver;
import functions.Function;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CASUI {
	private static final Pattern commaSpaces = Pattern.compile(",\\s*");

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		in.useDelimiter("\n");

		System.out.println("What are your variables? Separate with spaces.");
		String[] varsTemp = commaSpaces.split(in.next());
		char[] vars = new char[varsTemp.length];
		for (int i = 0; i < vars.length; i++) vars[i] = varsTemp[i].charAt(0);

		System.out.println("Enter your function to be stored:");
		String raw = in.next();

		System.out.println("What are your inputs? Separate with commas, and order them with your variables.");
		String[] visTemp = commaSpaces.split(in.next());
		double[] vis = Arrays.stream(visTemp).mapToDouble(ConstantEvaluator::getConstant).toArray();
		System.out.println("Processing...");

		PreProcessor preProcessor = new PreProcessor(vars);
		SingleVariableSolver solver = new SingleVariableSolver();
		Parser parser = new Parser(vars);

		Function curFun = parser.parse(preProcessor.toPostfix(raw));
		System.out.println("Here is your parsed function: " + curFun);
		System.out.println("Here is the simplified toString of your function: " + curFun.simplifyTimes(10));
		System.out.println("Here is your output: " + curFun.evaluate(vis));

		System.out.println("Here is the derivative, simplified once:");
		System.out.println(curFun.getSimplifiedDerivative(0));
		System.out.println("Here is the derivative, simplified completely:");
		System.out.println(curFun.getSimplifiedDerivative(0).simplifyTimes(10));

		System.out.println("Here is the derivative, evaluated:");
		System.out.println(curFun.getSimplifiedDerivative(0).simplifyTimes(10).evaluate(vis));

		double solution = SingleVariableSolver.getSolutionPoint(curFun, -10);
		if (!Double.isNaN(solution)) {
			System.out.println("Here is one zero for the expression:");
			System.out.println(solution);
		} else {
			System.out.println("The algorithm did not find an initial solution.");
		}

		double[] solutions = SingleVariableSolver.getSolutionsRange(curFun, -10, 10);
		if (solutions.length > 0) {
			System.out.println("Here are the zeros of the expression:");
			System.out.println(Arrays.toString(solutions));
		} else {
			System.out.println("The expression has no solutions.");
		}
	}
}

