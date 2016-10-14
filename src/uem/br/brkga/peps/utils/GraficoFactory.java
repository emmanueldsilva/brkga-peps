package uem.br.brkga.peps.utils;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import static java.math.MathContext.DECIMAL32;
import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.apache.commons.io.FileUtils.getUserDirectoryPath;
import static org.jfree.chart.ChartUtilities.saveChartAsPNG;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import uem.br.brkga.peps.genetico.DadosExecucao;
import uem.br.brkga.peps.genetico.IndividuoCodificado;

public class GraficoFactory {

	private XYSeriesCollection fitnessSeriesCollection = new XYSeriesCollection();
	
	private XYSeriesCollection custoDuracaoSeriesCollection = new XYSeriesCollection();
	
	private int numeroExecucoes;
	
	public GraficoFactory(int numeroExecucoes) {
		this.numeroExecucoes = numeroExecucoes;
	}
	
	public void zeraFitnessSeriesCollection() {
		this.fitnessSeriesCollection = new XYSeriesCollection();
	}
	
	public void zeraCustoDuracaoSeriesCollection() {
		this.custoDuracaoSeriesCollection = new XYSeriesCollection();
	}

	public void addFitnessSerie(DadosExecucao dadosExecucao) {
		final List<Double> acumuladorFitnessGeracoes = dadosExecucao.getAcumuladorFitnessGeracoes();
		final XYSeries fitnessSeries = new XYSeries(dadosExecucao.getBenchmark());
		for (int i = 0; i < acumuladorFitnessGeracoes.size(); i++) {
			fitnessSeries.add(i, new BigDecimal(acumuladorFitnessGeracoes.get(i)).divide(new BigDecimal(numeroExecucoes), DECIMAL32));
		}
		
		fitnessSeriesCollection.addSeries(fitnessSeries);
	}
	
	public void addCustoDuracaoSerie(DadosExecucao dadosExecucao) {
		final List<IndividuoCodificado> melhoresIndividuos = dadosExecucao.getMelhoresIndividuos();
		final XYSeries custoDuracaoSeries = new XYSeries(dadosExecucao.getBenchmark());
		for (IndividuoCodificado individuoCodificado : melhoresIndividuos) {
			if (individuoCodificado.isFactivel()) {
				custoDuracaoSeries.add(individuoCodificado.getCustoTotalProjeto(), individuoCodificado.getDuracaoTotalProjeto());
			}
		}
		
		custoDuracaoSeriesCollection.addSeries(custoDuracaoSeries);
	}
	
	public void buildGraficoFitness(String labelGrafico) throws IOException {
		final JFreeChart graficoFitness = ChartFactory.createXYLineChart("Valor de Fitness", "Gerações", "Fitness", fitnessSeriesCollection, 
				PlotOrientation.VERTICAL, true, true, false);
		
		final XYPlot xyPlot = graficoFitness.getXYPlot();
		xyPlot.setBackgroundPaint(WHITE);
		xyPlot.setDomainGridlinePaint(BLACK);
		xyPlot.setRangeGridlinePaint(BLACK);

		saveChartAsPNG(new File(buildPathDiretorioGraficos() + labelGrafico + ".png"), graficoFitness, 1000, 300);
	}
	
	public void buildGraficoCustoDuracao(String labelGrafico) throws IOException {
		final JFreeChart graficoFitness = ChartFactory.createScatterPlot("Custo X Duração", "Custo", "Duração", custoDuracaoSeriesCollection, 
				PlotOrientation.VERTICAL, true, true, false);
		
		final XYPlot xyPlot = graficoFitness.getXYPlot();
		xyPlot.setBackgroundPaint(WHITE);
		xyPlot.setDomainGridlinePaint(BLACK);
		xyPlot.setRangeGridlinePaint(BLACK);
		
		saveChartAsPNG(new File(buildPathDiretorioGraficos() + labelGrafico + ".png"), graficoFitness, 1000, 300);
	}
	
	private String buildPathDiretorioGraficos() throws IOException {
		String pathDiretorio = getUserDirectoryPath() + "/brkga-peps/execucoes/graficos/";
		
		final File file = new File(pathDiretorio);
		if (!file.exists()) forceMkdir(file);
		return pathDiretorio;
	}
	
}
