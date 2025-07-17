package tensors;

import core.functions.commutative.Product;
import core.functions.endpoint.Variable;
import core.functions.unitary.trig.normal.Sin;

import static core.tools.defaults.DefaultFunctions.*;

@SuppressWarnings("NonAsciiCharacters")
public class DefaultSpaces {

        public static final Variable G = new Variable("G");
        public static final Variable M = new Variable("M");
        public static final Variable t = new Variable("t");
        public static final Variable x = new Variable("x");
        public static final Variable y = new Variable("y");
        public static final Variable z = new Variable("z");
        public static final Variable r = new Variable("r");
        public static final Variable r_s = new Variable("\\r_s");
        public static final Variable θ = new Variable("θ");
        public static final Variable φ = new Variable("φ");
        public static final Variable ψ = new Variable("ψ");

        public static final Space cartesian2d = Space.fromDiagonalMetric(new String[] { "x", "y" },
                        ONE,
                        ONE);

        public static final Space cartesian3d = Space.fromDiagonalMetric(new String[] { "x", "y", "z" },
                        ONE,
                        ONE,
                        ONE);

        public static final Space polar = Space.fromDiagonalMetric(new String[] { "r", "θ" },
                        ONE,
                        square(r));

        public static final Space cylindrical = Space.fromDiagonalMetric(new String[] { "r", "θ", "z" },
                        ONE,
                        square(r),
                        ONE);

        public static final Space spherical = Space.fromDiagonalMetric(new String[] { "r", "θ", "φ" },
                        ONE,
                        square(r),
                        square(new Product(r, new Sin(θ))));

        public static final Space minkowski1d = Space.fromDiagonalMetric(new String[] { "t", "x" },
                        NEGATIVE_ONE,
                        ONE);

        public static final Space minkowski2d = Space.fromDiagonalMetric(new String[] { "t", "x", "y" },
                        NEGATIVE_ONE,
                        ONE,
                        ONE);

        public static final Space minkowski = Space.fromDiagonalMetric(new String[] { "t", "x", "y", "z" },
                        NEGATIVE_ONE,
                        ONE,
                        ONE,
                        ONE);

        public static final Space s2 = Space.fromDiagonalMetric(new String[] { "θ", "φ" },
                        ONE,
                        square(new Sin(θ)));

        public static final Space s3 = Space.fromDiagonalMetric(new String[] { "ψ", "θ", "φ" },
                        ONE,
                        square(new Sin(ψ)),
                        square(new Product(new Sin(ψ), new Sin(θ))));

        public static final Space schwarzschild = Space.fromDiagonalMetric(new String[] { "t", "r", "θ", "φ" },
                        negative(subtract(ONE, new Product(r_s, reciprocal(r)))),
                        reciprocal(subtract(ONE, new Product(r_s, reciprocal(r)))),
                        square(r),
                        square(new Product(r, new Sin(θ))));

        private DefaultSpaces() {
        }

        public static void initialize() {
                // Method does nothing, and serves to force the JVM to initialize the class
        }

}
