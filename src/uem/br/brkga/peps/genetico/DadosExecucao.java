package uem.br.brkga.peps.genetico;

import static com.google.common.collect.Lists.newArrayList;
import static java.math.MathContext.DECIMAL32;

import java.math.BigDecimal;
import java.util.List;

public class DadosExecucao {

	private List<Double> acumuladorFitnessGeracoes = newArrayList();
	
	private List<IndividuoCodificado> melhoresIndividuos = newArrayList();
	
	private String benchmark;
	
	public DadosExecucao(String benchmark) { 
		this.benchmark = benchmark;
	}
	
	public void acumularFitness(int geracao, Double valorFitness) {
		try {
			acumuladorFitnessGeracoes.set(geracao, new BigDecimal(acumuladorFitnessGeracoes.get(geracao), DECIMAL32).add(new BigDecimal(valorFitness)).doubleValue());
		} catch (IndexOutOfBoundsException e) {
			acumuladorFitnessGeracoes.add(geracao, valorFitness);
		}
	}
	
	public void addMelhorIndividuo(IndividuoCodificado individuoCodificado) {
		melhoresIndividuos.add(individuoCodificado);
	}
	
	public List<Double> getAcumuladorFitnessGeracoes() {
		return acumuladorFitnessGeracoes;
	}
	
	public List<IndividuoCodificado> getMelhoresIndividuos() {
		return melhoresIndividuos;
	}
	
	public String getBenchmark() {
		return benchmark;
	}
	
}
