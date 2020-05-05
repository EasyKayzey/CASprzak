package tools.integration;

import functions.GeneralFunction;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.commutative.CommutativeFunction;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import functions.special.Variable;
import functions.unitary.specialcases.Exp;
import functions.unitary.specialcases.Ln;
import functions.unitary.transforms.Integral;
import functions.unitary.transforms.PartialDerivative;
import functions.unitary.trig.normal.TrigFunction;
import tools.DefaultFunctions;
import tools.MiscTools;
import tools.SearchTools;
import tools.exceptions.IntegrationFailedException;
import tools.helperclasses.Pair;

@SuppressWarnings("ChainOfInstanceofChecks")
public class StageOne {

    /**
     * Performs the derivative divides method on the integrand and returns the integral
     * @param integrand The {@link GeneralFunction} who is being integrated
     * @param variableChar The {@link Variable#varID} the function is integrated with respect to.
     * @return The integral of the function if one is found.
     */
    public static GeneralFunction derivativeDivides(GeneralFunction integrand, char variableChar) { // TODO Erez needs to review this
        if (integrand instanceof Sum terms) {
            GeneralFunction[] integratedTerms = new GeneralFunction[terms.getFunctionsLength()];
            for(int i = 0; i < terms.getFunctionsLength(); i++) {
                integratedTerms[i] = new Integral(terms.getFunctions()[i], variableChar).execute();
            }
            return new Sum(integratedTerms);
        }
        if (integrand instanceof Pow power && power.getFunction2() instanceof Sum && power.getFunction1() instanceof Constant constant && MiscTools.isAlmostInteger(constant.constant)) {
            return new Integral(power.unwrapIntegerPower().distributeAll(), variableChar).execute();
        }
        if (integrand instanceof PartialDerivative derivative) {
            if (derivative.respectTo == variableChar)
                return integrand;
            else
                integrand = integrand.getSimplifiedDerivative(derivative.respectTo);
        }

        Pair<GeneralFunction, GeneralFunction> stripConstant = IntegralTools.stripConstants(integrand, variableChar);
        GeneralFunction function = stripConstant.second;
        GeneralFunction number = stripConstant.first;

        if (function instanceof Product product){
            GeneralFunction[] productTerms = product.getFunctions();
            for (GeneralFunction term : productTerms) {
                if (term instanceof Pow power && SearchTools.doesNotContainsVariable(power.getFunction2(), variableChar)) {
                    Pair<Boolean, GeneralFunction> results = derivativeDividesSearcher(product, term, power.getFunction1(), variableChar);
                    if (results.getFirst())
                        return exponential(new Product(number, DefaultFunctions.reciprocal(results.getSecond())), power.getFunction2(), power.getFunction1());
                } else if (term instanceof Pow power && SearchTools.doesNotContainsVariable(power.getFunction1(), variableChar)) {
                    Pair<Boolean, GeneralFunction> results = derivativeDividesSearcher(product, term, power.getFunction2(), variableChar);
                    if (results.getFirst())
                        return power(new Product(number, DefaultFunctions.reciprocal(results.getSecond())), power.getFunction1(), power.getFunction2());
                } else if (term instanceof Ln ln) {
                    Pair<Boolean, GeneralFunction> results = derivativeDividesSearcher(product, term, ln.operand, variableChar);
                    if (results.getFirst())
                        return naturalLog(new Product(number, DefaultFunctions.reciprocal(results.getSecond())), ln.operand);
                } else if (term instanceof Exp exp) {
                    Pair<Boolean, GeneralFunction> results = derivativeDividesSearcher(product, term, exp.operand, variableChar);
                    if (results.getFirst())
                        return naturalExponential(new Product(number, DefaultFunctions.reciprocal(results.getSecond())), exp.operand);
                } else if (term instanceof Logb logb && SearchTools.doesNotContainsVariable(logb.getFunction2(), variableChar)) {
                    Pair<Boolean, GeneralFunction> results = derivativeDividesSearcher(product, term, logb.getFunction1(), variableChar);
                    if (results.getFirst())
                        return naturalLog(new Product(number, DefaultFunctions.reciprocal(new Product(results.getSecond(), new Ln(logb.getFunction2())))), logb.getFunction2());
                } else if (term instanceof TrigFunction trig) {
                    Pair<Boolean, GeneralFunction> results = derivativeDividesSearcher(product, term, trig.operand, variableChar);
                    if (results.getFirst())
                        return new Product(new Product(number, DefaultFunctions.reciprocal(results.getSecond())), trig.getElementaryIntegral());
                }
                Pair<Boolean, GeneralFunction> results = derivativeDividesSearcher(product, term, term, variableChar);
                if (results.getFirst())
                    return power(new Product(number, DefaultFunctions.reciprocal(results.getSecond())), DefaultFunctions.ONE, term);
            }
        } else {
            if (function instanceof Pow power && SearchTools.doesNotContainsVariable(power.getFunction2(), variableChar) && SearchTools.doesNotContainsVariable(power.getFunction1().getSimplifiedDerivative(variableChar), variableChar)) {
                return exponential(new Product(number, DefaultFunctions.reciprocal(power.getFunction1().getSimplifiedDerivative(variableChar))), power.getFunction2(), power.getFunction1());
            } else if (function instanceof Pow power && SearchTools.doesNotContainsVariable(power.getFunction1(), variableChar) && SearchTools.doesNotContainsVariable(power.getFunction2().getSimplifiedDerivative(variableChar), variableChar)) {
                return power(new Product(number, DefaultFunctions.reciprocal(power.getFunction2().getSimplifiedDerivative(variableChar))), power.getFunction1(), power.getFunction2());
            } else if (function instanceof Ln log && SearchTools.doesNotContainsVariable(log.operand.getSimplifiedDerivative(variableChar), variableChar)) {
                return naturalLog(new Product(number, DefaultFunctions.reciprocal(log.operand.getSimplifiedDerivative(variableChar))), log.operand);
            } else if (function instanceof Exp exp && SearchTools.doesNotContainsVariable(exp.operand.getSimplifiedDerivative(variableChar), variableChar)) {
                return naturalExponential(new Product(number, DefaultFunctions.reciprocal(exp.operand.getSimplifiedDerivative(variableChar))), exp.operand);
            } else if (function instanceof Logb logb && SearchTools.doesNotContainsVariable(logb.getFunction2(), variableChar) && SearchTools.doesNotContainsVariable(logb.getFunction1().getSimplifiedDerivative(variableChar), variableChar)) {
                return naturalLog(new Product(number, DefaultFunctions.reciprocal(new Product(logb.getFunction1().getSimplifiedDerivative(variableChar), new Ln(logb.getFunction2())))), logb.getFunction1());
            } else if (SearchTools.doesNotContainsVariable(function, variableChar)) {
                return new Product(number, function, new Variable(variableChar));
            } else if (function instanceof Variable variable) {
                return power(number, DefaultFunctions.ONE, variable);
            } else if (function instanceof TrigFunction unit && SearchTools.doesNotContainsVariable(unit.operand.getSimplifiedDerivative(variableChar), variableChar)) {
                return new Product(new Product(number, DefaultFunctions.reciprocal(unit.operand.getSimplifiedDerivative(variableChar))), unit.getElementaryIntegral());
            }
        }
        throw new IntegrationFailedException("Integration failed");
    }


    private static GeneralFunction exponential(GeneralFunction number, GeneralFunction base, GeneralFunction exponent) {
        return new Product(number, new Pow(DefaultFunctions.NEGATIVE_ONE, new Ln(base)), new Pow(exponent, base));
    }

    private static GeneralFunction power(GeneralFunction number, GeneralFunction exponent, GeneralFunction base) {
        if (exponent instanceof Constant constant && constant.constant == -1)
            return new Product(number, new Ln(base));
        else
            return new Product(number, DefaultFunctions.reciprocal(new Sum(exponent, DefaultFunctions.ONE)), new Pow(new Sum(exponent, DefaultFunctions.ONE), base));
    }

    private static GeneralFunction naturalLog(GeneralFunction number, GeneralFunction operand) {
        return new Product(number, new Sum(new Product(operand, new Ln(operand)), DefaultFunctions.negative(operand)));
    }

    private static GeneralFunction naturalExponential(GeneralFunction number, GeneralFunction operand) {
        return new Product(number, new Exp(operand));
    }

    private static Pair<Boolean, GeneralFunction> derivativeDividesSearcher(CommutativeFunction product, GeneralFunction term, GeneralFunction toTakeDerivative, char variableChar) {
        GeneralFunction derivativeWithConstants = toTakeDerivative.getSimplifiedDerivative(variableChar);
        Pair<GeneralFunction, GeneralFunction> derivative = IntegralTools.stripConstants(derivativeWithConstants, variableChar);
        GeneralFunction derivativeWithoutConstant = derivative.second;
        GeneralFunction constantInFront = derivative.first;
        Product derivativeTimesOperation = new Product(derivativeWithoutConstant, term);
        return new Pair<>(SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equals) && !SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.existsAny(u, SearchTools.isVariable(variableChar))), derivativeTimesOperation::equals), constantInFront);
        }
}
