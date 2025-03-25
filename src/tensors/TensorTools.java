package tensors;

import core.functions.GeneralFunction;
import core.functions.commutative.Sum;
import core.functions.endpoint.Constant;
import core.tools.defaults.DefaultFunctions;
import tensors.elementoperations.*;
import tensors.elementoperations.ElementAccessor.IndexStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TensorTools {

	public static final GeneralFunctionWrapper WRAPPED_ONE = wrap(DefaultFunctions.ONE);
	public static final GeneralFunctionWrapper WRAPPED_HALF = wrap(DefaultFunctions.HALF);
	public static final GeneralFunctionWrapper WRAPPED_NEGATIVE_ONE = wrap(DefaultFunctions.NEGATIVE_ONE);

	public static DirectedNested<?, GeneralFunction> createFrom(List<String> freeIndices, boolean[] directions,
			int dimension, ElementAccessor formula) {
		Nested<?, GeneralFunction> array = NestedArray.createSquare(freeIndices.size(), dimension, null);
		Map<String, Integer> indexValues = new HashMap<>();
		Map<String, GeneralFunction> toSubstitute = new HashMap<>();

		Map<String, IndexStructure> formulaStructure = new HashMap<>();
		formula.getIndices(formulaStructure);
		Set<String> toContract = new HashSet<>();
		Set<String> uncontracted = new HashSet<>();
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
		if (!Set.copyOf(freeIndices).equals(uncontracted))
			throw new IllegalArgumentException("The free indices " + freeIndices + " do not match the uncontracted "
					+ uncontracted);

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

		int[] freeValues = new int[freeIndices.size()];
		do {
			for (int i = 0; i < freeValues.length; i++) {
				indexValues.put(freeIndices.get(i), freeValues[i]);
				toSubstitute.put(freeIndices.get(i), new Constant(freeValues[i])); // TODO make this more efficient by
																					// replacing the loop with stuff in
																					// incrementArray
			}
			GeneralFunction[] toAdd = new GeneralFunction[contractedIndexValuesList.size()];
			for (int i = 0; i < contractedIndexValuesList.size(); i++) {
				HashMap<String, Integer> curIndexValues = new HashMap<>(indexValues);
				HashMap<String, GeneralFunction> curToSubstitute = new HashMap<>(toSubstitute);
				curIndexValues.putAll(contractedIndexValuesList.get(i));
				curToSubstitute.putAll(contractedIndexValuesList.get(i).entrySet().stream()
						.collect(Collectors.toMap(Map.Entry::getKey, e -> new Constant(e.getValue()))));

				toAdd[i] = formula.getValueAt(curIndexValues, curToSubstitute, dimension);
			}
			array.setAtIndex(new Sum(toAdd).simplify(), freeValues);
		} while (directions.length != 0 && incrementArray(freeValues, dimension, 0));

		return DirectedNestedArray.direct(array, directions);
	}

	private static boolean incrementArray(int[] array, int max, int start) {
		array[start]++;
		if (array[start] < max)
			return true;
		array[start] = 0;
		if (start + 1 < array.length)
			return incrementArray(array, max, start + 1);
		return false;
	}

	public static ElementWrapper indexTensor(DirectedNested<?, GeneralFunction> toAccess, String... indices) {
		return new ElementWrapper(toAccess, indices);
	}

	public static GeneralFunctionWrapper wrap(GeneralFunction toWrap) {
		return new GeneralFunctionWrapper(toWrap);
	}

	public static ElementAccessor sum(ElementAccessor... elements) {
		return new ElementSum(elements);
	}

	public static ElementAccessor product(ElementAccessor... elements) {
		if (elements.length == 0)
			return WRAPPED_ONE;
		if (elements.length == 1)
			return elements[0];

		ElementProduct current = new ElementProduct(elements[0], elements[1]);
		for (int i = 2; i < elements.length; i++)
			current = new ElementProduct(current, elements[i]);

		return current;
	}

	public static ElementAccessor negative(ElementAccessor elementAccessor) {
		return new ElementProduct(WRAPPED_NEGATIVE_ONE, elementAccessor);
	}

	public static boolean isSquare(int[] dimensions) {
		int dimension = dimensions[0];
		for (int i = 1; i < dimensions.length; i++)
			if (dimension != dimensions[i])
				return false;
		return true;
	}

	public static int getDimension(int[] dimensions) {
		if (isSquare(dimensions))
			return dimensions[0];
		else
			throw new IllegalArgumentException("Cannot get the dimension of a non-square array.");
	}

	public static Tensor identityTensor(int dimension) {
		GeneralFunction[][] delta = new GeneralFunction[dimension][dimension];
		for (GeneralFunction[] row : delta)
			Arrays.fill(row, DefaultFunctions.ZERO);

		for (int i = 0; i < dimension; i++) {
			delta[i][i] = DefaultFunctions.ONE;
		}
		return ArrayTensor.tensor(delta, true, false);
	}

	public static String prettyString(DirectedNested<?, GeneralFunction> tensor, Space space,
			String[] indexLabels) {
		String[] coords = space.variableStrings;
		int dims = coords.length;
		boolean[] directions = tensor.getDirections();
		int depth = directions.length;
		if (indexLabels.length != depth) {
			throw new IllegalArgumentException("indexLabels length " + indexLabels.length + " and depth " + depth
					+ " do not match.");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Directions are ");
		for (int i = 0; i < depth; i++) {
			sb.append(directions[i] ? "upper" : "lower");
			if (i != depth - 1)
				sb.append(", ");
		}
		sb.append("\n");
		prettyStringHelper(sb, tensor.getDimensions(), depth, new int[depth], 0, coords, directions, indexLabels,
				tensor);
		return sb.toString();
	}

	public static void prettyStringHelper(StringBuilder sb, int[] dimensions, int depth, int[] currentIndex,
			int currentDepth, String[] coords, boolean[] directions, String[] indexLabels,
			DirectedNested<?, GeneralFunction> tensor) {
		if (currentDepth == depth) {
			GeneralFunction cur = tensor.getAtIndex(currentIndex);
			if (cur.equals(DefaultFunctions.ZERO))
				return;
			for (int i = 0; i < currentIndex.length; i++) {
				sb.append(indexLabels[i]);
				sb.append("=");
				sb.append(coords[currentIndex[i]]);
				if (i != currentIndex.length - 1)
					sb.append(", ");
			}
			sb.append(": ");
			sb.append(cur.toString());
			sb.append("\n");
			return;
		}
		for (int i = 0; i < dimensions[currentDepth]; i++) {
			currentIndex[currentDepth] = i;
			prettyStringHelper(sb, dimensions, depth, currentIndex, currentDepth + 1, coords, directions, indexLabels,
					tensor);
		}
	}

	public static void addIndexStructure(String index, boolean direction, Map<String, IndexStructure> indexStructure) {
		// direction=false is down
		if (direction) {
			if (indexStructure.containsKey(index)) {
				if (indexStructure.get(index) == IndexStructure.DOWN)
					indexStructure.put(index, IndexStructure.CONTRACTED);
				else if (indexStructure.get(index) == IndexStructure.UP
						|| indexStructure.get(index) == IndexStructure.CONTRACTED)
					indexStructure.put(index, IndexStructure.TWOUP);
			} else {
				indexStructure.put(index, IndexStructure.UP);
			}
		} else {
			if (indexStructure.containsKey(index)) {
				if (indexStructure.get(index) == IndexStructure.UP)
					indexStructure.put(index, IndexStructure.CONTRACTED);
				else if (indexStructure.get(index) == IndexStructure.DOWN
						|| indexStructure.get(index) == IndexStructure.CONTRACTED)
					indexStructure.put(index, IndexStructure.TWODOWN);
			} else {
				indexStructure.put(index, IndexStructure.DOWN);
			}
		}
	}

}
