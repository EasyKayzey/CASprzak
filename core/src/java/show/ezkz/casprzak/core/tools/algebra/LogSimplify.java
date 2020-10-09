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

    public static GeneralFunction logChainRule(Product input) {
        List<GeneralFunction> functionList = new LinkedList<>(List.of(input.getFunctions()));
        List<GeneralFunction> newFunctions = new ArrayList<>();

        boolean combinedAny = false;
        while (functionList.size() > 0) {

            GeneralFunction compare = functionList.remove(0);
            if (compare instanceof LogInterface) {

                Iterator<GeneralFunction> iter = functionList.iterator();

                while (iter.hasNext()) {
                    GeneralFunction current = iter.next();
                    if (current instanceof LogInterface log) {
                        if (((LogInterface) current).base().equalsFunction(((LogInterface) compare).argument())) {
                            GeneralFunction combined = new Logb(((LogInterface) current).argument(), ((LogInterface) compare).base());
                            iter.remove();
                            combinedAny = true;
                            newFunctions.add(combined);
                        } else if (((LogInterface) current).argument().equalsFunction(((LogInterface) compare).base())) {
                            GeneralFunction combined = new Logb(((LogInterface) compare).argument(), ((LogInterface) current).base());
                            iter.remove();
                            combinedAny = true;
                            newFunctions.add(combined);
                        } else {
                            newFunctions.add(new Product(compare, current));
                        }
                    } else {
                        newFunctions.add(new Product(compare, current));
                    }
                }

            } else
                newFunctions.add(compare);
        }

        if (combinedAny)
            return new Product(newFunctions.toArray(new GeneralFunction[0])).simplify();
        else
            return input;
    }



}
