package uem.br.ag.peps.genetico;

import static java.lang.Long.toBinaryString;
import static java.lang.Math.round;
import static org.apache.commons.lang3.StringUtils.leftPad;
import uem.br.ag.peps.entidade.Employee;
import uem.br.ag.peps.entidade.Task;

public class GrauDedicacao {

	private Employee employee;
	
	private Task task;
	
	private Double valor;
	
	public GrauDedicacao(Employee employee, Task task, String binaryValue) {
		this(employee, task, parseBinaryValue(binaryValue));
	}
	
	public GrauDedicacao(Employee employee, Task task, Double valor) {
		this.employee = employee;
		this.task = task;
		this.valor = valor;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public void setValor(String binaryValue) {
		this.valor = parseBinaryValue(binaryValue);
	}
	
	private static Double parseBinaryValue(String binaryValue) {
		switch (Integer.parseInt(binaryValue, 2)) {
		case 0:
			return 0.0;
		case 1:
			return 0.14;
		case 2:
			return 0.29;
		case 3:
			return 0.43;
		case 4:
			return 0.57;
		case 5:
			return 0.71;
		case 6:
			return 0.86;
		case 7:
			return 1.00;
		default:
			throw new RuntimeException("Valor indefinido: " + binaryValue);
		}
	}
	
	public String getValorAsBinary() {
		return leftPad(toBinaryString(round(getValor() * 7)), 3, '0');
	}
	
}
