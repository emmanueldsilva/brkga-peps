package uem.br.brkga.peps.utils;

import static org.apache.commons.io.FileUtils.forceDelete;
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

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import uem.br.brkga.peps.entidade.Employee;
import uem.br.brkga.peps.entidade.Task;
import uem.br.brkga.peps.genetico.Individuo;
import uem.br.brkga.peps.genetico.MatrizDedicacao;
import uem.br.brkga.peps.genetico.ParametrosAlgoritmo;
import uem.br.brkga.peps.genetico.Populacao;
import uem.br.brkga.peps.problema.ProblemaBuilder;

public class PrintFactory {

	private final String MELHOR_FITNESS = "Melhor Fitness";
	private final String MEDIA_FITNESS = "Média Fitness";
	private final String PIOR_FITNESS = "Pior Fitness";
	
	private final String MELHOR_CUSTO_PROJETO = "Melhor Custo Projeto";
	private final String MEDIA_CUSTO_PROJETO = "Média Custo Projeto";
	private final String PIOR_CUSTO_PROJETO = "Pior Custo Projeto";
	
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
		
		deleteRegistrosAnteriores();
	}
	
	private void deleteRegistrosAnteriores() {
		try {
			File file = getRegistrosExecucaoFile();
			if (file.exists()) forceDelete(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void imprimePopulacao(List<Individuo> individuos) {
		individuos.forEach(i -> printIndividuo(i));
	}

	public void geraEstatisticas(Populacao populacao, Integer geracao) {
		populaDataSetFitness(populacao, geracao);
		populaDataSetCustoProjeto(populacao, geracao);
		populaDataSetDuracaoProjeto(populacao, geracao);
		
		printEstatisticaPopulacao(populacao, geracao);
	}

	private void printEstatisticaPopulacao(Populacao populacao, Integer geracao) {
		final CustomStringBuilder sb = new CustomStringBuilder();
		sb.appendLine("--------------------------------------------------------------");
		sb.appendLine("ESTATÍSTICAS GERAÇÃO " + geracao);
		sb.appendLine();
		sb.appendLine("MELHOR FITNESS: " + populacao.getMaiorValorFitness());
		sb.appendLine("MEDIA FITNESS: " + populacao.getMediaValorFitness());
		sb.appendLine("PIOR FITNESS: " + populacao.getMenorValorFitness());
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
		
		appendToEnd(sb.toString());
	}
	
	public void printEstatisticaExecucao(Double tempoExecucao, ParametrosAlgoritmo parametrosAlgoritmo) {
		final CustomStringBuilder sb = new CustomStringBuilder();
		sb.appendLine("--------------------------------------------------------------");
		sb.appendLine("TEMPO DE EXECUÇÃO: " + tempoExecucao);
		sb.appendLine();
		sb.appendLine("NUMERO DE EMPLOYEES: " + ProblemaBuilder.getInstance().getNumeroEmployees());
		sb.appendLine("NUMERO DE TASKS: " + ProblemaBuilder.getInstance().getNumeroTasks());
		sb.appendLine();
		sb.appendLine("BENCHMARK: " + new File(parametrosAlgoritmo.getPathBenchmark()).getName());
		sb.appendLine("NÚMERO DE EXECUÇÕES: " + parametrosAlgoritmo.getNumeroExecucoes());
		sb.appendLine("NÚMERO DE GERAÇÕES: " + parametrosAlgoritmo.getNumeroGeracoes());
		sb.appendLine("TAMANHO POPULAÇÃO: " + parametrosAlgoritmo.getTamanhoPopulacao());
		sb.appendLine("TAMANHO GRUPO ELITE: " + parametrosAlgoritmo.getTamanhoGrupoElite());
		sb.appendLine("TAMANHO GRUPO MUTANTES: " + parametrosAlgoritmo.getTamanhoGrupoMutantes());
		sb.appendLine("PROBABILIDADE HERANÇA ELITE: " + parametrosAlgoritmo.getProbabilidadeHerancaElite());
		
		appendToEnd(sb.toString());
	}
	
	private void appendToEnd(String string) {
		try {
			write(getRegistrosExecucaoFile(), string, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File getRegistrosExecucaoFile() throws IOException {
		return new File(buildPathDiretorio() + "estatisticas" + execucao + ".txt");
	}

	private void populaDataSetFitness(Populacao populacao, Integer geracao) {
		dataSetFitness.addValue(populacao.getMaiorValorFitness(), MELHOR_FITNESS, geracao);
		dataSetFitness.addValue(populacao.getMediaValorFitness(), MEDIA_FITNESS, geracao);
		dataSetFitness.addValue(populacao.getMenorValorFitness(), PIOR_FITNESS, geracao);
	}
	
	private void populaDataSetDuracaoProjeto(Populacao populacao, Integer geracao) {
		dataSetDuracaoProjeto.addValue(populacao.getMaiorDuracaoProjeto(), PIOR_DURACAO_PROJETO, geracao);
		dataSetDuracaoProjeto.addValue(populacao.getMediaDuracaoProjeto(), MEDIA_DURACAO_PROJETO, geracao);
		dataSetDuracaoProjeto.addValue(populacao.getMenorDuracaoProjeto(), MELHOR_DURACAO_PROJETO, geracao);
	}
	
	private void populaDataSetCustoProjeto(Populacao populacao, Integer geracao) {
		dataSetCustoProjeto.addValue(populacao.getMaiorValorCustoProjeto(), PIOR_CUSTO_PROJETO, geracao);
		dataSetCustoProjeto.addValue(populacao.getMediaValorCustoProjeto(), MEDIA_CUSTO_PROJETO, geracao);
		dataSetCustoProjeto.addValue(populacao.getMenorValorCustoProjeto(), MELHOR_CUSTO_PROJETO, geracao);
	}
	
	public synchronized void plotaGraficos(Populacao populacao) {
		try {
			Individuo melhorIndividuo = populacao.getMelhorIndividuo().getIndividuo();
			Individuo piorIndividuo = populacao.getPiorIndividuo().getIndividuo();
			
			String pathDiretorio = buildPathDiretorio();
			buildGraficoFitness(pathDiretorio, melhorIndividuo, piorIndividuo);
			buildGraficoCustoProjeto(pathDiretorio, melhorIndividuo, piorIndividuo);
			buildGraficoDuracaoProjeto(pathDiretorio);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void buildGraficoFitness(String pathDiretorio, Individuo melhorIndividuo, Individuo piorIndividuo) throws IOException {
		JFreeChart graficoFitness = createLineChart("Valor de Fitness", "Gerações", "Fitness", dataSetFitness, 
				PlotOrientation.VERTICAL, true, true, false);
		
		CategoryPlot categoryPlot = graficoFitness.getCategoryPlot();
		categoryPlot.setDomainCrosshairVisible(true);
		categoryPlot.setRangeCrosshairVisible(true);
		
		saveChartAsPNG(new File(pathDiretorio + "grafico_fitness_" + execucao + ".png"), graficoFitness, 1000, 300);
	}
	
	private void buildGraficoCustoProjeto(String pathDiretorio, Individuo melhorIndividuo, Individuo piorIndividuo) throws IOException {
		JFreeChart graficoCustoProjeto = createLineChart("Custo do Projeto", "Gerações", "Custo", dataSetCustoProjeto, 
				PlotOrientation.VERTICAL, true, true, false);
		
		CategoryPlot categoryPlot = (CategoryPlot) graficoCustoProjeto.getPlot();
		categoryPlot.setDomainCrosshairVisible(true);
		categoryPlot.setRangeCrosshairVisible(true);
		
		saveChartAsPNG(new File(pathDiretorio + "grafico_custo_" + execucao + ".png"), graficoCustoProjeto, 1000, 300);
	}
	
	private void buildGraficoDuracaoProjeto(String pathDiretorio) throws IOException {
		JFreeChart graficoDuracaoProjeto = createLineChart("Duração do Projeto", "Gerações", "Duração", dataSetDuracaoProjeto, 
				PlotOrientation.VERTICAL, true, true, false);
		
		saveChartAsPNG(new File(pathDiretorio + "grafico_duracao_" + execucao + ".png"), graficoDuracaoProjeto, 1000, 300);
	}

	private String buildPathDiretorio() throws IOException {
		String pathDiretorio = getUserDirectoryPath() + "/brkga-peps/execucoes/";
		pathDiretorio += new File(parametrosAlgoritmo.getPathBenchmark()).getName() + "/";
		pathDiretorio += "exe" + parametrosAlgoritmo.getNumeroExecucoes() + "/";
		pathDiretorio += "ger" + parametrosAlgoritmo.getNumeroGeracoes() + "/";
		pathDiretorio += "pop" + parametrosAlgoritmo.getTamanhoPopulacao() + "/";
		pathDiretorio += "elite" + parametrosAlgoritmo.getTamanhoGrupoElite().intValue() + "/";
		pathDiretorio += "mut" + parametrosAlgoritmo.getTamanhoGrupoMutantes().intValue() + "/";
		pathDiretorio += "prob" + parametrosAlgoritmo.getProbabilidadeHerancaElite().intValue() + "/";
		
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
		
		appendToEnd(sb.toString());
	}
	
}
