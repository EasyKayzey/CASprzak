package tensors.elementoperations;

import core.functions.GeneralFunction;
import tensors.DirectedNested;

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

	public GeneralFunction getValueAt(Map<String, Integer> indexValues, Map<String, GeneralFunction> toSubstitute,
			int dimension) {
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
			if (directions[i]) {
				if (indexStructure.containsKey(indices[i])) {
					if (indexStructure.get(indices[i]) == IndexStructure.DOWN)
						indexStructure.put(indices[i], IndexStructure.CONTRACTED);
					else if (indexStructure.get(indices[i]) == IndexStructure.UP
							|| indexStructure.get(indices[i]) == IndexStructure.CONTRACTED)
						indexStructure.put(indices[i], IndexStructure.TWOUP);
				} else {
					indexStructure.put(indices[i], IndexStructure.UP);
				}
			} else {
				if (indexStructure.containsKey(indices[i])) {
					if (indexStructure.get(indices[i]) == IndexStructure.UP)
						indexStructure.put(indices[i], IndexStructure.CONTRACTED);
					else if (indexStructure.get(indices[i]) == IndexStructure.DOWN
							|| indexStructure.get(indices[i]) == IndexStructure.CONTRACTED)
						indexStructure.put(indices[i], IndexStructure.TWODOWN);
				} else {
					indexStructure.put(indices[i], IndexStructure.DOWN);
				}
			}
		}
	}

}
