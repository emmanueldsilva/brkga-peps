package uem.br.ag.peps;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ParametroEmployeeSkillNumber extends ParametroLinha {


	private final String EMPLOYEE_PREFIX = "employee.";
	private final String SKILL_NUMBER = ".skill.number";
	private final String EQUALS = "=";

	private int employee;
	
	private int value;

	@Override
	public void readParameter(String linha) {
		employee = valueOf(substringBetween(linha, EMPLOYEE_PREFIX, SKILL_NUMBER));
		value = valueOf(substringAfter(linha, EQUALS));
	}
	
	@Override
	public String getPattern() {
		return EMPLOYEE_PREFIX + "\\d+" + SKILL_NUMBER + EQUALS + "\\d+";
	}
	
	public int getEmployee() {
		return employee;
	}
	
	public int getValue() {
		return value;
	}
	
}
