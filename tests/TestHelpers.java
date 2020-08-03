import functions.GeneralFunction;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import parsing.FunctionParser;
import tools.ParsingTools;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHelpers {

	public static class FunctionConverter extends SimpleArgumentConverter {

		@Override
		protected Object convert(Object object, Class<?> targetType) throws ArgumentConversionException {
			assertEquals(GeneralFunction.class, targetType);
			return FunctionParser.parseInfix((String) object);
		}
	}

	public static class XMapper extends SimpleArgumentConverter {

		@Override
		protected Object convert(Object object, Class<?> targetType) throws ArgumentConversionException {
			assertEquals(Map.class, targetType);
			return Map.of("x", ParsingTools.getConstant((String) object));
		}
	}

	public static class XYMapper extends SimpleArgumentConverter {

		@Override
		protected Object convert(Object object, Class<?> targetType) throws ArgumentConversionException {
			assertEquals(Map.class, targetType);
			String[] cons = ((String) object).split(",");
			return Map.of("x", ParsingTools.getConstant(cons[0]), "y", ParsingTools.getConstant(cons[1]));
		}
	}

}
