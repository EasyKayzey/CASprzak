package output;

import java.util.Collection;
import java.util.Collections;

public class OutputString implements OutputFunction {

	private final String string;

	public OutputString(String string) {
		this.string = string;
	}

	@Override
	public String getName() {
		return "string";
	}

	@Override
	public Collection<OutputFunction> getOperands() {
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return string;
	}

	@Override
	public String toLatex() {
		return string;
	}
}
