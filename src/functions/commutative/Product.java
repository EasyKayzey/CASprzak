package functions.commutative;

import config.Settings;
import functions.GeneralFunction;
import functions.binary.Pow;
import functions.special.Constant;
import functions.special.Variable;
import tools.ArrayTools;
import tools.DefaultFunctions;
import tools.PolynomialTools;
import tools.helperclasses.AbstractPair;
import tools.helperclasses.Pair;

import java.util.*;

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

	@Override
	public GeneralFunction getDerivative(char varID) {
		GeneralFunction[] toAdd = new GeneralFunction[functions.length];
		for (int i = 0; i < toAdd.length; i++) {
			GeneralFunction[] toMultiply = Arrays.copyOf(functions, functions.length);
			toMultiply[i] = functions[i].getSimplifiedDerivative(varID);
			toAdd[i] = new Product(toMultiply);
		}
		return new Sum(toAdd);
	}

	public CommutativeFunction me(GeneralFunction... functions) {
		return new Product(functions);
	}

	public Product clone() {
		return new Product(ArrayTools.deepClone(functions));
	}

	public String toString() {
		if (functions.length < 1)
			return "()";
		StringBuilder string = new StringBuilder("(");
		for (int i = 0; i < functions.length - 1; i++) {
			string.append(functions[i].toString());
			string.append(" * ");
		}
		string.append(functions[functions.length - 1].toString());
		string.append(")");
		return string.toString();
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

	public GeneralFunction simplify() {
		Product currentFunction = simplifyInternal();
		if (currentFunction.isTimesZero())
			return new Constant(0);
		else if (currentFunction.getFunctions().length <= 1)
			return currentFunction.simplifyTrivialElement();
		else if (Settings.distributeFunctions)
			return currentFunction.distributeAll();
		else
			return currentFunction;
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
		for (GeneralFunction function : functions)
			if (function instanceof Constant constant && constant.constant == 0)
				return true;

		return false;
	}

	/**
	 * If {@link #functions} contains multiple of the same {@link Variable} multiplied by each other (e.g. x*x^3) then the exponents will be added and the terms will be combined into one element of {@link #functions}
	 * @return A new {@link Product} with all variable combined with added exponents
	 */
	public Product addExponents() {
		List<GeneralFunction> functionList = new LinkedList<>(List.of(functions));
		List<GeneralFunction> newFunctions = new ArrayList<>();

		{
			ListIterator<GeneralFunction> iter = functionList.listIterator();
			while (iter.hasNext()) {
				GeneralFunction current = iter.next();
				if (!(current instanceof Pow))
					iter.set(new Pow(DefaultFunctions.ONE, current));
			}
		}

		boolean combinedAny = false;
		while (functionList.size() > 0) {
			Pow comparingPow = (Pow) functionList.remove(0);
			AbstractPair<GeneralFunction, GeneralFunction> comparingPair = new Pair<>(comparingPow.getFunction1(), comparingPow.getFunction2());
			Iterator<GeneralFunction> iter = functionList.iterator();
			while (iter.hasNext()) {
				Pow current = (Pow) iter.next();
				if (current.getFunction2().equalsFunction(comparingPair.getSecond())) {
					comparingPair = new Pair<>(new Sum(comparingPair.getFirst(), current.getFunction1()), comparingPair.getSecond());
					iter.remove();
					combinedAny = true;
				}
			}
			newFunctions.add(new Pow(comparingPair.getFirst(), comparingPair.getSecond()));
		}

		if (combinedAny)
			return new Product(newFunctions.toArray(new GeneralFunction[0])).simplifyInternal();
		else
			return this;
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
				multiplyTerms = ArrayTools.removeFunctionAt(multiplyTerms, i);
				return new Sum(ArrayTools.distribute(multiplyTerms, addTerms)).simplify();
			}
		}
		return this;
	}
}
