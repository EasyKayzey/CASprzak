package functions.commutative;

import functions.GeneralFunction;
import functions.special.Constant;
import tools.ArrayTools;
import tools.MiscTools;
import tools.helperclasses.AbstractPair;
import tools.helperclasses.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Sum extends CommutativeFunction {
	/**
	 * Constructs a new {@link Sum}
	 * @param functions The terms being added together
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

	public String toString() {
		if (functions.length < 1)
			return "()";
		StringBuilder string = new StringBuilder("(");
		for (int i = functions.length - 1; i >= 1; i--) {
			string.append(functions[i].toString());
			string.append(" + ");
		}
		string.append(functions[0].toString());
		string.append(")");
		return string.toString();
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
}
