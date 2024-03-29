package core.functions.binary.integer;

import core.functions.GeneralFunction;
import core.functions.binary.BinaryFunction;
import core.functions.endpoint.Constant;
import core.output.OutputBinary;
import core.output.OutputFunction;
import core.tools.ParsingTools;

import static core.output.ToStringManager.maybeParenthesize;

public class Modulo extends IntegerBinaryFunction {
    /**
     * Constructs a new Modulo
     * @param function1 The first {@link GeneralFunction} in the binary operation
     * @param function2 The second {@link GeneralFunction} in the binary operation
     */
    public Modulo(GeneralFunction function1, GeneralFunction function2) {
        super(function1, function2);
    }

    @Override
    public BinaryFunction getInstance(GeneralFunction function1, GeneralFunction function2) {
        return new Modulo(function1, function2);
    }

    @Override
    public String toString() {
        return "(" + function2.toString() + "%" + function1.toString() + ")";
    }

    @Override
    public GeneralFunction clone() {
        return new Modulo(function1.clone(), function2.clone());
    }

    @SuppressWarnings("RedundantCast")
    @Override
    public GeneralFunction simplify() {
        if (function1 instanceof Constant constant1 && function2 instanceof Constant constant2)
            return new Constant((double) (ParsingTools.toInteger(constant2.constant) % ParsingTools.toInteger(constant1.constant)));
        return new Modulo(function1.simplify(), function2.simplify());
    }

    protected int operate(int first, int second) {
        return first % second;
    }

    public OutputFunction toOutputFunction() {
        OutputFunction first = function2.toOutputFunction();
        OutputFunction second = function1.toOutputFunction();
        return new OutputModulo(maybeParenthesize(first), maybeParenthesize(second));
    }

    private static class OutputModulo extends OutputBinary {

        public OutputModulo(OutputFunction first, OutputFunction second) {
            super("mod", first, second);
        }

        @Override
        public String toString() {
            return first + " mod " + second;
        }

        @Override
        public String toLatex() {
            return first.toLatex() + " \\text{ mod } " + second.toLatex();
        }

    }
}
