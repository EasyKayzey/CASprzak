package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.DoublePredicate;

/**
 * The {@link DomainRestrictor} class allows for the restriction of domains of ranges of functions.
 * When {@link #domainTester} returns true, this class functions as the identity,
 * but when {@link #domainTester} returns false, {@code evaluate} returns {@code Double.NaN}.
 */
public class DomainRestrictor extends PiecewiseFunction {

    /**
     * This {@code BiPredicate} describes the domain of this function given an argument and list of variable values
     */
    public final BiPredicate<Double, Map<String, Double>> domainTester;

    /**
     * Constructs a new {@link DomainRestrictor}
     * @param operand The function whose output will be restricted
     * @param domainTester the {@code BiPredicate} describing the domain of the function
     */
    public DomainRestrictor(GeneralFunction operand, BiPredicate<Double, Map<String, Double>> domainTester) {
        super(operand);
        this.domainTester = domainTester;
    }

    /**
     * Constructs a new {@link DomainRestrictor}
     * @param operand The function whose output will be restricted
     * @param domainTester the {@code DoublePredicate} describing the domain of the function
     */
    public DomainRestrictor(GeneralFunction operand, DoublePredicate domainTester) {
        this(operand, (argument, list) -> domainTester.test(argument));
    }

    @Override
    public UnitaryFunction getInstance(GeneralFunction operand) {
        return new DomainRestrictor(operand, domainTester);
    }

    @Override
    public GeneralFunction getDerivative(String varID) {
        return new DomainRestrictor(
                operand.getDerivative(varID),
                (argument, list) -> domainTester.test(operand.evaluate(list), list)
        );
    }

    @Override
    public double evaluate(Map<String, Double> variableValues) {
        double operandEvaluated = operand.evaluate(variableValues);
        if (domainTester.test(operandEvaluated, variableValues))
            return operandEvaluated;
        else
            return Double.NaN;
    }

    @Override
    public String toString() {
        return operand.toString();
    }
}
