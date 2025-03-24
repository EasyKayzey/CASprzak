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
		return new Product(
				first.getValueAt(indexValues, toSubstitute, dimension),
				second.getValueAt(indexValues, toSubstitute, dimension));
	}

	public void getIndices(Map<String, IndexStructure> indexStructure) {
		first.getIndices(indexStructure);
		second.getIndices(indexStructure);
	}

}