package uem.br.brkga.peps.genetico;

public class ParametrosAlgoritmo {
	
	private int tamanhoPopulacao;
	
	private Double tamanhoGrupoElite;
	
	private Double tamanhoGrupoMutantes;
	
	private Double tamanhoGrupoCombinatorio;
	
	private int numeroGeracoes;
	
	private int numeroExecucoes;
	
	private Double probabilidadeHerancaElite;
	
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

	public String getPathBenchmark() {
		return pathBenchmark;
	}

	public void setPathBenchmark(String pathBenchmark) {
		this.pathBenchmark = pathBenchmark;
	}

	public Double getTamanhoGrupoElite() {
		return tamanhoGrupoElite;
	}

	public void setTamanhoGrupoElite(Double tamanhoGrupoElite) {
		this.tamanhoGrupoElite = tamanhoGrupoElite;
	}

	public Double getTamanhoGrupoMutantes() {
		return tamanhoGrupoMutantes;
	}

	public void setTamanhoGrupoMutantes(Double tamanhoGrupoMutantes) {
		this.tamanhoGrupoMutantes = tamanhoGrupoMutantes;
	}
	
	public Double getTamanhoGrupoCombinatorio() {
		return tamanhoGrupoCombinatorio;
	}

	public void setTamanhoGrupoCombinatorio(Double tamanhoGrupoCombinatorio) {
		this.tamanhoGrupoCombinatorio = tamanhoGrupoCombinatorio;
	}

	public Double getProbabilidadeHerancaElite() {
		return probabilidadeHerancaElite;
	}

	public void setProbabilidadeHerancaElite(Double probabilidadeHerancaElite) {
		this.probabilidadeHerancaElite = probabilidadeHerancaElite;
	}
	
}
