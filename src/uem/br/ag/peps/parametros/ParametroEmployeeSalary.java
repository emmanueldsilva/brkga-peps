package uem.br.ag.peps.parametros;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ParametroEmployeeSalary extends ParametroLinha {

	private static final String EMPLOYEE_PREFIX = "employee.";
	private static final String SALARY = ".salary";
	private static final String EQUALS = "=";

	private int employee;
	
	private Double value;
	
	public ParametroEmployeeSalary(String linha) {
		employee = valueOf(substringBetween(linha, EMPLOYEE_PREFIX, SALARY));
		value = Double.valueOf(substringAfter(linha, EQUALS));
	}

	public static String pattern() {
		return EMPLOYEE_PREFIX + "\\d+" + SALARY + EQUALS + "\\d+.\\d+";
	}
	
	public int getEmployee() {
		return employee;
	}
	
	public Double getValue() {
		return value;
	}
	
}
