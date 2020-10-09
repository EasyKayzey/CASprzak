package show.ezkz.casprzak.core.tools.helperclasses;

import show.ezkz.casprzak.core.functions.GeneralFunction;

public interface PowInterface {
	PowInterface newWith(GeneralFunction exponent);
	GeneralFunction exponent();
	GeneralFunction base();
}
