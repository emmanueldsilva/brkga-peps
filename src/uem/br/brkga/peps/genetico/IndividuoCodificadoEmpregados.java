package uem.br.brkga.peps.genetico;

import static java.lang.Long.toBinaryString;
import static java.lang.Math.pow;
import static org.apache.commons.lang3.StringUtils.leftPad;
import uem.br.brkga.peps.entidade.Employee;
import uem.br.brkga.peps.entidade.Task;
import uem.br.brkga.peps.problema.ProblemaBuilder;
import uem.br.brkga.peps.utils.RandomFactory;

public class IndividuoCodificadoEmpregados extends IndividuoCodificado {
	
	private String[] empregadosPorTarefas = new String[ProblemaBuilder.getInstance().getNumeroTasks()];

	public IndividuoCodificadoEmpregados() {
		this(new Double[ProblemaBuilder.getInstance().getNumeroTasks()]);
	}
	
	public IndividuoCodificadoEmpregados(Double[] genes) {
		this.genes = genes;
	}
	
	@Override
	public void codificar() {
		for (int i = 0; i < genes.length; i++) {
			setGene(i, RandomFactory.getInstance().randomDoubleRange1());
		}
		
		decodificar();
	}

	@Override
	public void decodificar() {
		for (int i = 0; i < genes.length; i++) {
			long valorNormalizado = normalizaValorCodificado(getValor(i));
			valorNormalizado = resolvePrimeiraRestricao(i, valorNormalizado);
			
			empregadosPorTarefas[i] = leftPad(toBinaryString(valorNormalizado), ProblemaBuilder.getInstance().getNumeroEmployees(), '0');
		}
		
		individuo = new Individuo(buildMatrizDedicacao());
	}
	
	@Override
	public void recodificar() {	}

	private long resolvePrimeiraRestricao(int i, long valorNormalizado) {
		while (valorNormalizado == 0) {
			setGene(i, RandomFactory.getInstance().randomDoubleRange1());
			
			valorNormalizado = normalizaValorCodificado(getValor(i));
		}
		
		return valorNormalizado;
	}

	private MatrizDedicacao buildMatrizDedicacao() {
		final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
		
		for (int i = 0; i < empregadosPorTarefas.length; i++) {
			String atuacaoBinario = empregadosPorTarefas[i];
			
			for (int j = 0; j < ProblemaBuilder.getInstance().getNumeroEmployees(); j++) {
				final Task task = ProblemaBuilder.getInstance().getTask(i);
				final Employee employee = ProblemaBuilder.getInstance().getEmployee(j);
				
				boolean empregadoAtuaNaTarefa = atuacaoBinario.charAt(j) == '1';
				matrizDedicacao.addGrauDedicacao(employee, task, getGrauDedicacaoByAtuacao(empregadoAtuaNaTarefa));
			}
		}
		
		matrizDedicacao.efetuaCalculosProjeto();
		
		return matrizDedicacao;
	}
	
	private Double getGrauDedicacaoByAtuacao(boolean empregadoAtuaNaTarefa) {
		if (empregadoAtuaNaTarefa) {
			return RandomFactory.getInstance().randomGrauDedicacaoPositivo();
		}
			
		return RandomFactory.getInstance().getValorGrauDedicacao(0);
	}
	
	private long normalizaValorCodificado(Double valorCodificado) {
		return (long) (valorCodificado * (pow(2, ProblemaBuilder.getInstance().getNumeroEmployees())));
	}
	
}
