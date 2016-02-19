package uem.br.brkga.peps.genetico;

import uem.br.brkga.peps.problema.ProblemaBuilder;
import uem.br.brkga.peps.utils.RandomFactory;

public class IndividuoCodificado {
	
	private final int LINHAS = 2;
	
	private Double[][] genes;
	
	private Individuo individuo;
	
	public IndividuoCodificado() {
		this.genes = new Double[LINHAS][ProblemaBuilder.getInstance().getNumeroTasks()];
	}

	public void popularGenesAleatoriamente() {
		for (int i = 0; i < LINHAS; i++) {
			for (int j = 0; j < ProblemaBuilder.getInstance().getNumeroTasks(); j++) {
				genes[i][j] = RandomFactory.getInstance().randomGeneCodificado();
			}
		}
		
		decodificar();
	}
	
	private void decodificar() {
//		this.individuo = 
		//TODO 
	}

	public Individuo getIndividuo() {
		return individuo;
	}
	
	public Double[][] getGenes() {
		return genes;
	}

}
