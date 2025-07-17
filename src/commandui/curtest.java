package commandui;

import core.functions.GeneralFunction;
import core.functions.endpoint.Placeholder;
import core.functions.unitary.specialcases.Exp;
import static core.tools.defaults.DefaultFunctions.*;
import tensors.*;
import static tensors.TensorTools.prettyString;
import static tensors.TensorTools.product;
import static tensors.TensorTools.wrap;

public class curtest {
    public static void main(String[] args) {
        System.out.println("hi");
        String[] genericVariables = new String[10];
        for (int i = 0; i < 3; ++i) {
            genericVariables[i] = ((char) ('x' + i)) + "";
        }
        for (int i = 3; i < genericVariables.length; ++i) {
            genericVariables[i] = ((char) ('z' - i)) + "";
        }

        {
            String[] vars = new String[2];
            for (int i = 0; i < vars.length; ++i) {
                vars[i] = genericVariables[i];
            }
            // var genericMetrics = TensorTools.placeholderMetrics(vars);
            var genericMetrics = TensorTools.placeholderDiagonalMetrics(vars);
            Tensor genericMetric = genericMetrics.getFirst();
            Tensor genericInverseMetric = genericMetrics.getSecond();
            // GeneralFunction conformalScale = new Placeholder("M", vars);
            GeneralFunction conformalScale = new Exp(new Placeholder("w", vars));
            Tensor conformalMetric = TensorTools
                    .magicTensor(product(wrap(conformalScale), genericMetric.index("a", "b")));
            Tensor conformalInverseMetric = TensorTools
                    .magicTensor(product(wrap(reciprocal(conformalScale)), genericInverseMetric.index("a", "b")));
            Space conformalSpace = new Space(vars, conformalMetric, conformalInverseMetric);
            System.out.println("Conformal Space: " + conformalSpace
                    + "\nConformal Metric: " + prettyString(conformalMetric, conformalSpace, "a", "b")
                    + "\nConformal Inverse Metric: " + prettyString(conformalInverseMetric, conformalSpace, "a", "b"));
            System.out.println("Conformal Christoffel: "
                    + prettyString(conformalSpace.christoffel, conformalSpace, "a", "b", "c"));
            // System.out.println("Conformal Riemann Tensor: "
            // + prettyString(conformalSpace.riemannTensor, conformalSpace, vars));
            System.out.println("Conformal Ricci Tensor: "
                    + prettyString(conformalSpace.ricciTensor, conformalSpace, "a", "b"));
            // System.out.println("Conformal Ricci Scalar: "
            // + prettyString(conformalSpace.ricciScalar, conformalSpace, vars));
        }
        // {
        // String[] vars = { "t", "r", "h", "f", "s" };
        // GeneralFunction[] diag = {
        // NEGATIVE_ONE,
        // new Pow(TWO, new Placeholder("a", "t")),
        // new Pow(TWO, new Sinh(new Variable(vars[1]))),
        // new Pow(TWO, new Sin(new Variable(vars[2]))),
        // new Pow(TWO, new Sin(new Variable(vars[3]))),

        // };
        // for (int i = 2; i < diag.length; ++i) {
        // diag[i] = new Product(diag[i], diag[i]).simplifyPull();
        // }

        // System.out.println(Space.fromDiagonalMetric(vars, diag).ricciScalar);
        // }

        // // {
        // String[] vars = { "t", "r", "h", "f" };
        // Variable[] variables =
        // Arrays.stream(vars).map(Variable::new).toArray(Variable[]::new);
        // var radial = new Variable("r");
        // // var integration_constant = new Variable("C");
        // // var placeholder_f = new Placeholder("f", "r");
        // var integration_constant = ONE;
        // var mass = new Variable("M");
        // var charge = new Variable("q");
        // var placeholder_f = new Sum(ONE, new Product(NEGATIVE_TWO, mass,
        // reciprocal(radial)),
        // square(new Product(charge, reciprocal(radial))));
        // GeneralFunction[] diag = {
        // negative(placeholder_f),
        // new Product(integration_constant, reciprocal(placeholder_f)),
        // square(radial),
        // new Product(square(radial), square(new Sin(new Variable(vars[2]))))
        // };

        // Space space = Space.fromDiagonalMetric(vars, diag);
        // System.out.println(prettyString(space.christoffel, space, new String[] { "a",
        // "b", "c" }));
        // // System.out.println(prettyString(space.riemannTensor, space, new String[] {
        // // "a", "b", "c", "d" }));
        // // System.out.println(prettyString(space.einsteinTensor, space, new String[]
        // {
        // // "a", "b" }));
        // Tensor einsteinDownUp = magicTensor(
        // product(space.einsteinTensor.index("\\alpha", "\\beta"),
        // space.inverseMetric.index("\\gamma", "\\beta")));
        // System.out.println(prettyString(einsteinDownUp, space, new String[] {
        // "a", "b" }));
        // // }

        // var curt = DefaultSpaces.s3.ricciTensor;
        // var curtm = curt.modifyWithTensor(MiscTools::trigForSinners);
        // System.out.println(
        // TensorTools.prettyString(curtm,
        // DefaultSpaces.s3, new String[] { "a", "b" }));

    }
    /**
     * @BeforeAll
     *            static void beforeAll() {
     *            DefaultSpaces.initialize();
     *            }
     * 
     * @Test
     *       void undirectedTest() {
     *       NestedArray<?, Integer> test = NestedArray.nest(new Object[][] {
     *       { 1, 2 },
     *       { 3, 4 }
     *       });
     *       System.out.println(test);
     *       assertEquals(2, test.getAtIndex(0, 1));
     *       assertEquals(2, test.getRank());
     *       test.setAtIndex(-1, 0, 0);
     *       assertEquals(-1, test.getAtIndex(0, 0));
     *       Nested<?, Integer> test2 = test.modifyWith(i -> -2 * i);
     *       assertEquals(-4, test2.getAtIndex(0, 1));
     *       }
     * 
     * @Test
     *       void directedTest() {
     *       DirectedNested<?, Integer> test = DirectedNestedArray.direct(
     *       new Object[][] {
     *       { 1, 2 },
     *       { 3, 4 }
     *       }, new boolean[] { true, false });
     *       System.out.println(test);
     *       assertEquals(2, test.getAtIndex(0, 1));
     *       assertTrue(test.getDirection());
     *       }
     * 
     * @Test
     *       void tensorTest() {
     *       Tensor test = ArrayTensor.tensor(
     *       new Object[][] {
     *       { ONE, TWO },
     *       { new Constant(3), new Product(TWO, E) }
     *       },
     *       true, false);
     *       System.out.println(test);
     *       assertEquals(TWO, test.getAtIndex(0, 1));
     *       assertTrue(test.getDirection());
     *       DirectedNested<?, GeneralFunction> test2d =
     *       DirectedNestedArray.direct(NestedArray.nest(
     *       new Object[][] {
     *       { ONE, ONE },
     *       { ONE, TWO }
     *       }), new boolean[] { true, false });
     *       Tensor test2 = ArrayTensor.tensor(test2d);
     *       DirectedNested<?, ?> sum = TensorTools.createFrom(
     *       List.of("a", "b"),
     *       new boolean[] { true, false },
     *       2,
     *       new ElementSum(indexTensor(test, "a", "b"), indexTensor(test2, "a",
     *       "b")));
     *       assertEquals(sum.getAtIndex(1, 0), new Constant(4));
     *       System.out.println(sum);
     *       }
     * 
     * @Test
     *       void elementTest() {
     *       Tensor id2u = ArrayTensor.tensor(
     *       new Object[][] {
     *       { ZERO, ONE },
     *       { ONE, ZERO }
     *       },
     *       false, false);
     *       Tensor id2d = ArrayTensor.tensor(
     *       new Object[][] {
     *       { ZERO, ONE },
     *       { ONE, ZERO }
     *       },
     *       true, true);
     *       System.out.println(createFrom(List.of("a", "b"), new boolean[] { true,
     *       false }, 2,
     *       new ElementProduct(new ElementWrapper(id2u, "a", "m"), new
     *       ElementWrapper(id2d, "m", "b"))));
     *       }
     * 
     * @Test
     *       void scalarTest1() {
     *       Tensor C = ArrayTensor.tensor(
     *       new Object[] {
     *       ONE,
     *       TWO
     *       },
     *       true);
     *       Tensor R = ArrayTensor.tensor(
     *       new Object[] {
     *       TEN, ONE
     *       },
     *       false);
     *       System.out.println(createFrom(List.of(), new boolean[] {}, 2,
     *       new ElementProduct(new ElementWrapper(R, "\\mu"), new ElementWrapper(C,
     *       "\\mu"))));
     *       }
     * 
     * @Test
     *       void scalarTest2() {
     *       Tensor C = ArrayTensor.tensor(
     *       new Object[] {
     *       TEN, ONE
     *       },
     *       true);
     *       Tensor metric = ArrayTensor.tensor(
     *       new Object[][] {
     *       { NEGATIVE_ONE, ZERO },
     *       { ZERO, ONE }
     *       },
     *       false, false);
     *       System.out.println(createFrom(List.of("\\nu"), new boolean[] { false },
     *       2,
     *       new ElementProduct(new ElementWrapper(C, "\\mu"), new
     *       ElementWrapper(metric, "\\mu", "\\nu"))));
     *       }
     * 
     * @Test
     *       void christoffel() {
     *       Space space = DefaultSpaces.s2;
     *       System.out.println(space.christoffel);
     *       }
     * 
     * @Test
     *       void cov1() {
     *       Space space = DefaultSpaces.cartesian2d;
     *       Tensor tensor = ArrayTensor.tensor(
     *       new Object[] { square(new Variable("x")), new Product(TWO, new
     *       Variable("y")) },
     *       true);
     *       assertEquals(space.covariantDerivative("a", tensor, "b"),
     *       ArrayTensor.tensor(
     *       new Object[][] {
     *       { new Product(TWO, new Variable("x")), ZERO },
     *       { ZERO, TWO }
     *       },
     *       false, true));
     * 
     *       }
     * 
     * @Test
     *       void cov2() {
     *       Space space = DefaultSpaces.spherical;
     *       Tensor tensor = ArrayTensor.tensor(
     *       new Object[] {
     *       new Product(new Variable("k"), new Variable("q"), new Pow(NEGATIVE_TWO,
     *       new Variable("r"))),
     *       ZERO, ZERO },
     *       true);
     *       Tensor expected = ArrayTensor.tensor(
     *       new Object[][] {
     *       { new Product(NEGATIVE_TWO, new Variable("k"), new Variable("q"),
     *       new Pow(new Constant(-3), new Variable("r"))), ZERO, ZERO },
     *       { ZERO, new Product(new Variable("k"), new Variable("q"),
     *       new Pow(new Constant(-3), new Variable("r"))), ZERO },
     *       { ZERO, ZERO,
     *       new Product(new Variable("k"), new Variable("q"),
     *       new Pow(new Constant(-3), new Variable("r"))) }
     *       },
     *       false, true);
     *       assertEquals(expected, space.covariantDerivative("\\mu", tensor,
     *       "\\nu"));
     *       }
     * 
     * @Test
     *       void cov3() {
     *       Space space = DefaultSpaces.cartesian2d;
     *       Tensor tensor = ArrayTensor.tensor(
     *       new Object[][] {
     *       { square(new Variable("x")), new Product(TWO, new Variable("y")) },
     *       { ZERO, new Sin(new Variable("x")) }
     *       },
     *       true, false);
     *       assertEquals(space.covariantDerivative("a", tensor, "b", "c"),
     *       ArrayTensor.tensor(
     *       new Object[][][] {
     *       {
     *       { new Product(TWO, new Variable("x")), ZERO },
     *       { ZERO, new Cos(new Variable("x")) }
     *       },
     *       {
     *       { ZERO, TWO },
     *       { ZERO, ZERO }
     *       }
     *       },
     *       false, true, false));
     * 
     *       }
     * 
     * @Test
     *       void access1() {
     *       Tensor vector = ArrayTensor.tensor(
     *       new Object[] {
     *       new Product(new Variable("k"), new Variable("q"), new Pow(NEGATIVE_TWO,
     *       new Variable("r"))),
     *       ZERO, ZERO },
     *       true);
     *       System.out.println(TensorTools.createFrom(
     *       List.of("\\beta", "a"),
     *       new boolean[] { false, true },
     *       3,
     *       new ElementProduct(
     *       TensorTools.indexTensor(vector, "\\alpha"),
     *       TensorTools.indexTensor(DefaultSpaces.spherical.christoffel, "\\alpha",
     *       "a", "\\beta"))));
     *       }
     * 
     **/

}
