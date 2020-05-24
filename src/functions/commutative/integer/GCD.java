package functions.commutative.integer;

import functions.GeneralFunction;
import functions.commutative.CommutativeFunction;
import tools.MiscTools;

public class GCD extends IntegerCommutativeFunction {
	/**
	 * Constructs a new {@link GCD}
	 * @param functions the {@link GeneralFunction}s that will be acted on
	 */
	public GCD(GeneralFunction... functions) {
		super(functions);
	}

	@Override
	protected long operate(int... operands) {
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
	public String toString() {
		StringBuilder str = new StringBuilder("gcd(");
		for (int i = 0; i < functions.length - 1; i++) {
			str.append(functions[i]);
			str.append(", ");
		}
		str.append(functions[functions.length - 1]);
		return str.toString();
	}

	@Override
	public GeneralFunction clone() {
		return new GCD(functions.clone());
	}
}
