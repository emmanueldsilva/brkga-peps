package uem.br.ag.peps.parametros;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ParametroEmployeeSkill extends ParametroLinha {

	private static final String EMPLOYEE_PREFIX = "employee.";
	private static final String SKILL_PREFIX = ".skill.";
	private static final String EQUALS = "=";

	private int employee;
	
	private int skill;
	
	private int value;
	
	public ParametroEmployeeSkill(String linha) {
		employee = valueOf(substringBetween(linha, EMPLOYEE_PREFIX, SKILL_PREFIX));
		skill = valueOf(substringBetween(linha, SKILL_PREFIX, EQUALS));
		value = valueOf(substringAfter(linha, EQUALS));
	}

	public static String pattern() {
		return EMPLOYEE_PREFIX + "\\d+" + SKILL_PREFIX + "\\d+" + EQUALS + "\\d+";
	}
	
	public int getSkill() {
		return skill;
	}
	
	public int getEmployee() {
		return employee;
	}
	
	public int getValue() {
		return value;
	}
	
}
