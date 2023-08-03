package core.tools.helperclasses;

import core.functions.GeneralFunction;

public interface LogInterface {
	LogInterface newWith(GeneralFunction argument);
	GeneralFunction argument();
	GeneralFunction base();
}
