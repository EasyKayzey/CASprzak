package functions.commutative;

import config.Settings;
import functions.GeneralFunction;
import functions.binary.Pow;
import functions.special.Constant;
import tools.ArrayTools;
import tools.DefaultFunctions;
import tools.PolynomialTools;
import tools.helperclasses.AbstractPair;
import tools.helperclasses.Pair;

import java.util.*;

public class Product extends CommutativeFunction {
	/**
	 * Constructs a new {@link Product}
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

	public CommutativeFunction getInstance(GeneralFunction... functions) {
		return new Product(functions);
	}

	public Product clone() {
		return new Product(ArrayTools.deepClone(functions));
	}

	public String toString() {
		if (functions.length < 1)
			return "()";
		if (functions.length == 2 && functions[0] instanceof Constant constant && constant.constant == -1.0 && !(functions[1] instanceof Constant))
			return "-"+functions[1].toString();
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
	 * Returns true if the {@link Product} contains a {@link Constant} with value {@code 0}
	 * @return true if the {@link Product} contains a {@link Constant} with value {@code 0}
	 */
	public boolean isTimesZero() {
		for (GeneralFunction function : functions)
			if (function instanceof Constant constant && constant.constant == 0)
				return true;

		return false;
	}

	/**
	 * If {@link #functions} contains multiple functions that are equal to each other, including the bases of {@link Pow}s, then the exponents are added and the terms are combined. Ex: {@code sin(x)*(sin(x))^2} becomes {@code (sin(x))^3}
	 * @return A new {@link Product} with exponents combined as specified above
	 */
	public Product addExponents() {
		List<GeneralFunction> functionList = new LinkedList<>(List.of(functions));
		List<GeneralFunction> newFunctions = new ArrayList<>();

		ListIterator<GeneralFunction> initialIter = functionList.listIterator();
		while (initialIter.hasNext()) {
			GeneralFunction current = initialIter.next();
			if (!(current instanceof Pow))
				initialIter.set(new Pow(DefaultFunctions.ONE, current));
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
	 * If one element of the product is a {@link Sum}, distributes the other contents of the product onto the sum. Example: {@code sin(y)*(x+2) = x*sin(y) + 2*sin(y)}
	 * @return this function with multiplication and addition distributed if possible
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
