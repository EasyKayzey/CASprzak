package output;

import functions.GeneralFunction;

import java.util.Collection;
import java.util.Collections;

public class OutputEndpoint implements OutputFunction {

	protected final GeneralFunction contained;

	public OutputEndpoint(GeneralFunction contained) {
		this.contained = contained;
	}

	public GeneralFunction getContained() {
		return contained;
	}

	public String getName() {
		return contained.toString();
	}

	public Collection<OutputFunction> getOperands() {
		return Collections.emptyList();
	}

	public String toString() {
		return contained.toString();
	}

	public boolean equals(Object that) {
		if (that instanceof OutputEndpoint other)
			return contained.equalsFunction(other.contained);
		else
			return false;
	}

	public int hashCode() {
		return contained.hashCode() + 7 * getClass().hashCode();
	}

}
