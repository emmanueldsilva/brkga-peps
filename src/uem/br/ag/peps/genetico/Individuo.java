package uem.br.ag.peps.genetico;

import static java.math.MathContext.DECIMAL32;

import java.math.BigDecimal;

public class Individuo {

	private MatrizDedicacao matrizDedicacao;
	
	private Double valorFitness;
	
	private Boolean factivel;
	
	public Individuo() {
		this.matrizDedicacao = new MatrizDedicacao();
		this.valorFitness = null;
		this.factivel = null;
	}
	
	public void verificaFactibilidade() {
		this.factivel = matrizDedicacao.isSolucaoValidaPeranteRestricao1() && 
						matrizDedicacao.isSolucaoValidaPeranteRestricao2() && 
						matrizDedicacao.isSolucaoValidaPeranteRestricao3();
	}
	
	public void calculaValorFitness(ParametrosPesos parametrosPesos) {
		BigDecimal valorFitness = BigDecimal.ZERO;
		if (factivel) {
			BigDecimal pesoCustoProjeto = BigDecimal.valueOf(parametrosPesos.getPesoCustoProjeto());
			BigDecimal pesoDuracaoProjeto = BigDecimal.valueOf(parametrosPesos.getPesoDuracaoProjeto());
			
			
			valorFitness = BigDecimal.ONE.divide(valorFitness, DECIMAL32);
		} else {
			
		}
		
	}

	public MatrizDedicacao getMatrizDedicacao() {
		return matrizDedicacao;
	}

	public void setMatrizDedicacao(MatrizDedicacao matrizDedicacao) {
		this.matrizDedicacao = matrizDedicacao;
	}

	public Double getValorFitness() {
		return valorFitness;
	}

	public void setValorFitness(Double valorFitness) {
		this.valorFitness = valorFitness;
	}

	public boolean isFactivel() {
		return factivel;
	}

	public void setFactivel(boolean isFactivel) {
		this.factivel = isFactivel;
	}
	
}
