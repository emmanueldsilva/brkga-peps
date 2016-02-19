package uem.br.brkga.peps.genetico;

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
	
	public AlgoritmoBRKGA(ParametrosAlgoritmo parametrosAlgoritmo) {
		this.parametrosAlgoritmo = parametrosAlgoritmo;
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
             
            final Populacao populacao = new Populacao(parametrosAlgoritmo.getTamanhoPopulacao());
            populacao.gerarIndividuos();
            
            for (int i = 0; i < parametrosAlgoritmo.getNumeroGeracoes(); i++) {
				populacao.avaliarIndividuos();
				populacao.selecionarMaisAptos();
				printFactory.geraEstatisticas(populacao, i);
				
				populacao.efetuarCruzamento(parametrosAlgoritmo.getTamanhoGrupoCombinatorio(), parametrosAlgoritmo.getProbabilidadeHerancaElite());
				populacao.gerarMutantes(parametrosAlgoritmo.getTamanhoGrupoMutantes());
            }

            populacao.avaliarIndividuos();
            populacao.selecionarMaisAptos();

            final Individuo melhorIndividuo = populacao.getMelhorIndividuo();
            
            printFactory.geraEstatisticas(populacao, parametrosAlgoritmo.getNumeroGeracoes());
			printFactory.printIndividuo(melhorIndividuo);
            populacao.getIndividuos().sort((i1, i2) -> i1.getValorFitness().compareTo(i2.getValorFitness()));
            
            if (melhorIndividuo.isFactivel()) {
            	hitRate++;
            	melhoresFitness.add(melhorIndividuo.getValorFitness());
            	melhoresCustosProjeto.add(populacao.getMenorValorCustoProjeto());
            	melhoresDuracaoProjeto.add(populacao.getMenorDuracaoProjeto());
            }
             
            printFactory.printEstatisticaExecucao(calculaTempoExecucao(start), parametrosAlgoritmo);
            printFactory.plotaGraficos(populacao);
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
	
}

