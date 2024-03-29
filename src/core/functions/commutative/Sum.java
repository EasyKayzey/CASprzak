package core.functions.commutative;

import core.functions.GeneralFunction;
import core.functions.Outputable;
import core.functions.endpoint.Constant;
import core.output.OutputCommutative;
import core.output.OutputFunction;
import core.tools.ArrayTools;
import core.tools.MiscTools;
import core.tools.helperclasses.AbstractPair;
import core.tools.helperclasses.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Sum extends CommutativeFunction {

	private static final Collector<CharSequence, ?, String> joiningCollector = Collectors.joining(" + ", "(", ")");

	/**
	 * Constructs a new {@link Sum}
	 * @param functions the terms being added together
	 */
	public Sum(GeneralFunction... functions) {
		super(functions);
	}

	public double evaluate(Map<String, Double> variableValues) {
		double accumulator = getIdentityValue();
		for (GeneralFunction f : functions)
			accumulator += f.evaluate(variableValues);
		return accumulator;
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		GeneralFunction[] toAdd = new GeneralFunction[functions.length];
		for (int i = 0; i < functions.length; i++)
			toAdd[i] = functions[i].getSimplifiedDerivative(varID);
		return new Sum(toAdd);
	}

	public CommutativeFunction getInstance(GeneralFunction... functions) {
		return new Sum(functions);
	}

	public Sum clone() {
		return new Sum(ArrayTools.deepClone(functions));
	}

	protected Collector<CharSequence, ?, String> getJoiningCollector() {
		return joiningCollector;
	}


	public Sum simplifyInternal() {
		Sum current = (Sum) super.simplifyInternal();
		current = current.combineLikeTerms();
		return current;
	}

	/**
	 * Returns a {@link Sum} where like terms are added together. Ex: {@code 2x+x=3x}
	 * @return a {@link Sum} where like terms are added together
	 */
	public Sum combineLikeTerms() {
		List<Pair<Double, GeneralFunction>> functionList = MiscTools.stripConstantsOfSum(this);
		List<GeneralFunction> newFunctions = new ArrayList<>();

		boolean combinedAny = false;
		while (functionList.size() > 0) {
			AbstractPair<Double, GeneralFunction> comparing = functionList.remove(0);
			Iterator<Pair<Double, GeneralFunction>> iter = functionList.iterator();
			while (iter.hasNext()) {
				Pair<Double, GeneralFunction> current = iter.next();
				if (current.getSecond().equalsFunction(comparing.getSecond())) {
					comparing = new Pair<>(comparing.getFirst() + current.getFirst(), comparing.getSecond());
					iter.remove();
					combinedAny = true;
				}
			}
			newFunctions.add(new Product(new Constant(comparing.getFirst()), comparing.getSecond()));
		}

		if (combinedAny)
			return new Sum(newFunctions.toArray(new GeneralFunction[0])).simplifyInternal();
		else
			return this;
	}

	public double getIdentityValue() {
		return 0;
	}

	public double operate(double a, double b) {
		return a + b;
	}

	public OutputFunction toOutputFunction() {
		return new OutputSum(List.of(functions));
	}

	private static class OutputSum extends OutputCommutative {

		private final List<AbstractPair<OutputFunction, Boolean>> pairedOperands;

		public OutputSum(List<? extends GeneralFunction> operands) {
			super(
					"sum",
					operands.stream()
							.map(Outputable::toOutputFunction)
							.collect(Collectors.toList()),
					Collectors.joining(" + "),
					Collectors.joining(" + ")
			);
			pairedOperands = operands.stream()
					.map(f -> new Pair<>(f.toOutputFunction(), parenthesize(f)))
					.collect(Collectors.toList());
		}

		@Override
		public String toString() {
			return pairedOperands.stream()
					.map(e -> e.getSecond() ? "(" + e.getFirst().toString() + ")" : e.getFirst().toString())
					.collect(normalJoiningCollector);
		}

		@Override
		public String toLatex() {
			return pairedOperands.stream()
					.map(e -> e.getSecond() ? " \\left( " + e.getFirst().toLatex() + " \\right) " : e.getFirst().toLatex())
					.collect(latexJoiningCollector);
		}

		private static boolean parenthesize(GeneralFunction function) {
			return function instanceof Sum;
		}

	}
}
