package tensors;

import functions.GeneralFunction;
import functions.endpoint.Variable;
import org.jetbrains.annotations.NotNull;
import output.OutputFunction;
import parsing.FunctionParser;
import tools.DefaultFunctions;
import tools.exceptions.NotYetImplementedException;
import tools.helperclasses.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static tensors.TensorOperations.*;

public class Tensor extends GeneralFunction {

	public static boolean assertValidity = true;
	public static boolean zeroIndexed = true;
	public static boolean reindexVariableSubstitution = true;


	public static void main(String[] args) {
		Tensor zeroes = new Tensor("a", true, 1, 7, DefaultFunctions.ZERO);
		Tensor vec1 = newVector("b", DefaultFunctions.ONE, DefaultFunctions.ONE);
		Tensor bigBoy = newTensor(new int[]{2, 2}, new String[]{"a", "b"}, new boolean[]{true, false},
				new Object[][]{
						{tt("cos(x)"), tt("sin(x)")},
						{tt("-sin(x)"), tt("cos(x)")}
				}
		);
		System.out.println(zeroes);
		System.out.println(vec1);
		System.out.println(bigBoy);
		System.out.println(bigBoy.getElement(1));
		System.out.println(bigBoy.getElement(1, 0));
		zeroIndexed = false;
		System.out.println(bigBoy.getElement(2, 1));
		zeroIndexed = true;
		System.out.println(Arrays.deepToString(bigBoy.getElementTree()));
		System.out.println(tensorProduct(bigBoy, vec1));
		System.out.println();
//		Tensor traceTest = bigBoy.changeIndex("b", "a"); // TODO this no work because it's not a tensor anymore
//		System.out.println(traceTest.executeInternalSums());
		Tensor bigger = tensorProduct(vec1, bigBoy);
		System.out.println(bigger);
		System.out.println(bigger.executeInternalSums());
		System.out.println(tensorProduct(bigBoy, vec1).executeInternalSums());
	}

	private static GeneralFunction tt(String s) {
		return FunctionParser.parseSimplified(s);
	}


	protected String index;
	protected final boolean isContra;
	protected final GeneralFunction[] elements;
	public final int rank;

	protected Tensor(String index, boolean isContra, int rank, GeneralFunction... elements) {
		this.index = index;
		this.isContra = isContra;
		this.rank = rank;
		this.elements = elements;
		if (assertValidity)
			assertValid();
	}

	protected Tensor(String index, boolean isContra, int rank, int length, GeneralFunction fill) {
		this.index = index;
		this.isContra = isContra;
		this.rank = rank;
		elements = new GeneralFunction[length];
		Arrays.fill(elements, fill);
		if (assertValidity)
			assertValid();
	}

	protected void assertValid() {
		if (elements.length == 0) {
			throw new IllegalArgumentException("Tensors cannot have zero length.");
		} else if (elements[0] instanceof Tensor) {
			for (GeneralFunction function : elements)
				if (function instanceof Tensor tensor) {
					tensor.assertValid();
					if (tensor.rank != rank - 1)
						throw new IllegalArgumentException("Mismatched ranks in tensor construction.");
				} else
					throw new IllegalArgumentException("Mismatched tensors and non-tensors in tensor construction.");
		} else {
			if (rank != 1)
				throw new IllegalArgumentException("Mismatched ranks in tensor construction.");
			for (GeneralFunction function : elements)
				if (function instanceof Tensor)
					throw new IllegalArgumentException("Mismatched tensors and non-tensors in tensor construction.");
		}
	}

	public static Tensor newVector(String index, GeneralFunction... elements) {
		return new Tensor(index, true, 1, elements);
	}

	public static Tensor newVector(GeneralFunction... elements) {
		return newVector(null, elements);
	}

	public static Tensor newCovector(String index, GeneralFunction... elements) {
		return new Tensor(index, false, 1, elements);
	}

	public static Tensor newCovector(GeneralFunction... elements) {
		return newCovector(null, elements);
	}

	public static Tensor newTensor(int[] dimensions, String[] indices, boolean[] contravariants, Object[] elements) {
		if (dimensions.length != indices.length || indices.length != contravariants.length)
			throw new IllegalArgumentException("Tensor argument arrays do not have matching lengths.");
		return newTensor(0, dimensions, indices, contravariants, elements);
	}

	private static Tensor newTensor(int loc, int[] dimensions, String[] indices, boolean[] contravariants, Object[] elements) {
		GeneralFunction[] arr = new GeneralFunction[dimensions[loc]];
		try {
			if (loc == dimensions.length - 1) {
				for (int i = 0; i < arr.length; i++)
					arr[i] = (GeneralFunction) elements[i];
				return new Tensor(indices[loc], contravariants[loc], 1, arr);
			} else {
				for (int i = 0; i < arr.length; i++)
					arr[i] = newTensor(loc + 1, dimensions, indices, contravariants, (Object[]) elements[i]);
				return new Tensor(indices[loc], contravariants[loc], ((Tensor) arr[0]).rank + 1, arr);
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Array argument length (rank) does not match nested GeneralFunction array (Object[]) depth.");
		}
	}


	private GeneralFunction getElementHelper(LinkedList<Integer> indices) {
		int idx = indices.removeFirst();
		if (indices.isEmpty())
			return elements[idx];
		else if (elements[idx] instanceof Tensor tensor)
			return tensor.getElementHelper(indices);
		else
			throw new IllegalArgumentException("Supplied too more indices than there are nested tensors to index.");
	}

	public GeneralFunction getElement(List<Integer> indices) {
		LinkedList<Integer> linkedIndices = new LinkedList<>(indices);
		if (!zeroIndexed)
			for (ListIterator<Integer> iter = linkedIndices.listIterator(); iter.hasNext();)
				iter.set(iter.next() - 1);
		return getElementHelper(linkedIndices);
	}

	public GeneralFunction getElement(Integer... indices) {
		return getElement(List.of(indices));
	}


	private void getIndicesHelper(String[] indexArray) {
		indexArray[indexArray.length - rank] = index;
		if (elements[0] instanceof Tensor tensor)
			tensor.getIndicesHelper(indexArray);
	}

	public String[] getIndices() {
		String[] indices = new String[rank];
		getIndicesHelper(indices);
		return indices;
	}

	private void getVariancesHelper(boolean[] variances) {
		variances[variances.length - rank] = isContra;
		if (elements[0] instanceof Tensor tensor)
			tensor.getVariancesHelper(variances);
	}

	public boolean[] getVariances() {
		boolean[] list = new boolean[rank];
		getVariancesHelper(list);
		return list;
	}

	private void getDimensionsHelper(int[] dimensions) {
		dimensions[dimensions.length - rank] = elements.length;
		if (elements[0] instanceof Tensor tensor)
			tensor.getDimensionsHelper(dimensions);
	}

	public int[] getDimensions() {
		int[] list = new int[rank];
		getDimensionsHelper(list);
		return list;
	}


	public Object[] getElementTree() {
		if (rank > 1)
			return Arrays.stream(elements)
					.map(function -> ((Tensor) function).getElementTree())
					.toArray();
		else
			return elements;
	}


	public Tensor changeIndex(String from, String to) {
		return modifyWith(this,
				i -> i.equals(from) ? to : i,
				i -> i,
				i -> i,
				i -> i.changeIndex(from, to),
				reindexVariableSubstitution ? i -> i.substituteVariables(Map.of(from, new Variable(to))) : i -> i,
				true);
	}


	public Tensor scale(GeneralFunction scalar) {
		return TensorOperations.scale(scalar, this);
	}


	public Tensor executeInternalSums() {
		String[] oldIndices = getIndices();
		Pair<Integer, Integer> repeatedIndex = getRepeatedIndex(oldIndices);
		if (repeatedIndex == null)
			return this;
		int first = repeatedIndex.getFirst();
		int second = repeatedIndex.getSecond();
		boolean[] oldVariances = getVariances();
		if (oldVariances[first] == oldVariances[second])
			throw new IllegalArgumentException("Variances are not opposite in internal tensor sum.");
		int[] oldDimensions = getDimensions();
		if (oldDimensions[first] != oldDimensions[second])
			throw new IllegalArgumentException("Mismatched dimensions of indices in internal tensor sum.");
		int sumLength = oldDimensions[first];

		String[] newIndices = new String[oldIndices.length - 2];
		boolean[] newVariances = new boolean[oldVariances.length - 2];
		int[] newDimensions = new int[oldDimensions.length - 2];
		for (int i = 0, j = 0; i < rank; i++) {
			if (i != first && i != second) {
				newIndices[j] = oldIndices[i];
				newVariances[j] = oldVariances[i];
				newDimensions[j] = oldDimensions[i];
				j++;
			}
		}

		int[] newIxs = new int[newDimensions.length];
		Arrays.fill(newIxs, 0);

		NestedArray oldElements = new NestedArray(getElementTree());
		NestedArray newElements = NestedArray.createFromDimensions(newDimensions);
		boolean flag = true;
		while (flag) {
			int[] oldIxs = copyToArraySkipping(newIxs, first, second);
			GeneralFunction[] toAdd = new GeneralFunction[sumLength];
			for (int i = 0; i < sumLength; i++) {
				oldIxs[first] = i;
				oldIxs[second] = i;
				toAdd[i] = (GeneralFunction) oldElements.getObjectAtIndex(oldIxs);
			}
			newElements.setObjectAtIndex(sumArbitrary(toAdd), newIxs);
			flag = incrementArray(0, newIxs, newDimensions);
		}
		return Tensor.newTensor(
				newDimensions,
				newIndices,
				newVariances,
				newElements.array
		);
	}

	private static int[] copyToArraySkipping(int[] oldArray, int first, int second) {
		int[] newArray = new int[oldArray.length + 2];
		int j = 0;
		for (int i = 0; i < newArray.length; i++)
			if (i != first && i != second)
				newArray[i] = oldArray[j++];
		return newArray;
	}

	private static boolean incrementArray(int loc, int[] indices, int[] maxes) {
		if (loc == indices.length)
			return false;
		else if (indices[loc] + 1 < maxes[loc]) {
			indices[loc]++;
			return true;
		} else {
			indices[loc] = 0;
			return incrementArray(loc + 1, indices, maxes);
		}
	}

	private static Pair<Integer, Integer> getRepeatedIndex(String[] sourceIndices) {
		for (int i = 1; i < sourceIndices.length; i++)
			for (int j = 0; j < i; j++)
				if (sourceIndices[i].equals(sourceIndices[j]))
					return new Pair<>(i, j);
		return null;
	}

	@Override
	public GeneralFunction clone() {
		throw new NotYetImplementedException("Not implemented in Tensor.");
	}

	@Override
	public GeneralFunction substituteAll(Predicate<? super GeneralFunction> test, Function<? super GeneralFunction, ? extends GeneralFunction> replacer) {
		throw new NotYetImplementedException("Not implemented in Tensor.");
	}

	@Override
	public boolean equalsFunction(GeneralFunction that) {
		throw new NotYetImplementedException("Not implemented in Tensor.");
	}

	@Override
	protected int compareSelf(GeneralFunction that) {
		throw new NotYetImplementedException("Not implemented in Tensor.");
	}

	@Override
	public int hashCode() {
		throw new NotYetImplementedException("Not implemented in Tensor.");
	}

	@Override
	public @NotNull Iterator<GeneralFunction> iterator() {
		throw new NotYetImplementedException("Not implemented in Tensor.");
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		throw new NotYetImplementedException("Not implemented in Tensor.");
	}

	@Override
	public double evaluate(Map<String, Double> variableValues) {
		throw new NotYetImplementedException("Not implemented in Tensor.");
	}

	@Override
	public OutputFunction toOutputFunction() {
		throw new NotYetImplementedException("Not implemented in Tensor.");
	}

	@Override
	public GeneralFunction simplify() {
		throw new NotYetImplementedException("Not implemented in Tensor.");
	}
	
	public String toString() {
		return (isContra ? "vec" : "cov") + Arrays.toString(elements) + (index == null ? "" : index);
	}
	
}
