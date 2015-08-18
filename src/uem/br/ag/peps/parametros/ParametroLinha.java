package uem.br.ag.peps.parametros;

import java.util.regex.Pattern;


public abstract class ParametroLinha {

	protected static String REGEX;

	public static boolean matches(String string) {
		return Pattern.compile(getRegex()).matcher(string).matches();
	}

	public static String getRegex() {
		return REGEX;
	}
	
}
