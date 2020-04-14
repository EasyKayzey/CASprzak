package core;

import functions.Function;
import functions.special.Constant;

import java.util.Stack;

public class Parser {
  public static final String[] binaryOperations = {"^", "*", "+", "logb"};
  public static final String[] unitaryOperations = {"-", "/", "sin", "cos", "tan", "log", "ln", "sqrt", "exp", "abs", "sign", "dirac", "sin", "cos", "tan", "csc", "sec", "cot", "asin", "acos", "atan", "acsc", "asec", "acot", "sinh", "cosh", "tanh", "csch", "sech", "coth", "asinh", "acosh", "atanh", "acsch", "asech", "acoth"};

  private final char[] variables;

  public Parser(char... variables) {
    this.variables = variables;
  }

  public boolean isAnOperator1(String input) {
    for (String x : unitaryOperations) {
      if(x.equals(input)) return true;
    }
    return false;
  }

  public boolean isAnOperator2(String input) {
    for (String x : binaryOperations) {
      if(x.equals(input)) return true;
    }
    return false;
  }

  /**
   * @param variable the character corresponding to the variable
   * @return the ID of the variable, used internally
   * @throws IndexOutOfBoundsException if no such variable exists
   */
  public int getVarID(char variable) throws IndexOutOfBoundsException{
    for (int i = 0; i < variables.length; i++) if (variables[i] == variable) {
      return i;
    }
    throw new IndexOutOfBoundsException("No variable " + variable + " found.");
  }

  public Function parse(String infix) {
    PreProcessor.setVariables(variables);
    return parse(PreProcessor.toPostfix(infix));
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
          if (token.length() > 1)
            throw new IllegalArgumentException(token + " is not a valid function.");
          char v = token.charAt(0);
          functionStack.push(functionMaker.variable(getVarID(v), variables));
        }
      } else if (isAnOperator2(token)) {
        Function a = functionStack.pop();
        Function b = functionStack.pop();
        functionStack.push(functionMaker.makeBinary(token, a, b));
      } else if (isAnOperator1(token)) {
        Function c = functionStack.pop();
        functionStack.push(functionMaker.makeUnitary(token, c));
      }
    }
    if (functionStack.size() != 1) throw new IndexOutOfBoundsException("functionStack size is " + functionStack.size());
    return functionStack.pop();
  }

}
