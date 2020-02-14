public class OldFunction {
  private double num;
  private String operation;
  private OldFunction fun1;
  private OldFunction fun2;
  private boolean isNum;
  private boolean isVar;
  private String variable;
  private static String[] operations1 = Parser.operations1;
  private static String[] operations2 = Parser.operations2;

  public static OldFunction makeFunction(String input){
    PreProcessor preprocessor = new PreProcessor();
    Parser parser = new Parser();
    return parser.parse(preprocessor.toPostfix(input));
  }

  private boolean isAVariable(String input, String[] variables) {
    for (String x : variables) {
      if(x.equals(input)) return true;
    }
    return false;
  }

  private int indexOf(String input, String[] variables) {
    for (int i = 0; i < variables.length; i++) {
      if(input.equals(variables[i])) return i;
    }
    return -1;
  }

  OldFunction(String operation, OldFunction fun1, OldFunction fun2){
    this.operation = operation;
    this.fun1 = fun1;
    this.fun2 = fun2;
    isNum = false;
  }

  OldFunction(String operation, OldFunction fun1){
    this.operation = operation;
    this.fun1 = fun1;
    isNum = false;
  }

  OldFunction(double num){
    this.num = num;
    isNum = true;
  }

  OldFunction(String variable) {
    this.variable = variable;
    isVar = true;
  }

  public double evaluate(String[] variables, double[] values) {
    if (isVar) return values[indexOf(variable, variables)];
    else if (isNum) return num;
    else {
      if(operation.equals("+")) return fun2.evaluate(variables, values) + fun1.evaluate(variables, values);
      if(operation.equals("-")) return fun2.evaluate(variables, values) - fun1.evaluate(variables, values);
      if(operation.equals("*")) return fun2.evaluate(variables, values) * fun1.evaluate(variables, values);
      if(operation.equals("/")) return fun2.evaluate(variables, values) / fun1.evaluate(variables, values);
      if(operation.equals("^")) return Math.pow(fun2.evaluate(variables, values), fun1.evaluate(variables, values));
      if(operation.equals("logb")) return Math.log(fun1.evaluate(variables, values)) / Math.log(fun2.evaluate(variables, values));
      if(operation.equals("sin")) return Math.sin(fun1.evaluate(variables, values));
      if(operation.equals("cos")) return Math.cos(fun1.evaluate(variables, values));
      if(operation.equals("tan")) return Math.tan(fun1.evaluate(variables, values));
      if(operation.equals("sec")) return 1 / Math.cos(fun1.evaluate(variables, values));
      if(operation.equals("csc")) return 1 / Math.sin(fun1.evaluate(variables, values));
      if(operation.equals("cot")) return 1 / Math.tan(fun1.evaluate(variables, values));
      if(operation.equals("asin")) return Math.asin(fun1.evaluate(variables, values));
      if(operation.equals("acos")) return Math.acos(fun1.evaluate(variables, values));
      if(operation.equals("atan")) return Math.atan(fun1.evaluate(variables, values));
      if(operation.equals("sinh")) return Math.sinh(fun1.evaluate(variables, values));
      if(operation.equals("cosh")) return Math.cosh(fun1.evaluate(variables, values));
      if(operation.equals("tanh")) return Math.tanh(fun1.evaluate(variables, values));
      if(operation.equals("exp")) return Math.exp(fun1.evaluate(variables, values));
      if(operation.equals("log")) return Math.log10(fun1.evaluate(variables, values));
      if(operation.equals("ln")) return Math.log(fun1.evaluate(variables, values));
      if(operation.equals("sqrt")) return Math.sqrt(fun1.evaluate(variables, values));
      return Double.NaN;
    }
  }

  public String toString() {
    StringBuilder temp1 = new StringBuilder();
    if (isNum) {
      temp1.append(num);
    } else {
      if(operation.equals("+")) temp1.append( "(" + fun2.toString() + ") +  (" + fun1.toString() + ")");
      if(operation.equals("-")) temp1.append( fun2.toString() + " - " + fun1.toString());
      if(operation.equals("*")) temp1.append( fun2.toString() + " * " + fun1.toString());
      if(operation.equals("/")) temp1.append( fun2.toString() + " / " + fun1.toString());
      if(operation.equals("^")) temp1.append( fun2.toString() + " ^ " + fun1.toString());
      if(operation.equals("logb")) temp1.append( "logb " + fun2.toString() + " " + fun1.toString());
      if(operation.equals("sin")) temp1.append( "sin " + fun1.toString());
      if(operation.equals("cos")) temp1.append( "cos " + fun1.toString());
      if(operation.equals("tan")) temp1.append( "tan " + fun1.toString());
      if(operation.equals("ln")) temp1.append( "ln " + fun1.toString());
      if(operation.equals("log")) temp1.append( "log " + fun1.toString());
      if(operation.equals("sqrt")) temp1.append( "sqrt " + fun1.toString());
      if(operation.equals("sec")) temp1.append( "sec " + fun1.toString());
      if(operation.equals("csc")) temp1.append( "csc " + fun1.toString());
      if(operation.equals("cot")) temp1.append( "cot " + fun1.toString());
      if(operation.equals("asin")) temp1.append( "asin " + fun1.toString());
      if(operation.equals("acos")) temp1.append( "acos " + fun1.toString());
      if(operation.equals("atan")) temp1.append( "atan " + fun1.toString());
      if(operation.equals("sinh")) temp1.append( "sinh " + fun1.toString());
      if(operation.equals("cosh")) temp1.append( "cosh " + fun1.toString());
      if(operation.equals("tanh")) temp1.append( "tanh " + fun1.toString());
      if(operation.equals("exp")) temp1.append( "exp " + fun1.toString());
    }
    return temp1.toString();
  }

  public OldFunction derivative(String variable) {
    if (isNum || isVar) {
      if (isVar && this.variable.equals(variable)) return new OldFunction(1);
      return new OldFunction(0);
    } else {
      if(operation.equals("+")) return add(fun2.derivative(variable), fun1.derivative(variable));
      if(operation.equals("-")) return sub(fun2.derivative(variable), fun1.derivative(variable));
      if(operation.equals("*")) return add(mul(fun1, fun2.derivative(variable)), mul(fun1.derivative(variable), fun2));
      if(operation.equals("/")) return div(sub(mul(fun1, fun2.derivative(variable)), mul(fun1.derivative(variable), fun2)), pow(fun1, new OldFunction(2)));
      if(operation.equals("^")) return mul(pow(fun1, fun2), add(mul(fun1.derivative(variable), ln(fun2)), div(mul(fun1, fun2.derivative(variable)), fun2)));
      if(operation.equals("logb")) return div(sub(div(mul(fun1.derivative(variable), ln(fun2)), fun1), div(mul(fun2.derivative(variable), ln(fun1)), fun2)), pow(ln(fun2), new OldFunction(2)));
      if(operation.equals("sin")) return mul(cos(fun1), fun1.derivative(variable));
      if(operation.equals("cos")) return mul(mul(sin(fun1), new OldFunction(-1)), fun1.derivative(variable));
      if(operation.equals("tan")) return mul(pow(sec(fun1), new OldFunction(2)), fun1.derivative(variable));
      if(operation.equals("csc")) return mul(mul(mul(cot(fun1), csc(fun1)), new OldFunction(-1)), fun1.derivative(variable));
      if(operation.equals("sec")) return mul(mul(tan(fun1), sec(fun1)), fun1.derivative(variable));
      if(operation.equals("cot")) return mul(mul(pow(csc(fun1), new OldFunction(2)), new OldFunction(-1)), fun1.derivative(variable));
      if(operation.equals("sinh")) return mul(cosh(fun1), fun1.derivative(variable));
      if(operation.equals("cosh")) return mul(sinh(fun1), fun1.derivative(variable));
      if(operation.equals("tanh")) return div(fun1.derivative(variable), pow(cosh(fun1), new OldFunction(2)));
      if(operation.equals("asin")) return div(fun1.derivative(variable), sqrt(sub(pow(fun1, new OldFunction(2)), new OldFunction(1))));
      if(operation.equals("acos")) return div(mul(fun1.derivative(variable), new OldFunction(-1)), sqrt(sub(new OldFunction(1), pow(fun1, new OldFunction(2)))));
      if(operation.equals("atan")) return div(fun1.derivative(variable), add(new OldFunction(1), pow(fun1, new OldFunction(2))));
      if(operation.equals("log")) return div(fun1.derivative(variable), mul(new OldFunction("ln", new OldFunction(10)), fun1));
      if(operation.equals("ln")) return div(fun1.derivative(variable), fun1);
      if(operation.equals("sqrt")) return div(fun1.derivative(variable), mul(sqrt(fun1), new OldFunction(2)));
      if(operation.equals("exp")) return mul(exp(fun1), fun1.derivative(variable));
    }
    throw new IndexOutOfBoundsException("This function "+operation+" does not exist");
  }

  static OldFunction mul(OldFunction a, OldFunction b) {
     return new OldFunction("*", b, a);
   }
  static OldFunction div(OldFunction a, OldFunction b) {
    return new OldFunction("/", b, a);
  }
  static OldFunction add(OldFunction a, OldFunction b){
    return new OldFunction("+", b, a);
  }
  static OldFunction sub(OldFunction a, OldFunction b){
    return new OldFunction("-", b, a);
  }
  static OldFunction pow(OldFunction a, OldFunction b){
    return new OldFunction("^", b, a);
  }
  static OldFunction ln(OldFunction a){
    return new OldFunction("ln", a);
  }
  static OldFunction log(OldFunction a){
    return new OldFunction("log", a);
  }
  static OldFunction sin(OldFunction a){
    return new OldFunction("sin", a);
  }
  static OldFunction cos(OldFunction a){
    return new OldFunction("cos", a);
  }
  static OldFunction tan(OldFunction a){
    return new OldFunction("tan", a);
  }
  static OldFunction csc(OldFunction a){
    return div(new OldFunction(1), sin(a));
  }
  static OldFunction sec(OldFunction a){
    return div(new OldFunction(1), cos(a));
  }
  static OldFunction cot(OldFunction a){
    return div(new OldFunction(1), tan(a));
  }
  static OldFunction sqrt(OldFunction a){
    return new OldFunction("^", a, new OldFunction(.5));
  }
  static OldFunction sinh(OldFunction a){
    return new OldFunction("sinh", a);
  }
  static OldFunction cosh(OldFunction a){
    return new OldFunction("cosh", a);
  }
  static OldFunction tanh(OldFunction a){
    return new OldFunction("tanh", a);
  }
  static OldFunction asin(OldFunction a){
    return new OldFunction("asin", a);
  }
  static OldFunction acos(OldFunction a){
    return new OldFunction("acos", a);
  }
  static OldFunction atan(OldFunction a){
    return new OldFunction("atan", a);
  }
  static OldFunction exp(OldFunction a){
    return new OldFunction("exp", a);
  }


}
