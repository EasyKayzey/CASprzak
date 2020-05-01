package tools.integral;

import functions.Function;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.commutative.CommutativeFunction;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import functions.special.Variable;
import functions.unitary.Abs;
import functions.unitary.Ln;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.*;
import tools.DefaultFunctions;
import tools.SearchTools;
import tools.helperclasses.Pair;

@SuppressWarnings("ChainOfInstanceofChecks")
public class StageOne {
    public static Function derivativeDivides(Function integrand, char variableChar) {
        Pair<Double, Function> stripConstant = IntegralsTools.stripConstants(integrand);
        Function function = stripConstant.second;
        double number = stripConstant.first;

        if (function instanceof Product product){
            Function[] productTerms = product.getFunctions();
            for (Function f : productTerms) {
                if (f instanceof Pow power && power.getFunction2() instanceof Constant base) {
                    Function derivativeWithConstants = power.getFunction1().getSimplifiedDerivative(variableChar);
                    Pair<Double, Function> derivative = IntegralsTools.stripConstants(derivativeWithConstants);
                    Function derivativeWithoutConstant = derivative.second;
                    double constantInfront = derivative.first;
                    Product derivativeTimesOperation = new Product(derivativeWithoutConstant, f);
                    if (SearchTools.existsSurface(product, (u -> u.equals(derivativeWithoutConstant))) && !SearchTools.existsInOppositeSurfaceSubset((CommutativeFunction) function, (u -> (u instanceof Variable v) && (v.varID == variableChar)), (u -> u.equals(derivativeTimesOperation)))) {
                        number /= constantInfront;
                        return exponential(number, base.constant, power.getFunction1());
                    }
                }
            }
        } else {
            if (function instanceof Pow power && power.getFunction2() instanceof Constant constant1 && power.getFunction1().getSimplifiedDerivative(variableChar) instanceof Constant constant2) {
                number /= constant2.constant;
                return exponential(number, constant1.constant, power.getFunction1());
            } else if (function instanceof Pow power && power.getFunction1() instanceof Constant constant1 && power.getFunction2().getSimplifiedDerivative(variableChar) instanceof Constant constant2) {
                number /= constant2.constant;
                return power(number, constant1.constant, power.getFunction2());
            } else if (function instanceof Ln log && log.operand.getSimplifiedDerivative(variableChar) instanceof Constant constant1) {
                number /= constant1.constant;
                return naturalLog(number, log.operand);
            } else if (function instanceof Logb logb && logb.getFunction2() instanceof Constant constant1 && logb.getFunction1().getSimplifiedDerivative(variableChar) instanceof Constant constant2) {
                number /= (constant2.constant * Math.log(constant1.constant));
                return naturalLog(number, logb.getFunction1());
            } else if (function instanceof UnitaryFunction unit && unit.operand.getSimplifiedDerivative(variableChar) instanceof Constant constant1) {
                if (function instanceof Sin sin) {
                    number /= constant1.constant;
                    return sin(number, sin.operand);
                } else if (function instanceof Cos cos) {
                    number /= constant1.constant;
                    return cos(number, cos.operand);
                } else if (function instanceof Tan tan) {
                    number /= constant1.constant;
                    return tan(number, tan.operand);
                } else if (function instanceof Csc csc) {
                    number /= constant1.constant;
                    return csc(number, csc.operand);
                } else if (function instanceof Sec sec) {
                    number /= constant1.constant;
                    return sec(number, sec.operand);
                } else if (function instanceof Cot cot) {
                    number /= constant1.constant;
                    return cot(number, cot.operand);
                } else if (function instanceof Sinh sinh) {
                    number /= constant1.constant;
                    return sinh(number, sinh.operand);
                } else if (function instanceof Cosh cosh) {
                    number /= constant1.constant;
                    return cosh(number, cosh.operand);
                } else if (function instanceof Tanh tanh) {
                    number /= constant1.constant;
                    return tanh(number, tanh.operand);
                } else if (function instanceof Csch csch) {
                    number /= constant1.constant;
                    return csch(number, csch.operand);
                } else if (function instanceof Sech sech) {
                    number /= constant1.constant;
                    return sech(number, sech.operand);
                } else if (function instanceof Coth coth) {
                    number /= constant1.constant;
                    return coth(number, coth.operand);
                } else if (function instanceof Asin asin) {
                    number /= constant1.constant;
                    return asin(number, asin.operand);
                } else if (function instanceof Acos acos) {
                    number /= constant1.constant;
                    return acos(number, acos.operand);
                } else if (function instanceof Atan atan) {
                    number /= constant1.constant;
                    return atan(number, atan.operand);
                } else if (function instanceof Acsc acsc) {
                    number /= constant1.constant;
                    return acsc(number, acsc.operand);
                } else if (function instanceof Asec asec) {
                    number /= constant1.constant;
                    return asec(number, asec.operand);
                } else if (function instanceof Acot acot) {
                    number /= constant1.constant;
                    return acot(number, acot.operand);
                } else if (function instanceof Asinh asinh) {
                    number /= constant1.constant;
                    return asinh(number, asinh.operand);
                } else if (function instanceof Acosh acosh) {
                    number /= constant1.constant;
                    return acosh(number, acosh.operand);
                } else if (function instanceof Atanh atanh) {
                    number /= constant1.constant;
                    return atanh(number, atanh.operand);
                } else if (function instanceof Acsch acsch) {
                    number /= constant1.constant;
                    return acsch(number, acsch.operand);
                } else if (function instanceof Asech asech) {
                    number /= constant1.constant;
                    return asech(number, asech.operand);
                } else if (function instanceof Acoth acoth) {
                    number /= constant1.constant;
                    return acoth(number, acoth.operand);
                }
             }
        }

        return function;
        // Let's say I want to check if all xs are in the form e^x
//        SearchTools.exists(integrand, (f -> f.equals(toFind))) && !SearchTools.existsExcluding(integrand, (f -> f instanceof Variable), (f -> f.equals(toFind)))
    }

    private static Function exponential(double number, double base, Function exponent) {
        return new Product(new Constant(1/Math.log(base)), new Pow(exponent, new Constant(base)));
    }

    private static Function power(double number, double exponent, Function base) {
        if (exponent == -1)
            return new Product(new Constant(number), new Ln(base));
        else
            return new Product(new Constant(number/(exponent+1)), new Pow(new Constant(exponent+1), base));
    }

    private static Function naturalLog(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Ln(operand)), new Product(new Constant(-1), operand)));
    }

    private static Function sin(double number, Function operand) {
        return new Product(new Constant(-1*number), new Cos(operand));
    }

    private static Function cos(double number, Function operand) {
        return new Product(new Constant(number), new Sin(operand));
    }

    private static Function tan(double number, Function operand) {
        return new Product(new Constant(number), new Ln(new Abs(new Sec(operand))));
    }

    private static Function csc(double number, Function operand) {
        return new Product(new Constant(-1 * number), new Ln(new Abs(new Sum(new Csc(operand), new Cot(operand)))));
    }

    private static Function cot(double number, Function operand) {
        return new Product(new Constant(-1 * number), new Ln(new Abs(new Csc(operand))));
    }

    private static Function sec(double number, Function operand) {
        return new Product(new Constant(number), new Ln(new Abs(new Sum(new Sec(operand), new Tan(operand)))));
    }

    private static Function sinh(double number, Function operand) {
        return new Product(new Constant(number), new Cosh(operand));
    }

    private static Function cosh(double number, Function operand) {
        return new Product(new Constant(number), new Sinh(operand));
    }

    private static Function tanh(double number, Function operand) {
        return new Product(new Constant(number), new Ln(new Cosh(operand)));
    }

    private static Function csch(double number, Function operand) {
        return new Product(new Constant(number), new Ln(new Abs(new Tanh(new Product(DefaultFunctions.HALF, operand)))));
    }

    private static Function coth(double number, Function operand) {
        return new Product(new Constant(number), new Ln(new Abs(new Sinh(operand))));
    }

    private static Function sech(double number, Function operand) {
        return new Product(new Constant(number), new Atan(new Abs(new Sinh(operand))));
    }

    private static Function asin(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Asin(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, sin(1, new Asin(operand)))));
    }

    private static Function acos(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Acos(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, cos(1, new Acos(operand)))));
    }

    private static Function atan(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Atan(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, tan(1, new Atan(operand)))));
    }

    private static Function acsc(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Acsc(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, csc(1, new Acsc(operand)))));
    }

    private static Function acot(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Acot(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, cot(1, new Acot(operand)))));
    }

    private static Function asec(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Asec(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, sec(1, new Asec(operand)))));
    }

    private static Function asinh(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Asinh(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, sinh(1, new Asinh(operand)))));
    }

    private static Function acosh(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Acosh(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, cosh(1, new Acosh(operand)))));
    }

    private static Function atanh(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Atanh(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, tanh(1, new Atanh(operand)))));
    }

    private static Function acsch(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Acsch(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, csch(1, new Acsch(operand)))));
    }

    private static Function acoth(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Acoth(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, coth(1, new Acoth(operand)))));
    }

    private static Function asech(double number, Function operand) {
        return new Product(new Constant(number), new Sum(new Product(operand, new Asech(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, sech(1, new Asech(operand)))));
    }
}
