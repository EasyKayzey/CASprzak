package functions.commutative;

import functions.GeneralFunction;
import functions.special.Constant;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * TODO explain
 */
public abstract class CommutativeFunction extends GeneralFunction {

	/**
	 * The array of {@link GeneralFunction}s operated on by the {@link CommutativeFunction}
	 */
	protected final GeneralFunction[] functions;

	/**
	 * The identity of the {@link CommutativeFunction}. (e.g. 1 for * and 0 for +)
	 */
	protected double identityValue;

	/**
	 * The operation performed by the {@link CommutativeFunction}
	 */
	public DoubleBinaryOperator operation;

	/**
	 * Constructs a new {@link CommutativeFunction}
	 * @param functions The {@link GeneralFunction}s that will be acted on
	 */
	public CommutativeFunction(GeneralFunction... functions) {
		this.functions = functions;
		Arrays.sort(this.functions);
	}

	/**
	 * Returns {@link #functions}
	 * @return {@link #functions}
	 */
	public GeneralFunction[] getFunctions() {
		return functions;
	}

	/**
	 * Returns an instance of this {@link GeneralFunction}
	 * @param functions the {@link GeneralFunction}s that will be acted on
	 * @return an instance of this {@link GeneralFunction}
	 */
	public abstract CommutativeFunction getInstance(GeneralFunction... functions);

	public boolean equalsFunction(GeneralFunction that) {
		if (that instanceof CommutativeFunction function && this.getClass().equals(that.getClass()))
			return Arrays.equals(functions, function.getFunctions());
		else
			return false;
	}

	public int compareSelf(GeneralFunction that) {
		if (that instanceof CommutativeFunction function) {
			if (functions.length != function.getFunctions().length)
				return functions.length - function.getFunctions().length;
			GeneralFunction[] thisFunctions = functions;
			GeneralFunction[] thatFunctions = function.getFunctions();
			for (int i = 0; i < thisFunctions.length; i++)
				if (!thisFunctions[i].equalsFunction(thatFunctions[i]))
					return thisFunctions[i].compareTo(thatFunctions[i]);
		} else {
			throw new IllegalCallerException("Illegally called CommutativeFunction.compareSelf on a non-CommutativeFunction");
		}
		throw new IllegalStateException("This code in CommutativeFunction.compareSelf should never run.");
	}

	public GeneralFunction substituteAll(Predicate<? super GeneralFunction> test, Function<? super GeneralFunction, ? extends GeneralFunction> replacer) {
		if (test.test(this))
			return replacer.apply(this);

		GeneralFunction[] newFunctions = new GeneralFunction[functions.length];
		for (int i = 0; i < functions.length; i++)
			newFunctions[i] = functions[i].substituteAll(test, replacer);
		return getInstance(newFunctions);
	}

	public GeneralFunction simplify() {
		return this.simplifyInternal().simplifyTrivialElement();
	}

	/**
	 * Simplifies this {@link CommutativeFunction} using all methods that are guaranteed to return a {@link CommutativeFunction} of the same type
	 * @return the simplified {@link CommutativeFunction}
	 */
	public CommutativeFunction simplifyInternal() {
		CommutativeFunction current = this;
		current = current.simplifyElements();
		current = current.simplifyPull();
		current = current.simplifyIdentity();
		current = current.simplifyConstants();
		return current;
	}

	/**
	 * Simplifies each element of this {@link CommutativeFunction}
	 * @return a new {@link CommutativeFunction} with each element of {@link #functions} simplified
	 */
	public CommutativeFunction simplifyElements() {
		GeneralFunction[] simplifiedFunctions = new GeneralFunction[functions.length];
		for (int i = 0; i < functions.length; i++)
			simplifiedFunctions[i] = functions[i].simplify();
		return getInstance(simplifiedFunctions);
	}

	/**
	 * Removes all instances of {@link #identityValue} from {@link #functions}
	 * @return a new {@link CommutativeFunction} with
	 */
	public CommutativeFunction simplifyIdentity() {
		List<GeneralFunction> toPut = new ArrayList<>(Arrays.asList(functions));
		toPut.removeIf(generalFunction -> generalFunction instanceof Constant constant && constant.constant == identityValue);
		return getInstance(toPut.toArray(new GeneralFunction[0]));
	}

	/**
	 * Combines all {@link Constant}s using {@link #operation}
	 * @return a new {@link CommutativeFunction} with the combined {@link Constant}s
	 */
	public CommutativeFunction simplifyConstants() {
		if (hasMultipleConstants()) {
			double accumulator = identityValue;
			List<GeneralFunction> functionList = new LinkedList<>(List.of(functions));
			ListIterator<GeneralFunction> iter = functionList.listIterator();
			while (iter.hasNext()) {
				if (iter.next() instanceof Constant constant) {
					accumulator = operation.applyAsDouble(accumulator, constant.constant);
					iter.remove();
				}
			}
			functionList.add(new Constant(accumulator));
			return getInstance(functionList.toArray(new GeneralFunction[0]));
		} else {
			return this;
		}
	}

	private boolean hasMultipleConstants() {
		boolean flag = false;
		for (GeneralFunction function : functions)
			if (function instanceof Constant)
				if (flag)
					return true;
				else
					flag = true;
		return false;
	}


	/**
	 * Composes all sub-functions of the same type. Ex: {@code (a+(b+c))} becomes {@code (a+b+c)}
	 * @return a new {@link CommutativeFunction} with all compositions performed
	 */
	public CommutativeFunction simplifyPull() {
		for (int i = 0; i < functions.length; i++)
			if (this.getClass().equals(functions[i].getClass()))
				return getInstance(pullUp(functions, ((CommutativeFunction) functions[i]).getFunctions(), i)).simplifyPull();

		return this;
	}

	private static GeneralFunction[] pullUp(GeneralFunction[] outer, GeneralFunction[] inner, int indexInOuter) {
		GeneralFunction[] newArray = new GeneralFunction[outer.length + inner.length - 1];
		if (indexInOuter > 0)
			System.arraycopy(outer, 0, newArray, 0, indexInOuter);
		if (indexInOuter < outer.length - 1)
			System.arraycopy(outer, indexInOuter + 1, newArray, indexInOuter, outer.length - indexInOuter - 1);
		System.arraycopy(inner, 0, newArray, outer.length - 1, inner.length);
		return newArray;
	}

	/**
	 * Returns identity {@link Constant} if {@link #functions} length is 0 and returns the {@link GeneralFunction} if {@link #functions} length is 1
	 * @return identity {@link Constant} if {@link #functions} length is 0 and returns the {@link GeneralFunction} if {@link #functions} length is 1
	 */
	public GeneralFunction simplifyTrivialElement() {
		if (functions.length == 0)
			return new Constant(identityValue);
		else if (functions.length == 1)
			return functions[0].simplify();
		else
			return this;
	}

	public @NotNull Iterator<GeneralFunction> iterator() {
		return new CommutativeIterator();
	}

	private class CommutativeIterator implements Iterator<GeneralFunction> {
		private int loc;

		private CommutativeIterator() {
			loc = 0;
		}

		@Override
		public boolean hasNext() {
			return loc < functions.length;
		}

		@SuppressWarnings("ValueOfIncrementOrDecrementUsed")
		@Override
		public GeneralFunction next() {
			if (!hasNext())
				throw new NoSuchElementException("Out of elements in CommutativeFunction " + Arrays.toString(functions));
			return functions[loc++];
		}
	}
}
