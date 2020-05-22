package functions.binary.integer;

import config.Settings;
import functions.GeneralFunction;
import functions.binary.BinaryFunction;
import functions.special.Constant;
import tools.DefaultFunctions;
import tools.ParsingTools;
import tools.VariableTools;

import java.util.Map;

public class IntegerQuotient extends BinaryFunction {
    /**
     * Constructs a new IntegerDivision
     * @param function1 The first {@link GeneralFunction} in the binary operation
     * @param function2 The second {@link GeneralFunction} in the binary operation
     */
    public IntegerQuotient(GeneralFunction function1, GeneralFunction function2) {
        super(function1, function2);
    }

    @Override
    public BinaryFunction getInstance(GeneralFunction function1, GeneralFunction function2) {
        return new IntegerQuotient(function1, function2);
    }


    @Override
    public String toString() {
        return "(" + function2 + "//" + function1 + ")";
    }

    @Override
    public GeneralFunction clone() {
        return new IntegerQuotient(function1.clone(), function2.clone());
    }

    @Override
    public GeneralFunction getDerivative(char varID) {
        if (VariableTools.doesNotContainsVariable(function1, varID) && VariableTools.doesNotContainsVariable(function2, varID))
            return DefaultFunctions.ZERO;
        else
            throw new UnsupportedOperationException("IntegerDivision has no derivative.");
    }

    @SuppressWarnings("RedundantCast")
    @Override
    public double evaluate(Map<Character, Double> variableValues) {
        if (!Settings.enforceIntegerOperations)
            throw new IllegalStateException("RFactorial cannot be used if Settings.enforceIntegerOperations is not enabled.");
        int argument1 = ParsingTools.toInteger(function1.evaluate(variableValues));
        int argument2 = ParsingTools.toInteger(function2.evaluate(variableValues));
        return (double) (argument2 / argument1);
    }

    @SuppressWarnings("RedundantCast")
    @Override
    public GeneralFunction simplify() {
        if (function1 instanceof Constant constant1 && function2 instanceof Constant constant2)
            return new Constant((double) (ParsingTools.toInteger(constant2.constant) / ParsingTools.toInteger(constant1.constant)));
        return new IntegerQuotient(function1.simplify(), function2.simplify());
    }
}
