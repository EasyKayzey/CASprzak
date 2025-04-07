package tensors.elementoperations;

import core.functions.GeneralFunction;

import java.util.Map;
import java.util.Set;

public interface ElementAccessor {

	// TODO pull dimension out to here

	GeneralFunction getValueAt(Map<String, Integer> indexValues, Map<String, GeneralFunction> toSubstitute);

	void getIndices(Map<String, IndexStructure> indexStructure);

	int getDimension();

	public static enum IndexStructure {
		UP, DOWN, CONTRACTED, TWODOWN, TWOUP
	}

}
