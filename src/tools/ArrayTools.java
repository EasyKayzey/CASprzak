package tools;

import functions.GeneralFunction;
import functions.commutative.Product;

/**
 * The {@link ArrayTools} class contains miscellaneous methods relating to arrays.
 */
public class ArrayTools {

	private ArrayTools(){}

	/**
	 * Deep-clones a {@link GeneralFunction} array
	 * @param functionArray array of Functions
	 * @return a deep-clone of the {@code GeneralFunction[]}
	 */
	public static GeneralFunction[] deepClone(GeneralFunction[] functionArray) {
		GeneralFunction[] newArray = new GeneralFunction[functionArray.length];
		for (int i = 0; i < newArray.length; i++)
			newArray[i] = functionArray[i].clone();
		return newArray;
	}

	/**
	 * Returns a copy of the input array with the {@link GeneralFunction} at the specified index removed
	 * @param functionArray the array of GeneralFunctions
	 * @param index         index of the function to be removed
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
	 * Returns a {@code GeneralFunction[]} where every element in add is now a {@link Product} of {@code multiply} and the function that was previously at its location in {@code add}
	 * @param multiply the {@code GeneralFunction[]} which is distributed to every element in {@code add}
	 * @param add      the {@code GeneralFunction[]} which is {@code multiply} being distributed on to
	 * @return {@code GeneralFunction[]} where {@code multiply} has been distributed to {@code add}
	 */
	public static GeneralFunction[] distribute(GeneralFunction[] multiply, GeneralFunction[] add) {
		GeneralFunction[] finalAdd = new GeneralFunction[add.length];
		for (int i = 0; i < finalAdd.length; i++)
			finalAdd[i] = new Product(append(multiply, add[i]));
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
