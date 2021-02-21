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
	 * Returns a Maclaurin series of the specified function of a given degree
	 * @param function the function whose Maclaurin series is being found
	 * @param degree the degree of the Maclaurin polynomial
	 * @param variable the String of the {@link Variable} of the polynomial
	 * @return a Maclaurin series of the specified function
	 */
	public static GeneralFunction makeTaylorSeries(GeneralFunction function, int degree, String variable) {
		GeneralFunction taylorSeries = makeTaylorSeries(function, degree);
		if (Settings.singleVariableDefault.equals(variable))
			return taylorSeries;
		else {
			Map<String, Variable> substitution = new HashMap<>() {
				{
					put(Settings.singleVariableDefault, new Variable(variable));
				}
			};
			return taylorSeries.substituteVariables(substitution);
		}
	}

	/**
	 * Returns a Taylor series of the specified function at the specified center of a given degree
	 * @param function the function whose Taylor series is being found
	 * @param degree the degree of the Taylor polynomial
	 * @param center where the Taylor series is centered
	 * @param variable the String of the {@link Variable} of the polynomial
	 * @return a Taylor series of the specified function
	 */
	public static GeneralFunction makeTaylorSeries(GeneralFunction function, int degree, double center, String variable) {
		GeneralFunction taylorSeries = makeTaylorSeries(function, degree, center);
		if (Settings.singleVariableDefault.equals(variable))
			return taylorSeries;
		else {
			Map<String, Variable> substitution = new HashMap<>() {
				{
					put(Settings.singleVariableDefault, new Variable(variable));
				}
			};
			return taylorSeries.substituteVariables(substitution);
		}
	}

    /**
     * Returns a Taylor series of the specified function at the specified center of a given degree
     * @param function the function whose Taylor series is being found
     * @param degree the degree of the Taylor polynomial
     * @param center where the Taylor series is centered
     * @return a Taylor series of the specified function
     */
    public static GeneralFunction makeTaylorSeries(GeneralFunction function, int degree, double center) {
        GeneralFunction[] taylorSeriesTerms = new GeneralFunction[degree];
        String var = VariableTools.getSingleVariable(function);
        for (int i = 0; i < degree; i++){
            taylorSeriesTerms[i] = new Product(new Constant(function.getNthDerivative(Settings.singleVariableDefault, i).evaluate(Map.of(var, center)) / MiscTools.factorial(i)), new Pow(new Constant(i), new Sum(new Variable(Settings.singleVariableDefault), new Constant(-center))));
        }
        return new Sum(taylorSeriesTerms).simplify();
    }
}


