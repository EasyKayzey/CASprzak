package show.ezkz.casprzak.core.functions.unitary.transforms;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.tools.exceptions.TransformFailedException;

/**
 * The abstract {@link Transformation} class represents any transformation that turns one function into another function. Ex: {@code ∫} or {@code d/dx}
 */
public abstract class Transformation extends UnitaryFunction {
	/**
	 * The String of the variable that the {@link Transformation} is with respect to
	 */
	public final String respectTo;

	/**
	 * Constructs a new {@link Transformation}
	 * @param operand the operand of the {@link Transformation}
	 * @param respectTo the variable that the {@link Transformation} operates with respect to
	 */
	public Transformation(GeneralFunction operand, String respectTo) {
		super(operand);
		this.respectTo = respectTo;
	}

	@Override
	public GeneralFunction simplify() {
		if (Settings.executeOnSimplify) {
			Transformation current = (Transformation) simplifyInternal();
			try {
				return current.execute().simplify();
			} catch (TransformFailedException ignored) {
				return current;
			}
		} else {
			return simplifyInternal();
		}
	}

	/**
	 * Returns the transformation of the {@link #operand}
	 * @return the transformation of the {@link #operand}
	 * @throws TransformFailedException if the transform did not succeed
	 */
	@SuppressWarnings("unused")
	public abstract GeneralFunction execute() throws TransformFailedException;

	/**
	 * Returns the variable that this transform works with respect to
	 * @return the variable that this transform works with respect to
	 */
	public String getRespectTo() {
		return respectTo;
	}
}
