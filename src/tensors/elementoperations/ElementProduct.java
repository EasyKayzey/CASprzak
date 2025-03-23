package tensors.elementoperations;

import core.functions.GeneralFunction;
import core.functions.commutative.Product;
import core.functions.commutative.Sum;
import core.functions.endpoint.Constant;

import java.util.*;

public class ElementProduct implements ElementAccessor {

	private final ElementAccessor first;
	private final ElementAccessor second;

	public ElementProduct(ElementAccessor first, ElementAccessor second) {
		this.first = first;
		this.second = second;
	}

	public GeneralFunction getValueAt(Map<String, Integer> indexValues, Map<String, GeneralFunction> toSubstitute,
			int dimension) {
		Set<String> entries = indexValues.keySet();
		Set<String> firstSet = new HashSet<>();
		first.getIndices(firstSet);
		Set<String> secondSet = new HashSet<>();
		second.getIndices(secondSet);

		for (String index : firstSet) {
			if (!entries.contains(index) && secondSet.contains(index)) {
				Map<String, Integer> newIndices = new HashMap<>(indexValues);
				Map<String, GeneralFunction> newSubstitutions = new HashMap<>(toSubstitute);
				GeneralFunction[] toAdd = new Product[dimension];

				for (int i = 0; i < dimension; i++) {
					newIndices.put(index, i);
					newSubstitutions.put(index, new Constant(i));
					toAdd[i] = new Product(
							first.getValueAt(newIndices, newSubstitutions, dimension),
							second.getValueAt(newIndices, newSubstitutions, dimension));
				}

				return new Sum(toAdd);
			}
		}

		return new Product(
				first.getValueAt(indexValues, toSubstitute, dimension),
				second.getValueAt(indexValues, toSubstitute, dimension));
	}

	public void getIndices(Set<String> set) {
		first.getIndices(set);
		second.getIndices(set);
	}

}