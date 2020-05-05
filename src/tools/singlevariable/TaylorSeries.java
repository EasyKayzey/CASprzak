package tools.singlevariable;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import functions.special.Variable;
import tools.MiscTools;
import tools.VariableTools;

import java.util.Map;

public class TaylorSeries {

    private TaylorSeries(){}

    /**
     * Returns a Maclaurin series of the specified function of a given degree
     * @param function The function whose Maclaurin series is being found
     * @param degree The amount of terms in the polynomial (this includes zero terms like {@code 0*x^2}
     * @return Maclaurin series of the specified function
     */
    public static GeneralFunction makeTaylorSeries(GeneralFunction function, int degree) {
        return makeTaylorSeries(function, degree, 0);
    }

    /**
     * Returns a Taylor series of the specified function at the specified center of a given degree
     * @param function The function whose Taylor series is being found
     * @param degree The amount of terms in the polynomial (this includes zero terms like {@code 0*x^2}
     * @param center Where the Taylor series is centered
     * @return Taylor series of the specified function
     */
    public static GeneralFunction makeTaylorSeries(GeneralFunction function, int degree, double center) {
        GeneralFunction[] taylorSeriesTerms = new GeneralFunction[degree];
        char var = VariableTools.getSingleVariable(function);
        for (int i = 0; i < degree; i++){
            taylorSeriesTerms[i] = new Product(new Constant(function.getNthDerivative('x', i).evaluate(Map.of(var, center)) / MiscTools.factorial(i)), new Pow(new Constant(i), new Sum(new Variable('x'), new Constant(-center))));
        }
        return new Sum(taylorSeriesTerms);
    }
}


