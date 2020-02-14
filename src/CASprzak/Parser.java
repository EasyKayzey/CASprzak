package CASprzak;

import java.util.*;

public class Parser {
  public static final String[] operations2 = {"^", "*", "/", "+", "-", "logb"};
  public static final String[] operations1 = {"sin", "cos", "tan", "log", "ln", "sqrt", "exp", "sinh", "cosh", "tanh", "csc", "sec", "cot", "asin", "acos", "atan"};

  public boolean isAnOperator1(String input) {
    for (String x : operations1) {
      if(x.equals(input)) return true;
    }
    return false;
  }

  public boolean isAnOperator2(String input) {
    for (String x : operations2) {
      if(x.equals(input)) return true;
    }
    return false;
  }

  public Function parse(String[] postfix) throws Exception {

    FunctionMaker functionMaker = new FunctionMaker();
    Stack<Function> functionStack = new Stack<Function>();
    for (String i : postfix) {
      if (!isAnOperator1(i) && !isAnOperator2(i)) {
        try {
          functionStack.push(functionMaker.constant(Double.parseDouble(i)));
        } catch (Exception e) {
          if (i.length() > 1) System.out.println(i + " is not a valid function.");
          functionStack.push(functionMaker.variable(i.charAt(0)));
        }
      } else if (isAnOperator2(i)) {
        Function a = functionStack.pop();
        Function b = functionStack.pop();
        functionStack.push(functionMaker.find2(i, a, b));
      } else if (isAnOperator1(i)) {
        Function c = functionStack.pop();
        functionStack.push(functionMaker.find1(i, c));
      }
    }
    if (functionStack.size() != 1) throw new IndexOutOfBoundsException("functionStack size is " + functionStack.size());
    return functionStack.pop();
  }

}
