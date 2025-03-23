package tensors;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NestedArray<I extends Nested<I, T>, T> implements Nested<I, T> {

	public static int indexOffset = 0;
	public static boolean assertValidity = true;

	private final int rank;
	protected final List<I> elements;
	private final int[] dimensions;


	@SuppressWarnings({"unchecked"})
	public static <I extends Nested<I, T>, T> NestedArray<I, T> nest(Object[] elements) {
		if (elements[0] instanceof Object[])
			return new NestedArray<>(
					Arrays.stream(elements)
							.map(e -> (I) nest((Object[]) e))
							.collect(Collectors.toList())
			);
		else
			return new NestedArray<>(
					Arrays.stream(elements)
							.map(e -> (I) new NestedEndpoint<>((T) e))
							.collect(Collectors.toList())
			);
	}

	@SuppressWarnings("unchecked")
	public static <I extends Nested<I, T>, T> Nested<I, T> createSquare(int rank, int dimension, T fill) {
		if (rank == 0)
			return new NestedEndpoint<>(fill);
		else
			return new NestedArray<>(
					IntStream.range(0, dimension)
							.mapToObj(t -> (I) createSquare(rank - 1, dimension, fill))
							.collect(Collectors.toList())
			);
	}

	protected NestedArray(List<I> elements) {
		this.elements = elements;
		if (assertValidity)
			assertValidity();
		this.rank = calculateRank();
		this.dimensions = calculateDimensions();
	}

	private void assertValidity() {
		try {
			Iterator<I> iter = elements.listIterator();
			I check = iter.next();
			while (iter.hasNext())
				if (!check.deepMatches(iter.next()))
					throw new IllegalArgumentException("Elements of NestedArray do not satisfy deepMatches.");
		} catch (NullPointerException e) {
			throw new IllegalStateException("Exception in assertValidity", e);
		}
	}

	private int calculateRank() {
		return elements.get(0).getRank() + 1;
	}

	public int getRank() {
		return rank;
	}

	public int getSize() {
		return elements.size();
	}


	@Override
	public T getAtIndex(int... index) { // maybe there should be a check both here and below for length
		return elements.get(index[index.length - rank] + indexOffset).getAtIndex(index);
	}


	@Override
	public void setAtIndex(T toSet, int... index) {
		elements.get(index[index.length - rank] + indexOffset).setAtIndex(toSet, index);
	}


	private int[] calculateDimensions() {
		int[] lowerDimensions = elements.get(0).getDimensions();
		int[] dimensions = new int[lowerDimensions.length + 1];
		dimensions[0] = elements.size();
		System.arraycopy(lowerDimensions, 0, dimensions, 1, lowerDimensions.length);
		return dimensions;
	}

	@Override
	public int[] getDimensions() {
		return dimensions;
	}


	public boolean matches(Nested<I, T> other) {
		return elements.size() == other.getElements().size();
	}


	public List<I> getElements() {
		return elements;
	}


	@SuppressWarnings("unchecked")
	public Nested<I, T> modifyWith(UnaryOperator<I> elementModifier,
								   UnaryOperator<T> endpointModifier) {
		return new NestedArray<>(
				elements.stream()
						.map(e -> (I) e.modifyWith(elementModifier, endpointModifier))
						.map(elementModifier)
						.collect(Collectors.toList())
		);
	}


	public String toString() {
		return elements.toString();
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object other) {
		return other instanceof Nested<?, ?> nested && equals((Nested<I, T>) nested);
	}

	public boolean equals(Nested<I, T> other) { // TODO test rigorously with subclasses
		return matches(other) && elements.equals(other.getElements());
	}

	public static class NestedEndpoint<I extends Nested<I, T>, T> implements Nested<I, T> {

		protected T contained;

		public NestedEndpoint(T contained) {
			this.contained = contained;
		}

		@Override
		public int getRank() {
			return 0;
		}

		@Override
		public boolean matches(Nested<I, T> other) {
			return other instanceof NestedEndpoint;
		}

		@Override
		public List<I> getElements() {
			return Collections.emptyList();
		}

		@Override
		public Nested<I, T> modifyWith(UnaryOperator<I> elementModifier, UnaryOperator<T> endpointModifier) {
			return new NestedEndpoint<>(endpointModifier.apply(contained));
		}

		@Override
		public T getAtIndex(int... index) {
			return contained;
		}

		@Override
		public void setAtIndex(T toSet, int... index) {
			contained = toSet;
		}

		@Override
		public int[] getDimensions() {
			return new int[0];
		}

		@Override
		public String toString() {
			return String.valueOf(contained);
		}

		public boolean equals(Object other) {
			return other instanceof NestedEndpoint<?, ?> endpoint && contained.equals(endpoint.contained);
		}

	}
}
