package CASprzak;

import CASprzak.UnitaryFunctions.*;
import CASprzak.BinaryFunctions.*;
import CASprzak.CommutativeFunctions.*;
import CASprzak.SpecialFunctions.*;

public class FunctionMaker {
	public Function constant(double parseDouble) {
	}

	public Function variable(char charAt) {
	}

	public Function find1(String i, Function c) {
		switch (i) {
			case "sin": return new Sin(c);
		}
	}

	public Function find2(String i, Function a, Function b) {
	}
}
