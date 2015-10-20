package uem.br.ag.peps.genetico;

public class ParametrosAlgoritmo {
	
	private int tamanhoPopulacao;
	
	private int numeroGeracoes;
	
	private int numeroExecucoes;
	
	private Double percentualCruzamento;
	
	private Double percentualMutacao;
	
	private String pathBenchmark;

	public int getTamanhoPopulacao() {
		return tamanhoPopulacao;
	}

	public void setTamanhoPopulacao(int tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
	}
	
	public int getNumeroGeracoes() {
		return numeroGeracoes;
	}

	public void setNumeroGeracoes(int numeroGeracoes) {
		this.numeroGeracoes = numeroGeracoes;
	}

	public int getNumeroExecucoes() {
		return numeroExecucoes;
	}

	public void setNumeroExecucoes(int numeroExecucoes) {
		this.numeroExecucoes = numeroExecucoes;
	}

	public Double getPercentualCruzamento() {
		return percentualCruzamento;
	}

	public void setPercentualCruzamento(Double percentualCruzamento) {
		this.percentualCruzamento = percentualCruzamento;
	}

	public Double getPercentualMutacao() {
		return percentualMutacao;
	}

	public void setPercentualMutacao(Double percentualMutacao) {
		this.percentualMutacao = percentualMutacao;
	}

	public String getPathBenchmark() {
		return pathBenchmark;
	}

	public void setPathBenchmark(String pathBenchmark) {
		this.pathBenchmark = pathBenchmark;
	}
	
}
