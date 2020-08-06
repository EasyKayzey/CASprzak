package tensors;

public class NestedArray {

	public final Object[] array;

	public NestedArray(Object[] array) {
		this.array = array;
	}

	private static Object getObjectAtIndexHelper(int loc, Object[] array, int[] index) {
		if (loc < index.length - 1)
			return getObjectAtIndexHelper(loc + 1, (Object[]) array[index[loc]], index);
		else
			return array[index[loc]];
	}

	public Object getObjectAtIndex(int... index) {
		try {
			return getObjectAtIndexHelper(0, array, index);
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Class cast when indexing nested Object[]", e);
		}
	}

	private static void setObjectAtIndexHelper(int loc, Object toSet, Object[] array, int[] index) {
		if (loc < index.length - 1)
			setObjectAtIndexHelper(loc + 1, toSet, (Object[]) array[index[loc]], index);
		else
			array[index[loc]] = toSet;
	}

	public void setObjectAtIndex(Object toSet, int... index) {
		try {
			setObjectAtIndexHelper(0, toSet, array, index);
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Class cast when indexing nested Object[]", e);
		}
	}


}
