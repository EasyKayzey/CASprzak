package functions.commutative;

import config.Settings;
import functions.Function;
import functions.special.Constant;
import org.jetbrains.annotations.NotNull;
import tools.FunctionTools;
import tools.helperclasses.FunctionPredicate;

import java.util.*;
import java.util.function.DoubleBinaryOperator;

public abstract class CommutativeFunction extends Function {

	/**
	 * Array of {@link Function}s operated on by the {@link CommutativeFunction}
	 */
	protected final Function[] functions;

	/**
	 * The identity of the {@link CommutativeFunction} (e.g. 1 for * and 0 for +)
	 */
	protected double identityValue;

	/**
	 * The operation performed by the {@link CommutativeFunction}
	 */
	public DoubleBinaryOperator operation;

	/**
	 * Constructs a new CommutativeFunction
	 * @param functions The {@link Function}s that will be acted on
	 */
	public CommutativeFunction(Function... functions) {
		this.functions = functions;
		Arrays.sort(this.functions);
	}


	public Function simplify() {
		return this.simplifyInternal().simplifyTrivialElement();
	}

	/**
	 * Returns current {@link CommutativeFunction} after {@link #simplifyElements()}, {@link #simplifyPull()}, {@link #simplifyIdentity()}, and {@link #simplifyConstants()}
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
	 * Returns current {@link CommutativeFunction} after simplifying each {@link Function} in {@link #functions}
	 * @return current {@link CommutativeFunction} after simplifying each {@link Function} in {@link #functions}
	 */
	public abstract CommutativeFunction simplifyElements();

	/**
	 * Returns current {@link CommutativeFunction} after simplifying the {@link #identityValue}
	 * @return current {@link CommutativeFunction} after simplifying the {@link #identityValue}
	 */
	public CommutativeFunction simplifyIdentity() {
		Function[] toPut = getFunctions();
		for (int i = 0; i < toPut.length; i++) {
			if (toPut[i] instanceof Constant constant) {
				if (constant.constant == identityValue) {
					toPut = FunctionTools.removeFunctionAt(toPut, i);
					i--;
				}
			}
		}
		return me(toPut);
	}

	/**
	 * Returns current {@link CommutativeFunction} after combined the {@link Constant}s
	 * @return current {@link CommutativeFunction} after combined the {@link Constant}s
	 */
	public CommutativeFunction simplifyConstants() {
		for (int i = 1; i < functions.length; i++) {
			for (int j = 0; j < i; j++) {
				if (functions[i] instanceof Constant first && functions[j] instanceof Constant second) {
					Function[] toOperate = FunctionTools.deepClone(functions);
					toOperate[i] = new Constant(operation.applyAsDouble(first.constant, second.constant));
					toOperate = FunctionTools.removeFunctionAt(toOperate, j);
					return me(toOperate).simplifyConstants();
				}
			}
		}
		if (Settings.trustImmutability)
			return this;
		else
			return (CommutativeFunction) clone();
	}


	/**
	 * Returns current {@link CommutativeFunction} after instance of the same {@link CommutativeFunction} have been pulled out and each term added to {@link #functions}
	 * @return current {@link CommutativeFunction} after instance of the same {@link CommutativeFunction} have been pulled out and each term added to {@link #functions}
	 */
	public CommutativeFunction simplifyPull() {
		for (int i = 0; i < functions.length; i++) {
			if (this.getClass().equals(functions[i].getClass())) {
				return (me(FunctionTools.pullUp(functions, ((CommutativeFunction) functions[i]).getFunctions(), i))).simplifyInternal();
			}
		}
		if (Settings.trustImmutability)
			return this;
		else
			return (CommutativeFunction) clone();
	}

	/**
	 * Returns identity {@link Constant} if {@link #functions} length is 0 or the {@link Function} if {@link #functions} length is 1
	 * @return identity {@link Constant} if {@link #functions} length is 0 or the {@link Function} if {@link #functions} length is 1
	 */
	public Function simplifyTrivialElement() {
		if (functions.length == 0)
			return new Constant(identityValue);
		if (functions.length == 1)
			return functions[0].simplify();
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}


	/**
	 * Returns {@link #functions}
	 * @return {@link #functions}
	 */
	public Function[] getFunctions() {
		if (Settings.trustImmutability)
			return functions;
		else
			return FunctionTools.deepClone(functions);
	}

	/**
	 * Returns the length of {@link #functions}
	 * @return the length of {@link #functions}
	 */
	public int getFunctionsLength() {
		return functions.length;
	}


	/**
	 * Returns an instance of this {@link Function}
	 * @param functions Constructor parameter
	 * @return an instance of this Function
	 */
	public abstract CommutativeFunction me(Function... functions);

	/**
	 * Checks if this CommutativeFunction has a subset (as a Multiply) satisfying the condition, including empty and single-element products
	 * @param test the condition to be satisfied
	 * @return true if the condition was satisfied by a subset
	 */
	public boolean hasSubsetSatisfying(FunctionPredicate test) {
		for (int run = 0; run < Math.pow(2, functions.length); run++) {
			List<Function> subset = new ArrayList<>();
			for (int ix = 0; ix < functions.length; ix++)
				if (((run >> ix) & 1) > 0)
					subset.add(functions[ix]);
			if (test.test(me(subset.toArray(new Function[0]))))
				return true;
		}
		return false;
	}

	public Function substitute(char varID, Function toReplace) {
		Function[] newFunctions = new Function[functions.length];
		for (int i = 0; i < functions.length; i++)
			newFunctions[i] = functions[i].substitute(varID, toReplace);
		return me(newFunctions);
	}


	public boolean equals(Function that) {
		if (that instanceof CommutativeFunction function && this.getClass().equals(that.getClass()))
			return FunctionTools.deepEquals(functions, function.getFunctions());
		return false;
	}

	public int compareSelf(Function that) {
		if (that instanceof CommutativeFunction function) {
			if (functions.length != function.getFunctionsLength())
				return functions.length - function.getFunctionsLength();
			Function[] thisFunctions = functions;
			Function[] thatFunctions = function.getFunctions();
			for (int i = 0; i < thisFunctions.length; i++) {
				if (!thisFunctions[i].equals(thatFunctions[i]))
					return thisFunctions[i].compareTo(thatFunctions[i]);
			}
		} else {
			throw new IllegalArgumentException("Illegally called CommutativeFunction.compareSelf on a non-CommutativeFunction");
		}
		System.out.println("This isn't supposed to happen. Check CompareSelf of CommutativeFunction and Function.compareTo");
		return 0;
	}


	public @NotNull Iterator<Function> iterator() {
		return new CommutativeIterator();
	}

	private class CommutativeIterator implements Iterator<Function> {
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
		public Function next() {
			if (!hasNext())
				throw new NoSuchElementException("Out of elements in CommutativeFunction " + Arrays.toString(functions));
			return functions[loc++];
		}
	}
}
