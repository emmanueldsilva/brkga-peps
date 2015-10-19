package uem.br.ag.peps.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uem.br.ag.peps.entidade.Employee;
import uem.br.ag.peps.entidade.Task;
import uem.br.ag.peps.genetico.Individuo;
import uem.br.ag.peps.genetico.MatrizDedicacao;
import uem.br.ag.peps.genetico.ParametrosPesos;
import uem.br.ag.peps.problema.ProblemaBuilder;

public class IndividuoTest {

	private final ProblemaBuilder problemaBuilder = ProblemaBuilder.getInstance();
	
	@Before
	public void before() {
		problemaBuilder.clear();
		problemaBuilder.setParametrosPath("/home/emmanuel/projetos/ag-peps/test-resources/test-intances/peps-4-tasks-2-employees.conf");
		problemaBuilder.readParametrosArquivo();
	}
	
	@Test
	public void validaValoresIndividuoSolucaoFactivel() {
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
		
		matrizDedicacao.efetuaCalculosProjeto();
		
		final Individuo individuo = new Individuo(matrizDedicacao);
		individuo.verificaFactibilidade();
		assertTrue("Indivíduo é uma solução factível", individuo.isFactivel());
		
		individuo.calculaValorFitness(buildParametrosPesos());
		assertEquals(Double.valueOf(0.00007471235), individuo.getValorFitness());
	}
	
	@Test
	public void validaValoresIndividuoSolucaoNaoFactivelRestricao1() {
		final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
		
		final Task task0 = problemaBuilder.getTask(0);
		final Task task1 = problemaBuilder.getTask(1);
		final Task task2 = problemaBuilder.getTask(2);
		final Task task3 = problemaBuilder.getTask(3);
		
		final Employee employee0 = problemaBuilder.getEmployee(0);
		matrizDedicacao.addGrauDedicacao(employee0, task0, 0.43);
		matrizDedicacao.addGrauDedicacao(employee0, task1, 0.0);
		matrizDedicacao.addGrauDedicacao(employee0, task2, 0.29);
		matrizDedicacao.addGrauDedicacao(employee0, task3, 0.86);
		
		final Employee employee1 = problemaBuilder.getEmployee(1);
		matrizDedicacao.addGrauDedicacao(employee1, task0, 0.29);
		matrizDedicacao.addGrauDedicacao(employee1, task1, 0.0);
		matrizDedicacao.addGrauDedicacao(employee1, task2, 0.71);
		matrizDedicacao.addGrauDedicacao(employee1, task3, 0.57);
		
		matrizDedicacao.efetuaCalculosProjeto();
		
		final Individuo individuo = new Individuo(matrizDedicacao);
		individuo.verificaFactibilidade();
		assertFalse("Indivíduo não é uma solução factível", individuo.isFactivel());
		
		individuo.calculaValorFitness(buildParametrosPesos());
		assertEquals(Double.valueOf(0.000101175), individuo.getValorFitness());
	}
	
	@Test
	public void validaValoresIndividuoSolucaoNaoFactivelRestricao2() {
		final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
		
		final Task task0 = problemaBuilder.getTask(0);
		final Task task1 = problemaBuilder.getTask(1);
		final Task task2 = problemaBuilder.getTask(2);
		final Task task3 = problemaBuilder.getTask(3);
		
		final Employee employee0 = problemaBuilder.getEmployee(0);
		matrizDedicacao.addGrauDedicacao(employee0, task0, 0.43);
		matrizDedicacao.addGrauDedicacao(employee0, task1, 0.0);
		matrizDedicacao.addGrauDedicacao(employee0, task2, 0.29);
		matrizDedicacao.addGrauDedicacao(employee0, task3, 0.86);
		
		final Employee employee1 = problemaBuilder.getEmployee(1);
		matrizDedicacao.addGrauDedicacao(employee1, task0, 0.29);
		matrizDedicacao.addGrauDedicacao(employee1, task1, 1.0);
		matrizDedicacao.addGrauDedicacao(employee1, task2, 0.71);
		matrizDedicacao.addGrauDedicacao(employee1, task3, 0.57);
		
		matrizDedicacao.efetuaCalculosProjeto();
		
		final Individuo individuo = new Individuo(matrizDedicacao);
		individuo.verificaFactibilidade();
		assertFalse("Indivíduo não é uma solução factível", individuo.isFactivel());
		
		individuo.calculaValorFitness(buildParametrosPesos());
		assertEquals(Double.valueOf(0.00008139565), individuo.getValorFitness());
	}
	
	@Test
	public void validaValoresIndividuoSolucaoNaoFactivelRestricao3() {
		final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
		
		final Task task0 = problemaBuilder.getTask(0);
		final Task task1 = problemaBuilder.getTask(1);
		final Task task2 = problemaBuilder.getTask(2);
		final Task task3 = problemaBuilder.getTask(3);
		
		final Employee employee0 = problemaBuilder.getEmployee(0);
		matrizDedicacao.addGrauDedicacao(employee0, task0, 0.43);
		matrizDedicacao.addGrauDedicacao(employee0, task1, 1.14);
		matrizDedicacao.addGrauDedicacao(employee0, task2, 0.29);
		matrizDedicacao.addGrauDedicacao(employee0, task3, 0.86);
		
		final Employee employee1 = problemaBuilder.getEmployee(1);
		matrizDedicacao.addGrauDedicacao(employee1, task0, 0.29);
		matrizDedicacao.addGrauDedicacao(employee1, task1, 0.0);
		matrizDedicacao.addGrauDedicacao(employee1, task2, 0.71);
		matrizDedicacao.addGrauDedicacao(employee1, task3, 0.57);
		
		matrizDedicacao.efetuaCalculosProjeto();
		
		final Individuo individuo = new Individuo(matrizDedicacao);
		individuo.verificaFactibilidade();
		assertFalse("Indivíduo não é uma solução factível", individuo.isFactivel());
		
		individuo.calculaValorFitness(buildParametrosPesos());
		assertEquals(Double.valueOf(0.00007415748), individuo.getValorFitness());
	}

	private ParametrosPesos buildParametrosPesos() {
		ParametrosPesos parametrosPesos = new ParametrosPesos();
		parametrosPesos.setPesoDuracaoProjeto(0.6);
		parametrosPesos.setPesoCustoProjeto(0.4);
		
		parametrosPesos.setPesoPenalidade(100.0);
		parametrosPesos.setPesoHabilidadesNecessarias(1.0);
		parametrosPesos.setPesoTrabalhoNaoRealizado(1.0);
		parametrosPesos.setPesoTrabalhoExtra(1.0);
		return parametrosPesos;
	}
	
}
