package tools;

import functions.Function;
import functions.commutative.Multiply;

public class FunctionTools {

	private FunctionTools(){}

	/**
	 * Deep-clones an array of Functions
	 *
	 * @param functionArray array of Functions
	 * @return new Function[]
	 */
	public static Function[] deepClone(Function[] functionArray) {
		Function[] newArray = new Function[functionArray.length];
		for (int i = 0; i < newArray.length; i++)
			newArray[i] = functionArray[i].clone();
		return newArray;
	}


	/**
	 * Checks if two {@link Function} arrays have equal Functions at each index (assumes sorted)
	 *
	 * @param functionArray1 first array
	 * @param functionArray2 second array
	 * @return true if equal
	 */
	public static boolean deepEquals(Function[] functionArray1, Function[] functionArray2) {
		return deepEquals(functionArray1, functionArray2, 0);
	}

	/**
	 * Checks if two {@link Function} arrays have equal Functions at each index, starting at a point (assumes sorted)
	 *
	 * @param functionArray1 first array
	 * @param functionArray2 second array
	 * @param start          index to begin at
	 * @return true if equal
	 */
	public static boolean deepEquals(Function[] functionArray1, Function[] functionArray2, int start) {
		if (functionArray1.length != functionArray2.length)
			return false;
		for (int i = start; i < functionArray1.length; i++) {
			if (!functionArray1[i].equals(functionArray2[i]))
				return false;
		}
		return true;
	}

	/**
	 * Removes a {@link Function} from a Function[] and returns the new array (does not modify)
	 *
	 * @param functionArray the array of Functions
	 * @param index         index of the Function to be removed
	 * @return the new array
	 */
	public static Function[] removeFunctionAt(Function[] functionArray, int index) {
		Function[] newArray = new Function[functionArray.length - 1];
		for (int i = 0; i < newArray.length; i++)
			newArray[i] = functionArray[(i < index ? i : i + 1)];
		return newArray;
	}

	/**
	 * Creates a new {@link Function}[] out of two Function arrays, including all elements from both except for one in the first.
	 *
	 * @param outer        first Function[]
	 * @param inner        second Function[]
	 * @param indexInOuter index to not include in the first Function[]
	 * @return new Function[] with elements from both
	 */
	public static Function[] pullUp(Function[] outer, Function[] inner, int indexInOuter) {
		Function[] an = new Function[outer.length + inner.length - 1];
		if (indexInOuter > 0)
			System.arraycopy(outer, 0, an, 0, indexInOuter);
		if (indexInOuter < outer.length - 1)
			System.arraycopy(outer, indexInOuter + 1, an, indexInOuter, outer.length - indexInOuter - 1);
		System.arraycopy(inner, 0, an, outer.length - 1, inner.length);
		return an;
	}

	/**
	 * Returns a Function[] where every element in add is now a {@link Multiply} of multiply and the function that was previously there in add
	 *
	 * @param multiply the Function[] which is distributed to every element in add
	 * @param add      the Function[] which is being distributed to
	 * @return Function[] where the multiply has been distributed to the add
	 */
	public static Function[] distribute(Function[] multiply, Function[] add) {
		Function[] finalAdd = new Function[add.length];
		for (int i = 0; i < finalAdd.length; i++) {
			finalAdd[i] = new Multiply(append(multiply, add[i]));
		}
		return finalAdd;
	}

	/**
	 * Appends two {@link Function} arrays
	 *
	 * @param first  first array
	 * @param second second array
	 * @return new combined array
	 */
	public static Function[] append(Function[] first, Function second) {
		Function[] finalFunctionArray = new Function[first.length + 1];
		System.arraycopy(first, 0, finalFunctionArray, 0, first.length);
		finalFunctionArray[first.length] = second;
		return finalFunctionArray;
	}

}
