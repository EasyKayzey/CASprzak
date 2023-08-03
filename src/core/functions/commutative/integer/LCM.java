package core.functions.commutative.integer;

import core.functions.GeneralFunction;
import core.functions.commutative.CommutativeFunction;
import core.tools.MiscTools;

public class LCM extends IntegerCommutativeFunction {
	/**
	 * Constructs a new {@link LCM}
	 * @param functions the {@link GeneralFunction}s that will be acted on
	 */
	public LCM(GeneralFunction... functions) {
		super(functions);
	}

	@Override
	protected long operateInt(int... operands) {
		int lcm = operands[0];
		for (int i = 1 ; i < operands.length; i++)
			lcm = (operands[i] * lcm) / MiscTools.gcd(operands[i], lcm);
		return lcm;
	}

	@Override
	public CommutativeFunction getInstance(GeneralFunction... functions) {
		return new LCM(functions);
	}

	@Override
	public GeneralFunction clone() {
		return new LCM(functions.clone());
	}

	public double getIdentityValue() {
		return 1;
	}
}
