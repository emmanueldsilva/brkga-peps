package uem.br.ag.peps;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;

public class ParametroEmployeeNumber extends ParametroLinha {

	private final String EMPLOYEE_NUMBER = "employee.number";
	private final String EQUALS = "=";

	private int value;

	@Override
	public void readParameter(String linha) {
		value = valueOf(substringAfter(linha, EQUALS));
	}
	
	@Override
	public String getPattern() {
		return EMPLOYEE_NUMBER + EQUALS + "\\d+";
	}
	
	public int getValue() {
		return value;
	}
	
}
