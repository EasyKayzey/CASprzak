package tools.singlevariable;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.special.Variable;
import tools.MiscTools;

public class TaylorSeries {

    private TaylorSeries(){}

    /**
     * Returns a Maclaurin series of the specified function of a specified length
     * @param function The function whose Maclaurin series is being found
     * @param size The amount of terms in the polynomial (this includes zero terms like {@code 0*x^2}
     * @return Maclaurin series of the specified function
     */
    public static Function makeTaylorSeries(Function function, int size) {
        return makeTaylorSeries(function, size, 0);
    }

    /**
     * Returns a Taylor series of the specified function at specified center of a specified length
     * @param function The function whose Maclaurin series is being found
     * @param size The amount of terms in the polynomial (this includes zero terms like {@code 0*x^2}
     * @param center Where the Taylor series is centered
     * @return Taylor series of the specified function
     */
    public static Function makeTaylorSeries(Function function, int size, double center) {
        Function[] taylorSeriesTerms = new Function[size];
        for (int i = 0; i < size; i++){
            taylorSeriesTerms[i] = new Multiply(new Constant(function.getNthDerivative('x', i).oldEvaluate(center) / MiscTools.factorial(i)), new Pow(new Constant(i), new Add(new Variable('x'), new Constant(-center))));
        }
        return new Add(taylorSeriesTerms);
    }
}


