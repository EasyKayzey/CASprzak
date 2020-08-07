package tensors;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.commutative.Sum;

import java.util.Arrays;
import java.util.function.Function;

import static tools.MiscTools.minimalSimplify;

public class TensorOperations {

    static Tensor tensorProduct(Tensor first, Tensor second) { // TODO check order of resulting indices
        return modifyWith(second,
                i -> i,
                i -> i,
                i -> i + first.rank,
                i -> tensorProduct(first, i),
                first::scale,
                false);
    }

    public static Tensor tensorProduct(Tensor... tensors) {
        Tensor tensor = tensors[0];
        for (int i = 1; i < tensors.length; i++)
            tensor = tensorProduct(tensor, tensors[i]);
        return tensor;
    }

    public static Tensor scale(GeneralFunction scalar, Tensor tensor) {
        return modifyWith(tensor,
                i -> i,
                i -> i,
                i -> i,
                i -> i.scale(scalar),
                i -> new Product(i, scalar),
                true);
    }

    static Tensor addTwo(Tensor first, Tensor second) {
        if (first.isContra != second.isContra)
            throw new IllegalArgumentException("Mismatched tensor variance in addition.");
        if (first.rank != second.rank)
            throw new IllegalArgumentException("Mismatched tensor rank in addition.");
        else if (!first.index.equals(second.index))
            throw new IllegalArgumentException("Mismatched tensor index name in addition.");
        else if (first.elements.length != second.elements.length)
            throw new IllegalArgumentException("Mismatched tensor size in addition.");

        if (first.elements[0] instanceof Tensor && second.elements[0] instanceof Tensor) {
            GeneralFunction[] elementSums = new GeneralFunction[first.elements.length];
            for (int i = 0; i < elementSums.length; i++)
                elementSums[i] = addTwo((Tensor) first.elements[i], (Tensor) second.elements[i]);
            return new Tensor(first.index, first.isContra, first.rank, elementSums);
        } else if (!(first.elements[0] instanceof Tensor || second.elements[0] instanceof Tensor)) {
            GeneralFunction[] elementSums = new GeneralFunction[first.elements.length];
            for (int i = 0; i < elementSums.length; i++)
                elementSums[i] = minimalSimplify(new Sum(first.elements[i], second.elements[i]));
            return new Tensor(first.index, first.isContra, first.rank, elementSums);
        } else
            throw new IllegalArgumentException("Mismatched tensor dimensions in addition.");
    }

    public static Tensor sum(Tensor... tensors) {
        Tensor tensor = tensors[0];
        for (int i = 1; i < tensors.length; i++)
            tensor = addTwo(tensor, tensors[i]);
        return tensor;
    }

    static GeneralFunction sumArbitrary(GeneralFunction... elements) {
        if (elements[0] instanceof Tensor) {
            Tensor tensor = (Tensor) elements[0];
            for (int i = 1; i < elements.length; i++)
                tensor = addTwo(tensor, (Tensor) elements[i]);
            return tensor;
        } else
            return new Sum(elements);
    }

    public static Tensor modifyWith(Tensor seed,
                                    Function<String, String> indexModifier,
                                    Function<Boolean, Boolean> contravarianceModifier,
                                    Function<Integer, Integer> rankModifier,
                                    Function<Tensor, GeneralFunction> tensorElementModifier,
                                    Function<GeneralFunction, GeneralFunction> functionElementModifier,
                                    boolean simplifyFunctions) { // TODO move the if statement up a level for efficiency
        if (seed.rank > 1)
            return new Tensor(
                    indexModifier.apply(seed.index),
                    contravarianceModifier.apply(seed.isContra),
                    rankModifier.apply(seed.rank),
                    Arrays.stream(seed.elements)
                            .map(function -> tensorElementModifier.apply((Tensor) function))
                            .toArray(GeneralFunction[]::new)
            );
        else
            return new Tensor(
                    indexModifier.apply(seed.index),
                    contravarianceModifier.apply(seed.isContra),
                    rankModifier.apply(seed.rank),
                    Arrays.stream(seed.elements)
                            .map(function ->
                                    simplifyFunctions
                                    ? minimalSimplify(functionElementModifier.apply(function))
                                    : functionElementModifier.apply(function))
                            .toArray(GeneralFunction[]::new)
            );
    }
}
