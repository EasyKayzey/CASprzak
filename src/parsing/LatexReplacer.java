package parsing;

import java.util.List;
import java.util.regex.Pattern;

import static parsing.OperationLists.*;

public class LatexReplacer {

	private LatexReplacer() {}

	private static final Pattern A   = Pattern.compile("\\\\Alpha");
	private static final Pattern a   = Pattern.compile("\\\\alpha");
	private static final Pattern B   = Pattern.compile("\\\\Beta");
	private static final Pattern b   = Pattern.compile("\\\\beta");
	private static final Pattern G   = Pattern.compile("\\\\Gamma");
	private static final Pattern g   = Pattern.compile("\\\\gamma");
	private static final Pattern D   = Pattern.compile("\\\\Delta");
	private static final Pattern d   = Pattern.compile("\\\\delta");
	private static final Pattern E   = Pattern.compile("\\\\Epsilon");
	private static final Pattern e   = Pattern.compile("\\\\epsilon");
	private static final Pattern e2  = Pattern.compile("\\\\varepsilon");
	private static final Pattern Z   = Pattern.compile("\\\\Zeta");
	private static final Pattern z   = Pattern.compile("\\\\zeto");
	private static final Pattern H   = Pattern.compile("\\\\Eta");
	private static final Pattern h   = Pattern.compile("\\\\eta");
	private static final Pattern TH  = Pattern.compile("\\\\Theta");
	private static final Pattern th  = Pattern.compile("\\\\theta");
	private static final Pattern th2 = Pattern.compile("\\\\vartheta");
	private static final Pattern I   = Pattern.compile("\\\\Iota");
	private static final Pattern i   = Pattern.compile("\\\\iota");
	private static final Pattern K   = Pattern.compile("\\\\Kappa");
	private static final Pattern k   = Pattern.compile("\\\\kappa");
	private static final Pattern k2  = Pattern.compile("\\\\varkappa");
	private static final Pattern L   = Pattern.compile("\\\\Lambda");
	private static final Pattern l   = Pattern.compile("\\\\lambda");
	private static final Pattern M   = Pattern.compile("\\\\Mu");
	private static final Pattern m   = Pattern.compile("\\\\mu");
	private static final Pattern N   = Pattern.compile("\\\\Nu");
	private static final Pattern n   = Pattern.compile("\\\\nu");
	private static final Pattern CH  = Pattern.compile("\\\\Xi");
	private static final Pattern ch  = Pattern.compile("\\\\xi");
	private static final Pattern O   = Pattern.compile("\\\\Omicron");
	private static final Pattern o   = Pattern.compile("\\\\omicron");
	private static final Pattern P   = Pattern.compile("\\\\Pi");
	private static final Pattern p   = Pattern.compile("\\\\pi");
	private static final Pattern p2  = Pattern.compile("\\\\varpi");
	private static final Pattern R   = Pattern.compile("\\\\Rho");
	private static final Pattern r   = Pattern.compile("\\\\rho");
	private static final Pattern r2  = Pattern.compile("\\\\varrho");
	private static final Pattern S   = Pattern.compile("\\\\Sigma");
	private static final Pattern s   = Pattern.compile("\\\\sigma");
	private static final Pattern s2  = Pattern.compile("\\\\varsigma");
	private static final Pattern T   = Pattern.compile("\\\\Tau");
	private static final Pattern t   = Pattern.compile("\\\\tau");
	private static final Pattern U   = Pattern.compile("\\\\Upsilon");
	private static final Pattern u   = Pattern.compile("\\\\upsilon");
	private static final Pattern PH  = Pattern.compile("\\\\Phi");
	private static final Pattern ph  = Pattern.compile("\\\\phi");
	private static final Pattern ph2 = Pattern.compile("\\\\varphi");
	private static final Pattern X   = Pattern.compile("\\\\Chi");
	private static final Pattern x   = Pattern.compile("\\\\chi");
	private static final Pattern PS  = Pattern.compile("\\\\Psi");
	private static final Pattern ps  = Pattern.compile("\\\\psi");
	private static final Pattern W   = Pattern.compile("\\\\Omega");
	private static final Pattern w   = Pattern.compile("\\\\omega");
	private static final Pattern hb  = Pattern.compile("\\\\hbar");
	private static final Pattern par = Pattern.compile("\\\\par(tial)?");

	/**
	 * Replaces LaTeX-escaped Greek letters in a string with their actual letter characters
	 * @param input a LaTeX-escaped string
	 * @return the encoded string
	 */
	public static String encodeGreek(String input) {
		input = A  .matcher(input).replaceAll("Α");
		input = a  .matcher(input).replaceAll("α");
		input = B  .matcher(input).replaceAll("Β");
		input = b  .matcher(input).replaceAll("β");
		input = G  .matcher(input).replaceAll("Γ");
		input = g  .matcher(input).replaceAll("γ");
		input = D  .matcher(input).replaceAll("Δ");
		input = d  .matcher(input).replaceAll("δ");
		input = E  .matcher(input).replaceAll("Ε");
		input = e  .matcher(input).replaceAll("ϵ");
		input = e2 .matcher(input).replaceAll("ε");
		input = Z  .matcher(input).replaceAll("Ζ");
		input = z  .matcher(input).replaceAll("ζ");
		input = H  .matcher(input).replaceAll("Η");
		input = h  .matcher(input).replaceAll("η");
		input = TH .matcher(input).replaceAll("Θ");
		input = th .matcher(input).replaceAll("θ");
		input = th2.matcher(input).replaceAll("ϑ");
		input = I  .matcher(input).replaceAll("Ι");
		input = i  .matcher(input).replaceAll("ι");
		input = K  .matcher(input).replaceAll("Κ");
		input = k  .matcher(input).replaceAll("κ");
		input = k2 .matcher(input).replaceAll("ϰ");
		input = L  .matcher(input).replaceAll("Λ");
		input = l  .matcher(input).replaceAll("λ");
		input = M  .matcher(input).replaceAll("Μ");
		input = m  .matcher(input).replaceAll("μ");
		input = N  .matcher(input).replaceAll("Ν");
		input = n  .matcher(input).replaceAll("ν");
		input = CH .matcher(input).replaceAll("Ξ");
		input = ch .matcher(input).replaceAll("ξ");
		input = O  .matcher(input).replaceAll("Ο");
		input = o  .matcher(input).replaceAll("ο");
		input = P  .matcher(input).replaceAll("Π");
		input = p  .matcher(input).replaceAll("π");
		input = p2 .matcher(input).replaceAll("ϖ");
		input = R  .matcher(input).replaceAll("Ρ");
		input = r  .matcher(input).replaceAll("ρ");
		input = r2 .matcher(input).replaceAll("ϱ");
		input = S  .matcher(input).replaceAll("Σ");
		input = s  .matcher(input).replaceAll("σ");
		input = s2 .matcher(input).replaceAll("ς");
		input = T  .matcher(input).replaceAll("Τ");
		input = t  .matcher(input).replaceAll("τ");
		input = U  .matcher(input).replaceAll("Υ");
		input = u  .matcher(input).replaceAll("υ");
		input = PH .matcher(input).replaceAll("Φ");
		input = ph .matcher(input).replaceAll("φ");
		input = ph2.matcher(input).replaceAll("ϕ");
		input = X  .matcher(input).replaceAll("Χ");
		input = x  .matcher(input).replaceAll("χ");
		input = PS .matcher(input).replaceAll("Ψ");
		input = ps .matcher(input).replaceAll("ψ");
		input = W  .matcher(input).replaceAll("Ω");
		input = w  .matcher(input).replaceAll("ω");
		input = hb .matcher(input).replaceAll("ħ");
		input = par.matcher(input).replaceAll("∂");
		return input;
	}

	/**
	 * Adds LaTeX escapes to all operations that should be escaped, and to {@code pi}.
	 * @param input the unescaped input
	 * @return the input with LaTeX escapes inserted as specified above
	 */
	public static String addEscapes(String input) {
		for (List<String> ops : List.of(binaryOperations, unitaryOperations, List.of("\\pi")))
			for (String op : ops)
				if (op.charAt(0) == '\\')
					input = input.replaceAll("(?<![\\\\a])\\s*(?=" + op.substring(1) + ")", "\\\\");
		return input;
	}

}
