package tools.integration;

import functions.GeneralFunction;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.commutative.CommutativeFunction;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.endpoint.Constant;
import functions.endpoint.Variable;
import functions.unitary.specialcases.Exp;
import functions.unitary.specialcases.Ln;
import functions.unitary.transforms.Integral;
import functions.unitary.transforms.PartialDerivative;
import functions.unitary.trig.normal.TrigFunction;
import tools.*;
import tools.exceptions.IntegrationFailedException;
import tools.helperclasses.Pair;

/**
 * The {@link StageOne} class attempts to integrate a function using a method very similar to Stage One of the integration procedure used in <a href="http://www-inst.eecs.berkeley.edu/~cs282/sp02/readings/moses-int.pdf">SIN</a>. Firstly,
 * the program performs simplifications and slight modifications to the integrand. The integrand is simplified using {@link MiscTools#minimalSimplify(GeneralFunction)}.
 * If the integral is an instance of {@link PartialDerivative} and {@link PartialDerivative#respectTo} is the same as the variable that is being integrated with
 * respect to, the program returns {@link PartialDerivative#operand}. Otherwise it executes the {@link PartialDerivative}. If the integrand is a {@link Sum}, then the
 * integral is distributed to each term in the {@link Sum} and returned as a {@link Sum} of {@link Integral}s. The final simplification expands {@link Sum}s to
 * integer {@link Constant} powers. After these steps, a standard "derivative divides" integration technique is used. The programs check if the integrand is of the form
 * {@code C*op(u(x))*u'(x)}. If the integrand matches this form, the program returns {@code C*OP(u(x))}, where {@code OP} is the integral of {@code op(x) dx}.
 */
@SuppressWarnings("ChainOfInstanceofChecks")
public class StageOne {
    private StageOne(){}

    /**
     * Performs the "derivative divides" method of integration and returns the integral if the method succeeded
     * @param integrand The {@link GeneralFunction} that is being integrated
     * @param variableString The {@link Variable#varID} that the integrand is integrated with respect to
     * @return The integral of the function, if one is found
     * @throws IntegrationFailedException if the integration did not succeed
     */
    public static GeneralFunction derivativeDivides(GeneralFunction integrand, String variableString) throws IntegrationFailedException {
        integrand = MiscTools.minimalSimplify(integrand);

        if (integrand instanceof PartialDerivative derivative)
            if (derivative.respectTo.equals(variableString))
                return integrand;
            else
                integrand = integrand.getSimplifiedDerivative(derivative.respectTo);

        if (integrand instanceof Sum terms) {
            GeneralFunction[] sumTerms = terms.getFunctions();
            GeneralFunction[] integratedTerms = new GeneralFunction[sumTerms.length];
            for (int i = 0; i < sumTerms.length; i++)
                integratedTerms[i] = new Integral(sumTerms[i], variableString).execute();
            return new Sum(integratedTerms);
        }

        if (integrand instanceof Pow power && power.getFunction2() instanceof Sum && power.getFunction1() instanceof Constant constant && ParsingTools.isAlmostInteger(constant.constant))
            return new Integral(power.unwrapIntegerPower().distributeAll(), variableString).execute();

        Pair<GeneralFunction, GeneralFunction> stripConstant = IntegralTools.stripConstantsRespectTo(integrand, variableString);
        GeneralFunction function = stripConstant.getSecond();
        GeneralFunction number = stripConstant.getFirst();

        if (function instanceof Product product){
            GeneralFunction[] productTerms = product.getFunctions();
            for (GeneralFunction term : productTerms) {
                if (term instanceof Pow power && VariableTools.doesNotContainsVariable(power.getFunction2(), variableString)) {
                    GeneralFunction results = derivativeDividesSearcher(product, term, power.getFunction1(), variableString);
                    if (results != null)
                        return exponential(new Product(number, DefaultFunctions.reciprocal(results)), power.getFunction2(), power.getFunction1());
                } else if (term instanceof Pow power && VariableTools.doesNotContainsVariable(power.getFunction1(), variableString)) {
                    GeneralFunction results = derivativeDividesSearcher(product, term, power.getFunction2(), variableString);
                    if (results != null)
                        return power(new Product(number, DefaultFunctions.reciprocal(results)), power.getFunction1(), power.getFunction2());
                } else if (term instanceof Ln ln) {
                    GeneralFunction results = derivativeDividesSearcher(product, term, ln.operand, variableString);
                    if (results != null)
                        return naturalLog(new Product(number, DefaultFunctions.reciprocal(results)), ln.operand);
                } else if (term instanceof Exp exp) {
                    GeneralFunction results = derivativeDividesSearcher(product, term, exp.operand, variableString);
                    if (results != null)
                        return naturalExponential(new Product(number, DefaultFunctions.reciprocal(results)), exp.operand);
                } else if (term instanceof Logb logb && VariableTools.doesNotContainsVariable(logb.getFunction2(), variableString)) {
                    GeneralFunction results = derivativeDividesSearcher(product, term, logb.getFunction1(), variableString);
                    if (results != null)
                        return naturalLog(new Product(number, DefaultFunctions.reciprocal(new Product(results, new Ln(logb.getFunction2())))), logb.getFunction2());
                } else if (term instanceof TrigFunction trig) {
                    GeneralFunction results = derivativeDividesSearcher(product, term, trig.operand, variableString);
                    if (results != null)
                        return new Product(new Product(number, DefaultFunctions.reciprocal(results)), trig.getElementaryIntegral());
                }
                GeneralFunction results = derivativeDividesSearcher(product, term, term, variableString);
                if (results != null)
                    return power(new Product(number, DefaultFunctions.reciprocal(results)), DefaultFunctions.ONE, term);
            }
        } else {
            if (function instanceof Pow power && VariableTools.doesNotContainsVariable(power.getFunction2(), variableString) && VariableTools.doesNotContainsVariable(power.getFunction1().getSimplifiedDerivative(variableString), variableString))
                return exponential(new Product(number, DefaultFunctions.reciprocal(power.getFunction1().getSimplifiedDerivative(variableString))), power.getFunction2(), power.getFunction1());
            else if (function instanceof Pow power && VariableTools.doesNotContainsVariable(power.getFunction1(), variableString) && VariableTools.doesNotContainsVariable(power.getFunction2().getSimplifiedDerivative(variableString), variableString))
                return power(new Product(number, DefaultFunctions.reciprocal(power.getFunction2().getSimplifiedDerivative(variableString))), power.getFunction1(), power.getFunction2());
            else if (function instanceof Ln log && VariableTools.doesNotContainsVariable(log.operand.getSimplifiedDerivative(variableString), variableString))
                return naturalLog(new Product(number, DefaultFunctions.reciprocal(log.operand.getSimplifiedDerivative(variableString))), log.operand);
            else if (function instanceof Exp exp && VariableTools.doesNotContainsVariable(exp.operand.getSimplifiedDerivative(variableString), variableString))
                return naturalExponential(new Product(number, DefaultFunctions.reciprocal(exp.operand.getSimplifiedDerivative(variableString))), exp.operand);
            else if (function instanceof Logb logb && VariableTools.doesNotContainsVariable(logb.getFunction2(), variableString) && VariableTools.doesNotContainsVariable(logb.getFunction1().getSimplifiedDerivative(variableString), variableString))
                return naturalLog(new Product(number, DefaultFunctions.reciprocal(new Product(logb.getFunction1().getSimplifiedDerivative(variableString), new Ln(logb.getFunction2())))), logb.getFunction1());
            else if (VariableTools.doesNotContainsVariable(function, variableString))
                return new Product(number, function, new Variable(variableString));
            else if (function instanceof Variable variable)
                return power(number, DefaultFunctions.ONE, variable);
            else if (function instanceof TrigFunction unit && VariableTools.doesNotContainsVariable(unit.operand.getSimplifiedDerivative(variableString), variableString))
                return new Product(new Product(number, DefaultFunctions.reciprocal(unit.operand.getSimplifiedDerivative(variableString))), unit.getElementaryIntegral());
        }
        throw new IntegrationFailedException(integrand);
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

    private static GeneralFunction derivativeDividesSearcher(CommutativeFunction product, GeneralFunction term, GeneralFunction toTakeDerivative, String variableString) {
        GeneralFunction derivativeWithConstants = toTakeDerivative.getSimplifiedDerivative(variableString);
        Pair<GeneralFunction, GeneralFunction> derivative = IntegralTools.stripConstantsRespectTo(derivativeWithConstants, variableString);
        GeneralFunction derivativeWithoutConstant = derivative.getSecond();
        GeneralFunction constantInFront = derivative.getFirst();
        Product derivativeTimesOperation = new Product(derivativeWithoutConstant, term);
        if (!SearchTools.existsInSurfaceSubset(product, derivativeTimesOperation::equalsSimplified) || SearchTools.existsInOppositeSurfaceSubset(product, (u -> SearchTools.existsAny(u, VariableTools.isVariable(variableString))), derivativeTimesOperation::equalsSimplified))
            return null;
        else
            return constantInFront;
        }
}
