package tools.singlevariable;

import config.Settings;
import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import functions.special.Variable;
import tools.MiscTools;
import tools.SearchTools;

import java.util.Map;
import java.util.Set;

public class TaylorSeries {

    private TaylorSeries(){}

    /**
     * Returns a Maclaurin series of the specified function with the specified amount of terms
     * @param function The function whose Maclaurin series is being found
     * @param size The amount of terms in the polynomial (this includes zero terms like {@code 0*x^2}
     * @return Maclaurin series of the specified function
     */
    public static GeneralFunction makeTaylorSeries(GeneralFunction function, int size) { // TODO rename size to degree in all methods/docs
        return makeTaylorSeries(function, size, 0);
    }

    /**
     * Returns a Taylor series of the specified function at the specified center with the specified amount of terms
     * @param function The function whose Taylor series is being found
     * @param size The amount of terms in the polynomial (this includes zero terms like {@code 0*x^2}
     * @param center Where the Taylor series is centered
     * @return Taylor series of the specified function
     */
    public static GeneralFunction makeTaylorSeries(GeneralFunction function, int size, double center) { // TODO rename size to degree in all methods/docs
        GeneralFunction[] taylorSeriesTerms = new GeneralFunction[size];
        char var;
        Set<Character> vars = SearchTools.getAllVariables(function);
        if (vars.size() == 1)
            var = vars.iterator().next();
        else
            var = Settings.singleVariableDefault;

        for (int i = 0; i < size; i++){
            taylorSeriesTerms[i] = new Product(new Constant(function.getNthDerivative('x', i).evaluate(Map.of(var, center)) / MiscTools.factorial(i)), new Pow(new Constant(i), new Sum(new Variable('x'), new Constant(-center))));
        }
        return new Sum(taylorSeriesTerms);
    }
}


