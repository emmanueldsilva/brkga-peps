package uem.br.ag.peps.genetico;

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
	
}
