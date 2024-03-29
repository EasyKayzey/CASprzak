package core.functions.binary;

import core.functions.GeneralFunction;
import core.tools.exceptions.DerivativeDoesNotExistException;

import java.util.Map;

public class Rand extends BinaryFunction {
    /**
     * Constructs a new {@link Rand}
     * @param function1 the upper bound of the range
     * @param function2 the lower bound of the range
     */
    public Rand(GeneralFunction function1, GeneralFunction function2) {
        super(function1, function2);
    }

    @Override
    public BinaryFunction getInstance(GeneralFunction function1, GeneralFunction function2) {
        return new Rand(function1, function2);
    }

    @Override
    public String toString() {
        return "rand( " + function2 + " , " + function1 + " )";
    }

    @Override
    public GeneralFunction clone() {
        return new Rand(function1.clone(), function2.clone());
    }

    @Override
    public GeneralFunction getDerivative(String varID) {
        throw new DerivativeDoesNotExistException(this);
    }

    @Override
    public double evaluate(Map<String, Double> variableValues) {
        double lowerBoundEvaluated = function2.evaluate(variableValues);
        return lowerBoundEvaluated + (function1.evaluate(variableValues) - lowerBoundEvaluated) * Math.random();
    }

    @Override
    public GeneralFunction simplify() {
        return new Rand(function1.simplify(), function2.simplify());
    }
}
