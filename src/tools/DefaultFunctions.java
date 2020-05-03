package tools;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.special.Constant;
import functions.special.Variable;

@SuppressWarnings({"StaticVariableOfConcreteClass", "unused"})
public class DefaultFunctions {
	public static final Constant PI = new Constant("pi");
	public static final Constant E = new Constant("e");
	public static final Constant NEGATIVE_TWO = new Constant(-2);
	public static final Constant NEGATIVE_ONE = new Constant(-1);
	public static final Constant NEGATIVE_HALF = new Constant(-.5);
	public static final Constant ZERO = new Constant(0);
	public static final Constant HALF = new Constant(.5);
	public static final Constant ONE = new Constant(1);
	public static final Constant TWO = new Constant(2);

	public static final Variable X = new Variable('x');
	public static final Variable Y = new Variable('y');
	public static final Variable Z = new Variable('z');

	public static Product negative(GeneralFunction input) {
		return new Product(NEGATIVE_ONE, input);
	}

	public static Pow reciprocal(GeneralFunction input) {
		return new Pow(NEGATIVE_ONE, input);
	}

	public static Pow sqrt(GeneralFunction input) {
		return new Pow(HALF, input);
	}

	public static Pow square(GeneralFunction input) {
		return new Pow(TWO, input);
	}
}
