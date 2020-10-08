package show.ezkz.casprzak.core.tools.helperclasses;

import show.ezkz.casprzak.core.functions.GeneralFunction;

public interface LogInterface {
	LogInterface newWith(GeneralFunction argument);
	GeneralFunction argument();
	GeneralFunction base();
}
