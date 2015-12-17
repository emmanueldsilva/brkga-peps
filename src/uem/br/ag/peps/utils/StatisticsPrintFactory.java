package uem.br.ag.peps.utils;

import static java.lang.String.valueOf;
import static org.apache.commons.io.FileUtils.forceDelete;
import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.apache.commons.io.FileUtils.getUserDirectoryPath;
import static org.apache.commons.io.FileUtils.write;

import java.io.File;
import java.io.IOException;
import java.util.List;

import uem.br.ag.peps.genetico.ParametrosAlgoritmo;

public class StatisticsPrintFactory {

	private ParametrosAlgoritmo parametrosAlgoritmo;
	
	private Integer hitRate;
	
	private List<Double> melhoresFitness;
	
	private List<Double> melhoresCustos;
	
	private List<Double> melhoresDuracoes;
	
	public StatisticsPrintFactory(ParametrosAlgoritmo parametrosAlgoritmo, Integer hitRate, List<Double> melhoresFitness, List<Double> melhoresCustos, List<Double> melhoresDuracoes) {
		this.parametrosAlgoritmo = parametrosAlgoritmo;
		this.hitRate = hitRate;
		this.melhoresFitness = melhoresFitness;
		this.melhoresCustos = melhoresCustos;
		this.melhoresDuracoes = melhoresDuracoes;
		
		deleteEstatisticasAnteriores();
	}

	private void deleteEstatisticasAnteriores() {
		try {
			File file = getEstatisticasFile();
			if (file.exists()) forceDelete(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printEstatisticasExecucoes() {
		final CustomStringBuilder sb = new CustomStringBuilder();
		sb.appendLine("--------------------------------------------------------------");
		sb.appendLine("ESTATÍSTICAS EXECUÇÕES");
		sb.appendLine("BENCHMARK: " + new File(parametrosAlgoritmo.getPathBenchmark()).getName());
		sb.appendLine();
		sb.appendLine("HIT RATE: " + hitRate);
		sb.appendLine("NÚMERO DE EXECUÇÕES: " + parametrosAlgoritmo.getNumeroExecucoes());
		sb.appendLine("PERCENTUAL HATE: " + (new Double(hitRate) / new Double(parametrosAlgoritmo.getNumeroExecucoes())) * 100.0);
		sb.appendLine("--------------------------------------------------------------");
		sb.appendLine();
		sb.appendLine("MELHORES FITNESS");
		melhoresFitness.forEach(d -> sb.appendLine(valueOf(d)));
		sb.appendLine("--------------------------------------------------------------");
		sb.appendLine();
		sb.appendLine("MELHORES CUSTOS");
		melhoresCustos.forEach(d -> sb.appendLine(valueOf(d)));
		sb.appendLine("--------------------------------------------------------------");
		sb.appendLine();
		sb.appendLine("MELHORES DURAÇÕES");
		melhoresDuracoes.forEach(d -> sb.appendLine(valueOf(d)));
		sb.appendLine("--------------------------------------------------------------");
		sb.appendLine();
		
		appendToEnd(sb.toString());
	}
	
	private void appendToEnd(String string) {
		try {
			write(getEstatisticasFile(), string, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File getEstatisticasFile() throws IOException {
		return new File(buildPathDiretorio() + "estatisticas_execucoes.txt");
	}

	private String buildPathDiretorio() throws IOException {
		String pathDiretorio = getUserDirectoryPath() + "/ag-peps/execucoes/";
		pathDiretorio += new File(parametrosAlgoritmo.getPathBenchmark()).getName() + "/";
		pathDiretorio += "exe" + parametrosAlgoritmo.getNumeroExecucoes() + "/";
		pathDiretorio += "ger" + parametrosAlgoritmo.getNumeroGeracoes() + "/";
		pathDiretorio += "pop" + parametrosAlgoritmo.getTamanhoPopulacao() + "/";
		pathDiretorio += "cru" + parametrosAlgoritmo.getPercentualCruzamento().intValue() + "/";
		pathDiretorio += "mut" + parametrosAlgoritmo.getPercentualMutacao().intValue() + "/";
		
		File file = new File(pathDiretorio);
		if (!file.exists()) forceMkdir(file);
		return pathDiretorio;
	}

}
