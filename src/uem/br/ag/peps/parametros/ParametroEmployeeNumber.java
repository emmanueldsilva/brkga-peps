package uem.br.ag.peps.parametros;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;

public class ParametroEmployeeNumber extends ParametroLinha {

	private static final String EMPLOYEE_NUMBER = "employee.number";
	private static final String EQUALS = "=";

	private int value;
	
	public ParametroEmployeeNumber(String linha) {
		value = valueOf(substringAfter(linha, EQUALS));
	}

	public static String pattern() {
		return EMPLOYEE_NUMBER + EQUALS + "\\d+";
	}
	
	public int getValue() {
		return value;
	}
	
}
