package uem.br.brkga.peps.genetico;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Grafico {

	public static void main(String[] args) throws IOException {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		ds.addValue(40.5, "maximo", "dia 1");
		ds.addValue(5, "minimo", "dia 1");
		ds.addValue(38.2, "maximo", "dia 2");
		ds.addValue(10, "minimo", "dia 2");
		ds.addValue(37.3, "maximo", "dia 3");
		ds.addValue(15, "minimo", "dia 3");
		ds.addValue(31.5, "maximo", "dia 4");
		ds.addValue(20, "minimo", "dia 4");
		ds.addValue(35.7, "maximo", "dia 5");
		ds.addValue(25, "minimo", "dia 5");
		ds.addValue(42.5, "maximo", "dia 6");
		ds.addValue(30, "minimo", "dia 6");
		
		JFreeChart chart = ChartFactory.createLineChart("Gráfico Teste", "Gerações", "Fitness", ds, PlotOrientation.VERTICAL, true, true, false);
		File file = new File("/home/emmanuel/grafico.png");
		ChartUtilities.saveChartAsPNG(file, chart, 500, 500);
	}
	
}
