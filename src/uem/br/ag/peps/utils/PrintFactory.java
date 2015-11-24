package uem.br.ag.peps.utils;

import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.apache.commons.io.FileUtils.getUserDirectoryPath;
import static org.apache.commons.io.FileUtils.write;
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
import uem.br.ag.peps.genetico.ParametrosAlgoritmo;
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
	
	private ParametrosAlgoritmo parametrosAlgoritmo;
	
	private Integer execucao;
	
	public PrintFactory(ParametrosAlgoritmo parametrosAlgoritmo, Integer execucao) {
		this.parametrosAlgoritmo = parametrosAlgoritmo;
		this.execucao = execucao;
	}

	public void imprimePopulacao(List<Individuo> individuos) {
		individuos.forEach(printIndividuo());
	}

	private Consumer<Individuo> printIndividuo() {
		return new Consumer<Individuo>() {

			@Override
			public void accept(Individuo individuo) {
				printIndividuo(individuo);
			}
		};
	}

	public void geraEstatisticas(Populacao populacao, Integer geracao) {
//		if (!file.exists()) file.createNewFile(); 
		
		populaDataSetFitness(populacao, geracao);
		populaDataSetCustoProjeto(populacao, geracao);
		populaDataSetDuracaoProjeto(populacao, geracao);
		
		printEstatisticaPopulacao(populacao);
	}

	private void printEstatisticaPopulacao(Populacao populacao) {
		final CustomStringBuilder sb = new CustomStringBuilder();
		sb.appendLine("--------------------------------------------------------------");
		sb.appendLine("ESTATÍSTICAS GERAÇÃO");
		sb.appendLine();
		sb.appendLine("MELHOR FITNESS: " + populacao.getMaiorValorFitness());
		sb.appendLine("MEDIA FITNESS: " + populacao.getMediaValorFitness());
		sb.appendLine("MENOR FITNESS: " + populacao.getMenorValorFitness());
		sb.appendLine("--------------------------------------------------------------");
		sb.appendLine("MAIOR CUSTO PROJETO: " + CURRENCY_INSTANCE.format(populacao.getMaiorValorCustoProjeto()));
		sb.appendLine("MEDIA CUSTO PROJETO: " + CURRENCY_INSTANCE.format(populacao.getMediaValorCustoProjeto()));
		sb.appendLine("MENOR CUSTO PROJETO: " + CURRENCY_INSTANCE.format(populacao.getMenorValorCustoProjeto()));
		sb.appendLine("--------------------------------------------------------------");
		sb.appendLine("MAIOR DURAÇÃO PROJETO: " + populacao.getMaiorDuracaoProjeto());
		sb.appendLine("MEDIA DURAÇÃO PROJETO: " + populacao.getMediaDuracaoProjeto());
		sb.appendLine("MENOR DURAÇÃO PROJETO: " + populacao.getMenorDuracaoProjeto());
		sb.appendLine("--------------------------------------------------------------");
		sb.appendLine();
		sb.appendLine();
		
		appendEstatistica(sb.toString());
	}
	
	private void appendEstatistica(String string) {
		try {
			write(new File(buildPathDiretorio() + "estatisticas" + execucao), string, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void populaDataSetFitness(Populacao populacao, Integer geracao) {
		dataSetFitness.addValue(populacao.getMaiorValorFitness(), MELHOR_FITNESS, geracao);
		dataSetFitness.addValue(populacao.getMediaValorFitness(), MEDIA_FITNESS, geracao);
		dataSetFitness.addValue(populacao.getMenorValorFitness(), PIOR_FITNESS, geracao);
	}
	
	private void populaDataSetDuracaoProjeto(Populacao populacao, Integer geracao) {
		dataSetDuracaoProjeto.addValue(populacao.getMaiorDuracaoProjeto(), MELHOR_DURACAO_PROJETO, geracao);
		dataSetDuracaoProjeto.addValue(populacao.getMediaDuracaoProjeto(), MEDIA_DURACAO_PROJETO, geracao);
		dataSetDuracaoProjeto.addValue(populacao.getMenorDuracaoProjeto(), PIOR_DURACAO_PROJETO, geracao);
	}
	
	private void populaDataSetCustoProjeto(Populacao populacao, Integer geracao) {
		dataSetCustoProjeto.addValue(populacao.getMaiorValorCustoProjeto(), MELHOR_CUSTO_PROJETO, geracao);
		dataSetCustoProjeto.addValue(populacao.getMediaValorCustoProjeto(), MEDIA_CUSTO_PROJETO, geracao);
		dataSetCustoProjeto.addValue(populacao.getMenorValorCustoProjeto(), PIOR_CUSTO_PROJETO, geracao);
	}
	
	public void plotaGraficos() {
		try {
			String pathDiretorio = buildPathDiretorio();
			buildGraficoFitness(pathDiretorio);
			buildGraficoCustoProjeto(pathDiretorio);
			buildGraficoDuracaoProjeto(pathDiretorio);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void buildGraficoFitness(String pathDiretorio) throws IOException {
		JFreeChart graficoFitness = createLineChart("Valor de Fitness", "Gerações", "Fitness", dataSetFitness, 
				PlotOrientation.VERTICAL, true, true, false);
		saveChartAsPNG(new File(pathDiretorio + "grafico_fitness_" + execucao + ".png"), graficoFitness, 1000, 300);
	}
	
	private void buildGraficoCustoProjeto(String pathDiretorio) throws IOException {
		JFreeChart graficoCustoProjeto = createLineChart("Custo do Projeto", "Gerações", "Custo", dataSetCustoProjeto, 
				PlotOrientation.VERTICAL, true, true, false);
		saveChartAsPNG(new File(pathDiretorio + "grafico_custo_" + execucao + ".png"), graficoCustoProjeto, 1000, 300);
	}
	
	private void buildGraficoDuracaoProjeto(String pathDiretorio) throws IOException {
		JFreeChart graficoDuracaoProjeto = createLineChart("Duração do Projeto", "Gerações", "Duração", dataSetDuracaoProjeto, 
				PlotOrientation.VERTICAL, true, true, false);
		saveChartAsPNG(new File(pathDiretorio + "grafico_duracao_" + execucao + ".png"), graficoDuracaoProjeto, 1000, 300);
	}

	private String buildPathDiretorio() throws IOException {
		String pathDiretorio = getUserDirectoryPath() + "/ag-peps/execucoes/";
		pathDiretorio += "ger" + parametrosAlgoritmo.getNumeroGeracoes() + "/";
		pathDiretorio += "pop" + parametrosAlgoritmo.getTamanhoPopulacao() + "/";
		pathDiretorio += "cru" + parametrosAlgoritmo.getTamanhoPopulacao() + "/";
		pathDiretorio += "mut" + parametrosAlgoritmo.getTamanhoPopulacao() + "/";
		
		File file = new File(pathDiretorio);
		if (!file.exists()) forceMkdir(file);
		return pathDiretorio;
	}

	public void printIndividuo(Individuo individuo) {
		final CustomStringBuilder sb = new CustomStringBuilder();
		sb.appendLine("==================================================================================");
		sb.appendLine("INDIVÍDUO");
		sb.appendLine("FITNESS: " + individuo.fitnessToString());
		
		final MatrizDedicacao matrizDedicacao = individuo.getMatrizDedicacao();
		if (individuo.isFactivel() != null) {
			sb.appendLine("FACTIVEL: " + individuo.isFactivel());
			sb.appendLine("TAREFAS NÃO REALIZADAS: " + matrizDedicacao.getTarefasNaoRealizadas());
			sb.appendLine("HABILIDADES NECESSARIAS: " + matrizDedicacao.getHabilidadesNecessarias());
			sb.appendLine("TRABALHO EXTRA: " + matrizDedicacao.getTrabalhoExtra());
			sb.appendLine("CUSTO PROJETO: " + CURRENCY_INSTANCE.format(matrizDedicacao.getCustoTotalProjeto()));
			sb.appendLine("DURAÇÃO PROJETO: " + matrizDedicacao.getDuracaoTotalProjeto());
		}

		for (Employee employee : ProblemaBuilder.getInstance().getEmployees()) {
			for (Task task: ProblemaBuilder.getInstance().getTasks()) {
				sb.append(matrizDedicacao.getGrauDedicacao(employee, task).getValor() + "\t");
			}
			
			sb.appendLine();
		}
		
		sb.appendLine("==================================================================================");
		sb.appendLine();
		sb.appendLine();
		
		appendEstatistica(sb.toString());
	}
	
}
