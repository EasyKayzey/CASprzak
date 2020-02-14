package CASprzak;

import java.util.*;

public class PreProcessor {
  public static final String[] operations = {"^", "*", "/", "+", "-", "logb", "log", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.ln", "sqrt", "exp", "sinh", "cosh", "tanh"};
  public static final String[] operationsTrig = {"CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.sin", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.cos", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.tan", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.csc", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.sec", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.cot", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.asin", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.acos", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.atan"};

  private int getPrecedence(String input){
    if(input.equals("^")) return 4;
    if(input.equals("*") || input.equals("/")) return 3;
    if(input.equals("+") || input.equals("-")) return 2;
    if(input.equals("(")) return 0;
    return 5;
  }

  public boolean isAnOperator(String input) {
    for (String x : operations) {
      if(x.equals(input)) return true;
    }
    for (String x : operationsTrig) {
      if(x.equals(input)) return true;
    }
    return false;
  }

  public String[] toPostfix(String infix) {
    String[] tokens = infix.split("\\s+");
    ArrayList<String> postfix = new ArrayList<String>();
    Stack<String> operators = new Stack<String>();

      for (String i : tokens) {

        if(isAnOperator(i)) {
          if(operators.empty()) {
            operators.push(i);
          } else if (getPrecedence(i) <= getPrecedence(operators.peek()) && !i.equals("^")) {
            postfix.add(operators.pop());
            operators.push(i);
          } else {
            operators.push(i);
          }
        } else if (i.equals("(")) {
          operators.push(i);
        } else if (i.equals(")")) {

          while (!operators.peek().equals("(")) {
            postfix.add(operators.pop());
          }
          operators.pop();
        } else {
          postfix.add(i);
        }
    }

    while (operators.size() != 0) {
      postfix.add(operators.pop());
    }

    return (String[]) postfix.toArray(new String[postfix.size()]);
  }
}
