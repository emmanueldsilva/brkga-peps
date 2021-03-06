package uem.br.brkga.peps.tests;

import static uem.br.brkga.peps.genetico.TipoCodificacao.MATRIZ_DEDICACAO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uem.br.brkga.peps.genetico.IndividuoCodificado;
import uem.br.brkga.peps.genetico.IndividuoCodificadoHelper;
import uem.br.brkga.peps.genetico.ParametrosAlgoritmo;
import uem.br.brkga.peps.genetico.Populacao;
import uem.br.brkga.peps.problema.ProblemaBuilder;
import uem.br.brkga.peps.utils.RandomFactory;

public class PopulacaoTest {

	private final ProblemaBuilder problemaBuilder = ProblemaBuilder.getInstance();
	
	@Before
	public void before() {
		problemaBuilder.clear();
		problemaBuilder.setParametrosPath(System.getProperty("user.dir") + "/test-resources/test-intances/peps-4-tasks-2-employees.conf");
		problemaBuilder.readParametrosArquivo();
	}
	
	@Test
	public void testCruzamentoMatrizesDedicacao() {
		final ParametrosAlgoritmo parametrosAlgoritmo = new ParametrosAlgoritmo();
		parametrosAlgoritmo.setTamanhoPopulacao(2);
		parametrosAlgoritmo.setTamanhoGrupoElite(50.0);
		parametrosAlgoritmo.setTamanhoGrupoMutantes(50.0);
		parametrosAlgoritmo.setProbabilidadeHerancaElite(60.0);
		parametrosAlgoritmo.setTipoCodificacao(MATRIZ_DEDICACAO);
		
		RandomFactory.getInstance().setSeed(1);
		final Populacao populacao = new Populacao(parametrosAlgoritmo);
		
		final IndividuoCodificado paiElite = IndividuoCodificadoHelper.getInstance().newIndividuoCodificado(parametrosAlgoritmo.getTipoCodificacao(), buildGenesCodificados1());
		populacao.addIndividuo(paiElite);
		
		final IndividuoCodificado paiOutro = IndividuoCodificadoHelper.getInstance().newIndividuoCodificado(parametrosAlgoritmo.getTipoCodificacao(), buildGenesCodificados2());
		populacao.addIndividuo(paiOutro);
		
		final IndividuoCodificado novoFilho = populacao.efetuaCrossover(paiElite, paiOutro, parametrosAlgoritmo.getProbabilidadeHerancaElite());
		
		Assert.assertEquals(novoFilho.getValor(0), new Double(0.9));
		Assert.assertEquals(novoFilho.getValor(1), new Double(0.8));
		Assert.assertEquals(novoFilho.getValor(2), new Double(0.6));
		Assert.assertEquals(novoFilho.getValor(3), new Double(0.8));
		Assert.assertEquals(novoFilho.getValor(4), new Double(1.0));
		Assert.assertEquals(novoFilho.getValor(5), new Double(0.0));
		Assert.assertEquals(novoFilho.getValor(6), new Double(0.2));
		Assert.assertEquals(novoFilho.getValor(7), new Double(0.4));
	}

	private Double[] buildGenesCodificados1() {
		return new Double[]{0.2, 0.4, 0.6, 0.8, 1.0, 0.0, 0.2, 0.4};
	}
	
	private Double[] buildGenesCodificados2() {
		return new Double[]{0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2};
	}

}
