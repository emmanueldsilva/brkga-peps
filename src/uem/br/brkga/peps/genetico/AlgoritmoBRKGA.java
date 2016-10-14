package uem.br.brkga.peps.genetico;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import uem.br.brkga.peps.problema.ProblemaBuilder;
import uem.br.brkga.peps.utils.PrintFactory;
import uem.br.brkga.peps.utils.StatisticsPrintFactory;

import com.google.common.collect.Lists;

public class AlgoritmoBRKGA {
	
	private List<Individuo> populacao = Lists.newArrayList();
	
	private ParametrosAlgoritmo parametrosAlgoritmo;
	
	private Integer hitRate = 0;
	
	private List<Double> melhoresFitness = Lists.newArrayList();
	
	private List<Double> melhoresCustosProjeto = Lists.newArrayList();
	
	private List<Double> melhoresDuracaoProjeto = Lists.newArrayList();
	
	private DadosExecucao dadosExecucao;
	
	public AlgoritmoBRKGA(ParametrosAlgoritmo parametrosAlgoritmo) {
		this.parametrosAlgoritmo = parametrosAlgoritmo;
		this.dadosExecucao = new DadosExecucao(new File(parametrosAlgoritmo.getPathBenchmark()).getName());
	}
	
    public void inicializaDadosProblema() {
    	ProblemaBuilder.getInstance().clear();
    	ProblemaBuilder.getInstance().setParametrosPath(parametrosAlgoritmo.getPathBenchmark());
    	ProblemaBuilder.getInstance().readParametrosArquivo();
    }

    public void executarAlgoritmo() {
        for (int cont = 0; cont < parametrosAlgoritmo.getNumeroExecucoes(); cont++) {
        	final PrintFactory printFactory = new PrintFactory(parametrosAlgoritmo, cont + 1);
            long start = System.currentTimeMillis();  
            
            Populacao populacao = new Populacao(parametrosAlgoritmo);
            Populacao novaPopulacao = null;
            populacao.gerarIndividuos();
            
            for (int i = 0; i < parametrosAlgoritmo.getNumeroGeracoes(); i++) {
				populacao.avaliarIndividuos();
				populacao.aplicarBuscaLocal();
				populacao.ordenarIndividuos();

				printFactory.geraEstatisticas(populacao, i);
				
				dadosExecucao.acumularFitness(i, populacao.getMaiorValorFitness());
				
				novaPopulacao = new Populacao(parametrosAlgoritmo);
				novaPopulacao.addIndividuos(populacao.selecionarIndividuosMaisAptos());
				novaPopulacao.gerarMutantes();
				novaPopulacao.efetuarCruzamento(parametrosAlgoritmo.getProbabilidadeHerancaElite());
				
				populacao = novaPopulacao;
            }

            populacao.avaliarIndividuos();
            populacao.aplicarBuscaLocal();
            populacao.ordenarIndividuos();
            populacao.selecionarIndividuosMaisAptos();
            printFactory.geraEstatisticas(populacao, parametrosAlgoritmo.getNumeroGeracoes());
            
            dadosExecucao.acumularFitness(parametrosAlgoritmo.getNumeroGeracoes(), populacao.getMaiorValorFitness());

            final IndividuoCodificado melhorIndividuo = populacao.getMelhorIndividuo();
			printFactory.printIndividuo(melhorIndividuo.getIndividuo());
            dadosExecucao.addMelhorIndividuo(melhorIndividuo);
			
            if (melhorIndividuo.isFactivel()) {
            	hitRate++;
            	melhoresFitness.add(melhorIndividuo.getValorFitness());
            	melhoresCustosProjeto.add(melhorIndividuo.getCustoTotalProjeto());
            	melhoresDuracaoProjeto.add(melhorIndividuo.getDuracaoTotalProjeto());
            }
             
            printFactory.printEstatisticaExecucao(calculaTempoExecucao(start), parametrosAlgoritmo);
            printFactory.plotaGraficos(novaPopulacao);
        }
        
        final StatisticsPrintFactory statisticsPrintFactory = new StatisticsPrintFactory(parametrosAlgoritmo, hitRate, melhoresFitness, melhoresCustosProjeto, melhoresDuracaoProjeto);
        statisticsPrintFactory.printEstatisticasExecucoes();
    }

	private Double calculaTempoExecucao(long start) {
		BigDecimal delay = new BigDecimal(System.currentTimeMillis() - start);
		delay = delay.divide(new BigDecimal(1000), MathContext.DECIMAL32);
		delay = delay.multiply(new BigDecimal(10000000));
		delay = delay.divide(new BigDecimal(10000000), MathContext.DECIMAL32);
		
		return delay.doubleValue();
	}

	public List<Individuo> getPopulacao() {
		return populacao;
	}

	public ParametrosAlgoritmo getParametrosAlgoritmo() {
		return parametrosAlgoritmo;
	}
	
	public DadosExecucao getDadosExecucao() {
		return dadosExecucao;
	}
}

