import functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvalTest {

	@ParameterizedTest(name = "{0}: eval {1} expecting {2}")
	@CsvSource({
			"Simple constant, 2, 2",
			"Sin of pi, sin(pi), 0",
	})
	void noArg(
			String name,
			@ConvertWith(TestHelpers.FunctionConverter.class) GeneralFunction function,
			Double output
	) {
		assertEquals(function			.evaluate(Map.of()), output, 0.1);
		assertEquals(function.simplify().evaluate(Map.of()), output, 0.1);
	}


	@ParameterizedTest(name = "{0}: eval {1} at {2} expecting {3}")
	@CsvSource({
			"Simple x, x, 1, 1",
			"Multiple of x, 2*x, 2, 4",
	})
	void oneArg(
			String name,
			@ConvertWith(TestHelpers.FunctionConverter.class) GeneralFunction function,
			@ConvertWith(TestHelpers.XMapper.class) Map<String, Double> inputs,
			Double output
	) {
		assertEquals(function			.evaluate(inputs), output, 0.1);
		assertEquals(function.simplify().evaluate(inputs), output, 0.1);
	}


	@ParameterizedTest(name = "{0}: eval {1} at {2} expecting {3}")
	@CsvSource({
			"Simple sum, x+y, '1, 2', 3",
			"Unused variables, x, '2, 3', 2",
	})
	void twoArg(
			String name,
			@ConvertWith(TestHelpers.FunctionConverter.class) GeneralFunction function,
			@ConvertWith(TestHelpers.XYMapper.class) Map<String, Double> inputs,
			Double output
	) {
		assertEquals(function			.evaluate(inputs), output, 0.1);
		assertEquals(function.simplify().evaluate(inputs), output, 0.1);
	}
}
