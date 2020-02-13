import java.util.*;
import java.io.*;

public class FunctionTester {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    System.out.println("Please enter your function");
    String function = in.nextLine();
    System.out.println("Please enter your variables");
    String variableString = in.nextLine();
    String[] variables = variableString.split("\\s+");
    System.out.println("Please enter your values");
    double[] values = new double[variables.length];
    for (int i = 0; i < variables.length; i++) {
      double value = in.nextDouble();
      values[i] = value;
    }
    
  }
}
