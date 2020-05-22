package functions.binary.integer;

import functions.GeneralFunction;
import functions.binary.BinaryFunction;

import java.util.Map;

public class IntegerDivision extends BinaryFunction {
    /**
     * Constructs a new IntegerDivision
     * @param function1 The first {@link GeneralFunction} in the binary operation
     * @param function2 The second {@link GeneralFunction} in the binary operation
     */
    public IntegerDivision(GeneralFunction function1, GeneralFunction function2) {
        super(function1, function2);
    }

    @Override
    public BinaryFunction getInstance(GeneralFunction function1, GeneralFunction function2) {
        return null;
    }

    @Override
    public GeneralFunction toSpecialCase() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public GeneralFunction clone() {
        return null;
    }

    @Override
    public GeneralFunction getDerivative(char varID) {
        return null;
    }

    @Override
    public double evaluate(Map<Character, Double> variableValues) {
        return 0;
    }

    @Override
    public GeneralFunction simplify() {
        return null;
    }
}
