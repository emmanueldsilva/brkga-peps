package uem.br.ag.peps.genetico;

import uem.br.ag.peps.utils.RandomFactory;

public class Main {

	private static final Double PESO_CUSTO_PROJETO = 0.4;
	private static final Double PESO_DURACAO_PROJETO = 0.6;
	private static final Double PESO_PENALIDADE = 100.0;
	private static final Double PESO_TRABALHO_EXTRA = 1.0;
	private static final Double PESO_TRABALHO_NAO_REALIZADO = 1.0;
	private static final Double PESO_HABILIDADES_NECESSARIAS = 1.0;
	
	public static void main(String[] args) {
		final ParametrosAlgoritmo parametrosAlgoritmo = new ParametrosAlgoritmo();
		parametrosAlgoritmo.setNumeroExecucoes(1);
		parametrosAlgoritmo.setNumeroGeracoes(2);
		parametrosAlgoritmo.setTamanhoPopulacao(5);
		parametrosAlgoritmo.setPercentualCruzamento(50.0);
		parametrosAlgoritmo.setPercentualMutacao(50.0);
		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst10-5-10-5.conf");
		
		ParametrosPesos.getInstance().atribuiParametros(PESO_CUSTO_PROJETO, 
				  PESO_DURACAO_PROJETO, 
				  PESO_PENALIDADE, 
				  PESO_TRABALHO_NAO_REALIZADO, 
				  PESO_HABILIDADES_NECESSARIAS, 
				  PESO_TRABALHO_EXTRA);
		
		RandomFactory.getInstance().setSeed(10);
		
		final AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(parametrosAlgoritmo);
		algoritmoGenetico.inicializaDadosProblema();
		algoritmoGenetico.executarAlgoritmo();
	}
	
	
	
}
