package functions.commutative;

import config.Settings;
import functions.GeneralFunction;
import functions.binary.Pow;
import functions.special.Constant;
import functions.special.Variable;
import tools.DefaultFunctions;
import tools.FunctionTools;
import tools.PolynomialTools;

import java.util.Arrays;
import java.util.Map;

public class Product extends CommutativeFunction {
	/**
	 * Constructs a new Multiply
	 * @param functions The terms being multiplied together
	 */
	public Product(GeneralFunction... functions) {
		super(functions);
		identityValue = 1;
		operation = (a, b) -> (a * b);
	}

	public double evaluate(Map<Character, Double> variableValues) {
		double accumulator = identityValue;
		for (GeneralFunction f : functions)
			accumulator *= f.evaluate(variableValues);
		return accumulator;
	}


	public String toString() {
		if (functions.length < 1)
			return "(empty product)";
		StringBuilder string = new StringBuilder("(");
		for (int i = 0; i < functions.length - 1; i++) {
			string.append(functions[i].toString());
			string.append(" * ");
		}
		string.append(functions[functions.length - 1].toString());
		string.append(")");
		return string.toString();
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		GeneralFunction[] toAdd = new GeneralFunction[functions.length];

		for (int i = 0; i < toAdd.length; i++) {
			GeneralFunction[] toMultiply = new GeneralFunction[functions.length];
			for (int j = 0; j < functions.length; j++) {
				toMultiply = Arrays.copyOf(functions, functions.length);
				toMultiply[i] = functions[i].getSimplifiedDerivative(varID);
			}
			toAdd[i] = new Product(toMultiply);
		}

		return new Sum(toAdd);
	}

	public Product clone() {
		GeneralFunction[] toMultiply = new GeneralFunction[functions.length];
		for (int i = 0; i < functions.length; i++)
			toMultiply[i] = functions[i].clone();
		return new Product(toMultiply);
	}


	public GeneralFunction simplify() {
		Product currentFunction = simplifyInternal();
		if (currentFunction.isTimesZero())
			return new Constant((0));
		else if (currentFunction.getFunctions().length <= 1)
			return currentFunction.simplifyTrivialElement();
		else
			return currentFunction.distributeAll();
	}

	public Product simplifyInternal() {
		Product current = (Product) super.simplifyInternal();
		current = current.addExponents();
		return current;
	}

	@Override
	public Product simplifyElements() {
		GeneralFunction[] toMultiply = new GeneralFunction[functions.length];
		for (int i = 0; i < functions.length; i++) toMultiply[i] = functions[i].simplify();
		return new Product(toMultiply);
	}

	/**
	 * Returns true if the {@link Product} contains a 0 {@link Constant}
	 * @return true if the {@link Product} contains a 0 {@link Constant}
	 */
	public boolean isTimesZero() {
		for (GeneralFunction function : functions) {
			if (function instanceof Constant constant) {
				if (constant.constant == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * If {@link #functions} contains multiple of the same {@link Variable} multiplied by each other (e.g. x*x^3) then the exponents will be added and the terms will be combined into one element of {@link #functions}
	 * @return A new {@link Product} with all variable combined with added exponents
	 */
	public Product addExponents() {
		GeneralFunction[] simplifiedTerms = FunctionTools.deepClone(functions);
		for (int a = 0; a < simplifiedTerms.length; a++)
			if (!(simplifiedTerms[a] instanceof Pow))
				simplifiedTerms[a] = new Pow(DefaultFunctions.ONE, simplifiedTerms[a]);

		for (int i = 1; i < simplifiedTerms.length; i++) {
			for (int j = 0; j < i; j++) {
				if (simplifiedTerms[i] instanceof Pow first && simplifiedTerms[j] instanceof Pow second) {
					if (first.getFunction2().equalsFunction(second.getFunction2())) {
						simplifiedTerms[j] = new Pow(new Sum(first.getFunction1(), second.getFunction1()), first.getFunction2());
						simplifiedTerms = FunctionTools.removeFunctionAt(simplifiedTerms, i);
						return (new Product(simplifiedTerms)).simplifyInternal();
					}
				} else {
					throw new IllegalStateException("All functions should have been converted to powers.");
				}
			}
		}
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}

	public CommutativeFunction me(GeneralFunction... functions) {
		return new Product(functions);
	}

	/**
	 * Returns a {@link GeneralFunction} where the rest of the multiple has been distributed to any {@link Sum}. Example: {@code sin(y)*(x+2) = x*sin(y) + 2*sin(y)}
	 * @return a {@link GeneralFunction} where the rest of the multiple has been distributed to any {@link Sum}
	 */
	public GeneralFunction distributeAll() {
		GeneralFunction[] multiplyTerms = getFunctions();
		GeneralFunction[] addTerms;
		for (int i = 0; i < multiplyTerms.length; i++) {
			if (multiplyTerms[i] instanceof Sum sum) {
				addTerms = sum.getFunctions();
				multiplyTerms = FunctionTools.removeFunctionAt(multiplyTerms, i);
				return new Sum(FunctionTools.distribute(multiplyTerms, addTerms)).simplify();
			}
		}
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}

	public int compareSelf(GeneralFunction that) {
		boolean thisIsMonomial = PolynomialTools.isMonomial(this);
		boolean thatIsMonomial = PolynomialTools.isMonomial(that);
		if (thisIsMonomial && !thatIsMonomial) {
			return 1;
		} else if (!thisIsMonomial && thatIsMonomial) {
			return -1;
		} else if (!thisIsMonomial) { // && !thatIsMonomial
			return super.compareSelf(that);
		} else { // thisIsMonomial && thatIsMonomial
			double thisDegree = PolynomialTools.getDegree(this);
			double thatDegree = PolynomialTools.getDegree(that);
			if (thisDegree == thatDegree)
				return super.compareSelf(that);
			return (int) Math.signum(thisDegree - thatDegree);
		}
	}
}
