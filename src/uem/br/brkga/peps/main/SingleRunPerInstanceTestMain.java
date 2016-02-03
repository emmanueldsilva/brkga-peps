package uem.br.brkga.peps.main;

import static java.util.Arrays.asList;

import java.io.File;

import uem.br.brkga.peps.genetico.AlgoritmoGenetico;
import uem.br.brkga.peps.genetico.ParametrosAlgoritmo;
import uem.br.brkga.peps.genetico.ParametrosPesos;

public class SingleRunPerInstanceTestMain {

	private static final Double PESO_CUSTO_PROJETO = 0.000001;
	private static final Double PESO_DURACAO_PROJETO = 0.1;
	private static final Double PESO_PENALIDADE = 100.0;
	private static final Double PESO_TRABALHO_NAO_REALIZADO = 10.0;
	private static final Double PESO_HABILIDADES_NECESSARIAS = 10.0;
	private static final Double PESO_TRABALHO_EXTRA = 0.1;
	
	public static void main(String[] args) {
		System.out.println("Iniciando AG-PEPS");
		
		ParametrosPesos.getInstance().atribuiParametros(PESO_CUSTO_PROJETO, 
													    PESO_DURACAO_PROJETO, 
													    PESO_PENALIDADE, 
													    PESO_TRABALHO_NAO_REALIZADO, 
													    PESO_HABILIDADES_NECESSARIAS, 
													    PESO_TRABALHO_EXTRA);
		
		for (String pathBenchmark: asList("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst10-10-10-5.conf",
										  "/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst10-15-10-5.conf",
										  "/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst10-5-10-5.conf",
										  "/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst20-10-10-5.conf",
										  "/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst20-15-10-5.conf",
										  "/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst20-5-10-5.conf",
										  "/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst30-10-10-5.conf",
										  "/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst30-15-10-5.conf",
										  "/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst30-5-10-5.conf")) {
			System.out.println("Iniciando execução: " + new File(pathBenchmark).getName());
			
			final AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(getParametrosDefault(pathBenchmark));
			algoritmoGenetico.inicializaDadosProblema();
			algoritmoGenetico.executarAlgoritmo();
			
			System.out.println("Fim da execução");
			System.gc();
		}
	}
	
	public static ParametrosAlgoritmo getParametrosDefault(String pathBenchmark) {
		final ParametrosAlgoritmo parametrosAlgoritmo = new ParametrosAlgoritmo();
		parametrosAlgoritmo.setNumeroExecucoes(100);
		parametrosAlgoritmo.setNumeroGeracoes(2000);
		parametrosAlgoritmo.setTamanhoPopulacao(64);
		parametrosAlgoritmo.setPercentualCruzamento(3.0);
		parametrosAlgoritmo.setPercentualMutacao(1.0);
		parametrosAlgoritmo.setPathBenchmark(pathBenchmark);
		return parametrosAlgoritmo;
	}
	
	
}
