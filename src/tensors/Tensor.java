package tensors;

import functions.GeneralFunction;
import functions.commutative.Product;
import org.jetbrains.annotations.NotNull;
import output.OutputFunction;
import parsing.FunctionParser;
import tools.DefaultFunctions;
import tools.MiscTools;
import tools.exceptions.NotYetImplementedException;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Tensor extends GeneralFunction {

	public static final boolean assertValidity = true;
	public static final boolean zeroIndexed = true; // TODO implement this


	public static void main(String[] args) {
		Tensor zeroes = new Tensor("a", true, 7, DefaultFunctions.ZERO);
		Tensor cov1 = newCovector(DefaultFunctions.ONE, DefaultFunctions.TEN);
		Tensor bigBoy = newTensor(new int[]{2, 2}, new String[]{"a", "b"}, new boolean[]{true, false},
				new Object[][]{
						{tt("cos(x)"), tt("sin(x)")},
						{tt("-sin(x)"), tt("cos(x)")}
				}
		);
		System.out.println(zeroes);
		System.out.println(cov1);
		System.out.println(bigBoy);
		System.out.println(bigBoy.getElement(1));
		System.out.println(bigBoy.getElement(1, 0 ));
		System.out.println(Arrays.deepToString(bigBoy.getElementTree()));
		System.out.println(tensorProduct(cov1, bigBoy));
		System.out.println(tensorProduct(bigBoy, cov1));
	}

	private static GeneralFunction tt(String s) {
		return FunctionParser.parseSimplified(s);
	}


	protected String index;
	protected final boolean isContra;
	protected final GeneralFunction[] elements;

	protected Tensor(String index, boolean isContra, GeneralFunction... elements) {
		this.index = index;
		this.isContra = isContra;
		this.elements = elements;
		if (assertValidity)
			assertValid();
	}

	protected Tensor(String index, boolean isContra, int length, GeneralFunction fill) {
		this.index = index;
		this.isContra = isContra;
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
				if (!(function instanceof Tensor))
					throw new IllegalArgumentException("Mismatched tensors and non-tensors in tensor construction.");
		} else {
			for (GeneralFunction function : elements)
				if (function instanceof Tensor)
					throw new IllegalArgumentException("Mismatched tensors and non-tensors in tensor construction.");
		}
	}

	public static Tensor newVector(String index, GeneralFunction... elements) {
		return new Tensor(index, true, elements);
	}

	public static Tensor newVector(GeneralFunction... elements) {
		return newVector(null, elements);
	}

	public static Tensor newCovector(String index, GeneralFunction... elements) {
		return new Tensor(index, false, elements);
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
		if (loc == dimensions.length - 1)
			for (int i = 0; i < arr.length; i++)
				arr[i] = (GeneralFunction) elements[i]; // TODO check for class cast
		else
			for (int i = 0; i < arr.length; i++)
				arr[i] = newTensor(loc + 1, dimensions, indices, contravariants, (Object[]) elements[i]); // TODO check for class cast
		return new Tensor(indices[loc], contravariants[loc], arr);
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
		return getElementHelper(new LinkedList<>(indices));
	}

	public GeneralFunction getElement(Integer... indices) {
		return getElement(List.of(indices));
	}


	private void getIndicesHelper(List<String> indices) {
		indices.add(index);
		if (elements[0] instanceof Tensor tensor)
			tensor.getIndicesHelper(indices);
	}

	public List<String> getIndices() {
		List<String> list = new LinkedList<>();
		getIndicesHelper(list);
		return list;
	}


	public Object[] getElementTree() {
		if (elements[0] instanceof Tensor)
			return Arrays.stream(elements)
					.map(function -> ((Tensor) function).getElementTree())
					.toArray();
		else
			return elements;
	}


	public Tensor scale(GeneralFunction scalar) {
		return scale(scalar, this);
	}

	public static Tensor scale(GeneralFunction scalar, Tensor tensor) {
		if (tensor.elements[0] instanceof Tensor)
			return new Tensor(
					tensor.index,
					tensor.isContra,
					Arrays.stream(tensor.elements)
							.map(function -> Tensor.scale(scalar, (Tensor) function))
							.toArray(GeneralFunction[]::new)
			);
		else
			return new Tensor(
					tensor.index,
					tensor.isContra,
					Arrays.stream(tensor.elements)
							.map(function -> MiscTools.minimalSimplify(new Product(scalar, function)))
							.toArray(GeneralFunction[]::new)
			);
	}

	public static Tensor tensorProduct(Tensor first, Tensor second) {
		if (second.elements[0] instanceof Tensor)
			return new Tensor(
					second.index,
					second.isContra,
					Arrays.stream(second.elements)
							.map(function -> Tensor.tensorProduct(first, (Tensor) function))
							.toArray(GeneralFunction[]::new)
			);
		else
			return new Tensor(
					second.index,
					second.isContra,
					Arrays.stream(second.elements)
							.map(function -> Tensor.scale(function, first))
							.toArray(GeneralFunction[]::new)
			);
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
