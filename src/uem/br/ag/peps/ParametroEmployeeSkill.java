package uem.br.ag.peps;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ParametroEmployeeSkill extends ParametroLinha {


	private final String EMPLOYEE_PREFIX = "employee.";
	private final String SKILL_PREFIX = ".skill.";
	private final String EQUALS = "=";

	private int employee;
	
	private int skill;
	
	private int value;

	@Override
	public void readParameter(String linha) {
		employee = valueOf(substringBetween(linha, EMPLOYEE_PREFIX, SKILL_PREFIX));
		skill = valueOf(substringBetween(linha, SKILL_PREFIX, EQUALS));
		value = valueOf(substringAfter(linha, EQUALS));
	}
	
	@Override
	public String getPattern() {
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
