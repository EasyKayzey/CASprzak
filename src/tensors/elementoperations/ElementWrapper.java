package tensors.elementoperations;

import core.functions.GeneralFunction;
import tensors.DirectedNested;
import tensors.TensorTools;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ElementWrapper implements ElementAccessor {

	private final DirectedNested<?, GeneralFunction> contained;
	private final String[] indices;

	public ElementWrapper(DirectedNested<?, GeneralFunction> contained, String... indices) {
		this.contained = contained;
		this.indices = indices;
	}

	public GeneralFunction getValueAt(Map<String, Integer> indexValues, Map<String, GeneralFunction> toSubstitute) {
		if (!indexValues.keySet().containsAll(List.of(indices)))
			throw new IllegalStateException("Calling getValueAt with incomplete indexValues and/or toSubstitute, has "
					+ indexValues + " but expected values for " + Arrays.toString(indices));
		int[] index = Arrays.stream(indices)
				.mapToInt(indexValues::get)
				.toArray();
		return contained.getAtIndex(index);
	}

	public void getIndices(Map<String, IndexStructure> indexStructure) {
		var directions = contained.getDirections();
		assert directions.length == indices.length;
		for (int i = 0; i < indices.length; i++) {
			TensorTools.addIndexStructure(indices[i], directions[i], indexStructure);
		}
	}

	public int getDimension() {
		int[] dimensions = contained.getDimensions();
		int dimension = dimensions[0];
		for (int i = 1; i < dimensions.length; i++) {
			assert dimensions[i] == dimension : "Tensor is not square: " + Arrays.toString(dimensions);
		}
		return dimension;
	}

}
