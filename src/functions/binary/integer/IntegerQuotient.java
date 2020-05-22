package functions.binary.integer;

import functions.GeneralFunction;
import functions.binary.BinaryFunction;
import functions.special.Constant;
import tools.ParsingTools;

public class IntegerQuotient extends IntegerBinaryFunction {
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

    @SuppressWarnings("RedundantCast")
    @Override
    public GeneralFunction simplify() {
        if (function1 instanceof Constant constant1 && function2 instanceof Constant constant2)
            return new Constant((double) (ParsingTools.toInteger(constant2.constant) / ParsingTools.toInteger(constant1.constant)));
        return new IntegerQuotient(function1.simplify(), function2.simplify());
    }

    protected int operate(int first, int second) {
        return first / second;
    }
}
