package uem.br.brkga.peps.main;

import uem.br.brkga.peps.genetico.AlgoritmoBRKGA;
import uem.br.brkga.peps.genetico.ParametrosAlgoritmo;
import uem.br.brkga.peps.genetico.ParametrosPesos;


public class Main {

	private static final Double PESO_CUSTO_PROJETO = 0.0000005;
	private static final Double PESO_DURACAO_PROJETO = 0.045;
	private static final Double PESO_PENALIDADE = 100.0;
	private static final Double PESO_TRABALHO_EXTRA = 1.0;
	private static final Double PESO_TRABALHO_NAO_REALIZADO = 1.0;
	private static final Double PESO_HABILIDADES_NECESSARIAS = 1.0;
	
//	private static final Double PESO_CUSTO_PROJETO = 0.000001;
//	private static final Double PESO_DURACAO_PROJETO = 0.1;
//	private static final Double PESO_PENALIDADE = 100.0;
//	private static final Double PESO_TRABALHO_NAO_REALIZADO = 10.0;
//	private static final Double PESO_HABILIDADES_NECESSARIAS = 10.0;
//	private static final Double PESO_TRABALHO_EXTRA = 0.1;
	
	public static void main(String[] args) {
//		RandomFactory.getInstance().setSeed(2);
		ParametrosPesos.getInstance().atribuiParametros(PESO_CUSTO_PROJETO, 
													    PESO_DURACAO_PROJETO, 
													    PESO_PENALIDADE, 
													    PESO_TRABALHO_NAO_REALIZADO, 
													    PESO_HABILIDADES_NECESSARIAS, 
													    PESO_TRABALHO_EXTRA);
		
		final AlgoritmoBRKGA algoritmoGenetico = new AlgoritmoBRKGA(getParametrosDefault());
		algoritmoGenetico.inicializaDadosProblema();
		algoritmoGenetico.executarAlgoritmo();
	}
	
	public static ParametrosAlgoritmo getParametrosDefault() {
		final ParametrosAlgoritmo parametrosAlgoritmo = new ParametrosAlgoritmo();
		parametrosAlgoritmo.setNumeroExecucoes(100);
		parametrosAlgoritmo.setNumeroGeracoes(200);
		parametrosAlgoritmo.setTamanhoPopulacao(64);
		parametrosAlgoritmo.setTamanhoGrupoElite(40.0);
		parametrosAlgoritmo.setTamanhoGrupoMutantes(30.0);
		parametrosAlgoritmo.setProbabilidadeHerancaElite(65.0);
//		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst10-5-10-5.conf");
//		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst10-10-10-5.conf");
//		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst10-15-10-5.conf");
		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst20-5-10-5.conf");
//		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst20-10-10-5.conf");
//		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst20-15-10-5.conf");
//		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst30-15-10-5.conf");
//		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst30-5-10-5.conf");
//		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst30-10d-10-5.conf");
//		parametrosAlgoritmo.setPathBenchmark("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst30-15-10-5.conf");
		return parametrosAlgoritmo;
	}
	
}
