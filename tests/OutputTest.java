import core.functions.endpoint.Constant;
import org.junit.jupiter.api.Test;
import core.tools.defaults.DefaultFunctions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputTest {

	@Test
	void endpointTest1() {
		assertEquals(DefaultFunctions.TWO.toOutputFunction(), new Constant(2));
	}
}
