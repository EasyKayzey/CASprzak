package ui;

import config.Settings;
import functions.Function;
import functions.special.Variable;
import parsing.Parser;
import parsing.PreProcessor;
import tools.singlevariable.NumericalIntegration;
import tools.singlevariable.Solver;
import tools.singlevariable.TaylorSeries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CASDemo {
	private static final Pattern commaSpaces = Pattern.compile("(?<=\\w)(,\\s*|\\s+)(?=\\w)");

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		scanner.useDelimiter("\n");

		System.out.println("What are your variables? Separate with commas and/or spaces.");
		String[] variableStrings = commaSpaces.split(scanner.next());
		Character[] variables = new Character[variableStrings.length];
		for (int i = 0; i < variables.length; i++)
			variables[i] = Parser.getCharacter(variableStrings[i]);

		System.out.println("Enter your function to be stored:");
		String rawInput = scanner.next();

		System.out.println("What are your inputs? Separate with commas and/or spaces, and order them with your variables.");
		String[] inputStrings = commaSpaces.split(scanner.next());
		double[] inputs = Arrays.stream(inputStrings).mapToDouble(Parser::getConstant).toArray();

		if (inputs.length != variables.length)
			throw new IllegalArgumentException("Amount of values and amount of variables do not match!");

		System.out.println("Processing...");

		Variable.setVariablesArray(variables);
		Settings.singleVariableDefault = variables[0];
		Map<Character, Double> map = new HashMap<>();
		for (int i = 0; i < variables.length; i++)
			map.put(variables[i], inputs[i]);

		Function currentFunction = Parser.parse(PreProcessor.toPostfix(rawInput));
		System.out.println("Here is your parsed function: " + currentFunction);
		System.out.println("Here is the simplified toString of your function: " + currentFunction.simplifyTimes(10));
		System.out.println("Here is your output: " + currentFunction.evaluate(map));

		System.out.println("Here is the derivative with respect to " + Variable.variables.get(0) + ", simplified once:");
		System.out.println(currentFunction.getSimplifiedDerivative(Variable.variables.get(0)));
		System.out.println("Here is the derivative with respect to " + Variable.variables.get(0) + ", simplified completely:");
		System.out.println(currentFunction.getSimplifiedDerivative(Variable.variables.get(0)).simplifyTimes(10));

		System.out.println("Here is the derivative with respect to " + Variable.variables.get(0) + ", evaluated:");
		System.out.println(currentFunction.getSimplifiedDerivative(Variable.variables.get(0)).simplifyTimes(10).evaluate(map));

		if (Variable.variables.size() == 1) {
			double solution = Solver.getSolutionPointNewton(currentFunction, -10);
			if (!Double.isNaN(solution)) {
				System.out.println("Here is one zero for the expression:");
				System.out.println(solution);
			} else {
				System.out.println("The algorithm did not find an initial solution.");
			}

			double[] solutions = Solver.getSolutionsRangeNewton(currentFunction, -10, 10);
			if (solutions.length > 0) {
				System.out.println("Here are the zeros of the expression:");
				System.out.println(Arrays.toString(solutions));
			} else {
				System.out.println("The expression has no solutions.");
			}

			System.out.println("Here is a 5 term Taylor Series: " + TaylorSeries.makeTaylorSeries(currentFunction, 5).simplify());
			System.out.println("Here is the integral from 0 to 1: " + NumericalIntegration.simpsonsRule(currentFunction, 0, 1));
		} else {
			System.out.println("Finding solutions or extrema is not yet supported with multivariable functions.");
		}
	}
}

