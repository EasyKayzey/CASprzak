package tensors;

import functions.GeneralFunction;
import org.jetbrains.annotations.NotNull;
import output.OutputFunction;
import parsing.FunctionParser;
import tools.DefaultFunctions;
import tools.exceptions.NotYetImplementedException;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Tensor extends GeneralFunction {

	public static void main(String[] args) {
		Tensor zeroes = new Tensor("a", true, 7, DefaultFunctions.ZERO);
		Tensor cov1 = covector(DefaultFunctions.ONE, DefaultFunctions.TEN);
		Tensor bigBoye = tensor(new int[]{2, 2}, new String[]{"a", "b"}, new boolean[]{true, false},
				new Object[]{
						new Object[]{tt("cos(x)"), tt("sin(x)")},
						new Object[]{tt("-sin(x)"), tt("cos(x)")}
				}
		);
		System.out.println(zeroes);
		System.out.println(cov1);
		System.out.println(bigBoye);
		System.out.println(bigBoye.index(1));
		System.out.println(bigBoye.index(1, 0 ));
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
		assertValid();
	}

	protected Tensor(String index, boolean isContra, int length, GeneralFunction fill) {
		this.index = index;
		this.isContra = isContra;
		elements = new GeneralFunction[length];
		Arrays.fill(elements, fill);
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

	public static Tensor vector(String index, GeneralFunction... elements) {
		return new Tensor(index, true, elements);
	}

	public static Tensor vector(GeneralFunction... elements) {
		return vector(null, elements);
	}


	public static Tensor covector(String index, GeneralFunction... elements) {
		return new Tensor(index, false, elements);
	}

	public static Tensor covector(GeneralFunction... elements) {
		return covector(null, elements);
	}

	public static Tensor tensor(int[] dimensions, String[] indices, boolean[] contravariants, Object[] elements) {
		if (dimensions.length != indices.length || indices.length != contravariants.length)
			throw new IllegalArgumentException("Tensor argument arrays do not have matching lengths.");
		return tensor(0, dimensions, indices, contravariants, elements);
	}

	private static Tensor tensor(int loc, int[] dimensions, String[] indices, boolean[] contravariants, Object[] elements) {
		GeneralFunction[] arr = new GeneralFunction[dimensions[loc]];
		if (loc == dimensions.length - 1)
			for (int i = 0; i < arr.length; i++)
				arr[i] = (GeneralFunction) elements[i]; // TODO check for class cast
		else
			for (int i = 0; i < arr.length; i++)
				arr[i] = tensor(loc + 1, dimensions, indices, contravariants, (Object[]) elements[i]); // TODO check for class cast
		return new Tensor(indices[loc], contravariants[loc], arr);
	}


	protected GeneralFunction getIndex(LinkedList<Integer> indices) {
		int idx = indices.removeFirst();
		if (indices.isEmpty())
			return elements[idx];
		else if (elements[idx] instanceof Tensor tensor)
			return tensor.getIndex(indices);
		else
			throw new IllegalArgumentException("Supplied too more indices than there are nested tensors to index.");
	}

	public GeneralFunction index(List<Integer> indices) {
		return getIndex(new LinkedList<>(indices));
	}

	public GeneralFunction index(Integer... indices) {
		return index(List.of(indices));
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
