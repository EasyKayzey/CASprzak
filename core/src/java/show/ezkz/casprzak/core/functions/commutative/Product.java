package show.ezkz.casprzak.core.functions.commutative;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.Outputable;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import show.ezkz.casprzak.core.functions.unitary.transforms.Differential;
import show.ezkz.casprzak.core.functions.unitary.transforms.Integral;
import show.ezkz.casprzak.core.output.OutputCommutative;
import show.ezkz.casprzak.core.output.OutputFunction;
import show.ezkz.casprzak.core.tools.ArrayTools;
import show.ezkz.casprzak.core.tools.DefaultFunctions;
import show.ezkz.casprzak.core.tools.PolynomialTools;
import show.ezkz.casprzak.core.tools.helperclasses.AbstractPair;
import show.ezkz.casprzak.core.tools.helperclasses.Pair;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Product extends CommutativeFunction {

	private static final Collector<CharSequence, ?, String> joiningCollector = Collectors.joining(" * ", "(", ")");

	/**
	 * Constructs a new {@link Product}
	 * @param functions the terms being multiplied together
	 */
	public Product(GeneralFunction... functions) {
		super(functions);
	}

	public double evaluate(Map<String, Double> variableValues) {
		double accumulator = getIdentityValue();
		for (GeneralFunction f : functions)
			accumulator *= f.evaluate(variableValues);
		return accumulator;
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
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

	protected Collector<CharSequence, ?, String> getJoiningCollector() {
		return joiningCollector;
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
		current = current.fixNullIntegrals();
		current = current.addExponents();
		return current;
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
	 * Turns constructs like a null-respect integral times {@code \dx} into an integral with respect to {@code 'x'}
	 * @return this function with null-integrals fixed, if possible
	 */
	public Product fixNullIntegrals() {
		if (canFixNullIntegral()) {
			List<GeneralFunction> functionList = new LinkedList<>(List.of(functions));
			List<GeneralFunction> newFunctions = new ArrayList<>();

			outer: while (functionList.size() > 0) {
				GeneralFunction comparing = functionList.remove(0);
				if (comparing instanceof Integral integral) {
					Iterator<GeneralFunction> iter = functionList.iterator();
					while (iter.hasNext()) {
						GeneralFunction cur = iter.next();
						if (cur instanceof Differential diff) {
							newFunctions.add(new Integral(integral.operand, diff.respectTo).simplify());
							iter.remove();
							continue outer;
						}
					}
				} else if (comparing instanceof Differential diff) {
					Iterator<GeneralFunction> iter = functionList.iterator();
					while (iter.hasNext()) {
						GeneralFunction cur = iter.next();
						if (cur instanceof Integral integral) {
							newFunctions.add(new Integral(integral.operand, diff.respectTo).simplify());
							iter.remove();
							continue outer;
						}
					}
				} else {
					newFunctions.add(comparing);
				}
			}

			return new Product(newFunctions.toArray(new GeneralFunction[0])).simplifyInternal();
		} else {
			return this;
		}
	}

	private boolean canFixNullIntegral() {
		boolean hasNull = false;
		boolean hasDiff = false;
		for (GeneralFunction function : this) {
			if (function instanceof Integral integral && integral.respectTo == null)
				hasNull = true;
			if (function instanceof Differential)
				hasDiff = true;
		}
		return hasNull && hasDiff;
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

	public double getIdentityValue() {
		return 1;
	}

	public double operate(double a, double b) {
		return a * b;
	}


	public OutputFunction toOutputFunction() {
		return new OutputProduct(List.of(functions));
	}

	private static class OutputProduct extends OutputCommutative {

		private final List<AbstractPair<OutputFunction, GeneralFunction>> pairedOperands;

		public OutputProduct(List<GeneralFunction> operands) {
			super(
					"sum",
					operands.stream()
							.map(Outputable::toOutputFunction)
							.collect(Collectors.toList()),
					Settings.asteriskMultiplication ? Collectors.joining( "*" ) : Collectors.joining(),
					Settings.asteriskMultiplication ? Collectors.joining(" * ") : Collectors.joining()
			);
			pairedOperands = operands.stream()
					.map(f -> new Pair<>(f.toOutputFunction(), f))
					.collect(Collectors.toList());
		}

		@Override
		public String toString() {
			return pairedOperands.stream()
					.map(e -> parenthesizeNormal(e.getSecond()) ? "(" + e.getFirst().toString() + ")" : e.getFirst().toString())
					.collect(normalJoiningCollector);
		}

		@Override
		public String toLatex() {
			return pairedOperands.stream()
					.map(e -> parenthesizeLatex(e.getSecond()) ? "\\left(" + e.getFirst().toLatex() + "\\right)" : e.getFirst().toLatex())
					.collect(latexJoiningCollector);
		}

		private static boolean parenthesizeNormal(GeneralFunction function) {
			return function instanceof Sum;
		}

		private static boolean parenthesizeLatex(GeneralFunction function) {
			return function instanceof Sum;
		}

	}

}
