package ui;

import parsing.ConstantEvaluator;
import parsing.Parser;
import parsing.PreProcessor;
import tools.singlevariable.Solver;
import functions.Function;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CASUI {
	private static final Pattern commaSpaces = Pattern.compile("(?<=\\w)(,\\s*|\\s+)(?=\\w)");

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		scanner.useDelimiter("\n");

		System.out.println("What are your variables? Separate with commas and/or spaces.");
		String[] variableStrings = commaSpaces.split(scanner.next());
		char[] variables = new char[variableStrings.length];
		for (int i = 0; i < variables.length; i++)
			variables[i] = variableStrings[i].charAt(0);

		System.out.println("Enter your function to be stored:");
		String rawInput = scanner.next();

		System.out.println("What are your inputs? Separate with commas and/or spaces, and order them with your variables.");
		String[] inputStrings = commaSpaces.split(scanner.next());
		double[] inputs = Arrays.stream(inputStrings).mapToDouble(ConstantEvaluator::getConstant).toArray();
		System.out.println("Processing...");

		Parser.setVariables(variables);
		PreProcessor.setVariables(variables);

		Function currentFunction = Parser.parse(PreProcessor.toPostfix(rawInput));
		System.out.println("Here is your parsed function: " + currentFunction);
		System.out.println("Here is the simplified toString of your function: " + currentFunction.simplifyTimes(10));
		System.out.println("Here is your output: " + currentFunction.evaluate(inputs));

		System.out.println("Here is the derivative, simplified once:");
		System.out.println(currentFunction.getSimplifiedDerivative(0));
		System.out.println("Here is the derivative, simplified completely:");
		System.out.println(currentFunction.getSimplifiedDerivative(0).simplifyTimes(10));

		System.out.println("Here is the derivative, evaluated:");
		System.out.println(currentFunction.getSimplifiedDerivative(0).simplifyTimes(10).evaluate(inputs));

		double solution = Solver.getSolutionPoint(currentFunction, -10);
		if (!Double.isNaN(solution)) {
			System.out.println("Here is one zero for the expression:");
			System.out.println(solution);
		} else {
			System.out.println("The algorithm did not find an initial solution.");
		}

		double[] solutions = Solver.getSolutionsRange(currentFunction, -10, 10);
		if (solutions.length > 0) {
			System.out.println("Here are the zeros of the expression:");
			System.out.println(Arrays.toString(solutions));
		} else {
			System.out.println("The expression has no solutions.");
		}
	}
}

