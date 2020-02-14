import java.util.*;
import java.io.*;

public class FunctionTester {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

//    System.out.println("Please enter your function");
//    String function = in.nextLine();
//    System.out.println("Please enter your variables");
//    String variableString = in.nextLine();
//    String[] variables = variableString.split("\\s+");
//    System.out.println("Please enter your values");
//    double[] values = new double[variables.length];
//    for (int i = 0; i < variables.length; i++) {
//      double value = in.nextDouble();
//      values[i] = value;
//    }

    Function test1 = Function.makeFunction("3 + 2 * 6 + 2 ^ 2");
    if (test1.evaluate(new String[]{"x"}, new double[]{2.0}) == 19) System.out.println("Test1 worked");

    Function test2 = new Function("2 ^ x");


    
  }
}
