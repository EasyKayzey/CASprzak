package show.ezkz.casprzak.core.tools.functiongenerators;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import show.ezkz.casprzak.core.functions.endpoint.Variable;
import show.ezkz.casprzak.core.tools.MiscTools;
import show.ezkz.casprzak.core.tools.VariableTools;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The methods in {@link TaylorSeries} produce Taylor Series for functions.
 */
public class TaylorSeries {

    private TaylorSeries(){}

    /**
     * Returns a Maclaurin series of the specified function of a given degree
     * @param function the function whose Maclaurin series is being found
     * @param degree the degree of the Maclaurin polynomial
     * @return a Maclaurin series of the specified function
     */
    public static GeneralFunction makeTaylorSeries(GeneralFunction function, int degree) {
        return makeTaylorSeries(function, degree, 0);
    }

    /**
     * Returns a Taylor series of the specified function at the specified center of a given degree
     * @param function the function whose Taylor series is being found
     * @param degree the degree of the Taylor polynomial
     * @param center where the Taylor series is centered
     * @return a Taylor series of the specified function
     */
    public static GeneralFunction makeTaylorSeries(GeneralFunction function, int degree, double center) {
		Set<String> variables = VariableTools.getAllVariables(function);
		if (variables.size() > 1)
			throw new UnsupportedOperationException("Cannot perform Taylor series with a function of more than 1 variable.");

		GeneralFunction[] taylorSeriesTerms = new GeneralFunction[degree + 1];
		String var = VariableTools.getSingleVariable(function);
		for (int i = 0; i <= degree; i++) {
			taylorSeriesTerms[i] = new Product(
					new Constant(function.getNthDerivative(var, i).evaluate(Map.of(var, center)) / MiscTools.factorial(i)),
					new Pow(
							new Constant(i),
							new Sum(new Variable(var), new Constant(-center))
					)
			);
		}
		return new Sum(taylorSeriesTerms).simplify();
	}
}


