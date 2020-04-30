package tools.integral;

import functions.Function;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import functions.special.Variable;
import functions.unitary.Ln;
import functions.unitary.trig.Cos;
import functions.unitary.trig.Sin;
import tools.SearchTools;
import tools.helperclasses.Pair;

public class StageOne<function> {
    public static Function derivativeDivides(Function integrand, char variableChar) {
        Pair<Double, Function> stripConstant = IntegralsTools.stripConstants(integrand);
        Function function = stripConstant.second;
        double number = stripConstant.first;

        if (function instanceof Product product){
            Function[] productTerms = product.getFunctions();
            for (Function f : productTerms) {
                if (f instanceof Pow power && power.getFunction2() instanceof Constant) {
                    Function derivative = power.getFunction1().getSimplifiedDerivative(variableChar);
                    if (SearchTools.existsSurface(product, (u -> u.equals(derivative))) && !SearchTools.existsExcluding(function, (u -> (u instanceof Variable v) && (v.varID == variableChar)), (u -> u.equals(power.getFunction1()))));
                }
            }
        } else {
            if (function instanceof Pow power && power.getFunction2() instanceof Constant constant1 && power.getFunction1().getSimplifiedDerivative(variableChar) instanceof Constant constant2) {
                number /= constant2.constant;
                return exponential(number, constant1.constant, power.getFunction1());
            } else if (function instanceof Pow power && power.getFunction1() instanceof Constant constant1 && power.getFunction2().getSimplifiedDerivative(variableChar) instanceof Constant constant2) {
                number /= constant2.constant;
                return power(number, constant1.constant, power.getFunction2());
            } else if (function instanceof Ln log && log.operand.getSimplifiedDerivative(variableChar) instanceof Constant constant1) {
                number /= constant1.constant;
                return naturalLog(number, log.operand);
            } else if (function instanceof Logb logb && logb.getFunction2() instanceof Constant constant1 && logb.getFunction1().getSimplifiedDerivative(variableChar) instanceof Constant constant2) {
                number /= (constant2.constant * Math.log(constant1.constant));
                return naturalLog(number, logb.getFunction1());
            } else if (function instanceof Sin sin && sin.operand.getSimplifiedDerivative(variableChar) instanceof Constant constant1) {
                number /= constant1.constant;
                return sin(number, sin.operand);
            } else if (function instanceof Cos cos && cos.operand.getSimplifiedDerivative(variableChar) instanceof Constant constant1) {
                number /= constant1.constant;
                return cos(number, cos.operand);
            }
        }

        return function;
        // Let's say I want to check if all xs are in the form e^x
//        SearchTools.exists(integrand, (f -> f.equals(toFind))) && !SearchTools.existsExcluding(integrand, (f -> f instanceof Variable), (f -> f.equals(toFind)))
    }

    private static Function exponential(double number, double base, Function exponent) {
        return new Product(new Constant(1/Math.log(base)), new Pow(exponent, new Constant(base)));
    }

    private static Function power(double number, double exponent, Function base) {
        if (exponent == -1)
            return new Product(new Constant(number), new Ln(base));
        else
            return new Product(new Constant(number/(exponent+1)), new Pow(new Constant(exponent+1), base));
    }

    private static Function naturalLog(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Ln(operand)), new Product(new Constant(-1), operand)));
    }

    private static Function sin(double number, Function operand) {
        return new Product(new Constant(-1*number), new Cos(operand));
    }

    private static Function cos(double number, Function operand) {
        return new Product(new Constant(number), new Sin(operand));
    }
}
