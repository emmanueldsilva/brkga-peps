package uem.br.brkga.peps.tests;

import static org.junit.Assert.assertEquals;
import static uem.br.brkga.peps.genetico.TipoCodificacao.MATRIZ_EMPREGADO_ATUA_GRAU;

import org.junit.Before;
import org.junit.Test;

import uem.br.brkga.peps.entidade.Employee;
import uem.br.brkga.peps.entidade.Task;
import uem.br.brkga.peps.genetico.IndividuoCodificado;
import uem.br.brkga.peps.genetico.IndividuoCodificadoHelper;
import uem.br.brkga.peps.genetico.MatrizDedicacao;
import uem.br.brkga.peps.genetico.ParametrosPesos;
import uem.br.brkga.peps.problema.ProblemaBuilder;
import uem.br.brkga.peps.utils.RandomFactory;

public class IndividuoCodificadoTest {
	
	private static final Double PESO_CUSTO_PROJETO = 0.4;
	private static final Double PESO_DURACAO_PROJETO = 0.6;
	private static final Double PESO_PENALIDADE = 100.0;
	private static final Double PESO_TRABALHO_EXTRA = 1.0;
	private static final Double PESO_TRABALHO_NAO_REALIZADO = 1.0;
	private static final Double PESO_HABILIDADES_NECESSARIAS = 1.0;

	private final ProblemaBuilder problemaBuilder = ProblemaBuilder.getInstance();
	
	private final ParametrosPesos parametrosPesos = ParametrosPesos.getInstance();
	
	@Before
	public void before() {
		problemaBuilder.clear();
		problemaBuilder.setParametrosPath(System.getProperty("user.dir") + "/test-resources/test-intances/peps-4-tasks-2-employees.conf");
		problemaBuilder.readParametrosArquivo();
		
		RandomFactory.getInstance().setSeed(1);
		
		buildParametrosPesos();
	}
	
	@Test
	public void validaRecodificacaoMatrizEmpregadoAtuaGrau() {
		final IndividuoCodificado individuoCodificado = IndividuoCodificadoHelper.getInstance().newIndividuoCodificado(MATRIZ_EMPREGADO_ATUA_GRAU);
		individuoCodificado.codificar();
		
		final Double[] genes = individuoCodificado.getGenes();
		assertEquals(new Double(0.7308781907032909), new Double(genes[0]));
		assertEquals(new Double(0.4074398012118764), new Double(genes[1]));
		assertEquals(new Double(1.3327170559595112), new Double(genes[2]));
		assertEquals(new Double(0.7107396275716601), new Double(genes[3]));
		assertEquals(new Double(1.9637047970232078), new Double(genes[4]));
		assertEquals(new Double(1.554072319674416), new Double(genes[5]));
		assertEquals(new Double(0.9370821488959696), new Double(genes[6]));
		assertEquals(new Double(0.9139628760146802), new Double(genes[7]));
		
		individuoCodificado.decodificar();
		
		final Employee employee0 = problemaBuilder.getEmployee(0);
		final Task task0 = problemaBuilder.getTask(0);
		
		final Employee employee1 = problemaBuilder.getEmployee(1);
		final Task task3 = problemaBuilder.getTask(3);
		
		final MatrizDedicacao matrizDedicacao = individuoCodificado.getIndividuo().getMatrizDedicacao();
		matrizDedicacao.addGrauDedicacao(employee0, task0, 0.4286);
		matrizDedicacao.addGrauDedicacao(employee1, task3, 0.8571);
		
		individuoCodificado.recodificar();
		
		assertEquals(new Double(1.4286), new Double(genes[0]));
		assertEquals(new Double(0.4074398012118764), new Double(genes[1]));
		assertEquals(new Double(1.3327170559595112), new Double(genes[2]));
		assertEquals(new Double(0.7107396275716601), new Double(genes[3]));
		assertEquals(new Double(1.9637047970232078), new Double(genes[4]));
		assertEquals(new Double(1.554072319674416), new Double(genes[5]));
		assertEquals(new Double(0.9370821488959696), new Double(genes[6]));
		assertEquals(new Double(1.8571), new Double(genes[7]));
	}

	private void buildParametrosPesos() {
		parametrosPesos.atribuiParametros(PESO_CUSTO_PROJETO, 
										  PESO_DURACAO_PROJETO, 
										  PESO_PENALIDADE, 
										  PESO_TRABALHO_NAO_REALIZADO, 
										  PESO_HABILIDADES_NECESSARIAS, 
										  PESO_TRABALHO_EXTRA);
	}
	
}
