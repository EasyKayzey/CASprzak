package tensors;

import core.functions.GeneralFunction;
import core.functions.commutative.Sum;
import core.functions.endpoint.Constant;
import core.functions.unitary.transforms.PartialDerivative;
import core.tools.defaults.DefaultFunctions;
import tensors.elementoperations.ElementAccessor;
import tensors.elementoperations.ElementProduct;
import tensors.elementoperations.ElementSum;
import tensors.elementoperations.ElementWrapper;

import java.util.*;

import static tensors.TensorTools.*;
import static core.tools.defaults.DefaultFunctions.*;

public class Space {

	protected final String[] variableStrings;
	public final int dimension;
	public final Tensor metric;
	public final Tensor inverseMetric;
	public final DirectedNested<?, GeneralFunction> christoffel;

	public Space(String[] variableStrings, Tensor metric, Tensor inverseMetric) {
		this.variableStrings = variableStrings;
		this.dimension = variableStrings.length;
		this.metric = metric;
		this.inverseMetric = inverseMetric;
		christoffel = calculateChristoffel();
	}

	public static Space fromDiagonalMetric(String[] variableStrings, GeneralFunction... diagonal) {
		if (variableStrings.length != diagonal.length)
			throw new IllegalArgumentException("variableStrings length " + variableStrings.length
					+ " and diagonal length " + diagonal.length + " do not match.");
		GeneralFunction[][] metric = new GeneralFunction[diagonal.length][diagonal.length];
		for (GeneralFunction[] row : metric)
			Arrays.fill(row, ZERO);
		GeneralFunction[][] inverseMetric = new GeneralFunction[diagonal.length][diagonal.length];
		for (GeneralFunction[] row : inverseMetric)
			Arrays.fill(row, ZERO);

		for (int i = 0; i < diagonal.length; i++) {
			metric[i][i] = diagonal[i].simplify();
			inverseMetric[i][i] = DefaultFunctions.reciprocal(diagonal[i]).simplify();
		}

		return new Space(
				variableStrings,
				ArrayTensor.tensor(metric, false, false),
				ArrayTensor.tensor(inverseMetric, true, true));
	}

	public Partial partial(String index, ElementAccessor operand) {
		return new Partial(index, operand);
	}

	private DirectedNested<?, GeneralFunction> calculateChristoffel() {
		return createFrom(
				List.of("\\mu", "\\sigma", "\\nu"),
				new boolean[] { false, true, false },
				dimension,
				product(
						wrap(HALF),
						inverseMetric.index("\\sigma", "\\rho"),
						sum(
								partial("\\mu", metric.index("\\nu", "\\rho")),
								partial("\\nu", metric.index("\\rho", "\\mu")),
								negative(partial("\\rho", metric.index("\\mu", "\\nu"))))));
	}

	public Tensor covariantDerivative(String respectTo, Tensor tensor, String... tensorIndices) {
		// TODO assert tensorIndices length matches rank
		// TODO assert alpha is free and can be dummy
		int oldRank = tensor.getRank();
		boolean[] oldDirections = tensor.getDirections();
		ElementAccessor[] toAdd = new ElementAccessor[oldRank + 1];
		int dimension = TensorTools.getDimension(tensor.getDimensions());

		String[] currentTensorIndices = tensorIndices.clone();
		String current;
		String dummy = "\\dmy" + (hashCode() ^ tensor.hashCode() ^ respectTo.hashCode());
		for (int i = 0; i < oldRank; i++) {
			current = tensorIndices[i];
			currentTensorIndices[i] = dummy;
			if (oldDirections[i])
				toAdd[i] = new ElementProduct(new ElementWrapper(tensor, currentTensorIndices.clone()),
						new ElementWrapper(christoffel, dummy, current, respectTo));
			else
				toAdd[i] = negative(new ElementProduct(new ElementWrapper(tensor, currentTensorIndices.clone()),
						new ElementWrapper(christoffel, current, dummy, respectTo)));
			currentTensorIndices[i] = current;
		}
		toAdd[oldRank] = partial(respectTo, new ElementWrapper(tensor, tensorIndices));

		boolean[] newDirections = new boolean[oldRank + 1];
		newDirections[0] = false;
		System.arraycopy(oldDirections, 0, newDirections, 1, oldRank);

		List<String> newIndices = new ArrayList<>(List.of(tensorIndices));
		newIndices.add(0, respectTo);

		return ArrayTensor.tensor(
				createFrom(
						newIndices,
						newDirections,
						dimension,
						new ElementSum(toAdd)));
	}

	public class Partial implements ElementAccessor {

		public final ElementAccessor operand;
		public final String index;

		public Partial(String index, ElementAccessor operand) {
			this.operand = operand;
			this.index = index;
		}

		@Override
		public GeneralFunction getValueAt(Map<String, Integer> indexValues, Map<String, GeneralFunction> toSubstitute,
				int dimension) {
			Set<String> entries = indexValues.keySet();
			Set<String> operandSet = new HashSet<>();
			operand.getIndices(operandSet);

			if (!entries.contains(index) && operandSet.contains(index)) {
				Map<String, Integer> newIndices = new HashMap<>(indexValues);
				Map<String, GeneralFunction> newSubstitutions = new HashMap<>(toSubstitute);
				GeneralFunction[] toAdd = new PartialDerivative[dimension];

				for (int i = 0; i < dimension; i++) {
					newIndices.put(index, i);
					newSubstitutions.put(index, new Constant(i));
					toAdd[i] = new PartialDerivative(operand.getValueAt(newIndices, newSubstitutions, dimension),
							variableStrings[i]);
				}

				return new Sum(toAdd);
			}

			return new PartialDerivative(operand.getValueAt(indexValues, toSubstitute, dimension),
					variableStrings[indexValues.get(index)]);
		}

		@Override
		public void getIndices(Set<String> set) {
			set.add(index);
			operand.getIndices(set);
		}

	}

}
