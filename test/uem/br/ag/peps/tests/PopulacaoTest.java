package uem.br.ag.peps.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uem.br.ag.peps.entidade.Employee;
import uem.br.ag.peps.entidade.Task;
import uem.br.ag.peps.genetico.Individuo;
import uem.br.ag.peps.genetico.MatrizDedicacao;
import uem.br.ag.peps.genetico.Populacao;
import uem.br.ag.peps.problema.ProblemaBuilder;
import uem.br.ag.peps.utils.RandomFactory;

public class PopulacaoTest {

	private final ProblemaBuilder problemaBuilder = ProblemaBuilder.getInstance();
	
	@Before
	public void before() {
		problemaBuilder.clear();
		problemaBuilder.setParametrosPath("/home/emmanuel/projetos/ag-peps/test-resources/test-intances/peps-4-tasks-2-employees.conf");
		problemaBuilder.readParametrosArquivo();
	}
	
	@Test
	public void testCruzamentoMatrizesDedicacao() {
		RandomFactory.getInstance().setSeed(1);
		final Populacao populacao = new Populacao(2);
		
		final Individuo individuo1 = new Individuo(buildMatrizDedicacao1());
		populacao.addIndividuo(individuo1);
		
		final Individuo individuo2 = new Individuo(buildMatrizDedicacao2());
		populacao.addIndividuo(individuo2);
		
		populacao.efetuarCruzamento();
		
		Assert.assertEquals(4, populacao.getIndividuos().size());
		
		final Employee employee0 = problemaBuilder.getEmployee(0);
		final Employee employee1 = problemaBuilder.getEmployee(1);
		final Task task0 = problemaBuilder.getTask(0);
		final Task task1 = problemaBuilder.getTask(1);
		final Task task2 = problemaBuilder.getTask(2);
		final Task task3 = problemaBuilder.getTask(3);
		
		Individuo individuo3 = populacao.getIndividuos().get(2);
		MatrizDedicacao matrizDedicacao1 = individuo3.getMatrizDedicacao();
		
		Assert.assertEquals(Double.valueOf(0.0), matrizDedicacao1.getGrauDedicacao(employee0, task0).getValor());
		Assert.assertEquals(Double.valueOf(0.14), matrizDedicacao1.getGrauDedicacao(employee0, task1).getValor());
		Assert.assertEquals(Double.valueOf(0.29), matrizDedicacao1.getGrauDedicacao(employee0, task2).getValor());
		Assert.assertEquals(Double.valueOf(0.86), matrizDedicacao1.getGrauDedicacao(employee0, task3).getValor());
		Assert.assertEquals(Double.valueOf(0.29), matrizDedicacao1.getGrauDedicacao(employee1, task0).getValor());
		Assert.assertEquals(Double.valueOf(0.0), matrizDedicacao1.getGrauDedicacao(employee1, task1).getValor());
		Assert.assertEquals(Double.valueOf(0.86), matrizDedicacao1.getGrauDedicacao(employee1, task2).getValor());
		Assert.assertEquals(Double.valueOf(0.29), matrizDedicacao1.getGrauDedicacao(employee1, task3).getValor());

		
		Individuo individuo4 = populacao.getIndividuos().get(3);
		MatrizDedicacao matrizDedicacao2 = individuo4.getMatrizDedicacao();
		
		Assert.assertEquals(Double.valueOf(0.43), matrizDedicacao2.getGrauDedicacao(employee0, task0).getValor());
		Assert.assertEquals(Double.valueOf(1.00), matrizDedicacao2.getGrauDedicacao(employee0, task1).getValor());
		Assert.assertEquals(Double.valueOf(0.57), matrizDedicacao2.getGrauDedicacao(employee0, task2).getValor());
		Assert.assertEquals(Double.valueOf(0.71), matrizDedicacao2.getGrauDedicacao(employee0, task3).getValor());
		Assert.assertEquals(Double.valueOf(1.0), matrizDedicacao2.getGrauDedicacao(employee1, task0).getValor());
		Assert.assertEquals(Double.valueOf(0.43), matrizDedicacao2.getGrauDedicacao(employee1, task1).getValor());
		Assert.assertEquals(Double.valueOf(0.71), matrizDedicacao2.getGrauDedicacao(employee1, task2).getValor());
		Assert.assertEquals(Double.valueOf(0.57), matrizDedicacao2.getGrauDedicacao(employee1, task3).getValor());
		
	}

	/**
	 * 0.43 | 1.00 | 0.29 | 0.86
	 * -------------------------
	 * 0.29 | 0.00 | 0.71 | 0.57
	 * @return
	 */
	private MatrizDedicacao buildMatrizDedicacao1() {
		final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
		
		final Task task0 = problemaBuilder.getTask(0);
		final Task task1 = problemaBuilder.getTask(1);
		final Task task2 = problemaBuilder.getTask(2);
		final Task task3 = problemaBuilder.getTask(3);
		
		final Employee employee0 = problemaBuilder.getEmployee(0);
		matrizDedicacao.addGrauDedicacao(employee0, task0, 0.43);
		matrizDedicacao.addGrauDedicacao(employee0, task1, 1.0);
		matrizDedicacao.addGrauDedicacao(employee0, task2, 0.29);
		matrizDedicacao.addGrauDedicacao(employee0, task3, 0.86);
		
		final Employee employee1 = problemaBuilder.getEmployee(1);
		matrizDedicacao.addGrauDedicacao(employee1, task0, 0.29);
		matrizDedicacao.addGrauDedicacao(employee1, task1, 0.0);
		matrizDedicacao.addGrauDedicacao(employee1, task2, 0.71);
		matrizDedicacao.addGrauDedicacao(employee1, task3, 0.57);
		
		return matrizDedicacao;
	}
	
	/**
	 * 0.00 | 0.14 | 0.57 | 0.71
	 * -------------------------
	 * 1.00 | 0.43 | 0.86 | 0.29
	 * @return
	 */
	private MatrizDedicacao buildMatrizDedicacao2() {
		final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
		
		final Task task0 = problemaBuilder.getTask(0);
		final Task task1 = problemaBuilder.getTask(1);
		final Task task2 = problemaBuilder.getTask(2);
		final Task task3 = problemaBuilder.getTask(3);
		
		final Employee employee0 = problemaBuilder.getEmployee(0);
		matrizDedicacao.addGrauDedicacao(employee0, task0, 0.0);
		matrizDedicacao.addGrauDedicacao(employee0, task1, 0.14);
		matrizDedicacao.addGrauDedicacao(employee0, task2, 0.57);
		matrizDedicacao.addGrauDedicacao(employee0, task3, 0.71);
		
		final Employee employee1 = problemaBuilder.getEmployee(1);
		matrizDedicacao.addGrauDedicacao(employee1, task0, 1.0);
		matrizDedicacao.addGrauDedicacao(employee1, task1, 0.43);
		matrizDedicacao.addGrauDedicacao(employee1, task2, 0.86);
		matrizDedicacao.addGrauDedicacao(employee1, task3, 0.29);
		
		return matrizDedicacao;
	}
	
}
