package uem.br.brkga.peps.genetico;

import uem.br.brkga.peps.problema.ProblemaBuilder;
import uem.br.brkga.peps.utils.RandomFactory;

public class IndividuoCodificado {
	
	public static final int LINHAS = 2;
	
	private Double[] genes;
	
	private Individuo individuo;
	
	public IndividuoCodificado() {
		this.genes = new Double[ProblemaBuilder.getInstance().getNumeroTasks()];
	}
	
	public IndividuoCodificado(Double[] genes) {
		this.genes = genes;
	}

	public void popularGenesAleatoriamente() {
		for (int i = 0; i < ProblemaBuilder.getInstance().getNumeroTasks(); i++) {
			setGene(i, RandomFactory.getInstance().randomDoubleRange1());
		}
		
		decodificar();
	}
	
	public void decodificar() {
		Double valorCodificado = 0.0;
		String stringAtuacaoEmployees = ProblemaBuilder.getInstance().getStringAtuacaoEmployeesBy(valorCodificado);
	}

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

}
