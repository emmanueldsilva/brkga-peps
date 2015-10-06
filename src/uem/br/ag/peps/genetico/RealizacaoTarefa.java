package uem.br.ag.peps.genetico;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import java.math.BigDecimal;

import uem.br.ag.peps.entidade.Task;

public class RealizacaoTarefa {

	private Double tempoInicio;
	
	private Double tempoFim;
	
	private Double duracao;
	
	private Task task;
	
	public RealizacaoTarefa(Task task, Double duracao) {
		this.task = task;
		this.duracao = duracao;
	}
	
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

	public Double getDuracao() {
		return duracao;
	}

	public void setDuracao(Double duracao) {
		this.duracao = duracao;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	public RealizacaoTarefa buildRealizacaoTarefaInicial() {
		this.tempoInicio = DOUBLE_ZERO;
		this.tempoFim = duracao;
		
		return this;
	}
	
	public RealizacaoTarefa buildRealizacaoTarefa(Double tempoInicio) {
		this.tempoInicio = tempoInicio;
		this.tempoFim = BigDecimal.valueOf(tempoInicio).add(BigDecimal.valueOf(duracao)).doubleValue();
		
		return this;
	}
	
}
