package tools;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.special.Variable;

public class TaylorSeries {
    public static Function makeTaylorSeries(Function function, int size) {
        Function[] taylorSeriesTerms = new Function[size];
        for (int i = 0; i < size; i++){
            taylorSeriesTerms[i] = new Multiply(new Constant((function.getNthDerivative(0, i).evaluate(0))/(MiscTools.factorial(i))), new Pow(new Constant(i), new Variable(0)));
        }
        return new Add(taylorSeriesTerms);
    }

    public static Function makeTaylorSeries(Function function, int size, double center) {
        Function[] taylorSeriesTerms = new Function[size];
        for (int i = 0; i < size; i++){
            taylorSeriesTerms[i] = new Multiply(new Constant(function.getNthDerivative(0, i).evaluate(center)/ MiscTools.factorial(i)), new Pow(new Constant(i), new Add(new Variable(0), new Constant(-center))));
        }
        return new Add(taylorSeriesTerms);
    }
}


