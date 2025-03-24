package tensors.elementoperations;

import core.functions.GeneralFunction;
import core.functions.commutative.Product;
import core.functions.commutative.Sum;
import core.functions.endpoint.Constant;

import java.util.*;

public class ElementContractor implements ElementAccessor {

	private final ElementAccessor wrapped;
	private final String contracting;
	private final String sub;

	public ElementContractor(ElementAccessor wrapped, String contracting) {
		this.wrapped = wrapped;
		this.contracting = contracting;
		this.sub = null;
	}

	public ElementContractor(ElementAccessor wrapped, String contracting, String sub) {
		this.wrapped = wrapped;
		this.contracting = contracting;
		this.sub = sub;
	}

	public GeneralFunction getValueAt(Map<String, Integer> indexValues, Map<String, GeneralFunction> toSubstitute,
			int dimension) {
		Map<String, Integer> newIndices = new HashMap<>(indexValues);
		Map<String, GeneralFunction> newSubstitutions = new HashMap<>(toSubstitute);
		GeneralFunction[] toAdd = new GeneralFunction[dimension];

		for (int i = 0; i < dimension; i++) {
			newIndices.put(contracting, i);
			newSubstitutions.put(contracting, new Constant(i));
			newIndices.put(sub, i);
			newSubstitutions.put(sub, new Constant(i));
			System.out.println(i);
			System.out.println(wrapped.getValueAt(newIndices, newSubstitutions, dimension));
			toAdd[i] = wrapped.getValueAt(newIndices, newSubstitutions, dimension);
		}

		return new Sum(toAdd);
	}

	public void getIndices(Set<String> set) {
		wrapped.getIndices(set);
		set.remove(contracting);
	}

}