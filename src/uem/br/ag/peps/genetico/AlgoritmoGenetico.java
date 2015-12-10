package uem.br.ag.peps.genetico;

import static java.lang.Math.round;

import java.util.List;

import uem.br.ag.peps.problema.ProblemaBuilder;
import uem.br.ag.peps.utils.PrintFactory;

import com.google.common.collect.Lists;

public class AlgoritmoGenetico {
	
	private List<Individuo> populacao = Lists.newArrayList();
	
	private ParametrosAlgoritmo parametrosAlgoritmo;
	
	public AlgoritmoGenetico(ParametrosAlgoritmo parametrosAlgoritmo) {
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
				populacao.selecionarMaisAptosPorTorneio();
				printFactory.geraEstatisticas(populacao, i);
				populacao.efetuarCruzamento(parametrosAlgoritmo.getPercentualCruzamento());
				populacao.efetuarMutacao(parametrosAlgoritmo.getPercentualMutacao());
            }

            populacao.avaliarIndividuos();
            populacao.selecionarMaisAptosPorTorneio();

            printFactory.geraEstatisticas(populacao, parametrosAlgoritmo.getNumeroGeracoes());
            printFactory.printIndividuo(populacao.getMelhorIndividuo());
            populacao.getIndividuos().sort((i1, i2) -> i1.getValorFitness().compareTo(i2.getValorFitness()));
             
            printFactory.printEstatisticaExecucao(calculaTempoExecucao(start), parametrosAlgoritmo);
            printFactory.plotaGraficos(populacao);
        }
    }

	private Double calculaTempoExecucao(long start) {
		long delay = System.currentTimeMillis() - start;  
		return round((delay/1000) * 10000000)/10000000.0;
	}

	public List<Individuo> getPopulacao() {
		return populacao;
	}

	public ParametrosAlgoritmo getParametrosAlgoritmo() {
		return parametrosAlgoritmo;
	}
	
}
