package tools;

import functions.GeneralFunction;
import functions.commutative.Product;

public class ArrayTools {

	private ArrayTools(){}

	/**
	 * Deep-clones {@link GeneralFunction} array
	 * @param functionArray array of Functions
	 * @return a clone of the {@code GeneralFunction[]}
	 */
	public static GeneralFunction[] deepClone(GeneralFunction[] functionArray) {
		GeneralFunction[] newArray = new GeneralFunction[functionArray.length];
		for (int i = 0; i < newArray.length; i++)
			newArray[i] = functionArray[i].clone();
		return newArray;
	}


	/**
	 * Checks if two {@link GeneralFunction} arrays have equal GeneralFunctions at each index
	 * @param functionArray1 first array
	 * @param functionArray2 second array
	 * @return true if the two arrays are deeply equal
	 */
	public static boolean deepEquals(GeneralFunction[] functionArray1, GeneralFunction[] functionArray2) {
		return deepEqualsExcluding(functionArray1, functionArray2, -1);
	}

	/**
	 * Checks if two {@link GeneralFunction} arrays have equal GeneralFunctions at each index, excluding one specified index
	 * @param functionArray1 first array
	 * @param functionArray2 second array
	 * @param excluding      index to exclude
	 * @return true if the two arrays are deeply equal
	 */
	public static boolean deepEqualsExcluding(GeneralFunction[] functionArray1, GeneralFunction[] functionArray2, int excluding) {
		if (functionArray1.length != functionArray2.length)
			return false;
		for (int i = 0; i < functionArray1.length; i++)
			if (i != excluding && !functionArray1[i].equalsFunction(functionArray2[i]))
				return false;

		return true;
	}

	/**
	 * Returns a copy of the input array with the {@link GeneralFunction} at the specified index removed
	 * @param functionArray the array of GeneralFunctions
	 * @param index         index of the GeneralFunction to be removed
	 * @return the new array
	 */
	public static GeneralFunction[] removeFunctionAt(GeneralFunction[] functionArray, int index) {
		if (index < 0 || index >= functionArray.length)
			throw new ArrayIndexOutOfBoundsException("Index " + index + "is out of bounds.");
		GeneralFunction[] newArray = new GeneralFunction[functionArray.length - 1];
		System.arraycopy(functionArray, 0, newArray, 0, index);
		System.arraycopy(functionArray, index + 1, newArray, index, functionArray.length - index - 1);
		return newArray;
	}

	/**
	 * Takes two {@link GeneralFunction} arrays as inputs, one of which contains a {@link functions.commutative.CommutativeFunction} containing the other. Creates a new array with all elements from both arrays, excluding the aforementioned {@link functions.commutative.CommutativeFunction} at indexInOuter.
	 * @param outer        first {@code GeneralFunction[]}
	 * @param inner        second {@code GeneralFunction[]}
	 * @param indexInOuter index to not include in the first {@code GeneralFunction[]}
	 * @return new {@code GeneralFunction[]} with elements from both
	 */
	public static GeneralFunction[] pullUp(GeneralFunction[] outer, GeneralFunction[] inner, int indexInOuter) {
		GeneralFunction[] an = new GeneralFunction[outer.length + inner.length - 1];
		if (indexInOuter > 0)
			System.arraycopy(outer, 0, an, 0, indexInOuter);
		if (indexInOuter < outer.length - 1)
			System.arraycopy(outer, indexInOuter + 1, an, indexInOuter, outer.length - indexInOuter - 1);
		System.arraycopy(inner, 0, an, outer.length - 1, inner.length);
		return an;
	}

	/**
	 * Returns a {@code GeneralFunction[]} where every element in add is now a {@link Product} of [multiply] and the function that was previously at its location in [add]
	 * @param multiply the {@code GeneralFunction[]} which is distributed to every element in add
	 * @param add      the {@code GeneralFunction[]} which is being distributed to
	 * @return {@code GeneralFunction[]} where the multiply has been distributed to the add
	 */
	public static GeneralFunction[] distribute(GeneralFunction[] multiply, GeneralFunction[] add) {
		GeneralFunction[] finalAdd = new GeneralFunction[add.length];
		for (int i = 0; i < finalAdd.length; i++) {
			finalAdd[i] = new Product(append(multiply, add[i]));
		}
		return finalAdd;
	}

	/**
	 * Appends a {@link GeneralFunction} to a {@code GeneralFunction[]}
	 * @param array the array to be appended to
	 * @param element the element to append
	 * @return new combined {@code GeneralFunction[]}
	 */
	public static GeneralFunction[] append(GeneralFunction[] array, GeneralFunction element) {
		GeneralFunction[] finalFunctionArray = new GeneralFunction[array.length + 1];
		System.arraycopy(array, 0, finalFunctionArray, 0, array.length);
		finalFunctionArray[array.length] = element;
		return finalFunctionArray;
	}

}
