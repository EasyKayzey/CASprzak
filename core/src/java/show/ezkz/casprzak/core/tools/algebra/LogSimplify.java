package show.ezkz.casprzak.core.tools.algebra;

import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Logb;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.unitary.specialcases.Exp;
import show.ezkz.casprzak.core.functions.unitary.specialcases.Ln;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;
import show.ezkz.casprzak.core.tools.helperclasses.LogInterface;

import java.util.*;

import static show.ezkz.casprzak.core.tools.defaults.DefaultFunctions.NEGATIVE_ONE;

public class LogSimplify {

    /**
     * Splits a logarithm of a product into a sum of logs. Ex: {@code log(xy) = log(x) +log(y)}
     * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
     * @param input The logarithm that is being expanded.
     * @return The split logarithm
     */
    public static GeneralFunction logarithmOfAProduct(SimplificationSettings settings, LogInterface input) {
        GeneralFunction operand = input.argument();

        if (operand instanceof Product product) {
            GeneralFunction[] terms = Arrays.stream(product.getFunctions())
                    .map(Ln::new)
                    .toArray(GeneralFunction[]::new);
            return new Sum(terms).simplify(settings);
        } else
            return (GeneralFunction) input;
    }

    /**
     * Splits a logarithm of a exponent into a product. Ex: {@code log(x^y) = y*log(x)}
     * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
     * @param input The logarithm that is being expanded.
     * @return The expanded expression
     */
    public static GeneralFunction logarithmOfAnExponent(SimplificationSettings settings, LogInterface input) {
        GeneralFunction operand = input.argument();

        if (operand instanceof Pow exponential)
            return new Product(exponential.getFunction1(), new Ln(exponential.getFunction2())).simplify(settings);
        else if (operand instanceof Exp exponential)
            return exponential.operand.simplify(settings);
        else
            return (GeneralFunction) input;

    }

    /**
     * Performs a change of base operation. Ex: {@code log(x) = logb_3(x)/logb_3(10)}
     * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
     * @param input The logarithm whose base is changing
     * @param newBase The new base
     * @return The logarithm with a changed base
     */
    public static GeneralFunction changeOfBase(SimplificationSettings settings, LogInterface input, GeneralFunction newBase) {
        return DefaultFunctions.frac(new Logb(input.argument(), newBase), new Logb(input.base(), newBase)).simplify(settings);
    }

    /**
     * Performs a change of base to {@code ln}. Ex: {@code log(x) = ln(x)/ln(10)}
     * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
     * @param input The logarithm whose base is changing
     * @return The logarithm with a changed base
     */
    public static GeneralFunction changeOfBase(SimplificationSettings settings, LogInterface input) {
        return DefaultFunctions.frac(new Ln(input.argument()), new Ln(input.base())).simplify(settings);
    }

    /**
     * Performs a "log chain rule" operation and reverses change-of-base. Ex: {@code logb_a(b) * logb_b(c) * logb_x(y) / logb_x(z) = logb_a(c) * logb_z(y)}
     * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
     * @param input A {@link Product} which contains the logarithms that want to be simplified.
     * @return A function with the simplification performed.
     */
    public static GeneralFunction logChainRule(SimplificationSettings settings, Product input) {
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
            return new Product(newFunctions.toArray(new GeneralFunction[0])).simplify(settings);
        else
            return input;
    }

    /**
     * Performs sum of logs simplification. Ex: {@code log(a) + log(b) = log(ab)}
     * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
     * @param input A {@link Sum} which contains the logarithms that want to be combined.
     * @return A function with the simplification performed.
     */
    public static GeneralFunction sumOfLogs(SimplificationSettings settings, Sum input) {
        List<GeneralFunction> functionList = new LinkedList<>(List.of(input.getFunctions()));
        List<GeneralFunction> newFunctions = new ArrayList<>();

        ListIterator<GeneralFunction> initialIter = functionList.listIterator();
        while (initialIter.hasNext()) {
            GeneralFunction cur = initialIter.next();
            if (!(cur instanceof LogInterface)) {
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
                        if (current.base().equalsFunction(comparing.base())) {
                            comparing = comparing.newWith(new Product(comparing.argument(), current.argument()).simplify(settings));
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
            return new Sum(newFunctions.toArray(new GeneralFunction[0])).simplify(settings);
        else
            return input;
    }

}
