package functions.unitary.transforms;

import config.Settings;
import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;
import tools.exceptions.TransformFailedException;

public abstract class TransformFunction extends UnitaryFunction {
	/**
	 * The character of the variable that the TransformFunction is with respect to
	 */
	public final char respectTo;

	/**
	 * Constructs a new {@code TransformFunction}
	 * @param operand The operand of the TransformFunction
	 * @param respectTo The variable that the TransformFunction operates with respect to
	 */
	public TransformFunction(GeneralFunction operand, char respectTo) {
		super(operand);
		this.respectTo = respectTo;
	}

	@Override
	public GeneralFunction simplify() {
		if (Settings.executeOnSimplify)
			try {
				return execute();
			} catch (TransformFailedException ignored) {
				return simplifyInternal();
			}
		else
			return simplifyInternal();
	}

	/**
	 * Returns the transformation of the {@link UnitaryFunction#operand}
	 * @return The the transformation of the {@link UnitaryFunction#operand}
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
