package uem.br.brkga.peps.genetico;

public abstract class IndividuoCodificado {
	
	protected Individuo individuo;
	
	protected Double[] genes;

	public abstract void codificar();
	
	public abstract void decodificar();
	
	public abstract void recodificar();
	
	public Individuo getIndividuo() {
		return individuo;
	}
	
	public Double[] getGenes() {
		return genes;
	}
	
	public Double getValor(int task) {
		return genes[task];
	}
	
	public void setGene(int task, Double valor) {
		genes[task] = valor;
	}
	
	public void verificaFactibilidade() {
		individuo.verificaFactibilidade();
	}

	public void calculaValorFitness() {
		individuo.calculaValorFitness();
	}

	public Double getValorFitness() {
		return individuo.getValorFitness();
	}
	
	public Double getCustoTotalProjeto() {
		return individuo.getCustoTotalProjeto();
	}
	
	public Double getDuracaoTotalProjeto() {
		return individuo.getDuracaoTotalProjeto();
	}
	
	public boolean isFactivel() {
		return individuo.isFactivel();
	}
	
}
