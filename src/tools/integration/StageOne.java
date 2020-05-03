package tools.integration;

import functions.Function;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import functions.special.Variable;
import functions.unitary.Ln;
import functions.unitary.trig.*;
import tools.SearchTools;
import tools.exceptions.IntegrationFailedException;
import tools.helperclasses.Pair;

@SuppressWarnings("ChainOfInstanceofChecks")
public class StageOne {

    /**
     * Performs the derivative divides method on the integrand and returns the integral
     * @param integrand The {@link Function} who is being integrated
     * @param variableChar The {@link Variable#varID} the function is integrated with respect to.
     * @return The integral of the function if one is found.\
     */
    public static Function derivativeDivides(Function integrand, char variableChar) {
        Pair<Double, Function> stripConstant = IntegralsTools.stripConstants(integrand);
        Function function = stripConstant.second;
        double number = stripConstant.first;

        if (function instanceof Product product){
            Function[] productTerms = product.getFunctions();
            for (Function f : productTerms) {
                if (f instanceof Pow power && power.getFunction2() instanceof Constant base) {
                    Function derivativeWithConstants = power.getFunction1().getSimplifiedDerivative(variableChar);
                    Pair<Double, Function> derivative = IntegralsTools.stripConstants(derivativeWithConstants);
                    Function derivativeWithoutConstant = derivative.second;
                    double constantInFront = derivative.first;
                    Product derivativeTimesOperation = new Product(derivativeWithoutConstant, f);
                    if (SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equals) && !SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.exists(u, SearchTools.isVariable(variableChar))), derivativeTimesOperation::equals)) {
                        number /= constantInFront;
                        return exponential(number, base.constant, power.getFunction1());
                    }
                } else if (f instanceof Pow power && power.getFunction1() instanceof Constant exponent) {
                    Function derivativeWithConstants = power.getFunction2().getSimplifiedDerivative(variableChar);
                    Pair<Double, Function> derivative = IntegralsTools.stripConstants(derivativeWithConstants);
                    Function derivativeWithoutConstant = derivative.second;
                    double constantInFront = derivative.first;
                    Product derivativeTimesOperation = new Product(derivativeWithoutConstant, f);
                    if (SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equals) && !SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.exists(u, SearchTools.isVariable(variableChar))), derivativeTimesOperation::equals)) {
                        number /= constantInFront;
                        return power(number, exponent.constant, power.getFunction2());
                    }
                } else if (f instanceof Ln ln) {
                    Function derivativeWithConstants = ln.operand.getSimplifiedDerivative(variableChar);
                    Pair<Double, Function> derivative = IntegralsTools.stripConstants(derivativeWithConstants);
                    Function derivativeWithoutConstant = derivative.second;
                    double constantInFront = derivative.first;
                    Product derivativeTimesOperation = new Product(derivativeWithoutConstant, f);
                    if (SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equals) && !SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.exists(u, SearchTools.isVariable(variableChar))), derivativeTimesOperation::equals)) {
                        number /= constantInFront;
                        return naturalLog(number, ln.operand);
                    }
                } else if (f instanceof Logb logb && logb.getFunction2() instanceof Constant constant1) {
                    Function derivativeWithConstants = logb.getFunction1().getSimplifiedDerivative(variableChar);
                    Pair<Double, Function> derivative = IntegralsTools.stripConstants(derivativeWithConstants);
                    Function derivativeWithoutConstant = derivative.second;
                    double constantInFront = derivative.first;
                    Product derivativeTimesOperation = new Product(derivativeWithoutConstant, f);
                    if (SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equals) && !SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.exists(u, SearchTools.isVariable(variableChar))), derivativeTimesOperation::equals)) {
                        number /= (constantInFront * Math.log(constant1.constant));
                        return naturalLog(number, logb.getFunction2());
                    }
                } else if (f instanceof TrigFunction unit) {
                    Function derivativeWithConstants = unit.operand.getSimplifiedDerivative(variableChar);
                    Pair<Double, Function> derivative = IntegralsTools.stripConstants(derivativeWithConstants);
                    Function derivativeWithoutConstant = derivative.second;
                    double constantInFront = derivative.first;
                    Product derivativeTimesOperation = new Product(derivativeWithoutConstant, f);
                    if (SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equals) && !SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.exists(u, SearchTools.isVariable(variableChar))), derivativeTimesOperation::equals)) {
                        number /= (constantInFront);
                        return new Product(new Constant(number), unit.integrate());
                    }
                }
                Function derivativeWithConstants = f.getSimplifiedDerivative(variableChar);
                Pair<Double, Function> derivative = IntegralsTools.stripConstants(derivativeWithConstants);
                Function derivativeWithoutConstant = derivative.second;
                double constantInFront = derivative.first;
                Product derivativeTimesOperation = new Product(derivativeWithoutConstant, f);
                if (SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equals) && !SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.exists(u, SearchTools.isVariable(variableChar))), derivativeTimesOperation::equals)) {
                    number /= (constantInFront);
                    return power(number, 1, f);
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
            } else if (function instanceof Constant constant1) {
                return new Product(new Constant(constant1.constant * number), new Variable(variableChar));
            } else if (function instanceof Variable variable) {
                return power(number, 1, variable);
            } else if (function instanceof TrigFunction unit && unit.operand.getSimplifiedDerivative(variableChar) instanceof Constant constant1) {
                number /= constant1.constant;
                return new Product(new Constant(number), unit.integrate());
            }
        }
        throw new IntegrationFailedException("Integration failed");
    }


    private static Function exponential(double number, double base, Function exponent) {
        return new Product(new Constant(number/Math.log(base)), new Pow(exponent, new Constant(base)));
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
