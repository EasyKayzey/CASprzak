package tensors.elementoperations;

import core.functions.GeneralFunction;
import core.functions.commutative.Sum;
import core.functions.endpoint.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ElementSum implements ElementAccessor {

	private final ElementAccessor[] elements;

	public ElementSum(ElementAccessor... elements) {
		this.elements = elements;
	}

	public GeneralFunction wrapSubContraction(ElementAccessor formula, Map<String, Integer> indexValues,
			Map<String, GeneralFunction> toSubstitute, int dimension) {
		// this is basically all a hack to handle different parts of the sum
		// having different numbers of contracted indices. we just won't report any!
		Map<String, IndexStructure> formulaStructure = new HashMap<>();
		formula.getIndices(formulaStructure);
		Set<String> toContract = new HashSet<>();
		Set<String> uncontracted = new HashSet<>();
		// copied from createFrom
		for (Map.Entry<String, IndexStructure> entry : formulaStructure.entrySet()) {
			String index = entry.getKey();
			IndexStructure structure = entry.getValue();
			if (structure == IndexStructure.CONTRACTED)
				toContract.add(index);
			else if (structure == IndexStructure.TWODOWN)
				throw new IllegalArgumentException("Index " + index + " is repeated down twice.");
			else if (structure == IndexStructure.TWOUP)
				throw new IllegalArgumentException("Index " + index + " is repeated up twice.");
			else
				uncontracted.add(index);
		}

		if (toContract.size() == 0) {
			return formula.getValueAt(indexValues, toSubstitute, dimension);
		}

		List<Map<String, Integer>> contractedIndexValuesList = new ArrayList<>();
		contractedIndexValuesList.add(new HashMap<>());
		for (String index : toContract) {
			List<Map<String, Integer>> newCIVL = new ArrayList<>();
			for (Map<String, Integer> contractedIndexValues : contractedIndexValuesList) {
				for (int i = 0; i < dimension; i++) {
					Map<String, Integer> newContractedIndexValues = new HashMap<>(contractedIndexValues);
					newContractedIndexValues.put(index, i);
					newCIVL.add(newContractedIndexValues);
				}
			}
			contractedIndexValuesList = newCIVL;
		}
		assert contractedIndexValuesList.size() == Math.pow(dimension, toContract.size());
		GeneralFunction[] toAdd = new GeneralFunction[contractedIndexValuesList.size()];
		for (int i = 0; i < contractedIndexValuesList.size(); i++) {
			HashMap<String, Integer> curIndexValues = new HashMap<>(indexValues);
			HashMap<String, GeneralFunction> curToSubstitute = new HashMap<>(toSubstitute);
			curIndexValues.putAll(contractedIndexValuesList.get(i));
			curToSubstitute.putAll(contractedIndexValuesList.get(i).entrySet().stream()
					.collect(Collectors.toMap(Map.Entry::getKey, e -> new Constant(e.getValue()))));

			toAdd[i] = formula.getValueAt(curIndexValues, curToSubstitute, dimension);
		}
		return new Sum(toAdd).simplify();
	}

	@Override
	public GeneralFunction getValueAt(Map<String, Integer> indexValues, Map<String, GeneralFunction> toSubstitute,
			int dimension) {
		// this is basically all a hack to handle different parts of the sum
		// having different numbers of contracted indices. we just won't report any!

		return new Sum(
				Arrays.stream(elements)
						.map(e -> wrapSubContraction(e, indexValues, toSubstitute, dimension))
						.toArray(GeneralFunction[]::new));
	}

	public void getIndices(Map<String, IndexStructure> indexStructure) {
		// this is basically all a hack to handle different parts of the sum
		// having different numbers of contracted indices. we just won't report any!
		List<String> newContractions = new ArrayList<>();
		List<Map<String, IndexStructure>> indexStructures = Arrays.stream(elements)
				.map(e -> {
					var map = new java.util.HashMap<String, IndexStructure>();
					e.getIndices(map);
					// return (Map<String, IndexStructure>) map.entrySet().stream()
					// .filter(en -> en.getValue() != IndexStructure.CONTRACTED)
					// .collect(
					// Collectors.toMap(en -> (String) en.getKey(), en -> (IndexStructure)
					// en.getValue()));
					for (String k : map.keySet()) {
						if (map.get(k) == IndexStructure.CONTRACTED) {
							newContractions.add(k);
						}
					}
					for (String c : newContractions) {
						map.remove(c);
					}

					return (Map<String, IndexStructure>) map;
				})
				.toList();
		for (int i = 1; i < indexStructures.size(); i++) {
			if (!indexStructures.get(i).equals(indexStructures.get(0))) {
				throw new IllegalArgumentException("All summands must have the same indices");
			}
		}
		elements[0].getIndices(indexStructure);
		for (String c : newContractions) {
			indexStructure.remove(c);
		}
	}

}
