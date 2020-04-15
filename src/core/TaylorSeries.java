package core;

import functions.Function;

public class TaylorSeries {
    public static Function makeTaylorSeries(Function function, int size) {
        Function[] taylorSeriesTerms = new Function[size];
        for (int i = 0; i<size; i++){
            taylorSeriesTerms[i] = function.getNthDerivative(0, i);
        }
        return function;
    }
}
