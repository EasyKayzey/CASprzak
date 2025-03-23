package tensors;

import core.functions.GeneralFunction;
import core.functions.endpoint.Constant;
import core.tools.defaults.DefaultFunctions;
import tensors.elementoperations.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TensorTools {

	public static final GeneralFunctionWrapper WRAPPED_ONE = wrap(DefaultFunctions.ONE);
	public static final GeneralFunctionWrapper WRAPPED_HALF = wrap(DefaultFunctions.HALF);
	public static final GeneralFunctionWrapper WRAPPED_NEGATIVE_ONE = wrap(DefaultFunctions.NEGATIVE_ONE);

	public static DirectedNested<?, GeneralFunction> createFrom(List<String> freeIndices, boolean[] directions,
			int dimension, ElementAccessor formula) {
		Nested<?, GeneralFunction> array = NestedArray.createSquare(freeIndices.size(), dimension, null);
		int[] freeValues = new int[freeIndices.size()];
		Map<String, Integer> indexValues = new HashMap<>();
		Map<String, GeneralFunction> toSubstitute = new HashMap<>();

		do {
			for (int i = 0; i < freeValues.length; i++) {
				indexValues.put(freeIndices.get(i), freeValues[i]);
				toSubstitute.put(freeIndices.get(i), new Constant(freeValues[i])); // TODO make this more efficient by
																					// replacing the loop with stuff in
																					// incrementArray
			}
			array.setAtIndex(formula.getValueAt(indexValues, toSubstitute, dimension).simplify(), freeValues);
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

}
