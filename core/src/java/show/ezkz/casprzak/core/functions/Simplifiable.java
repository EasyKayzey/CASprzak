package show.ezkz.casprzak.core.functions;

import show.ezkz.casprzak.core.config.SimplificationSettings;

/**
 * The {@link Simplifiable} interface provides an interface for the simplification of functions.
 */
public interface Simplifiable {

	/**
	 * Returns this {@link GeneralFunction}, simplified
	 * @return the simplified function
	 * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
	 */
	GeneralFunction simplify(SimplificationSettings settings);
}
