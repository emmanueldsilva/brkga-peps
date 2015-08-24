package uem.br.ag.peps.entidade;

import static java.util.Collections.emptyList;

import java.util.List;

public class Task {

	private int numero;
	
	private Double custo;
	
	private List<Skill> skills = emptyList();
	
	private List<Task> nextTasks = emptyList();
	
	private Task previousTask;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Double getCusto() {
		return custo;
	}

	public void setCusto(Double custo) {
		this.custo = custo;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Task> getNextTasks() {
		return nextTasks;
	}

	public void setNextTasks(List<Task> nextTasks) {
		this.nextTasks = nextTasks;
	}

	public Task getPreviousTask() {
		return previousTask;
	}

	public void setPreviousTask(Task previousTask) {
		this.previousTask = previousTask;
	}
	
}
