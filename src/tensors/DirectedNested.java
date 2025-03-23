package tensors;

public interface DirectedNested<I extends DirectedNested<I, T>, T> extends Nested<I, T> {

	boolean matches(DirectedNested<I, T> other); // TODO test if deepMatches accounts for direction

	boolean getDirection();

	boolean[] getDirections();

}
