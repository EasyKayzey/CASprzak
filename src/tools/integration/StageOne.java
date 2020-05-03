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
import tools.DefaultFunctions;
import tools.SearchTools;
import tools.exceptions.IntegrationFailedException;
import tools.helperclasses.Pair;

@SuppressWarnings("ChainOfInstanceofChecks")
public class StageOne {

    /**
     * Performs the derivative divides method on the integrand and returns the integral
     * @param integrand The {@link Function} who is being integrated
     * @param variableChar The {@link Variable#varID} the function is integrated with respect to.
     * @return The integral of the function if one is found.
     */
    public static Function derivativeDivides(Function integrand, char variableChar) {
        Pair<Function, Function> stripConstant = IntegralTools.stripConstants(integrand, variableChar);
        Function function = stripConstant.second;
        Function number = stripConstant.first;

        if (function instanceof Product product){
            Function[] productTerms = product.getFunctions();
            for (Function f : productTerms) {
                if (f instanceof Pow power && IntegralTools.doesNotContainsVariable(power.getFunction2(), variableChar)) {
                    Function derivativeWithConstants = power.getFunction1().getSimplifiedDerivative(variableChar);
                    Pair<Function, Function> derivative = IntegralTools.stripConstants(derivativeWithConstants, variableChar);
                    Function derivativeWithoutConstant = derivative.second;
                    Function constantInFront = derivative.first;
                    Product derivativeTimesOperation = new Product(derivativeWithoutConstant, f);
                    if (SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equals) && !SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.exists(u, SearchTools.isVariable(variableChar))), derivativeTimesOperation::equals)) {
                        return exponential(new Product(number, DefaultFunctions.reciprocal(constantInFront)), power.getFunction2(), power.getFunction1());
                    }
                } else if (f instanceof Pow power && IntegralTools.doesNotContainsVariable(power.getFunction1(), variableChar)) {
                    Function derivativeWithConstants = power.getFunction2().getSimplifiedDerivative(variableChar);
                    Pair<Function, Function> derivative = IntegralTools.stripConstants(derivativeWithConstants, variableChar);
                    Function derivativeWithoutConstant = derivative.second;
                    Function constantInFront = derivative.first;
                    Product derivativeTimesOperation = new Product(derivativeWithoutConstant, f);
                    if (SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equals) && !SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.exists(u, SearchTools.isVariable(variableChar))), derivativeTimesOperation::equals)) {
                        return power(new Product(number, DefaultFunctions.reciprocal(constantInFront)), power.getFunction1(), power.getFunction2());
                    }
                } else if (f instanceof Ln ln) {
                    Function derivativeWithConstants = ln.operand.getSimplifiedDerivative(variableChar);
                    Pair<Function, Function> derivative = IntegralTools.stripConstants(derivativeWithConstants, variableChar);
                    Function derivativeWithoutConstant = derivative.second;
                    Function constantInFront = derivative.first;
                    Product derivativeTimesOperation = new Product(derivativeWithoutConstant, f);
                    if (SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equals) && !SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.exists(u, SearchTools.isVariable(variableChar))), derivativeTimesOperation::equals)) {
                        return naturalLog(new Product(number, DefaultFunctions.reciprocal(constantInFront)), ln.operand);
                    }
                } else if (f instanceof Logb logb && IntegralTools.doesNotContainsVariable(logb.getFunction2(), variableChar)) {
                    Function derivativeWithConstants = logb.getFunction1().getSimplifiedDerivative(variableChar);
                    Pair<Function, Function> derivative = IntegralTools.stripConstants(derivativeWithConstants, variableChar);
                    Function derivativeWithoutConstant = derivative.second;
                    Function constantInFront = derivative.first;
                    Product derivativeTimesOperation = new Product(derivativeWithoutConstant, f);
                    if (SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equals) && !SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.exists(u, SearchTools.isVariable(variableChar))), derivativeTimesOperation::equals)) {
                        return naturalLog(new Product(number, DefaultFunctions.reciprocal(new Product(constantInFront, new Ln(logb.getFunction2())))), logb.getFunction2());
                    }
                } else if (f instanceof TrigFunction unit) {
                    Function derivativeWithConstants = unit.operand.getSimplifiedDerivative(variableChar);
                    Pair<Function, Function> derivative = IntegralTools.stripConstants(derivativeWithConstants, variableChar);
                    Function derivativeWithoutConstant = derivative.second;
                    Function constantInFront = derivative.first;
                    Product derivativeTimesOperation = new Product(derivativeWithoutConstant, f);
                    if (SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equals) && !SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.exists(u, SearchTools.isVariable(variableChar))), derivativeTimesOperation::equals)) {
                        return new Product(new Product(number, DefaultFunctions.reciprocal(constantInFront)), unit.getElementaryIntegral());
                    }
                }
                Function derivativeWithConstants = f.getSimplifiedDerivative(variableChar);
                Pair<Function, Function> derivative = IntegralTools.stripConstants(derivativeWithConstants, variableChar);
                Function derivativeWithoutConstant = derivative.second;
                Function constantInFront = derivative.first;
                Product derivativeTimesOperation = new Product(derivativeWithoutConstant, f);
                if (SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equals) && !SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.exists(u, SearchTools.isVariable(variableChar))), derivativeTimesOperation::equals)) {
                    return power(new Product(number, DefaultFunctions.reciprocal(constantInFront)), DefaultFunctions.ONE, f);
                }
            }
        } else {
            if (function instanceof Pow power && IntegralTools.doesNotContainsVariable(power.getFunction2(), variableChar) && IntegralTools.doesNotContainsVariable(power.getFunction1().getSimplifiedDerivative(variableChar), variableChar)) {
                return exponential(new Product(number, DefaultFunctions.reciprocal(power.getFunction1().getSimplifiedDerivative(variableChar))), power.getFunction2(), power.getFunction1());
            } else if (function instanceof Pow power && IntegralTools.doesNotContainsVariable(power.getFunction1(), variableChar) && IntegralTools.doesNotContainsVariable(power.getFunction2().getSimplifiedDerivative(variableChar), variableChar)) {
                return power(new Product(number, DefaultFunctions.reciprocal(power.getFunction2().getSimplifiedDerivative(variableChar))), power.getFunction1(), power.getFunction2());
            } else if (function instanceof Ln log && IntegralTools.doesNotContainsVariable(log.operand.getSimplifiedDerivative(variableChar), variableChar)) {
                return naturalLog(new Product(number, DefaultFunctions.reciprocal(log.operand.getSimplifiedDerivative(variableChar))), log.operand);
            } else if (function instanceof Logb logb && IntegralTools.doesNotContainsVariable(logb.getFunction2(), variableChar) && IntegralTools.doesNotContainsVariable(logb.getFunction1().getSimplifiedDerivative(variableChar), variableChar)) {
                return naturalLog(new Product(number, DefaultFunctions.reciprocal(new Product(logb.getFunction1().getSimplifiedDerivative(variableChar), new Ln(logb.getFunction2())))), logb.getFunction1());
            } else if (IntegralTools.doesNotContainsVariable(function, variableChar)) {
                return new Product(number, function, new Variable(variableChar));
            } else if (function instanceof Variable variable) {
                return power(number, DefaultFunctions.ONE, variable);
            } else if (function instanceof TrigFunction unit && IntegralTools.doesNotContainsVariable(unit.operand.getSimplifiedDerivative(variableChar), variableChar)) {
                return new Product(new Product(number, DefaultFunctions.reciprocal(unit.operand.getSimplifiedDerivative(variableChar))), unit.getElementaryIntegral());
            }
        }
        throw new IntegrationFailedException("Integration failed");
    }


    private static Function exponential(Function number, Function base, Function exponent) {
        return new Product(number, new Pow(DefaultFunctions.NEGATIVE_ONE, new Ln(base)), new Pow(exponent, base));
    }

    private static Function power(Function number, Function exponent, Function base) {
        if (exponent instanceof Constant constant && constant.constant == -1)
            return new Product(number, new Ln(base));
        else
            return new Product(number, DefaultFunctions.reciprocal(new Sum(exponent, DefaultFunctions.ONE)), new Pow(new Sum(exponent, DefaultFunctions.ONE), base));
    }

    private static Function naturalLog(Function number, Function operand) {
        return new Product(number, new Sum(new Product(operand, new Ln(operand)), DefaultFunctions.negative(operand)));
    }
}
