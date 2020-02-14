package CASprzak;

import java.util.*;
import java.io.*;
public class CAS {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		in.useDelimiter("\n");

		System.out.println("What are your variables? Separate with spaces.");
		String[] vars = in.next().split("\\s+");
		System.out.println("Enter your function to be stored:");
		String raw = in.next();

		raw = raw.replace("{","(").replace("}",")").replace("ln","log e").replace("\\","").replace("_"," ");

		System.out.println("What are your inputs? Separate with spaces.");
		String[] viss = in.next().split("\\s+");
		double[] vis = Arrays.stream(viss).mapToDouble(Double::parseDouble).toArray();
		// System.out.println(fun.toString());
		boolean[] bov = new boolean[vis.length];
		for (int i = 0; i < bov.length; i++) if (vis[i]!=0) bov[i]=true;
		PreProcessor test1 = new PreProcessor(raw);

		// System.out.println(fun.toString());
//		System.out.println("Function val: "+fun.eval(vis));
//		// System.out.println(FunctionEvaluator.df(fun,bov).toString());
//		System.out.println("Derivative val: "+FunctionEvaluator.df(fun,bov).eval(vis));
	}
}

