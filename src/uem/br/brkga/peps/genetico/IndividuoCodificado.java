package uem.br.brkga.peps.genetico;

import java.util.Arrays;

public abstract class IndividuoCodificado {
	
	protected Individuo individuo;
	
	protected Double[] genes;

	public abstract void codificar();
	
	public abstract void decodificar();
	
	public abstract void recodificar();
	
	public Individuo getIndividuo() {
		return individuo;
	}
	
	public void setIndividuo(Individuo individuo) {
		this.individuo = individuo;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(genes);
		result = prime * result + ((individuo == null) ? 0 : individuo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndividuoCodificado other = (IndividuoCodificado) obj;
		if (!Arrays.equals(genes, other.genes))
			return false;
		if (individuo == null) {
			if (other.individuo != null)
				return false;
		} else if (!individuo.equals(other.individuo))
			return false;
		return true;
	}

}
