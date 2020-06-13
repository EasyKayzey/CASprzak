package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

import java.util.Map;
import java.util.function.DoublePredicate;

public class DomainRestrictor extends PiecewiseFunction{
    DoublePredicate domainTester;

    /**
     * Constructs a new {@link DomainRestrictor}
     * @param operand The function which the DomainRestrictor function is operating on
     */
    public DomainRestrictor(GeneralFunction operand, DoublePredicate domainTester) {
        super(operand);
        this.domainTester = domainTester;
    }

    @Override
    public UnitaryFunction getInstance(GeneralFunction operand) {
        return new DomainRestrictor(operand, domainTester);
    }

    @Override
    public GeneralFunction getDerivative(String varID) {//TODO make this have the correct DoublePredicate with evals and stuff
        return new DomainRestrictor(operand.getDerivative(varID), domainTester);
    }

    @Override
    public double evaluate(Map<String, Double> variableValues) {
        double operandEvaluated = operand.evaluate(variableValues);
        if (domainTester.test(operandEvaluated))
            return operandEvaluated;
        else
            return Double.NaN;
    }
}
