package functions.unitary.transforms;

import config.Settings;
import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;
import tools.exceptions.TransformFailedException;

/**
 * TODO explain
 */
public abstract class TransformFunction extends UnitaryFunction {
	/**
	 * The character of the variable that the {@link TransformFunction} is with respect to
	 */
	public final Character respectTo;

	/**
	 * Constructs a new {@link TransformFunction}
	 * @param operand the operand of the {@link TransformFunction}
	 * @param respectTo the variable that the {@link TransformFunction} operates with respect to
	 */
	public TransformFunction(GeneralFunction operand, Character respectTo) {
		super(operand);
		this.respectTo = respectTo;
	}

	@Override
	public GeneralFunction simplify() {
		if (Settings.executeOnSimplify) {
			TransformFunction current = (TransformFunction) simplifyInternal();
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
	public char getRespectTo() {
		return respectTo;
	}
}
