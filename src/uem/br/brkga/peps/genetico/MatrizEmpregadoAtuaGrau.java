package uem.br.brkga.peps.genetico;

import uem.br.brkga.peps.entidade.Employee;
import uem.br.brkga.peps.entidade.Task;
import uem.br.brkga.peps.problema.ProblemaBuilder;
import uem.br.brkga.peps.utils.RandomFactory;

public class MatrizEmpregadoAtuaGrau extends IndividuoCodificado {
	
	public MatrizEmpregadoAtuaGrau() {
		this(new Double[ProblemaBuilder.getInstance().getNumeroTasks() * ProblemaBuilder.getInstance().getNumeroEmployees()]);
	}
	
	public MatrizEmpregadoAtuaGrau(Double[] genes) {
		this.genes = genes;
	}

	@Override
	public void codificar() {
		for (int i = 0; i < ProblemaBuilder.getInstance().getNumeroTasks() * ProblemaBuilder.getInstance().getNumeroEmployees(); i++) {
			setGene(i, RandomFactory.getInstance().randomDoubleRange2());
		}
		
		decodificar();
	}
	
	@Override
	public void decodificar() {
		individuo = new Individuo(buildMatrizDedicacao());
	}

	private MatrizDedicacao buildMatrizDedicacao() {
		int numeroTasks = ProblemaBuilder.getInstance().getNumeroTasks();
		
		final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
		for (int i = 0; i < genes.length; i++) {
			final Employee employee = ProblemaBuilder.getInstance().getEmployee((int) i / numeroTasks);
			final Task task = ProblemaBuilder.getInstance().getTask(i % numeroTasks);

			matrizDedicacao.addGrauDedicacao(employee, task, decodificaValor(genes[i]));
		}
		
		matrizDedicacao.efetuaCalculosProjeto();
		
		return matrizDedicacao;
	}

	private Double decodificaValor(Double valorCodificado) {
		if (valorCodificado < 1.0) return 0.0;
		
		return RandomFactory.getInstance().getValorGrauDedicacaoSemZero((int) ((valorCodificado - 1.0) * 7));
	}
	
	public void recodificar() {
		final MatrizDedicacao matrizDedicacao = individuo.getMatrizDedicacao();
		
		for (Employee employee : ProblemaBuilder.getInstance().getEmployees()) {
			for (Task task : ProblemaBuilder.getInstance().getTasks()) {
				int posicao = (task.getNumero() * ProblemaBuilder.getInstance().getNumeroTasks()) + employee.getCodigo();
				
				final Double grauDedicacaoCodificado = genes[posicao];
				final Double grauDedicacaoDecodificado = matrizDedicacao.getGrauDedicacao(employee, task).getValor();
				
				if (grauDedicacaoCodificado >= 1.0) {
					if (grauDedicacaoDecodificado == 0.0) {
						genes[posicao] -= 1.0; 
					} else if (!saoMesmosValores(grauDedicacaoCodificado, grauDedicacaoDecodificado)) {
						genes[posicao] = 1.0 + grauDedicacaoDecodificado;
					}
				} else { 
					if (grauDedicacaoDecodificado > 0.0) {
						genes[posicao] = 1.0 + grauDedicacaoDecodificado;
					}
				}
			}
		}
	}

	private boolean saoMesmosValores(Double grauDedicacaoCodificado, Double grauDedicacaoDecodificado) {
		return (int) ((grauDedicacaoCodificado - 1.0) * 7) == (int) ((grauDedicacaoDecodificado - 1.0) * 7);
	}
	
}
