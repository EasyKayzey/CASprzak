package tensors;

import java.util.function.UnaryOperator;

import core.functions.GeneralFunction;
import tensors.elementoperations.ElementWrapper;

public interface Tensor extends DirectedNested<Tensor, GeneralFunction> {

	default ElementWrapper index(String... indices) {
		return TensorTools.indexTensor(this, indices);
	}

	default Tensor modifyWithTensor(UnaryOperator<GeneralFunction> endpointModifier) {
		return ArrayTensor.tensor(ArrayTensor.direct(modifyWith(endpointModifier), getDirections()));
	}

}
