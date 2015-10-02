package uem.br.ag.peps.genetico;

import java.math.BigDecimal;

import uem.br.ag.peps.entidade.Task;

public class RealizacaoTarefa {

	private Double tempoInicio;
	
	private Double tempoFim;
	
	private Task task;
	
	//TODO Colocar a duração da tarefa aqui.
	
	public RealizacaoTarefa(Task task, Double tempoInicio, Double tempoFim) {
		this.tempoInicio = tempoInicio;
		this.tempoFim = tempoFim;
		this.task = task;
	}

	public Double getTempoInicio() {
		return tempoInicio;
	}

	public void setTempoInicio(Double tempoInicio) {
		this.tempoInicio = tempoInicio;
	}

	public Double getTempoFim() {
		return tempoFim;
	}

	public void setTempoFim(Double tempoFim) {
		this.tempoFim = tempoFim;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	public static Double calculaTempoFim(Double tempoInicio, Double duracao) {
		return BigDecimal.valueOf(tempoInicio).add(BigDecimal.valueOf(duracao)).doubleValue();
	}
	
}
