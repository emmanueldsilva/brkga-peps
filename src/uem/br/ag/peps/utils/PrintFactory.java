package uem.br.ag.peps.utils;

import static org.jfree.chart.ChartFactory.createLineChart;
import static org.jfree.chart.ChartUtilities.saveChartAsPNG;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import uem.br.ag.peps.entidade.Employee;
import uem.br.ag.peps.entidade.Task;
import uem.br.ag.peps.genetico.Individuo;
import uem.br.ag.peps.genetico.MatrizDedicacao;
import uem.br.ag.peps.genetico.Populacao;
import uem.br.ag.peps.problema.ProblemaBuilder;

public class PrintFactory {

	private final String MELHOR_FITNESS = "Melhor Fitness";
	private final String MEDIA_FITNESS = "Média Fitness";
	private final String PIOR_FITNESS = "Pior Fitness";
	
	private final String MELHOR_CUSTO_PROJETO = "Melhor Custo Projeto";
	private final String MEDIA_CUSTO_PROJETO = "Média Custo Projeto";
	private final String PIOR_CUSTO_PROJETO = "Melhor Custo Projeto";
	
	private final String MELHOR_DURACAO_PROJETO = "Melhor Duração Projeto";
	private final String MEDIA_DURACAO_PROJETO = "Média Duração Projeto";
	private final String PIOR_DURACAO_PROJETO = "Pior Duração Projeto";
	
	private static final NumberFormat CURRENCY_INSTANCE = NumberFormat.getCurrencyInstance(Locale.US);
	
	private DefaultCategoryDataset dataSetFitness = new DefaultCategoryDataset();
	
	private DefaultCategoryDataset dataSetCustoProjeto = new DefaultCategoryDataset();
	
	private DefaultCategoryDataset dataSetDuracaoProjeto = new DefaultCategoryDataset();
	
	public PrintFactory() {
	}

	public static void imprimePopulacao(List<Individuo> individuos) {
		System.out.println("--------------------------------------------------------------");
		System.out.println("INDIVÍDUOS");
		System.out.println();
		individuos.forEach(printIndividuo());
	}

	private static Consumer<Individuo> printIndividuo() {
		return new Consumer<Individuo>() {

			@Override
			public void accept(Individuo individuo) {
				System.out.println("==================================================================================");
				System.out.println("FITNESS: " + individuo.fitnessToString());
				
				final MatrizDedicacao matrizDedicacao = individuo.getMatrizDedicacao();
				if (individuo.isFactivel() != null) {
					System.out.println("FACTIVEL: " + individuo.isFactivel());
					System.out.println("TAREFAS NÃO REALIZADAS: " + matrizDedicacao.getTarefasNaoRealizadas());
					System.out.println("HABILIDADES NECESSARIAS: " + matrizDedicacao.getHabilidadesNecessarias());
					System.out.println("TRABALHO EXTRA: " + matrizDedicacao.getTrabalhoExtra());
					System.out.println("CUSTO PROJETO: " + CURRENCY_INSTANCE.format(matrizDedicacao.getCustoTotalProjeto()));
					System.out.println("DURAÇÃO PROJETO: " + matrizDedicacao.getDuracaoTotalProjeto());
				}

				for (Employee employee : ProblemaBuilder.getInstance().getEmployees()) {
					for (Task task: ProblemaBuilder.getInstance().getTasks()) {
						System.out.print(matrizDedicacao.getGrauDedicacao(employee, task).getValor() + "\t");
					}
					
					System.out.println();
				}
				System.out.println("==================================================================================");
				System.out.println();
				System.out.println();
			}
		};
	}

	public void geraEstatisticas(Populacao populacao, Integer geracao) {
		dataSetFitness.addValue(populacao.getMaiorValorFitness(), MELHOR_FITNESS, geracao);
		dataSetFitness.addValue(populacao.getMediaValorFitness(), MEDIA_FITNESS, geracao);
		dataSetFitness.addValue(populacao.getMenorValorFitness(), PIOR_FITNESS, geracao);
		
		dataSetCustoProjeto.addValue(populacao.getMaiorValorCustoProjeto(), MELHOR_CUSTO_PROJETO, geracao);
		dataSetCustoProjeto.addValue(populacao.getMediaValorCustoProjeto(), MEDIA_CUSTO_PROJETO, geracao);
		dataSetCustoProjeto.addValue(populacao.getMenorValorCustoProjeto(), PIOR_CUSTO_PROJETO, geracao);
		
		dataSetDuracaoProjeto.addValue(populacao.getMaiorDuracaoProjeto(), MELHOR_DURACAO_PROJETO, geracao);
		dataSetDuracaoProjeto.addValue(populacao.getMediaDuracaoProjeto(), MEDIA_DURACAO_PROJETO, geracao);
		dataSetDuracaoProjeto.addValue(populacao.getMenorDuracaoProjeto(), PIOR_DURACAO_PROJETO, geracao);
		
		System.out.println("--------------------------------------------------------------");
		System.out.println("ESTATÍSTICAS GERAÇÃO");
		System.out.println();
		System.out.println("MELHOR FITNESS: " + populacao.getMaiorValorFitness());
		System.out.println("MEDIA FITNESS: " + populacao.getMediaValorFitness());
		System.out.println("MENOR FITNESS: " + populacao.getMenorValorFitness());
		System.out.println("--------------------------------------------------------------");
		System.out.println("MAIOR CUSTO PROJETO: " + CURRENCY_INSTANCE.format(populacao.getMaiorValorCustoProjeto()));
		System.out.println("MEDIA CUSTO PROJETO: " + CURRENCY_INSTANCE.format(populacao.getMediaValorCustoProjeto()));
		System.out.println("MENOR CUSTO PROJETO: " + CURRENCY_INSTANCE.format(populacao.getMenorValorCustoProjeto()));
		System.out.println("--------------------------------------------------------------");
		System.out.println("MAIOR DURAÇÃO PROJETO: " + populacao.getMaiorDuracaoProjeto());
		System.out.println("MEDIA DURAÇÃO PROJETO: " + populacao.getMediaDuracaoProjeto());
		System.out.println("MENOR DURAÇÃO PROJETO: " + populacao.getMenorDuracaoProjeto());
		System.out.println("--------------------------------------------------------------");
		System.out.println();
		System.out.println();
	}
	
	public void plotaGraficos() {
		try {
			JFreeChart graficoFitness = createLineChart("Valor de Fitness", "Gerações", "Fitness", dataSetFitness, 
													PlotOrientation.VERTICAL, true, true, false);
			saveChartAsPNG(new File("/home/emmanuel/grafico_fitness.png"), graficoFitness, 1000, 300);

			JFreeChart graficoCustoProjeto = createLineChart("Custo do Projeto", "Gerações", "Custo", dataSetCustoProjeto, 
					PlotOrientation.VERTICAL, true, true, false);
			saveChartAsPNG(new File("/home/emmanuel/grafico_custo.png"), graficoCustoProjeto, 1000, 300);
			
			JFreeChart graficoDuracaoProjeto = createLineChart("Duração do Projeto", "Gerações", "Duração", dataSetDuracaoProjeto, 
					PlotOrientation.VERTICAL, true, true, false);
			saveChartAsPNG(new File("/home/emmanuel/grafico_duracao.png"), graficoDuracaoProjeto, 1000, 300);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
