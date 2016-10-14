package uem.br.brkga.peps.utils;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import static java.util.Calendar.JANUARY;
import static org.apache.commons.io.FileUtils.forceDelete;
import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.apache.commons.io.FileUtils.getUserDirectoryPath;
import static org.apache.commons.io.FileUtils.write;
import static org.jfree.chart.ChartUtilities.saveChartAsPNG;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import uem.br.brkga.peps.genetico.Individuo;
import uem.br.brkga.peps.genetico.MatrizDedicacao;
import uem.br.brkga.peps.genetico.ParametrosAlgoritmo;
import uem.br.brkga.peps.genetico.Populacao;
import uem.br.brkga.peps.genetico.TaskScheduling;
import uem.br.brkga.peps.problema.ProblemaBuilder;

public class PrintFactory {

	private final String MELHOR_FITNESS = "Melhor Fitness";
	private final String MELHOR_CUSTO_PROJETO = "Melhor Custo Projeto";
	private final String MELHOR_DURACAO_PROJETO = "Melhor Duração Projeto";
	
	private static final NumberFormat CURRENCY_INSTANCE = NumberFormat.getCurrencyInstance(Locale.US);
	
	private XYSeries fitnessSeries = new XYSeries(MELHOR_FITNESS);
	
	private XYSeries custoProjetoSeries = new XYSeries(MELHOR_CUSTO_PROJETO);
	
	private XYSeries duracaoProjetoSeries = new XYSeries(MELHOR_DURACAO_PROJETO);
	
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
		sb.appendLine("FITNESS DO MELHOR INDIVÍDUO: " + populacao.getMelhorIndividuo().getValorFitness());
		sb.appendLine("--------------------------------------------------------------");
		sb.appendLine("MAIOR CUSTO PROJETO: " + CURRENCY_INSTANCE.format(populacao.getMaiorValorCustoProjeto()));
		sb.appendLine("MEDIA CUSTO PROJETO: " + CURRENCY_INSTANCE.format(populacao.getMediaValorCustoProjeto()));
		sb.appendLine("MENOR CUSTO PROJETO: " + CURRENCY_INSTANCE.format(populacao.getMenorValorCustoProjeto()));
		sb.appendLine("CUSTO DO MELHOR INDIVÍDUO: " + CURRENCY_INSTANCE.format(populacao.getMelhorIndividuo().getCustoTotalProjeto()));
		sb.appendLine("--------------------------------------------------------------");
		sb.appendLine("MAIOR DURAÇÃO PROJETO: " + populacao.getMaiorDuracaoProjeto());
		sb.appendLine("MEDIA DURAÇÃO PROJETO: " + populacao.getMediaDuracaoProjeto());
		sb.appendLine("MENOR DURAÇÃO PROJETO: " + populacao.getMenorDuracaoProjeto());
		sb.appendLine("DURAÇÃO PROJETO DO MELHOR INDIVÍDUO: " + populacao.getMelhorIndividuo().getDuracaoTotalProjeto());
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
		fitnessSeries.add(geracao, populacao.getMaiorValorFitness());
	}
	
	private void populaDataSetDuracaoProjeto(Populacao populacao, Integer geracao) {
		duracaoProjetoSeries.add(geracao, populacao.getMelhorIndividuo().getDuracaoTotalProjeto());
	}
	
	private void populaDataSetCustoProjeto(Populacao populacao, Integer geracao) {
		custoProjetoSeries.add(geracao, populacao.getMelhorIndividuo().getCustoTotalProjeto());
	}
	
	public synchronized void plotaGraficos(Populacao populacao) {
		try {
			Individuo melhorIndividuo = populacao.getMelhorIndividuo().getIndividuo();
			Individuo piorIndividuo = populacao.getPiorIndividuo().getIndividuo();
			
			String pathDiretorio = buildPathDiretorio();
			buildGraficoFitness(pathDiretorio, melhorIndividuo, piorIndividuo);
			buildGraficoCustoProjeto(pathDiretorio, melhorIndividuo.getCustoTotalProjeto());
			buildGraficoDuracaoProjeto(pathDiretorio);
			buildDiagramaGantt(pathDiretorio, melhorIndividuo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void buildGraficoFitness(String pathDiretorio, Individuo melhorIndividuo, Individuo piorIndividuo) throws IOException {
		JFreeChart graficoFitness = ChartFactory.createXYLineChart("Valor de Fitness", "Gerações", "Fitness", new XYSeriesCollection(fitnessSeries), 
				PlotOrientation.VERTICAL, true, true, false);
		
		final XYPlot xyPlot = graficoFitness.getXYPlot();
		xyPlot.setBackgroundPaint(WHITE);
		xyPlot.setDomainGridlinePaint(BLACK);
		xyPlot.setRangeGridlinePaint(BLACK);

		saveChartAsPNG(new File(pathDiretorio + "grafico_fitness_" + execucao + ".png"), graficoFitness, 1000, 300);
	}
	
	private void buildGraficoCustoProjeto(String pathDiretorio, Double custoProjeto) throws IOException {
		JFreeChart graficoCustoProjeto = ChartFactory.createXYLineChart("Custo do Projeto", "Gerações", "Custo", new XYSeriesCollection(custoProjetoSeries), 
				PlotOrientation.VERTICAL, true, true, false);
		
		final XYPlot xyPlot = graficoCustoProjeto.getXYPlot();
		xyPlot.setBackgroundPaint(WHITE);
		xyPlot.setDomainGridlinePaint(BLACK);
		xyPlot.setRangeGridlinePaint(BLACK);
		
		double DEZENA_MILHAR = 10000.00;
		((NumberAxis) xyPlot.getRangeAxis()).setRange(custoProjeto - (2 * DEZENA_MILHAR), custoProjeto + (2 * DEZENA_MILHAR));
		
		saveChartAsPNG(new File(pathDiretorio + "grafico_custo_" + execucao + ".png"), graficoCustoProjeto, 1000, 300);
	}
	
	private void buildGraficoDuracaoProjeto(String pathDiretorio) throws IOException {
		JFreeChart graficoDuracaoProjeto = ChartFactory.createXYLineChart("Duração do Projeto", "Gerações", "Duração", new XYSeriesCollection(duracaoProjetoSeries), 
				PlotOrientation.VERTICAL, true, true, false);
		
		final XYPlot xyPlot = graficoDuracaoProjeto.getXYPlot();
		xyPlot.setBackgroundPaint(WHITE);
		xyPlot.setDomainGridlinePaint(BLACK);
		xyPlot.setRangeGridlinePaint(BLACK);
		
		saveChartAsPNG(new File(pathDiretorio + "grafico_duracao_" + execucao + ".png"), graficoDuracaoProjeto, 1000, 300);
	}
	
	private void buildDiagramaGantt(String pathDiretorio, Individuo melhorIndividuo) throws IOException {
		final List<TaskScheduling> escalaTarefas = melhorIndividuo.getMatrizDedicacao().getEscalaTarefas();
		
		final JFreeChart diagramaGantt = ChartFactory.createGanttChart("Diagrama de Gantt", "Tarefas", "Tempo", createDataset(escalaTarefas));
		
		saveChartAsPNG(new File(pathDiretorio + "diagrama_gantt_" + execucao + ".png"), diagramaGantt, 1000, 600);
	}

    private IntervalCategoryDataset createDataset(List<TaskScheduling> taskScheduling) {
        final TaskSeries taskSeries = new TaskSeries("Tarefas");

        taskScheduling.forEach((ts) -> {
        	taskSeries.add(new org.jfree.data.gantt.Task("Task " + ts.getTask().getNumero(), calculaData(ts.getTempoInicio()), calculaData(ts.getTempoFim())));
        });
        
        final TaskSeriesCollection taskSeriesCollection = new TaskSeriesCollection();
        taskSeriesCollection.add(taskSeries);
        
        return taskSeriesCollection;
    }

	private Date calculaData(Double tempo) {
		int meses = tempo.intValue();
		int dias = (int) ((tempo - meses) * (365.25/12));
		
		final Calendar calendarInicio = Calendar.getInstance();
		calendarInicio.set(2016, JANUARY, 1);
		calendarInicio.add(Calendar.MONTH, meses);
		calendarInicio.add(Calendar.DAY_OF_MONTH, dias);
		return calendarInicio.getTime();
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
			sb.appendLine("TAREFAS NÃO REALIZADAS: " + matrizDedicacao.getNumeroTarefasNaoRealizadas());
			sb.appendLine("HABILIDADES NECESSARIAS: " + matrizDedicacao.getNumeroHabilidadesNecessarias());
			sb.appendLine("TRABALHO EXTRA: " + matrizDedicacao.getTotalTrabalhoExtra());
			sb.appendLine("CUSTO PROJETO: " + CURRENCY_INSTANCE.format(matrizDedicacao.getCustoTotalProjeto()));
			sb.appendLine("DURAÇÃO PROJETO: " + matrizDedicacao.getDuracaoTotalProjeto());
		}

		sb.append(matrizDedicacao.getMatrizDedicacaoString());
		
		sb.appendLine("==================================================================================");
		sb.appendLine();
		sb.appendLine();
		
		appendToEnd(sb.toString());
	}
	
}
