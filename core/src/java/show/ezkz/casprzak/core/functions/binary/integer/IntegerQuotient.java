package show.ezkz.casprzak.core.functions.binary.integer;

import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.BinaryFunction;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import show.ezkz.casprzak.core.output.OutputBinary;
import show.ezkz.casprzak.core.output.OutputFunction;
import show.ezkz.casprzak.core.tools.ParsingTools;

import static show.ezkz.casprzak.core.output.ToStringManager.maybeParenthesize;

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
    public GeneralFunction simplify(SimplificationSettings settings) {
        if (function1 instanceof Constant constant1 && function2 instanceof Constant constant2)
            return new Constant((double) (ParsingTools.toInteger(constant2.constant) / ParsingTools.toInteger(constant1.constant)));
        return new IntegerQuotient(function1.simplify(settings), function2.simplify(settings));
    }

    protected int operate(int first, int second) {
        return first / second;
    }

    public OutputFunction toOutputFunction() {
        OutputFunction first = function2.toOutputFunction();
        OutputFunction second = function1.toOutputFunction();
        return new OutputIntegerQuotient(maybeParenthesize(first), maybeParenthesize(second));
    }

    private static class OutputIntegerQuotient extends OutputBinary {

        public OutputIntegerQuotient(OutputFunction first, OutputFunction second) {
            super("intdiv", first, second);
        }

        @Override
        public String toString() {
            return first + " div " + second;
        }

        @Override
        public String toLatex() {
            return first.toLatex() + " \\text{ div } " + second.toLatex();
        }

    }
}
