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
            long start = System.currentTimeMillis();  
             
            Populacao populacao = new Populacao(parametrosAlgoritmo.getTamanhoPopulacao());
            populacao.gerarIndividuos();
            for (int i = 0; i < parametrosAlgoritmo.getNumeroGeracoes(); i++) {
              System.out.println("avaliar população");
                populacao.avaliarIndividuos();
//                populacao.imprimirPopulacao();
                
              System.out.println("selecionar mais aptos");
                populacao.selecionarMaisAptosPorTorneio();
                PrintFactory.imprimeEstatisticas(populacao);
//                populacao.imprimirPopulacao();

              System.out.println("efetuar cruzamento");
                populacao.efetuarCruzamento(parametrosAlgoritmo.getPercentualCruzamento());
//                populacao.imprimirPopulacao();
                 
              System.out.println("efetuar mutação");
                populacao.efetuarMutacao(parametrosAlgoritmo.getPercentualMutacao());
//                populacao.imprimirPopulacao();
            }

            System.out.println("avaliar população");
            populacao.avaliarIndividuos();
//            populacao.imprimirPopulacao();
            
            System.out.println("selecionar mais aptos");
            populacao.selecionarMaisAptosPorTorneio();
//            populacao.imprimirPopulacao();
            PrintFactory.imprimeEstatisticas(populacao);
            populacao.getIndividuos().sort((i1, i2) -> i1.getValorFitness().compareTo(i2.getValorFitness()));
             
            long delay = System.currentTimeMillis() - start;  
//            populacao.imprimirPopulacao();
            System.out.println("Demorou " + round((delay/1000) * 10000000)/10000000.0 + " segundos");  
        }
    }

	public List<Individuo> getPopulacao() {
		return populacao;
	}

	public ParametrosAlgoritmo getParametrosAlgoritmo() {
		return parametrosAlgoritmo;
	}

}
