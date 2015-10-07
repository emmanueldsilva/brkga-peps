package uem.br.ag.peps.genetico;

import java.util.List;

import com.google.common.collect.Lists;

public class FaseProjeto {

	private List<RealizacaoTarefa> realizacoesConcomitantes;
	
	private Double inicioFase;
	
	private Double fimFase;
	
	public FaseProjeto(Double inicioFase, Double fimFase, List<RealizacaoTarefa> realizacoesConcomitantes) {
		this.inicioFase = inicioFase;
		this.fimFase = fimFase;
		this.realizacoesConcomitantes = Lists.newArrayList(realizacoesConcomitantes);
	}

	public List<RealizacaoTarefa> getRealizacoesConcomitantes() {
		return realizacoesConcomitantes;
	}

	public Double getInicioFase() {
		return inicioFase;
	}

	public Double getFimFase() {
		return fimFase;
	}

}
