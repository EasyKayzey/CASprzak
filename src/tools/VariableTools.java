package tools;

import config.Settings;
import functions.GeneralFunction;
import functions.special.Variable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * The {@link VariableTools} class contains miscellaneous methods revolving around the {@link Variable} class
 */
public class VariableTools {

	private VariableTools(){}

	/**
	 * Returns a {@link Predicate} of {@link GeneralFunction} describing whether a given {@link GeneralFunction} is an instance of {@link Variable} with {@code varID} equal to the specified character
	 * @param varID the character of the variable to be checked for
	 * @return the predicate described above
	 */
	public static Predicate<GeneralFunction> isVariable(char varID) {
		return input -> (input instanceof Variable v) && (v.varID == varID);
	}

	/**
	 * Returns a set of characters representing the characters of all variables used in this function
	 * @param input the function to be tested
	 * @return the set of variable characters
	 */
	public static Set<Character> getAllVariables(GeneralFunction input) {
		Set<Character> vars = new HashSet<>();
		SearchTools.consumeIf(input, f -> vars.add(((Variable) f).varID), f -> (f instanceof Variable));
		return vars;
	}

	/**
	 * If the input is in terms of only one variable, returns that variable's character; otherwise, returns {@link Settings#singleVariableDefault}
	 * @param input the function to be read from
	 * @return the variable character requested
	 */
	public static char getSingleVariable(GeneralFunction input) {
		Set<Character> vars = getAllVariables(input);
		if (vars.size() == 1)
			return vars.iterator().next();
		else
			return Settings.singleVariableDefault;
	}

	/**
	 * Returns true if the {@link Variable} specified is NOT found in the {@link GeneralFunction}
	 * @param function The {@link GeneralFunction} that is being searched
	 * @param varID The variable ID of the variable that is being looked for
	 * @return true if the {@link Variable} specified is NOT found in the {@link GeneralFunction}
	 */
	public static boolean doesNotContainsVariable(GeneralFunction function, char varID) {
		return !SearchTools.existsAny(function, isVariable(varID));
	}
}
