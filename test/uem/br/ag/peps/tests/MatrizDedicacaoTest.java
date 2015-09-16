package uem.br.ag.peps.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uem.br.ag.peps.entidade.Employee;
import uem.br.ag.peps.entidade.Task;
import uem.br.ag.peps.genetico.GrauDedicacao;
import uem.br.ag.peps.genetico.MatrizDedicacao;
import uem.br.ag.peps.problema.ProblemaBuilder;

public class MatrizDedicacaoTest {

	@Before
	public void before() {
		final ProblemaBuilder problemaBuilder = ProblemaBuilder.getInstance();
		problemaBuilder.clear();
		problemaBuilder.setParametrosPath("/home/emmanuel/projetos/ag-peps/test-resources/test-intances/peps-4-tasks-2-employees.conf");
		problemaBuilder.readParametrosArquivo();
	}
	
	@Test
	public void testMontagemMatrizDedicacao() {
		final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
		final GrauDedicacao[][] matriz = matrizDedicacao.getMatrizDedicacao();
		Assert.assertEquals(2, matriz.length);
		Assert.assertEquals(4, matriz[0].length);
		
		final Task task0 = ProblemaBuilder.getInstance().getTask(0);
		final Task task1 = ProblemaBuilder.getInstance().getTask(1);
		final Task task2 = ProblemaBuilder.getInstance().getTask(2);
		final Task task3 = ProblemaBuilder.getInstance().getTask(3);
		
		final Employee employee0 = ProblemaBuilder.getInstance().getEmployee(0);
		matrizDedicacao.addGrauDedicacao(employee0, task0, 0.4);
		matrizDedicacao.addGrauDedicacao(employee0, task1, 1.0);
		matrizDedicacao.addGrauDedicacao(employee0, task2, 0.3);
		matrizDedicacao.addGrauDedicacao(employee0, task3, 0.7);
		
		final Employee employee1 = ProblemaBuilder.getInstance().getEmployee(1);
		matrizDedicacao.addGrauDedicacao(employee1, task0, 0.6);
		matrizDedicacao.addGrauDedicacao(employee1, task1, 0.0);
		matrizDedicacao.addGrauDedicacao(employee1, task2, 0.7);
		matrizDedicacao.addGrauDedicacao(employee1, task3, 0.3);
		
		Assert.assertEquals(2, matriz.length);
		Assert.assertEquals(4, matriz[0].length);
		
		Assert.assertEquals(Double.valueOf(1.0), matrizDedicacao.getGrauDedicacao(employee0, task1).getValor());
		Assert.assertEquals(Double.valueOf(0.7), matrizDedicacao.getGrauDedicacao(employee0, task3).getValor());
		Assert.assertEquals(Double.valueOf(0.6), matrizDedicacao.getGrauDedicacao(employee1, task0).getValor());
		Assert.assertEquals(Double.valueOf(0.7), matrizDedicacao.getGrauDedicacao(employee1, task2).getValor());
	}
	
	@Test
	public void test() {
		final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
		final GrauDedicacao[][] matriz = matrizDedicacao.getMatrizDedicacao();
		Assert.assertEquals(2, matriz.length);
		Assert.assertEquals(4, matriz[0].length);
		
		final Task task0 = ProblemaBuilder.getInstance().getTask(0);
		final Task task1 = ProblemaBuilder.getInstance().getTask(1);
		final Task task2 = ProblemaBuilder.getInstance().getTask(2);
		final Task task3 = ProblemaBuilder.getInstance().getTask(3);
		
		final Employee employee0 = ProblemaBuilder.getInstance().getEmployee(0);
		matrizDedicacao.addGrauDedicacao(employee0, task0, 0.4);
		matrizDedicacao.addGrauDedicacao(employee0, task1, 1.0);
		matrizDedicacao.addGrauDedicacao(employee0, task2, 0.3);
		matrizDedicacao.addGrauDedicacao(employee0, task3, 0.8);
		
		final Employee employee1 = ProblemaBuilder.getInstance().getEmployee(1);
		matrizDedicacao.addGrauDedicacao(employee1, task0, 0.2);
		matrizDedicacao.addGrauDedicacao(employee1, task1, 0.0);
		matrizDedicacao.addGrauDedicacao(employee1, task2, 0.7);
		matrizDedicacao.addGrauDedicacao(employee1, task3, 0.6);
		
		Assert.assertEquals(Double.valueOf(0.6), Double.valueOf(matrizDedicacao.calculaSomatorioDedicacaoTask(task0).doubleValue()));
		Assert.assertEquals(Double.valueOf(1.0), Double.valueOf(matrizDedicacao.calculaSomatorioDedicacaoTask(task1).doubleValue()));
		Assert.assertEquals(Double.valueOf(1.0), Double.valueOf(matrizDedicacao.calculaSomatorioDedicacaoTask(task2).doubleValue()));
		Assert.assertEquals(Double.valueOf(1.4), Double.valueOf(matrizDedicacao.calculaSomatorioDedicacaoTask(task3).doubleValue()));
	}
	
}
