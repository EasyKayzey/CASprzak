package tensors.elementoperations;

import core.functions.GeneralFunction;
import core.functions.commutative.Sum;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class ElementSum implements ElementAccessor {

	private final ElementAccessor[] elements;

	public ElementSum(ElementAccessor... elements) {
		this.elements = elements;
	}

	@Override
	public GeneralFunction getValueAt(Map<String, Integer> indexValues, Map<String, GeneralFunction> toSubstitute,
			int dimension) {
		return new Sum(
				Arrays.stream(elements)
						.map(e -> e.getValueAt(indexValues, toSubstitute, dimension))
						.toArray(GeneralFunction[]::new));
	}

	public void getIndices(Set<String> set) {
		for (ElementAccessor e : elements)
			e.getIndices(set);
	}

}
