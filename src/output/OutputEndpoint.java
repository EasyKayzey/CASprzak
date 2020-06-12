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

	public String toAscii() {
		return contained.toString();
	}

}
