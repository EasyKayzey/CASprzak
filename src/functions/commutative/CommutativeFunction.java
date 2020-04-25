package functions.commutative;

import core.Settings;
import functions.Function;
import functions.special.Constant;
import tools.FunctionTools;

import java.util.Arrays;

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
	 * Constructs a new CommutativeFunction
	 * @param functions The {@link Function}s that will be acted on
	 */
	public CommutativeFunction(Function... functions) {
		this.functions = functions;
		Arrays.sort(this.functions);
	}


	public Function simplify() {
		return this.simplifyInternal().simplifyOneElement();
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
	public abstract CommutativeFunction simplifyConstants();

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
	public Function simplifyOneElement() {
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
}
