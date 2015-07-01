package uem.br.ag.peps;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ParametroEmployeeSalary extends ParametroLinha {

	private final String EMPLOYEE_PREFIX = "employee.";
	private final String SALARY = ".salary";
	private final String EQUALS = "=";

	private int employee;
	
	private Double value;

	@Override
	public void readParameter(String linha) {
		employee = valueOf(substringBetween(linha, EMPLOYEE_PREFIX, SALARY));
		value = Double.valueOf(substringAfter(linha, EQUALS));
	}
	
	@Override
	public String getPattern() {
		return EMPLOYEE_PREFIX + "\\d+" + SALARY + EQUALS + "\\d+.\\d+";
	}
	
	public int getEmployee() {
		return employee;
	}
	
	public Double getValue() {
		return value;
	}
	
}
