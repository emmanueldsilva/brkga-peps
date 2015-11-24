package uem.br.ag.peps.genetico;


public class Main {

	private static final Double PESO_CUSTO_PROJETO = 0.0000005;
	private static final Double PESO_DURACAO_PROJETO = 0.045;
	private static final Double PESO_PENALIDADE = 100.0;
	private static final Double PESO_TRABALHO_EXTRA = 1.0;
	private static final Double PESO_TRABALHO_NAO_REALIZADO = 1.0;
	private static final Double PESO_HABILIDADES_NECESSARIAS = 1.0;
	
	public static void main(String[] args) {
		final ParametrosAlgoritmo parametrosAlgoritmo = new ParametrosAlgoritmo();
		parametrosAlgoritmo.setNumeroExecucoes(1);
		parametrosAlgoritmo.setNumeroGeracoes(100);
		parametrosAlgoritmo.setTamanhoPopulacao(20);
		parametrosAlgoritmo.setPercentualCruzamento(20.0);
		parametrosAlgoritmo.setPercentualMutacao(1.0);
//		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst10-5-10-5.conf");
//		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst20-10-10-5.conf");
		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst30-15-10-5.conf");
		
		ParametrosPesos.getInstance().atribuiParametros(PESO_CUSTO_PROJETO, 
				  PESO_DURACAO_PROJETO, 
				  PESO_PENALIDADE, 
				  PESO_TRABALHO_NAO_REALIZADO, 
				  PESO_HABILIDADES_NECESSARIAS, 
				  PESO_TRABALHO_EXTRA);
		
		final AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(parametrosAlgoritmo);
		algoritmoGenetico.inicializaDadosProblema();
		algoritmoGenetico.executarAlgoritmo();
	}
	
	
	
}
