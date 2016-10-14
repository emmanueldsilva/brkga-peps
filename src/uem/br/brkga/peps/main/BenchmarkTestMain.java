package uem.br.brkga.peps.main;

import static java.util.Arrays.asList;
import static uem.br.brkga.peps.genetico.TipoCodificacao.MATRIZ_EMPREGADO_ATUA_GRAU;

import java.io.File;
import java.io.IOException;

import uem.br.brkga.peps.genetico.AlgoritmoBRKGA;
import uem.br.brkga.peps.genetico.ParametrosAlgoritmo;
import uem.br.brkga.peps.genetico.ParametrosPesos;
import uem.br.brkga.peps.utils.GraficoFactory;

public class BenchmarkTestMain {

	private static final Double PESO_CUSTO_PROJETO = 0.000001;
	private static final Double PESO_DURACAO_PROJETO = 0.1;
	private static final Double PESO_PENALIDADE = 100.0;
	private static final Double PESO_TRABALHO_NAO_REALIZADO = 10.0;
	private static final Double PESO_HABILIDADES_NECESSARIAS = 10.0;
	private static final Double PESO_TRABALHO_EXTRA = 0.1;
	
	private static final int NUMERO_EXECUCOES = 5;
	private static final int NUMERO_GERACOES = 10;
	private static final int TAMANHO_POPULACAO = 256;
	private static final Double TAMANHO_GRUPO_ELITE = 30.0;
	private static final Double PROBABILIDADE_HERANCA_ELITE = 70.0;
	private static final Double TAMANHO_GRUPO_MUTANTES = 20.0;
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("Iniciando BRKGA-PEPS");
		
		ParametrosPesos.getInstance().atribuiParametros(PESO_CUSTO_PROJETO, 
													    PESO_DURACAO_PROJETO, 
													    PESO_PENALIDADE, 
													    PESO_TRABALHO_NAO_REALIZADO, 
													    PESO_HABILIDADES_NECESSARIAS, 
													    PESO_TRABALHO_EXTRA);
		GraficoFactory graficoFactory = new GraficoFactory(NUMERO_EXECUCOES);
		
		for (String pathBenchmark: asList(System.getProperty("user.dir") + "/resources/problem-generator/inst10-5-10-5.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst10-10-10-5.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst10-15-10-5.conf"
										  )) {
			rodaAlgoritmoComParametros(graficoFactory, pathBenchmark);
		}
		graficoFactory.buildGraficoFitness("fitness_10tasks_4_5skills");
		graficoFactory.zeraFitnessSeriesCollection();
		
		for (String pathBenchmark: asList(System.getProperty("user.dir") + "/resources/problem-generator/inst20-5-10-5.conf",
				  						  System.getProperty("user.dir") + "/resources/problem-generator/inst20-10-10-5.conf",
				  						  System.getProperty("user.dir") + "/resources/problem-generator/inst20-15-10-5.conf"
										  )) {
			rodaAlgoritmoComParametros(graficoFactory, pathBenchmark);
		}
		graficoFactory.buildGraficoFitness("fitness_20tasks_4_5skills");
		graficoFactory.zeraFitnessSeriesCollection();
		
		for (String pathBenchmark: asList(System.getProperty("user.dir") + "/resources/problem-generator/inst30-5-10-5.conf",
				  						  System.getProperty("user.dir") + "/resources/problem-generator/inst30-10-10-5.conf",
				  						  System.getProperty("user.dir") + "/resources/problem-generator/inst30-15-10-5.conf"
				  						  )) {
			rodaAlgoritmoComParametros(graficoFactory, pathBenchmark);
		}
		graficoFactory.buildGraficoFitness("fitness_30tasks_4_5skills");
		graficoFactory.zeraFitnessSeriesCollection();
		graficoFactory.buildGraficoCustoDuracao("custo_duracao_4_5skills");
		graficoFactory.zeraCustoDuracaoSeriesCollection();
		
		
		for (String pathBenchmark: asList(System.getProperty("user.dir") + "/resources/problem-generator/inst10-5-10-7.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst10-10-10-7.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst10-15-10-7.conf"
				  						  )) {
			rodaAlgoritmoComParametros(graficoFactory, pathBenchmark);
		}
		graficoFactory.buildGraficoFitness("fitness_10tasks_6_7skills");
		graficoFactory.zeraFitnessSeriesCollection();
		
		for (String pathBenchmark: asList(System.getProperty("user.dir") + "/resources/problem-generator/inst20-5-10-7.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst20-10-10-7.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst20-15-10-7.conf"
										  )) {
			rodaAlgoritmoComParametros(graficoFactory, pathBenchmark);
		}
		graficoFactory.buildGraficoFitness("fitness_20tasks_6_7skills");
		graficoFactory.zeraFitnessSeriesCollection();
		
		for (String pathBenchmark: asList(System.getProperty("user.dir") + "/resources/problem-generator/inst30-5-10-7.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst30-10-10-7.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst30-15-10-7.conf"
										  )) {
			rodaAlgoritmoComParametros(graficoFactory, pathBenchmark);
		}
		graficoFactory.buildGraficoFitness("fitness_30tasks_6_7skills");
		graficoFactory.zeraFitnessSeriesCollection();
		graficoFactory.buildGraficoCustoDuracao("custo_duracao_6_7skills");
		graficoFactory.zeraCustoDuracaoSeriesCollection();
		
		
		for (String pathBenchmark: asList(System.getProperty("user.dir") + "/resources/problem-generator/inst10-5-5.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst10-10-5.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst10-15-5.conf"
										  )) {
			rodaAlgoritmoComParametros(graficoFactory, pathBenchmark);
		}
		graficoFactory.buildGraficoFitness("fitness_10tasks_5skills");
		graficoFactory.zeraFitnessSeriesCollection();

		for (String pathBenchmark: asList(System.getProperty("user.dir") + "/resources/problem-generator/inst20-5-5.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst20-10-5.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst20-15-5.conf"
										  )) {
			rodaAlgoritmoComParametros(graficoFactory, pathBenchmark);
		}
		graficoFactory.buildGraficoFitness("fitness_20tasks_5skills");
		graficoFactory.zeraFitnessSeriesCollection();

		for (String pathBenchmark: asList(System.getProperty("user.dir") + "/resources/problem-generator/inst30-5-5.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst30-10-5.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst30-15-5.conf"
										  )) {
			rodaAlgoritmoComParametros(graficoFactory, pathBenchmark);
		}
		graficoFactory.buildGraficoFitness("fitness_30tasks_5skills");
		graficoFactory.zeraFitnessSeriesCollection();
		graficoFactory.buildGraficoCustoDuracao("custo_duracao_5skills");
		graficoFactory.zeraCustoDuracaoSeriesCollection();
		
		
		for (String pathBenchmark: asList(System.getProperty("user.dir") + "/resources/problem-generator/inst10-5-10.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst10-10-10.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst10-15-10.conf"
										  )) {
			rodaAlgoritmoComParametros(graficoFactory, pathBenchmark);
		}
		graficoFactory.buildGraficoFitness("fitness_10tasks_10skills");
		graficoFactory.zeraFitnessSeriesCollection();
		
		for (String pathBenchmark: asList(System.getProperty("user.dir") + "/resources/problem-generator/inst20-5-10.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst20-10-10.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst20-15-10.conf"
										  )) {
			rodaAlgoritmoComParametros(graficoFactory, pathBenchmark);
		}
		graficoFactory.buildGraficoFitness("fitness_20tasks_10skills");
		graficoFactory.zeraFitnessSeriesCollection();
		
		for (String pathBenchmark: asList(System.getProperty("user.dir") + "/resources/problem-generator/inst30-5-10.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst30-10-10.conf",
										  System.getProperty("user.dir") + "/resources/problem-generator/inst30-15-10.conf"
										  )) {
			rodaAlgoritmoComParametros(graficoFactory, pathBenchmark);
		}
		graficoFactory.buildGraficoFitness("fitness_30tasks_10skills");
		graficoFactory.zeraFitnessSeriesCollection();
		graficoFactory.buildGraficoCustoDuracao("custo_duracao_10skills");
		graficoFactory.zeraCustoDuracaoSeriesCollection();
	}

	private static void rodaAlgoritmoComParametros(GraficoFactory graficoFactory, String pathBenchmark) {
		for (Integer numeroGeracoes : asList(NUMERO_GERACOES)) {
			for (Integer tamanhoPopulacao: asList(TAMANHO_POPULACAO)) {
				for (Double tamanhoGrupoElite: asList(TAMANHO_GRUPO_ELITE)) {
					for (Double probabilidadeHerancaElite: asList(PROBABILIDADE_HERANCA_ELITE)) {
						for (Double tamanhoGrupoMutantes: asList(TAMANHO_GRUPO_MUTANTES)) {
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
	
							final AlgoritmoBRKGA algoritmoBrkga = new AlgoritmoBRKGA(parametrosAlgoritmo);
							algoritmoBrkga.inicializaDadosProblema();
							algoritmoBrkga.executarAlgoritmo();
							
							graficoFactory.addFitnessSerie(algoritmoBrkga.getDadosExecucao());
							graficoFactory.addCustoDuracaoSerie(algoritmoBrkga.getDadosExecucao());
							
							System.out.println("Fim da execução");
							System.gc();
						}
					}
				}
			}
		}
	}
	
}
