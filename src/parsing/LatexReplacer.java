package parsing;

import config.Settings;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static parsing.OperationMaps.binaryOperations;
import static parsing.OperationMaps.unitaryOperations;

/**
 * {@link LatexReplacer} contains tools regarding the use of LaTeX escapes. See method documentation for more details.
 */
public class LatexReplacer {

	private LatexReplacer(){}

	/**
	 * Contains all encoding {@code Pattern}s and their replacement characters
	 */
	public static final Map<Pattern, String> encodings = new HashMap<>() {
		{
			put(Pattern.compile("\\\\Alpha"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Α");
			put(Pattern.compile("\\\\alpha"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "α");
			put(Pattern.compile("\\\\Beta"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Β");
			put(Pattern.compile("\\\\beta"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "β");
			put(Pattern.compile("\\\\Gamma"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Γ");
			put(Pattern.compile("\\\\gamma"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "γ");
			put(Pattern.compile("\\\\Delta"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Δ");
			put(Pattern.compile("\\\\delta"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "δ");
			put(Pattern.compile("\\\\Epsilon"		+ "(?![\\w.'[^\\x00-\\x7F]])"), "Ε");
			put(Pattern.compile("\\\\epsilon"		+ "(?![\\w.'[^\\x00-\\x7F]])"), "ϵ");
			put(Pattern.compile("\\\\varepsilon"	+ "(?![\\w.'[^\\x00-\\x7F]])"), "ε");
			put(Pattern.compile("\\\\Zeta"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Ζ");
			put(Pattern.compile("\\\\zeto"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "ζ");
			put(Pattern.compile("\\\\Eta"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Η");
			put(Pattern.compile("\\\\eta"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "η");
			put(Pattern.compile("\\\\Theta"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Θ");
			put(Pattern.compile("\\\\theta"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "θ");
			put(Pattern.compile("\\\\vartheta"		+ "(?![\\w.'[^\\x00-\\x7F]])"), "ϑ");
			put(Pattern.compile("\\\\Iota"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Ι");
			put(Pattern.compile("\\\\iota"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "ι");
			put(Pattern.compile("\\\\Kappa"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Κ");
			put(Pattern.compile("\\\\kappa"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "κ");
			put(Pattern.compile("\\\\varkappa"		+ "(?![\\w.'[^\\x00-\\x7F]])"), "ϰ");
			put(Pattern.compile("\\\\Lambda"		+ "(?![\\w.'[^\\x00-\\x7F]])"), "Λ");
			put(Pattern.compile("\\\\lambda"		+ "(?![\\w.'[^\\x00-\\x7F]])"), "λ");
			put(Pattern.compile("\\\\Mu"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Μ");
			put(Pattern.compile("\\\\mu"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "μ");
			put(Pattern.compile("\\\\Nu"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Ν");
			put(Pattern.compile("\\\\nu"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "ν");
			put(Pattern.compile("\\\\Xi"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Ξ");
			put(Pattern.compile("\\\\xi"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "ξ");
			put(Pattern.compile("\\\\Omicron"		+ "(?![\\w.'[^\\x00-\\x7F]])"), "Ο");
			put(Pattern.compile("\\\\omicron"		+ "(?![\\w.'[^\\x00-\\x7F]])"), "ο");
			put(Pattern.compile("\\\\Pi"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Π");
			put(Pattern.compile("\\\\pi"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "π");
			put(Pattern.compile("\\\\varpi"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "ϖ");
			put(Pattern.compile("\\\\Rho"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Ρ");
			put(Pattern.compile("\\\\rho"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "ρ");
			put(Pattern.compile("\\\\varrho"		+ "(?![\\w.'[^\\x00-\\x7F]])"), "ϱ");
			put(Pattern.compile("\\\\Sigma"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Σ");
			put(Pattern.compile("\\\\sigma"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "σ");
			put(Pattern.compile("\\\\varsigma"		+ "(?![\\w.'[^\\x00-\\x7F]])"), "ς");
			put(Pattern.compile("\\\\Tau"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Τ");
			put(Pattern.compile("\\\\tau"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "τ");
			put(Pattern.compile("\\\\Upsilon"		+ "(?![\\w.'[^\\x00-\\x7F]])"), "Υ");
			put(Pattern.compile("\\\\upsilon"		+ "(?![\\w.'[^\\x00-\\x7F]])"), "υ");
			put(Pattern.compile("\\\\Phi"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Φ");
			put(Pattern.compile("\\\\phi"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "φ");
			put(Pattern.compile("\\\\varphi"		+ "(?![\\w.'[^\\x00-\\x7F]])"), "ϕ");
			put(Pattern.compile("\\\\Chi"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Χ");
			put(Pattern.compile("\\\\chi"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "χ");
			put(Pattern.compile("\\\\Psi"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Ψ");
			put(Pattern.compile("\\\\psi"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "ψ");
			put(Pattern.compile("\\\\Omega"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "Ω");
			put(Pattern.compile("\\\\omega"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "ω");
			put(Pattern.compile("\\\\hbar"			+ "(?![\\w.'[^\\x00-\\x7F]])"), "ħ");
			put(Pattern.compile("\\\\par(tial)?"	+ "(?![\\w.'[^\\x00-\\x7F]])"), "∂");
		}
	};
	
	private static final Pattern dx = Pattern.compile("(?<![\\\\/])(?=d[a-ce-zA-Z](?![a-zA-Z]))");
	private static final Pattern pi = Pattern.compile("(?<!\\\\)pi");
	private static final Pattern logb = Pattern.compile("(?<!\\\\)logb_");
	private static final Pattern splitEscapes = Pattern.compile("(?=\\\\)");

	/**
	 * Performs all of the methods in this class on the input according to the settings in {@link Settings}
	 * @param input the input to be encoded
	 * @return the encoded input
	 */
	public static String encodeAll(String input) {
		if (!Settings.enforceEscapedFunctions)
			input = LatexReplacer.addEscapes(input);
		input = LatexReplacer.encodeMappings(input);
		return input;
	}

	/**
	 * Replaces LaTeX-escaped encodings in a string with their actual special characters,
	 * as long as they aren't followed by an allowed name character
	 * @param input a LaTeX-escaped string
	 * @return the encoded string
	 */
	public static String encodeMappings(String input) {
		String[] splitInput = splitEscapes.split(input);
		return Arrays.stream(splitInput)
				.map(ReplacementContainer::encode)
				.collect(Collectors.joining());
	}

	/**
	 * Adds LaTeX escapes to all operations that should be escaped, and to {@code pi}.
	 * @param input the unescaped input
	 * @return the input with LaTeX escapes inserted as specified above
	 */
	public static String addEscapes(String input) {
		for (Collection<String> ops : List.of(binaryOperations.keySet(), unitaryOperations.keySet()))
			for (String op : ops)
				if (op.charAt(0) == '\\')
					input = input.replaceAll("(?<!\\\\|a(rc)?)(?=" + op.substring(1) + "[\\s(])", "\\\\");
		input = pi.matcher(input).replaceAll("\\\\pi");
		input = dx.matcher(input).replaceAll("\\\\");
		input = logb.matcher(input).replaceAll("\\\\logb _");
		return input;
	}

	private static class ReplacementContainer {

		public String string;

		public ReplacementContainer(String string) {
			this.string = string;
		}

		public static String encode(String string) {
			ReplacementContainer container = new ReplacementContainer(string);
			encodings.entrySet()
					.forEach(container::replace);
			return container.string;
		}

		public void replace(Map.Entry<Pattern, String> entry) {
			string = entry.getKey().matcher(string).replaceAll(entry.getValue());
		}

	}

}
