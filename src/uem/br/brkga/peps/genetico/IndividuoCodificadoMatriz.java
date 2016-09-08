package uem.br.brkga.peps.genetico;

import uem.br.brkga.peps.entidade.Employee;
import uem.br.brkga.peps.entidade.Task;
import uem.br.brkga.peps.problema.ProblemaBuilder;
import uem.br.brkga.peps.utils.RandomFactory;

public class IndividuoCodificadoMatriz extends IndividuoCodificado {
	
	public IndividuoCodificadoMatriz() {
		this(new Double[ProblemaBuilder.getInstance().getNumeroTasks() * ProblemaBuilder.getInstance().getNumeroEmployees()]);
	}
	
	public IndividuoCodificadoMatriz(Double[] genes) {
		this.genes = genes;
	}

	@Override
	public void codificar() {
		for (int i = 0; i < ProblemaBuilder.getInstance().getNumeroTasks() * ProblemaBuilder.getInstance().getNumeroEmployees(); i++) {
			setGene(i, RandomFactory.getInstance().randomDoubleRange1());
		}
		
		decodificar();
	}
	
	@Override
	public void decodificar() {
		individuo = new Individuo(buildMatrizDedicacao());
	}
	
	@Override
	public void recodificar() {	}

	private MatrizDedicacao buildMatrizDedicacao() {
		int numeroTasks = ProblemaBuilder.getInstance().getNumeroTasks();
		
		final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
		for (int i = 0; i < genes.length; i++) {
			final Employee employee = ProblemaBuilder.getInstance().getEmployee((int) i / numeroTasks);
			final Task task = ProblemaBuilder.getInstance().getTask(i % numeroTasks);

			matrizDedicacao.addGrauDedicacao(employee, task, normalizaValorCodificado(genes[i]));
		}
		
		matrizDedicacao.efetuaCalculosProjeto();
		
		return matrizDedicacao;
	}

	private Double normalizaValorCodificado(Double valorCodificado) {
		return RandomFactory.getInstance().getValorGrauDedicacao((int) (valorCodificado * 8));
	}
	
}
