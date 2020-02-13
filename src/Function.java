public class Function {
  private String num;
  private String operation;
  private Function fun1;
  private Function fun2;
  private boolean isNum;
  private static String[] operations1 = Parser.operations1;
  private static String[] operations2 = Parser.operations2;

  public static Function makeFunction(String input){
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

  Function (String operation, Function fun1, Function fun2){
    this.operation = operation;
    this.fun1 = fun1;
    this.fun2 = fun2;
    isNum = false;
  }

  Function (String operation, Function fun1){
    this.operation = operation;
    this.fun1 = fun1;
    isNum = false;
  }

  Function (String num){
    this.num = num;
    isNum = true;
  }

  public double evaluate(String[] variables, double[] values) {
    if (isNum) {
      if(isAVariable(num, variables)) return values[indexOf(num, variables)];
      return Double.valueOf(num);
    } else {
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

  public Function derivative(String variable) {
    if (isNum) {
      if(num.equals(variable)) return new Function("1");
      return new Function("0");
    } else {
      if(operation.equals("+")) return add(fun2.derivative(variable), fun1.derivative(variable));
      if(operation.equals("-")) return sub(fun2.derivative(variable), fun1.derivative(variable));
      if(operation.equals("*")) return add(mul(fun1, fun2.derivative(variable)), mul(fun1.derivative(variable), fun2));
      if(operation.equals("/")) return div(sub(mul(fun1, fun2.derivative(variable)), mul(fun1.derivative(variable), fun2)), pow(fun1, new Function("2")));
      if(operation.equals("^")) return mul(pow(fun1, fun2), add(mul(fun1.derivative(variable), ln(fun2)), div(mul(fun1, fun2.derivative(variable)), fun2)));
      if(operation.equals("logb")) return div(sub(div(mul(fun1.derivative(variable), ln(fun2)), fun1), div(mul(fun2.derivative(variable), ln(fun1)), fun2)), pow(ln(fun2), new Function("2")));
      if(operation.equals("sin")) return mul(cos(fun1), fun1.derivative(variable));
      if(operation.equals("cos")) return mul(mul(sin(fun1), new Function("-1")), fun1.derivative(variable));
      if(operation.equals("tan")) return mul(pow(sec(fun1), new Function("2")), fun1.derivative(variable));
      if(operation.equals("csc")) return mul(mul(mul(cot(fun1), csc(fun1)), new Function("-1")), fun1.derivative(variable));
      if(operation.equals("sec")) return mul(mul(tan(fun1), sec(fun1)), fun1.derivative(variable));
      if(operation.equals("cot")) return mul(mul(pow(csc(fun1), new Function("2")), new Function("-1")), fun1.derivative(variable));
      if(operation.equals("sinh")) return mul(cosh(fun1), fun1.derivative(variable));
      if(operation.equals("cosh")) return mul(sinh(fun1), fun1.derivative(variable));
      if(operation.equals("tanh")) return div(fun1.derivative(variable), pow(cosh(fun1), new Function("2")));
      if(operation.equals("asin")) return div(fun1.derivative(variable), sqrt(sub(pow(fun1, new Function("2")), new Function("1"))));
      if(operation.equals("acos")) return div(mul(fun1.derivative(variable), new Function("-1")), sqrt(sub(new Function("1"), pow(fun1, new Function("2")))));
      if(operation.equals("atan")) return div(fun1.derivative(variable), add(new Function("1"), pow(fun1, new Function("2"))));
      if(operation.equals("log")) return div(fun1.derivative(variable), mul(new Function("ln", new Function("10")), fun1));
      if(operation.equals("ln")) return div(fun1.derivative(variable), fun1);
      if(operation.equals("sqrt")) return div(fun1.derivative(variable), mul(sqrt(fun1), new Function("2")));
      if(operation.equals("exp")) return mul(exp(fun1), fun1.derivative(variable));
    }
    System.out.println("Oh no");
    // throw new IndexOutOfBoundsExpection("This function does not exist");
    return new Function("You messed up");
  }

  static Function mul(Function a, Function b) {
     return new Function("*", b, a);
   }
  static Function div(Function a, Function b) {
    return new Function("/", b, a);
  }
  static Function add(Function a, Function b){
    return new Function("+", b, a);
  }
  static Function sub(Function a, Function b){
    return new Function("-", b, a);
  }
  static Function pow(Function a, Function b){
    return new Function("^", b, a);
  }
  static Function ln(Function a){
    return new Function("ln", a);
  }
  static Function log(Function a){
    return new Function("log", a);  
  }
  static Function sin(Function a){
    return new Function("sin", a);
  }
  static Function cos(Function a){
    return new Function("cos", a);
  }
  static Function tan(Function a){
    return new Function("tan", a);
  }
  static Function csc(Function a){
    return div(new Function("1"), sin(a));
  }
  static Function sec(Function a){
    return div(new Function("1"), cos(a));
  }
  static Function cot(Function a){
    return div(new Function("1"), tan(a));
  }
  static Function sqrt(Function a){
    return new Function("^", a, new Function(".5"));
  }
  static Function sinh(Function a){
    return new Function("sinh", a);
  }
  static Function cosh(Function a){
    return new Function("cosh", a);
  }
  static Function tanh(Function a){
    return new Function("tanh", a);
  }
  static Function asin(Function a){
    return new Function("asin", a);
  }
  static Function acos(Function a){
    return new Function("acos", a);
  }
  static Function atan(Function a){
    return new Function("atan", a);
  }
  static Function exp(Function a){
    return new Function("exp", a);
  }


}
