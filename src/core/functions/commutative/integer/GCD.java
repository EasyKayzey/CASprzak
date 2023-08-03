package core.functions.commutative.integer;

import core.functions.GeneralFunction;
import core.functions.commutative.CommutativeFunction;
import core.tools.MiscTools;

public class GCD extends IntegerCommutativeFunction {
	/**
	 * Constructs a new {@link GCD}
	 * @param functions the {@link GeneralFunction}s that will be acted on
	 */
	public GCD(GeneralFunction... functions) {
		super(functions);
	}

	@Override
	protected long operateInt(int... operands) {
		int gcd = operands[0];
		for (int i = 1 ; i < operands.length; i++)
			gcd = MiscTools.gcd(operands[i], gcd);
		return gcd;
	}

	@Override
	public CommutativeFunction getInstance(GeneralFunction... functions) {
		return new GCD(functions);
	}


	@Override
	public GeneralFunction clone() {
		return new GCD(functions.clone());
	}

	public double getIdentityValue() {
		return 0;
	}
}
