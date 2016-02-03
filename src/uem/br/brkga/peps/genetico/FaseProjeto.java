package uem.br.brkga.peps.genetico;

import java.util.List;

import com.google.common.collect.Lists;

public class FaseProjeto {

	private List<TaskScheduling> escalasConcomitantes;
	
	private Double inicioFase;
	
	private Double fimFase;
	
	public FaseProjeto(Double inicioFase, Double fimFase, List<TaskScheduling> escalasConcomitantes) {
		this.inicioFase = inicioFase;
		this.fimFase = fimFase;
		this.escalasConcomitantes = Lists.newArrayList(escalasConcomitantes);
	}

	public List<TaskScheduling> getEscalasConcomitantes() {
		return escalasConcomitantes;
	}

	public Double getInicioFase() {
		return inicioFase;
	}

	public Double getFimFase() {
		return fimFase;
	}

	public Double getDuracaoFase() {
		return fimFase - inicioFase;
	}
}
