package core.tools;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.endpoint.Variable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * The {@link VariableTools} class contains miscellaneous methods revolving around the {@link Variable} class.
 */
public class VariableTools {

	private VariableTools(){}

	/**
	 * Returns a {@link Predicate} of {@link GeneralFunction} describing whether a given {@link GeneralFunction} is an instance of {@link Variable} with {@code varID} equal to the specified String
	 * @param varID the String of the variable to be checked for
	 * @return the predicate described above
	 */
	public static Predicate<GeneralFunction> isVariable(String varID) {
		return input -> (input instanceof Variable v) && (v.varID.equals(varID));
	}

	/**
	 * Returns a set containing the Strings of all variables used in this function
	 * @param input the function to be tested
	 * @return the set of variable Strings
	 */
	public static Set<String> getAllVariables(GeneralFunction input) {
		Set<String> vars = new HashSet<>();
		SearchTools.consumeIf(input, f -> vars.add(((Variable) f).varID), f -> (f instanceof Variable));
		return vars;
	}

	/**
	 * If the input is in terms of only one variable, returns that variable's String; otherwise, returns {@link Settings#singleVariableDefault}
	 * @param input the function to be read from
	 * @return the variable String requested
	 */
	public static String getSingleVariable(GeneralFunction input) {
		Set<String> vars = getAllVariables(input);
		if (vars.size() == 1)
			return vars.iterator().next();
		else
			return Settings.singleVariableDefault;
	}

	/**
	 * Returns true if the {@link Variable} specified is NOT found in the {@link GeneralFunction}
	 * @param function The {@link GeneralFunction} that is being searched
	 * @param varID The String of the variable being searched for
	 * @return true if the {@link Variable} specified is NOT found in the {@link GeneralFunction}
	 */
	public static boolean doesNotContainsVariable(GeneralFunction function, String varID) {
		return !SearchTools.existsAny(function, isVariable(varID));
	}
}
