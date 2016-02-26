package uem.br.brkga.peps.main;

import static java.util.Arrays.asList;

import java.io.File;

import uem.br.brkga.peps.genetico.AlgoritmoBRKGA;
import uem.br.brkga.peps.genetico.ParametrosAlgoritmo;
import uem.br.brkga.peps.genetico.ParametrosPesos;

public class BenchmarkSimpleTestMain {

	private static final Double PESO_CUSTO_PROJETO = 0.0000005;
	private static final Double PESO_DURACAO_PROJETO = 0.045;
	private static final Double PESO_PENALIDADE = 100.0;
	private static final Double PESO_TRABALHO_EXTRA = 1.0;
	private static final Double PESO_TRABALHO_NAO_REALIZADO = 1.0;
	private static final Double PESO_HABILIDADES_NECESSARIAS = 1.0;
	
	private static final int NUMERO_EXECUCOES = 10;
	
	public static void main(String[] args) {
		System.out.println("Iniciando AG-PEPS");
		
		ParametrosPesos.getInstance().atribuiParametros(PESO_CUSTO_PROJETO, 
													    PESO_DURACAO_PROJETO, 
													    PESO_PENALIDADE, 
													    PESO_TRABALHO_NAO_REALIZADO, 
													    PESO_HABILIDADES_NECESSARIAS, 
													    PESO_TRABALHO_EXTRA);
		
		for (String pathBenchmark: asList("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst10-5-10-5.conf",
										  "/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst20-10-10-5.conf",
										  "/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst30-15-10-5.conf")) {
			for (Integer numeroGeracoes : asList(50, 75, 100, 125, 150)) {
				for (Integer tamanhoPopulacao: asList(30, 50, 75, 100, 125)) {
					for (Double tamanhoGrupoElite: asList(10.0, 15.0, 20.0, 25.0)) {
						for (Double tamanhoGrupoMutantes: asList(5.0, 10.0, 15.0, 20.0)) {
							for (Double probabilidadeHerancaElite: asList(55.0, 65.0, 75.0, 85.0, 95.0)) {
								System.out.println("Iniciando execução: " + new File(pathBenchmark).getName() + "/"
										+ NUMERO_EXECUCOES + "/"
										+ numeroGeracoes + "/" 
										+ tamanhoPopulacao + "/"
										+ tamanhoGrupoElite + "/"
										+ tamanhoGrupoMutantes + "/"
										+ probabilidadeHerancaElite);
								
								final ParametrosAlgoritmo parametrosAlgoritmo = new ParametrosAlgoritmo();
								parametrosAlgoritmo.setNumeroExecucoes(NUMERO_EXECUCOES);
								parametrosAlgoritmo.setNumeroGeracoes(numeroGeracoes);
								parametrosAlgoritmo.setTamanhoPopulacao(tamanhoPopulacao);
								parametrosAlgoritmo.setTamanhoGrupoElite(tamanhoGrupoElite);
								parametrosAlgoritmo.setTamanhoGrupoMutantes(tamanhoGrupoMutantes);
								parametrosAlgoritmo.setProbabilidadeHerancaElite(probabilidadeHerancaElite);
								parametrosAlgoritmo.setPathBenchmark(pathBenchmark);
								
								final AlgoritmoBRKGA algoritmoGenetico = new AlgoritmoBRKGA(parametrosAlgoritmo);
								algoritmoGenetico.inicializaDadosProblema();
								algoritmoGenetico.executarAlgoritmo();
								
								System.out.println("Fim da execução");
								System.gc();
							}
						}
					}
				}
			}
		}
	}	
}
