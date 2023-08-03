package core.tools.defaults;

import core.functions.GeneralFunction;
import core.functions.binary.Logb;
import core.functions.binary.Pow;
import core.functions.commutative.Product;
import core.functions.commutative.Sum;
import core.functions.endpoint.Constant;
import core.functions.endpoint.Variable;
import core.functions.unitary.integer.combo.Factorial;

/**
 * {@link DefaultFunctions} contains instances of many often-used {@link GeneralFunction}s so that they need not be re-instantiated repeatedly, as well as shortcuts for the negative and inverse of a {@link GeneralFunction}.
 */
@SuppressWarnings({"StaticVariableOfConcreteClass"})
public class DefaultFunctions {

	private DefaultFunctions(){}

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
	 * A {@link Constant} with the value of {@code π/2}
	 */
	public static final Constant HALF_PI = new Constant(Math.PI/2);

	/**
	 * A {@link Constant} with the value of {@code π}
	 */
	public static final Constant PI = new Constant("π");

	/**
	 * A {@link Constant} with the value of {@code 2π}
	 */
	public static final Constant TWO_PI = new Constant(Math.PI*2);

	/**
	 * A {@link Constant} with the value of {@code e}
	 */
	public static final Constant E = new Constant("e");


	/**
	 * A {@link Variable} with the variable ID of {@code x}
	 */
	public static final Variable X = new Variable("x");

	/**
	 * A {@link Variable} with the variable ID of {@code y}
	 */
	public static final Variable Y = new Variable("y");

	/**
	 * A {@link Variable} with the variable ID of {@code z}
	 */
	public static final Variable Z = new Variable("z");

	/**
	 * A {@link Variable} with the variable ID of {@code t}
	 */
	public static final Variable T = new Variable("t");


	/**
	 * Returns a {@link Product} of {@link #NEGATIVE_ONE} and the {@code input}
	 * @param input the input {@link GeneralFunction}
	 * @return a {@link Product} of {@link #NEGATIVE_ONE} and the {@code input}
	 */
	public static Product negative(GeneralFunction input) {
		return new Product(NEGATIVE_ONE, input);
	}

	/**
	 * Returns a {@link Pow} of {@code input} to the power of {@link #NEGATIVE_ONE}
	 * @param input the input {@link GeneralFunction}
	 * @return a {@link Pow} of {@code input} to the power of {@link #NEGATIVE_ONE}
	 */
	public static Pow reciprocal(GeneralFunction input) {
		return new Pow(NEGATIVE_ONE, input);
	}

	/**
	 * Returns a {@link Pow} of {@code input} to the power of {@link #HALF}
	 * @param input the input {@link GeneralFunction}
	 * @return a {@link Pow} of {@code input} to the power of {@link #HALF}
	 */
	public static Pow sqrt(GeneralFunction input) {
		return new Pow(HALF, input);
	}

	/**
	 * Returns a {@link Pow} of {@code input} to the power of {@link #TWO}
	 * @param input the input {@link GeneralFunction}
	 * @return a {@link Pow} of {@code input} to the power of {@link #TWO}
	 */
	public static Pow square(GeneralFunction input) {
		return new Pow(TWO, input);
	}

	/**
	 * Returns a {@link Logb} base {@link #TEN} of {@code input}
	 * @param input the input {@link GeneralFunction}
	 * @return a {@link Logb} base {@link #TEN} of {@code input}
	 */
	public static Logb log10(GeneralFunction input) {
		return new Logb(input, DefaultFunctions.TEN);
	}

	/**
	 * Returns the the function corresponding to {@code nCr}
	 * @param n the input {@link GeneralFunction}
	 * @param r the input {@link GeneralFunction}
	 * @return the the function corresponding to {@code nCr}
	 */
	public static GeneralFunction choose(GeneralFunction n, GeneralFunction r) {
		return new Product(Factorial.defaultFactorial(n), reciprocal(new Product(Factorial.defaultFactorial(r), Factorial.defaultFactorial(new Sum(n, negative(r))))));
	}

	/**
	 * Returns the the function corresponding to {@code nPr}
	 * @param n the input {@link GeneralFunction}
	 * @param r the input {@link GeneralFunction}
	 * @return the the function corresponding to {@code nCr}
	 */
	public static GeneralFunction permute(GeneralFunction n, GeneralFunction r) {
		return new Product(Factorial.defaultFactorial(n), reciprocal(Factorial.defaultFactorial(new Sum(n, negative(r)))));
	}

	/**
	 * Returns the the function corresponding to {@code first/second}
	 * @param numerator the input {@link GeneralFunction}
	 * @param denominator the input {@link GeneralFunction}
	 * @return the the function corresponding to {@code first/second}
	 */
	public static Product frac(GeneralFunction numerator, GeneralFunction denominator) {
		return new Product(numerator, DefaultFunctions.reciprocal(denominator));
	}

	/**
	 * Returns the the function corresponding to {@code first - second}
	 * @param first the input {@link GeneralFunction}
	 * @param second the input {@link GeneralFunction}
	 * @return the the function corresponding to {@code first - second}
	 */
	public static Sum subtract(GeneralFunction first, GeneralFunction second) {
		return new Sum(first, DefaultFunctions.negative(second));
	}
}
