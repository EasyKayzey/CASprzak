import functions.GeneralFunction;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import parsing.FunctionParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHelpers {

	public static class FunctionConverter extends SimpleArgumentConverter {

		@Override
		protected Object convert(Object object, Class<?> targetType) throws ArgumentConversionException {
			assertEquals(GeneralFunction.class, targetType);
			return FunctionParser.parseInfix((String) object);
		}
	}

}
