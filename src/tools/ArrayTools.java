package tools;

import functions.GeneralFunction;
import functions.commutative.Product;

public class ArrayTools {

	private ArrayTools(){}

	/**
	 * Deep-clones an array of Functions
	 * @param functionArray array of Functions
	 * @return new GeneralFunction[]
	 */
	public static GeneralFunction[] deepClone(GeneralFunction[] functionArray) {
		GeneralFunction[] newArray = new GeneralFunction[functionArray.length];
		for (int i = 0; i < newArray.length; i++)
			newArray[i] = functionArray[i].clone();
		return newArray;
	}


	/**
	 * Checks if two {@link GeneralFunction} arrays have equal GeneralFunctions at each index (assumes sorted)
	 * @param functionArray1 first array
	 * @param functionArray2 second array
	 * @return true if equal
	 */
	public static boolean deepEquals(GeneralFunction[] functionArray1, GeneralFunction[] functionArray2) {
		return deepEqualsExcluding(functionArray1, functionArray2, -1);
	}

	/**
	 * Checks if two {@link GeneralFunction} arrays have equal Functions at each index, excluding one at a specified index
	 * @param functionArray1 first array
	 * @param functionArray2 second array
	 * @param excluding      index to exclude
	 * @return true if equal
	 */
	public static boolean deepEqualsExcluding(GeneralFunction[] functionArray1, GeneralFunction[] functionArray2, int excluding) {
		if (functionArray1.length != functionArray2.length)
			return false;
		for (int i = 0; i < functionArray1.length; i++) {
			if (i != excluding && !functionArray1[i].equalsFunction(functionArray2[i]))
				return false;
		}
		return true;
	}

	/**
	 * Removes a {@link GeneralFunction} from a GeneralFunction[] and returns the new array (does not modify)
	 * @param functionArray the array of Functions
	 * @param index         index of the GeneralFunction to be removed
	 * @return the new array
	 */
	public static GeneralFunction[] removeFunctionAt(GeneralFunction[] functionArray, int index) {
		GeneralFunction[] newArray = new GeneralFunction[functionArray.length - 1];
		for (int i = 0; i < newArray.length; i++)
			newArray[i] = functionArray[(i < index ? i : i + 1)];
		return newArray;
	}

	/**
	 * Creates a new {@link GeneralFunction}[] out of two GeneralFunction arrays, including all elements from both except for one in the first.
	 * @param outer        first GeneralFunction[]
	 * @param inner        second GeneralFunction[]
	 * @param indexInOuter index to not include in the first GeneralFunction[]
	 * @return new GeneralFunction[] with elements from both
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
	 * Returns a GeneralFunction[] where every element in add is now a {@link Product} of multiply and the function that was previously there in add
	 * @param multiply the GeneralFunction[] which is distributed to every element in add
	 * @param add      the GeneralFunction[] which is being distributed to
	 * @return GeneralFunction[] where the multiply has been distributed to the add
	 */
	public static GeneralFunction[] distribute(GeneralFunction[] multiply, GeneralFunction[] add) {
		GeneralFunction[] finalAdd = new GeneralFunction[add.length];
		for (int i = 0; i < finalAdd.length; i++) {
			finalAdd[i] = new Product(append(multiply, add[i]));
		}
		return finalAdd;
	}

	/**
	 * Appends two {@link GeneralFunction} arrays
	 * @param first  first array
	 * @param second second array
	 * @return new combined array
	 */
	public static GeneralFunction[] append(GeneralFunction[] first, GeneralFunction second) {
		GeneralFunction[] finalFunctionArray = new GeneralFunction[first.length + 1];
		System.arraycopy(first, 0, finalFunctionArray, 0, first.length);
		finalFunctionArray[first.length] = second;
		return finalFunctionArray;
	}

}
