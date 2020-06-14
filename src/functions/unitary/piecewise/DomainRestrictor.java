package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.DoublePredicate;

public class DomainRestrictor extends PiecewiseFunction{
    BiPredicate<Double, Map<String, Double>> domainTester;

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
        this(operand, (d, l) -> domainTester.test(d));
    }

    @Override
    public UnitaryFunction getInstance(GeneralFunction operand) {
        return new DomainRestrictor(operand, domainTester);
    }

    @Override
    public GeneralFunction getDerivative(String varID) {
        return new DomainRestrictor(operand.getDerivative(varID), (d, l) -> domainTester.test(operand.evaluate(l), l));
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