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

	public GeneralFunction getValueAt(Map<String, Integer> indexValues, Map<String, GeneralFunction> toSubstitute) {
		return new Product(
				first.getValueAt(indexValues, toSubstitute),
				second.getValueAt(indexValues, toSubstitute));
	}

	public void getIndices(Map<String, IndexStructure> indexStructure) {
		first.getIndices(indexStructure);
		second.getIndices(indexStructure);
	}

	public int getDimension() {
		int firstDimension = first.getDimension();
		int secondDimension = second.getDimension();
		if (firstDimension == -1 || secondDimension == -1) {
			return Math.max(firstDimension, secondDimension);
		} else {
			assert firstDimension == secondDimension;
			return firstDimension;
		}
	}

}