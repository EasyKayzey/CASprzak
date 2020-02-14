package CASprzak;

import java.util.*;

public class Parser {
  public static final String[] operations2 = {"^", "*", "/", "+", "-", "logb"};
  public static final String[] operations1 = {"CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.sin", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.cos", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.tan", "log", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.ln", "sqrt", "exp", "sinh", "cosh", "tanh", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.csc", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.sec", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.cot", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.asin", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.acos", "CASprzak.CASprzak.UnitaryFunctions.CASprzak.UnitaryFunctions.atan"};

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

  public OldFunction parse(String[] postfix) throws IndexOutOfBoundsException {
    Stack<OldFunction> functionStack = new Stack<OldFunction>();
    for (String i : postfix) {
      if (!isAnOperator1(i) && !isAnOperator2(i)) {
        try {
          functionStack.push(new OldFunction(Double.parseDouble(i)));
        } catch (Exception e) {
          functionStack.push(new OldFunction(i));
        }
      } else if (isAnOperator2(i)) {
        OldFunction a = functionStack.pop();
        OldFunction b = functionStack.pop();
        functionStack.push(new OldFunction(i, a, b));
      } else if (isAnOperator1(i)) {
        OldFunction c = functionStack.pop();
        functionStack.push(new OldFunction(i, c));
      }
    }
    if (functionStack.size() != 1) throw new IndexOutOfBoundsException("functionStack size is " + functionStack.size());
    return functionStack.pop();
  }

}
