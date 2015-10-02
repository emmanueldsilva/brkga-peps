package uem.br.ag.peps.entidade;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;

public class Task {

	private int numero;
	
	private Double custo;
	
	private List<Skill> skills = emptyList();
	
	private List<Task> nextTasks = new ArrayList<Task>();
	
	private List<Task> previousTasks = new ArrayList<Task>();

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
	
	public void addNextTask(Task task) {
		if (!nextTasks.contains(task)) {
			nextTasks.add(task);
		}
	}

	public List<Task> getPreviousTasks() {
		return previousTasks;
	}

	public void setPreviousTasks(List<Task> previousTasks) {
		this.previousTasks = previousTasks;
	}
	
	public void addPreviousTask(Task task) {
		if (!previousTasks.contains(task)) {
			previousTasks.add(task);
		}
	}

	public boolean hasPreviousTasks() {
		return this.previousTasks.isEmpty();
	}
	
}
