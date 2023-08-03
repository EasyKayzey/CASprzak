package core.functions;

import core.functions.unitary.UnitaryFunction;

/**
 * Any {@link UnitaryFunction} {@code f} implementing {@link Integrable} has an elementary solution to {@code ∫ f(x) dx}.
 */
public interface Integrable {

    /**
     * Returns the elementary integral of the function. Ex: {@code sin(2x)} becomes {@code -cos{2x}}, NOT {@code -cos(2x)/2}.
     * @return the elementary integral of the function as specified above
     */
    GeneralFunction getElementaryIntegral();
}
