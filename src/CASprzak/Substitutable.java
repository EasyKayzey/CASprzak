package CASprzak;

public interface Substitutable {

	/**
	 * Substitutes a new {@link Function} into a variable
	 * @param varID the variable to be substituted into
	 * @param toReplace the {@link Function} that will be substituted
	 * @return the new {@link Function} after all substitutions are preformed
	 */
	Function substitute(int varID, Function toReplace);
}
