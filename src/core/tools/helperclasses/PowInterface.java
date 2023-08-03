package core.tools.helperclasses;

import core.functions.GeneralFunction;

public interface PowInterface {
	PowInterface newWith(GeneralFunction exponent);
	GeneralFunction exponent();
	GeneralFunction base();
}
