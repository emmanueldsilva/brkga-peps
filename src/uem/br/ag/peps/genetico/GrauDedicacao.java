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
	
	public String getValorAsBinary() {
		return leftPad(toBinaryString(round(getValor() * 7)), 3, '0');
	}
	
}
