package tensors;

import core.functions.GeneralFunction;

import java.util.List;
import java.util.stream.Collectors;

public class ArrayTensor extends DirectedNestedArray<Tensor, GeneralFunction> implements Tensor {

	@SuppressWarnings("unchecked")
	public static Tensor tensor(DirectedNested<?, GeneralFunction> directedNestedArray) {
		if (directedNestedArray instanceof DirectedEndpoint)
			return new TensorEndpoint(((DirectedEndpoint<?, GeneralFunction>) directedNestedArray).contained);
		else if (directedNestedArray.getElements().get(0) instanceof DirectedEndpoint)
			return new ArrayTensor(
					directedNestedArray.getDirection(),
					directedNestedArray.getElements().stream()
							.map(e -> (DirectedEndpoint<?, GeneralFunction>) e)
							.map(e -> new TensorEndpoint(e.contained))
							.collect(Collectors.toList()));
		else
			return new ArrayTensor(
					directedNestedArray.getDirection(),
					directedNestedArray.getElements().stream()
							.map(ArrayTensor::tensor)
							.collect(Collectors.toList()));
	}

	public static Tensor tensor(Nested<?, GeneralFunction> nestedArray, boolean... directions) {
		return tensor(direct(nestedArray, directions));
	}

	public static Tensor tensor(Object[] elements, boolean... directions) {
		return tensor(direct(nest(elements), directions));
	}

	protected ArrayTensor(boolean isUpper, List<Tensor> elements) {
		super(isUpper, elements);
	}

	@Override
	public void setAtIndex(GeneralFunction toSet, int... index) {
		throw new UnsupportedOperationException("Tensors are final, and their elements should never be changed.");
	}

	public static class TensorEndpoint extends DirectedEndpoint<Tensor, GeneralFunction> implements Tensor {
		public TensorEndpoint(GeneralFunction contained) {
			super(contained);
		}
	}
}
