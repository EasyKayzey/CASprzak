package tools;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.special.Constant;
import functions.special.Variable;

/**
 * {@link DefaultFunctions} contains instances of many often-used {@link GeneralFunction}s so that they need not be re-instantiated repeatedly, as well as shortcuts for the negative and inverse of a {@link GeneralFunction}.
 */
@SuppressWarnings({"StaticVariableOfConcreteClass", "unused"})
public class DefaultFunctions {

	private DefaultFunctions(){}

	/**
	 * A {@link Constant} with the value of {@code π}
	 */
	public static final Constant PI = new Constant("π");

	/**
	 * A {@link Constant} with the value of {@code e}
	 */
	public static final Constant E = new Constant("e");

	/**
	 * A {@link Constant} with the value of {@code -2}
	 */
	public static final Constant NEGATIVE_TWO = new Constant(-2);

	/**
	 * A {@link Constant} with the value of {@code -1}
	 */
	public static final Constant NEGATIVE_ONE = new Constant(-1);

	/**
	 * A {@link Constant} with the value of {@code -0.5}
	 */
	public static final Constant NEGATIVE_HALF = new Constant(-.5);

	/**
	 * A {@link Constant} with the value of {@code 0}
	 */
	public static final Constant ZERO = new Constant(0);

	/**
	 * A {@link Constant} with the value of {@code 0.5}
	 */
	public static final Constant HALF = new Constant(.5);

	/**
	 * A {@link Constant} with the value of {@code 1}
	 */
	public static final Constant ONE = new Constant(1);

	/**
	 * A {@link Constant} with the value of {@code 2}
	 */
	public static final Constant TWO = new Constant(2);

	/**
	 * A {@link Constant} with the value of {@code 10}
	 */
	public static final Constant TEN = new Constant(10);


	/**
	 * A {@link Variable} with the variable ID of {@code x}
	 */
	public static final Variable X = new Variable('x');

	/**
	 * A {@link Variable} with the variable ID of {@code y}
	 */
	public static final Variable Y = new Variable('y');

	/**
	 * A {@link Variable} with the variable ID of {@code z}
	 */
	public static final Variable Z = new Variable('z');


	/**
	 * Returns a {@link Product} of {@link DefaultFunctions#NEGATIVE_ONE} and the {@code input}
	 * @param input the input {@link GeneralFunction}
	 * @return a {@link Product} of {@link DefaultFunctions#NEGATIVE_ONE} and the {@code input}
	 */
	public static Product negative(GeneralFunction input) {
		return new Product(NEGATIVE_ONE, input);
	}

	/**
	 * Returns a {@link Pow} of {@code input} to the power of {@link DefaultFunctions#NEGATIVE_ONE}
	 * @param input the input {@link GeneralFunction}
	 * @return a {@link Pow} of {@code input} to the power of {@link DefaultFunctions#NEGATIVE_ONE}
	 */
	public static Pow reciprocal(GeneralFunction input) {
		return new Pow(NEGATIVE_ONE, input);
	}

	/**
	 * Returns a {@link Pow} of {@code input} to the power of {@link DefaultFunctions#HALF}
	 * @param input the input {@link GeneralFunction}
	 * @return a {@link Pow} of {@code input} to the power of {@link DefaultFunctions#HALF}
	 */
	public static Pow sqrt(GeneralFunction input) {
		return new Pow(HALF, input);
	}

	/**
	 * Returns a {@link Pow} of {@code input} to the power of {@link DefaultFunctions#TWO}
	 * @param input the input {@link GeneralFunction}
	 * @return a {@link Pow} of {@code input} to the power of {@link DefaultFunctions#TWO}
	 */
	public static Pow square(GeneralFunction input) {
		return new Pow(TWO, input);
	}
}
