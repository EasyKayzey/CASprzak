package show.ezkz.casprzak.core.tools.algebra;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Logb;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.unitary.specialcases.Exp;
import show.ezkz.casprzak.core.functions.unitary.specialcases.Ln;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;
import show.ezkz.casprzak.core.tools.helperclasses.AbstractPair;
import show.ezkz.casprzak.core.tools.helperclasses.LogInterface;
import show.ezkz.casprzak.core.tools.helperclasses.Pair;

import java.util.*;

import static show.ezkz.casprzak.core.tools.defaults.DefaultFunctions.NEGATIVE_ONE;

public class LogSimplify {

    /**
     * Splits a logarithm of a product into a sum of logs. Ex: {@code log(xy) = log(x) +log(y)}
     * @param input The logarithm that is being expanded.
     * @return The split logarithm
     */
    public static GeneralFunction logarithmOfAProduct(LogInterface input) {
        GeneralFunction operand = input.argument();

        if (operand instanceof Product product) {
            GeneralFunction[] terms = Arrays.stream(product.getFunctions())
                    .map(Ln::new)
                    .toArray(GeneralFunction[]::new);
            return new Sum(terms).simplify();
        } else
            return (GeneralFunction) input;
    }

    /**
     * Splits a logarithm of a exponent into a product. Ex: {@code log(x^y) = y*log(x)}
     * @param input The logarithm that is being expanded.
     * @return The expanded expression
     */
    public static GeneralFunction logarithmOfAnExponent(LogInterface input) {
        GeneralFunction operand = input.argument();

        if (operand instanceof Pow exponential)
            return new Product(exponential.getFunction1(), new Ln(exponential.getFunction2())).simplify();
        else if (operand instanceof Exp exponential)
            return exponential.operand.simplify();
        else
            return (GeneralFunction) input;

    }

    /**
     * Performs a change of base operation. Ex: {@code log(x) = logb_3(x)/logb_3(10)}
     * @param input The logarithm whose base is changing
     * @param newBase The new base
     * @return The logarithm with a changed base
     */
    public static GeneralFunction changeOfBase(LogInterface input, GeneralFunction newBase) {
        return DefaultFunctions.frac(new Logb(input.argument(), newBase), new Logb(input.base(), newBase)).simplify();
    }

    /**
     * Performs a change of base to {@code ln}. Ex: {@code log(x) = ln(x)/ln(10)}
     * @param input The logarithm whose base is changing
     * @return The logarithm with a changed base
     */
    public static GeneralFunction changeOfBase(LogInterface input) {
        return DefaultFunctions.frac(new Ln(input.argument()), new Ln(input.base())).simplify();
    }

    /**
     * Performs a "log chain rule" operation and reverses change-of-base. Ex: {@code logb_a(b) * logb_b(c) * logb_x(y) / logb_x(z) = logb_a(c) * logb_z(y)}
     * @param input A product which contains the logarithms that want to be simplified.
     * @return A function with the simplification performed.
     */
    public static GeneralFunction logChainRule(Product input) {
        List<GeneralFunction> functionList = new LinkedList<>(List.of(input.getFunctions()));
        List<GeneralFunction> newFunctions = new ArrayList<>();
        
        ListIterator<GeneralFunction> initialIter = functionList.listIterator();
        while (initialIter.hasNext()) {
            GeneralFunction cur = initialIter.next();
            if (cur instanceof Pow pow && pow.getFunction2() instanceof LogInterface li && pow.getFunction1().equals(NEGATIVE_ONE)) {
                initialIter.remove();
                initialIter.add(new Logb(li.base(), li.argument()));
            } else if (!(cur instanceof LogInterface)) {
                newFunctions.add(cur);
                initialIter.remove();
            }
        }
        
        boolean combinedAny = false;
        while (functionList.size() > 0) {
            GeneralFunction first = functionList.remove(0);
            if (first instanceof LogInterface) {
                LogInterface comparing = (LogInterface) first;
                Iterator<GeneralFunction> iter = functionList.iterator();
                while (iter.hasNext()) {
                    GeneralFunction second = iter.next();
                    if (second instanceof LogInterface current) {
                        if (current.base().equalsFunction(comparing.argument())) {
                            comparing = new Logb(current.argument(), comparing.base());
                            iter.remove();
                            iter = functionList.iterator();
                            combinedAny = true;
                        } else if (current.argument().equalsFunction(comparing.base())) {
                            comparing = new Logb(comparing.argument(), current.base());
                            iter.remove();
                            iter = functionList.iterator();
                            combinedAny = true;
                        }
                    } else
                        throw new IllegalStateException("Non-log detected in log simplification.");
                }
                newFunctions.add((GeneralFunction) comparing);
            } else
                throw new IllegalStateException("Non-log detected in log simplification.");
        }

        if (combinedAny)
            return new Product(newFunctions.toArray(new GeneralFunction[0])).simplify();
        else
            return input;
    }



}
