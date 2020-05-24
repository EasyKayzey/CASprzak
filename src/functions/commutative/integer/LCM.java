package functions.commutative.integer;

import functions.GeneralFunction;
import functions.commutative.CommutativeFunction;
import tools.MiscTools;

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
	public String toString() {
		StringBuilder str = new StringBuilder("lcm(");
		for (int i = 0; i < functions.length - 1; i++) {
			str.append(functions[i]);
			str.append(", ");
		}
		str.append(functions[functions.length - 1]);
		return str.toString();
	}

	@Override
	public GeneralFunction clone() {
		return new LCM(functions.clone());
	}

	public double getIdentityValue() {
		return 1;
	}
}
