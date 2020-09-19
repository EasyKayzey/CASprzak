package show.ezkz.casprzak.core.functions.unitary.trig;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.Integrable;
import show.ezkz.casprzak.core.functions.Invertible;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;

/**
 * The abstract {@link GeneralTrigFunction} class represents any trigonometric function (circular, hyperbolic, or inverse).
 */
public abstract class GeneralTrigFunction extends UnitaryFunction implements Invertible, Integrable {

	/**
	 * Constructs a new {@link GeneralTrigFunction}
	 * @param operand The operand of the {@code GeneralTrigFunction}
	 */
	public GeneralTrigFunction(GeneralFunction operand) {
		super(operand);
	}

}
