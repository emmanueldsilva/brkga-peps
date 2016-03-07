package uem.br.brkga.peps.genetico;

import uem.br.brkga.peps.entidade.Employee;
import uem.br.brkga.peps.entidade.Task;
import uem.br.brkga.peps.problema.ProblemaBuilder;
import uem.br.brkga.peps.utils.RandomFactory;

public class IndividuoCodificado {
	
	public static final int LINHAS = 2;
	
	private Double[] genes;
	
	private Individuo individuo;
	
	public IndividuoCodificado() {
		this.genes = new Double[ProblemaBuilder.getInstance().getNumeroTasks() * ProblemaBuilder.getInstance().getNumeroEmployees()];
	}
	
	public IndividuoCodificado(Double[] genes) {
		this.genes = genes;
	}

	public void popularGenesAleatoriamente() {
		for (int i = 0; i < ProblemaBuilder.getInstance().getNumeroTasks() * ProblemaBuilder.getInstance().getNumeroEmployees(); i++) {
			setGene(i, RandomFactory.getInstance().randomDoubleRange1());
		}
		
		decodificar();
	}
	
	public void decodificar() {
		individuo = new Individuo(buildMatrizDedicacao());
	}

	private MatrizDedicacao buildMatrizDedicacao() {
		int numeroTasks = ProblemaBuilder.getInstance().getNumeroTasks();
		int numeroEmployees = ProblemaBuilder.getInstance().getNumeroEmployees();
		
		final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
		for (int i = 0; i < genes.length; i++) {
			final Employee employee = ProblemaBuilder.getInstance().getEmployee((int) i / numeroTasks);
			final Task task = ProblemaBuilder.getInstance().getTask(i % numeroTasks);

			matrizDedicacao.addGrauDedicacao(employee, task, normalizaValorCodificado(genes[i]));
		}
//		final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
//		for (int i = 0; i < numeroEmployees; i++) {
//			for (int j = 0; j < numeroTasks; j++) {
//				final Employee employee = ProblemaBuilder.getInstance().getEmployee(i);
//				final Task task = ProblemaBuilder.getInstance().getTask(j);
//				
//				matrizDedicacao.addGrauDedicacao(employee, task, normalizaValorCodificado(genes[j + (numeroTasks * i)]));
//			}
//		}
		
		matrizDedicacao.efetuaCalculosProjeto();
		
		return matrizDedicacao;
	}

	private Double normalizaValorCodificado(Double valorCodificado) {
		return RandomFactory.getInstance().getValorGrauDedicacao((int) (valorCodificado * 8));
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
