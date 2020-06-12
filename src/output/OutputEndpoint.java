package output;

import java.util.Collection;
import java.util.Collections;

public class OutputEndpoint implements OutputFunction {

	protected final String string;

	public OutputEndpoint(String string) {
		this.string = string;
	}

	public String getName() {
		return string;
	}

	public Collection<OutputFunction> getOperands() {
		return Collections.emptyList();
	}

	public String toAscii() {
		return string;
	}

}
