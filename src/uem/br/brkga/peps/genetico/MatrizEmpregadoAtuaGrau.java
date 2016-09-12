package uem.br.brkga.peps.genetico;

import static java.math.BigDecimal.ONE;
import static java.math.MathContext.DECIMAL32;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import java.math.BigDecimal;

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
				int posicao = task.getNumero() + (employee.getCodigo() * ProblemaBuilder.getInstance().getNumeroTasks());
				
				final Double grauDedicacaoCodificado = genes[posicao];
				final Double grauDedicacaoDecodificado = matrizDedicacao.getGrauDedicacao(employee, task).getValor();
				
				if (grauDedicacaoCodificado >= DOUBLE_ONE) {
					if (grauDedicacaoDecodificado == DOUBLE_ZERO) {
						genes[posicao] = new BigDecimal(genes[posicao]).subtract(ONE, DECIMAL32).doubleValue(); 
					} else if (!saoMesmosValores(grauDedicacaoCodificado, grauDedicacaoDecodificado)) {
						genes[posicao] = new BigDecimal(grauDedicacaoDecodificado).add(ONE, DECIMAL32).doubleValue();
					}
				} else { 
					if (grauDedicacaoDecodificado > DOUBLE_ZERO) {
						genes[posicao] = new BigDecimal(grauDedicacaoDecodificado).add(ONE, DECIMAL32).doubleValue();
					}
				}
			}
		}
	}

	private boolean saoMesmosValores(Double grauDedicacaoCodificado, Double grauDedicacaoDecodificado) {
		final Double valorGrauDedicacaoDecodificado = RandomFactory.getInstance().getValorGrauDedicacaoSemZero((int) ((grauDedicacaoCodificado - 1.0) * 7));
		return valorGrauDedicacaoDecodificado.equals(grauDedicacaoDecodificado);
	}
	
}
