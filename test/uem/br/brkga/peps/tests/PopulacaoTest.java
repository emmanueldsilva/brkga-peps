package uem.br.brkga.peps.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uem.br.brkga.peps.genetico.IndividuoCodificado;
import uem.br.brkga.peps.genetico.ParametrosAlgoritmo;
import uem.br.brkga.peps.genetico.Populacao;
import uem.br.brkga.peps.problema.ProblemaBuilder;
import uem.br.brkga.peps.utils.RandomFactory;

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
		final ParametrosAlgoritmo parametrosAlgoritmo = new ParametrosAlgoritmo();
		parametrosAlgoritmo.setTamanhoPopulacao(2);
		parametrosAlgoritmo.setTamanhoGrupoElite(50.0);
		parametrosAlgoritmo.setTamanhoGrupoMutantes(50.0);
		parametrosAlgoritmo.setProbabilidadeHerancaElite(60.0);
		
		RandomFactory.getInstance().setSeed(1);
		final Populacao populacao = new Populacao(parametrosAlgoritmo);
		
		final IndividuoCodificado paiElite = new IndividuoCodificado(buildGenesCodificados1());
		populacao.addIndividuo(paiElite);
		
		final IndividuoCodificado paiOutro = new IndividuoCodificado(buildGenesCodificados2());
		populacao.addIndividuo(paiOutro);
		
		final IndividuoCodificado novoFilho = populacao.efetuaCrossover(paiElite, paiOutro, parametrosAlgoritmo.getProbabilidadeHerancaElite());
		
		Assert.assertEquals(novoFilho.getValor(0, 0), new Double(0.9));
		Assert.assertEquals(novoFilho.getValor(0, 1), new Double(0.4));
		Assert.assertEquals(novoFilho.getValor(0, 2), new Double(0.6));
		Assert.assertEquals(novoFilho.getValor(0, 3), new Double(0.8));
		Assert.assertEquals(novoFilho.getValor(1, 0), new Double(0.4));
		Assert.assertEquals(novoFilho.getValor(1, 1), new Double(0.3));
		Assert.assertEquals(novoFilho.getValor(1, 2), new Double(0.2));
		Assert.assertEquals(novoFilho.getValor(1, 3), new Double(0.1));
	}

	private Double[][] buildGenesCodificados1() {
		return new Double[][]{{0.2, 0.4, 0.6, 0.8},
							  {0.1, 0.3, 0.5, 0.7}};
	}
	
	private Double[][] buildGenesCodificados2() {
		return new Double[][]{{0.9, 0.8, 0.7, 0.6},
							  {0.4, 0.3, 0.2, 0.1}};
	}

}
