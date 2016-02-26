package uem.br.brkga.peps.genetico;

import uem.br.brkga.peps.problema.ProblemaBuilder;
import uem.br.brkga.peps.utils.RandomFactory;

public class IndividuoCodificado {
	
	public static final int LINHAS = 2;
	
	private Double[][] genes;
	
	private Individuo individuo;
	
	public IndividuoCodificado() {
		this.genes = new Double[LINHAS][ProblemaBuilder.getInstance().getNumeroTasks()];
	}
	
	public IndividuoCodificado(Double[][] genes) {
		this.genes = genes;
	}

	public void popularGenesAleatoriamente() {
		for (int i = 0; i < LINHAS; i++) {
			for (int j = 0; j < ProblemaBuilder.getInstance().getNumeroTasks(); j++) {
				setGene(i, j, RandomFactory.getInstance().randomDoubleRange1());
			}
		}
		
		decodificar();
	}
	
	public void decodificar() {
//		this.individuo = 
		//TODO 
	}

	public Individuo getIndividuo() {
		return individuo;
	}
	
	public Double[][] getGenes() {
		return genes;
	}
	
	public Double getValor(int linha, int coluna) {
		return genes[linha][coluna];
	}
	
	public void setGene(int linha, int coluna, Double valor) {
		genes[linha][coluna] = valor;
	}

	public void verificaFactibilidade() {
		individuo.verificaFactibilidade();
	}

	public void calculaValorFitness() {
		individuo.calculaValorFitness();
	}

}
