package tensors;

public interface Indexable<T> {
	T getAtIndex(int... index);
	void setAtIndex(T toSet, int... index);
	int[] getDimensions();
}
