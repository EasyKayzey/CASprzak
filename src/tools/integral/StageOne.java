package tools.integral;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import functions.special.Variable;
import functions.unitary.Ln;
import tools.SearchTools;
import tools.helperclasses.Pair;

public class StageOne<function> {
    public static Function derivativeDivides(Function integrand, char variableChar) {
        Pair<Double, Function> stripConstant = IntegralsTools.stripConstants(integrand);
        Function function = stripConstant.second;
        double number = stripConstant.first;

        if (!(function instanceof Product)) {
            if (function instanceof Pow power && power.getFunction2() instanceof Constant constant1 && power.getFunction1().getSimplifiedDerivative(variableChar) instanceof Constant constant2) {
                number /= constant2.constant;
                return exponential(number, constant1.constant, power.getFunction1());
            } else if (function instanceof Pow power && power.getFunction1() instanceof Constant constant1 && power.getFunction2().getSimplifiedDerivative(variableChar) instanceof Constant constant2) {
                number /= constant2.constant;
                return power(number, constant1.constant, power.getFunction2());
            } else if (function instanceof Ln log && log.operand().getSimplifiedDerivative(variableChar) instanceof Constant constant1) {
                number /= constant1.constant;
                return naturalLog(number, log.operand());
            }
        }


        for (Function f : function) {
            if (f instanceof Pow power && power.getFunction2() instanceof Constant) {
                Function derivative = power.getFunction1().getSimplifiedDerivative(variableChar);
               if (SearchTools.exists(function, (u -> u.equals(derivative))) && !SearchTools.existsExcluding(function, (u -> (u instanceof Variable v) && (v.varID == variableChar)), (u -> u.equals(power.getFunction1()))));
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
}
