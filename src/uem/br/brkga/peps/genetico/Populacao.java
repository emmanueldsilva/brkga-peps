package uem.br.brkga.peps.genetico;

import java.util.Comparator;
import java.util.List;

import uem.br.brkga.peps.utils.RandomFactory;

import com.google.common.collect.Lists;

public class Populacao {

	private int tamanhoPopulacao;
	
	private List<IndividuoCodificado> individuos = Lists.newArrayList();
	
	public Populacao(int tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
    }
 
    public void gerarIndividuos() {
        for (int i = 0; i < tamanhoPopulacao; i++) {
            gerarIndividuoAleatorio();
        }
    }
    
    private void gerarIndividuoCodificado() {
    	final IndividuoCodificado individuoCodificado = new IndividuoCodificado();
    	individuoCodificado.popularGenesAleatoriamente();
    	addIndividuo(individuoCodificado);
    }
 
    private void gerarIndividuoAleatorio() {
//    	final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
//    	matrizDedicacao.popularMatrizAleatoriamente();
//    	matrizDedicacao.efetuaCalculosProjeto();
//        addIndividuo(new Individuo(matrizDedicacao));
    }

	public void avaliarIndividuos() {
//		individuos.forEach(i -> { 
//			i.verificaFactibilidade();
//			i.calculaValorFitness();	   
//		});
	}
	
	public void ordenarIndividuos() {
//		individuos.stream().sorted(individuoComparator());
	}
	
	public void selecionarMaisAptos() {
		//TODO
		// aplicar elitismo
		
		while (individuos.size() > tamanhoPopulacao) {
		}
	}
	
	public IndividuoCodificado getIndividuoAleatorio() {
		return individuos.get(RandomFactory.getInstance().nextInt(tamanhoPopulacao - 1));
	}

	public void efetuarCruzamento(Double tamanhoGrupoCombinatorio, Double probabilidadeHerancaElite) {
		//TODO
		//Um pai deve ser do grupo elite e o outro aleatorio ou do grupo não elite
		int numeroIndividuosCruzamento = getNumeroIndividuosBy(tamanhoGrupoCombinatorio);
		for (int cont = 0; cont < numeroIndividuosCruzamento; cont++) {
			IndividuoCodificado pai1 = getIndividuoAleatorio(); //elite
			IndividuoCodificado pai2 = getIndividuoAleatorio(); //não elite ou aleatorio
			
			IndividuoCodificado novoFilho = efetuaCrossover(pai1, pai2, probabilidadeHerancaElite);
			individuos.add(novoFilho);
		}
	}

	private int getNumeroIndividuosBy(Double tamanhoGrupo) {
		return Double.valueOf(individuos.size() * (tamanhoGrupo/100)).intValue();
	}

	public IndividuoCodificado efetuaCrossover(IndividuoCodificado pai1, IndividuoCodificado pai2, Double probabilidadeHerancaElite) {
		//TODO
		// utilizar a propabilidadeHerancaElite para verificar se o gene i deve ser do pai elite ou do outro.
		
		return new IndividuoCodificado();
	}
	
	public void gerarMutantes(Double tamanhoGrupoMutantes) {
		for (int i = 0; i < getNumeroIndividuosBy(tamanhoGrupoMutantes); i++) {
			gerarIndividuoCodificado();
		}
	}

//	public Individuo getMelhorIndividuo() {
//		return individuos.stream()
//			.max(individuoComparator())
//			.get();
//	}
//	
//	public Individuo getPiorIndividuo() {
//		return individuos.stream()
//			.min(individuoComparator())
//			.get();
//	}

	private Comparator<? super Individuo> individuoComparator() {
		return (i1, i2) -> i1.getValorFitness().compareTo(i2.getValorFitness());
	}
	
//	public Double getMaiorValorFitness() {
//		return individuos.stream()
//			.mapToDouble(Individuo::getValorFitness)
//			.max()
//			.getAsDouble();
//	}
//	
//	public Double getMenorValorFitness() {
//		return individuos.stream()
//			.mapToDouble(Individuo::getValorFitness)
//			.min()
//			.getAsDouble();
//	}
//	
//	public Double getMediaValorFitness() {
//		return individuos.stream()
//			.mapToDouble(Individuo::getValorFitness)
//			.average()
//			.getAsDouble();
//	}
//	
//	public Double getMenorValorCustoProjeto() {
//		return individuos.stream()
//			.mapToDouble(i -> i.getMatrizDedicacao().getCustoTotalProjeto())
//			.min()
//			.getAsDouble();
//	}
//	
//	public Double getMaiorValorCustoProjeto() {
//		return individuos.stream()
//			.mapToDouble(i -> i.getMatrizDedicacao().getCustoTotalProjeto())
//			.max()
//			.getAsDouble();
//	}
//	
//	public Double getMediaValorCustoProjeto() {
//		return individuos.stream()
//			.mapToDouble(i -> i.getMatrizDedicacao().getCustoTotalProjeto())
//			.average()
//			.getAsDouble();
//	}
//	
//	public Double getMenorDuracaoProjeto() {
//		return individuos.stream()
//			.mapToDouble(i -> i.getMatrizDedicacao().getDuracaoTotalProjeto())
//			.min()
//			.getAsDouble();
//	}
//	
//	public Double getMaiorDuracaoProjeto() {
//		return individuos.stream()
//			.mapToDouble(i -> i.getMatrizDedicacao().getDuracaoTotalProjeto())
//			.max()
//			.getAsDouble();
//	}
//	
//	public Double getMediaDuracaoProjeto() {
//		return individuos.stream()
//			.mapToDouble(i -> i.getMatrizDedicacao().getDuracaoTotalProjeto())
//			.average()
//			.getAsDouble();
//	}
	
	public void addIndividuo(IndividuoCodificado individuoCodificado) {
		this.individuos.add(individuoCodificado);
	}

	public List<IndividuoCodificado> getIndividuosCodificados() {
		return individuos;
	}
	
}
