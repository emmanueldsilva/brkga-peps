package uem.br.brkga.peps.main;

import static java.util.Arrays.asList;
import static uem.br.brkga.peps.genetico.TipoCodificacao.MATRIZ_EMPREGADO_ATUA_GRAU;

import java.io.File;

import uem.br.brkga.peps.genetico.AlgoritmoBRKGA;
import uem.br.brkga.peps.genetico.ParametrosAlgoritmo;
import uem.br.brkga.peps.genetico.ParametrosPesos;

public class BenchmarkTestMain {

	private static final Double PESO_CUSTO_PROJETO = 0.000001;
	private static final Double PESO_DURACAO_PROJETO = 0.1;
	private static final Double PESO_PENALIDADE = 100.0;
	private static final Double PESO_TRABALHO_NAO_REALIZADO = 10.0;
	private static final Double PESO_HABILIDADES_NECESSARIAS = 10.0;
	private static final Double PESO_TRABALHO_EXTRA = 0.1;
	
	private static final int NUMERO_EXECUCOES = 15;
	
	public static void main(String[] args) {
		System.out.println("Iniciando BRKGA-PEPS");
		
		ParametrosPesos.getInstance().atribuiParametros(PESO_CUSTO_PROJETO, 
													    PESO_DURACAO_PROJETO, 
													    PESO_PENALIDADE, 
													    PESO_TRABALHO_NAO_REALIZADO, 
													    PESO_HABILIDADES_NECESSARIAS, 
													    PESO_TRABALHO_EXTRA);
		
		for (String pathBenchmark: asList(
										  System.getProperty("user.dir") + "/resources/problem-generator/inst10-5-10-5.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst10-10-10-5.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst10-15-10-5.conf"
//										  System.getProperty("user.dir") + "/resources/problem-generator/inst20-5-10-5.conf",
//										  System.getProperty("user.dir") + "/resources/problem-generator/inst20-10-10-5.conf",
//										  System.getProperty("user.dir") + "/resources/problem-generator/inst20-15-10-5.conf",
//										  System.getProperty("user.dir") + "/resources/problem-generator/inst30-5-10-5.conf",
//										  System.getProperty("user.dir") + "/resources/problem-generator/inst30-10-10-5.conf",
//										  System.getProperty("user.dir") + "/resources/problem-generator/inst30-15-10-5.conf"
										  )) {
			for (Integer numeroGeracoes : asList(1300)) {
				for (Integer tamanhoPopulacao: asList(256)) {
					for (Double tamanhoGrupoElite: asList(30.0)) {
						for (Double probabilidadeHerancaElite: asList(70.0)) {
						for (Double tamanhoGrupoMutantes: asList(10.0, 20.0)) {
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
								parametrosAlgoritmo.setTipoCodificacao(MATRIZ_EMPREGADO_ATUA_GRAU);
	
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
		
		
//		
//		for (String pathBenchmark: asList(System.getProperty("user.dir") + "/resources/problem-generator/inst10-5-10-5.conf",
//				System.getProperty("user.dir") + "/resources/problem-generator/inst10-10-10-5.conf",
//				System.getProperty("user.dir") + "/resources/problem-generator/inst10-15-10-5.conf",
//				System.getProperty("user.dir") + "/resources/problem-generator/inst20-5-10-5.conf",
//				System.getProperty("user.dir") + "/resources/problem-generator/inst20-10-10-5.conf",
//				System.getProperty("user.dir") + "/resources/problem-generator/inst20-15-10-5.conf",
//				System.getProperty("user.dir") + "/resources/problem-generator/inst30-5-10-5.conf",
//				System.getProperty("user.dir") + "/resources/problem-generator/inst30-10-10-5.conf",
//				System.getProperty("user.dir") + "/resources/problem-generator/inst30-15-10-5.conf")) {
//			for (Integer numeroGeracoes : asList(200)) {
//				for (Integer tamanhoPopulacao: asList(64)) {
//					for (Double tamanhoGrupoElite: asList(35.0)) {
//						for (Double probabilidadeHerancaElite: asList(95.0)) {
//							for (Double tamanhoGrupoMutantes: asList(20.0)) {
//								System.out.println("Iniciando execução: " + new File(pathBenchmark).getName() + "/"
//										+ NUMERO_EXECUCOES + "/"
//										+ numeroGeracoes + "/" 
//										+ tamanhoPopulacao + "/"
//										+ tamanhoGrupoElite + "/"
//										+ tamanhoGrupoMutantes + "/"
//										+ probabilidadeHerancaElite);
//								
//								final ParametrosAlgoritmo parametrosAlgoritmo = new ParametrosAlgoritmo();
//								parametrosAlgoritmo.setNumeroExecucoes(NUMERO_EXECUCOES);
//								parametrosAlgoritmo.setNumeroGeracoes(numeroGeracoes);
//								parametrosAlgoritmo.setTamanhoPopulacao(tamanhoPopulacao);
//								parametrosAlgoritmo.setTamanhoGrupoElite(tamanhoGrupoElite);
//								parametrosAlgoritmo.setTamanhoGrupoMutantes(tamanhoGrupoMutantes);
//								parametrosAlgoritmo.setProbabilidadeHerancaElite(probabilidadeHerancaElite);
//								parametrosAlgoritmo.setPathBenchmark(pathBenchmark);
//								
//								final AlgoritmoBRKGA algoritmoGenetico = new AlgoritmoBRKGA(parametrosAlgoritmo);
//								algoritmoGenetico.inicializaDadosProblema();
//								algoritmoGenetico.executarAlgoritmo();
//								
//								System.out.println("Fim da execução");
//								System.gc();
//							}
//						}
//					}
//				}
//			}
//		}
	}
	
}
