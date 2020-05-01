import functions.unitary.UnitaryFunction;
import functions.unitary.trig.Cos;
import tools.DefaultFunctions;

public class Playground {
	public static void main(String[] args) {
		System.out.println(UnitaryFunction.newInstanceOf(new Cos(null).getInverse(), DefaultFunctions.ONE));
	}
}
