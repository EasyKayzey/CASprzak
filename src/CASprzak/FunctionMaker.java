package CASprzak;

import CASprzak.UnitaryFunctions.*;
import CASprzak.BinaryFunctions.*;
import CASprzak.CommutativeFunctions.*;
import CASprzak.SpecialFunctions.*;

public class FunctionMaker {
	public Function constant(double parseDouble) {
		return new Constant(0);
	}

	public Function variable(char charAt) {
		return new Constant(0);
	}

	public Function find1(String i, Function c) {
		switch (i) {
			case "sin": return new Sin(c);
		}
		return new Constant(0);
	}

	public Function find2(String i, Function a, Function b) {
		return new Constant(0);
	}
}
