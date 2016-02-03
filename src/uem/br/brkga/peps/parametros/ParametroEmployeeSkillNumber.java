package uem.br.brkga.peps.parametros;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ParametroEmployeeSkillNumber extends ParametroLinha {

	private static final String EMPLOYEE_PREFIX = "employee.";
	private static final String SKILL_NUMBER = ".skill.number";
	private static final String EQUALS = "=";

	private int employee;
	
	private int value;

	public ParametroEmployeeSkillNumber(String linha) {
		employee = valueOf(substringBetween(linha, EMPLOYEE_PREFIX, SKILL_NUMBER));
		value = valueOf(substringAfter(linha, EQUALS));
	}
	
	public static String pattern() {
		return EMPLOYEE_PREFIX + "\\d+" + SKILL_NUMBER + EQUALS + "\\d+";
	}
	
	public int getEmployee() {
		return employee;
	}
	
	public int getValue() {
		return value;
	}
	
}
