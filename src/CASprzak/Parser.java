package CASprzak;

import CASprzak.SpecialFunctions.Constant;

import java.util.*;

public class Parser {
  public static final String[] operations2 = {"^", "*", "+", "logb"};
  public static final String[] operations1 = {"-", "/", "sin", "cos", "tan", "log", "ln", "sqrt", "exp", "sinh", "cosh", "tanh", "csc", "sec", "cot", "asin", "acos", "atan"};

  private final char[] variables;

  public Parser(char... variables) {
    this.variables = variables;
  }

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

  public int getVarID(char variable) throws IndexOutOfBoundsException{
    for (int i = 0; i < variables.length; i++) if (variables[i] == variable) {
      return i;
    }
    throw new IndexOutOfBoundsException("No variable "+variable+" found.");
  }

  public Function parse(String infix) {
    return parse((new PreProcessor()).toPostfix(infix));
  }

  public Function parse(String[] postfix) {
    FunctionMaker functionMaker = new FunctionMaker();
    Stack<Function> functionStack = new Stack<>();
    for (String token : postfix) {
      if (Constant.isSpecialConstant(token)) {
        functionStack.push(new Constant(token));
      } else if (!isAnOperator1(token) && !isAnOperator2(token)) {
        if (Constant.isSpecialConstant(token)) return functionMaker.specialConstant(token);
        try {
          functionStack.push(functionMaker.constant(Double.parseDouble(token)));
        } catch (Exception e) {
          if (token.length() > 1) System.out.println(token + " is not a valid function.");
          char v = token.charAt(0);
          functionStack.push(functionMaker.variable(getVarID(v), variables));
        }
      } else if (isAnOperator2(token)) {
        Function a = functionStack.pop();
        Function b = functionStack.pop();
        functionStack.push(functionMaker.find2(token, a, b));
      } else if (isAnOperator1(token)) {
        Function c = functionStack.pop();
        functionStack.push(functionMaker.find1(token, c));
      }
    }
    if (functionStack.size() != 1) throw new IndexOutOfBoundsException("functionStack size is " + functionStack.size());
    return functionStack.pop();
  }

}
