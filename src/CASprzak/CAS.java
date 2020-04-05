package CASprzak;

import java.util.*;

public class CAS {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		in.useDelimiter("\n");

		System.out.println("What are your variables? Separate with spaces.");
		String[] varss = in.next().split("\\s+");
		char[] vars = new char[varss.length];
		for (int i = 0; i < vars.length; i++) vars[i] = varss[i].charAt(0);

		System.out.println("Enter your function to be stored:");
		String raw = in.next();

		raw = raw.replace("{","(").replace("}",")").replace("ln","log e").replace("\\","").replace("_"," ");

		System.out.println("What are your inputs? Separate with spaces, and order them with your variables.");
		String[] viss = in.next().split("\\s+");
		double[] vis = Arrays.stream(viss).mapToDouble(Double::parseDouble).toArray();

		PreProcessor preProcessor = new PreProcessor();
		SingleVariableSolver solver = new SingleVariableSolver();
		Parser parser = new Parser(vars);
		Function curFun = parser.parse(preProcessor.toPostfix(raw));
		System.out.println(curFun);
		System.out.println("Here is the simplified toString of your function: " + curFun.simplify());
		System.out.println("Here is your output: " + curFun.evaluate(vis));
		System.out.println("Here is the derivative, unsimplified:");
		System.out.println(curFun.getDerivative(0));
		System.out.println("Here is the derivative, simplified:");
		System.out.println(curFun.getDerivative(0).simplifyTimes(10));
		System.out.println("Here is the derivative, evaluated:");
		System.out.println(curFun.getDerivative(0).simplifyTimes(10).evaluate(vis));
		System.out.println("Here is a zero for the expression");
		System.out.println(solver.getSolutionPoint( curFun, 10));




		// System.out.println(fun.toString());
//		System.out.println("Function val: "+fun.eval(vis));
//		// System.out.println(FunctionEvaluator.df(fun,bov).toString());
//		System.out.println("Derivative val: "+FunctionEvaluator.df(fun,bov).eval(vis));
	}
}

