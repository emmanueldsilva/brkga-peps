package uem.br.ag.peps.parametros;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;

public class ParametroTaskNumber extends ParametroLinha {

	private static final String TASK_NUMBER = "task.number";
	private static final String EQUALS = "=";

	private int value;
	
	public ParametroTaskNumber(String linha) {
		value = valueOf(substringAfter(linha, EQUALS));
	}

	public static String pattern() {
		return TASK_NUMBER + EQUALS + "\\d+";
	}
	
	public int getValue() {
		return value;
	}
	
}
