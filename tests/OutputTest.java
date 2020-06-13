import functions.special.Constant;
import org.junit.jupiter.api.Test;
import output.OutputEndpoint;
import tools.DefaultFunctions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputTest {

	@Test
	void endpointTest1() {
		assertEquals(DefaultFunctions.TWO.toOutputFunction(), new OutputEndpoint(new Constant(2)));
	}
}
