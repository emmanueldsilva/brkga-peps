package uem.br.brkga.peps.genetico;

import java.util.Comparator;
import java.util.List;

import uem.br.brkga.peps.problema.ProblemaBuilder;
import uem.br.brkga.peps.utils.RandomFactory;

import com.google.common.collect.Lists;

public class Populacao {

	private int tamanhoPopulacao;
	
	private int tamanhoGrupoElite;
	
	private int tamanhoGrupoMutantes;
	
	private List<IndividuoCodificado> individuos = Lists.newArrayList();
	
	public Populacao(ParametrosAlgoritmo parametrosAlgoritmo) {
		this.tamanhoPopulacao = parametrosAlgoritmo.getTamanhoPopulacao();
		this.tamanhoGrupoElite = getNumeroIndividuosBy(parametrosAlgoritmo.getTamanhoGrupoElite());
		this.tamanhoGrupoMutantes = getNumeroIndividuosBy(parametrosAlgoritmo.getTamanhoGrupoMutantes());
    }
 
    public void gerarIndividuos() {
        for (int i = 0; i < tamanhoPopulacao; i++) {
            gerarIndividuoCodificado();
        }
    }
    
    private void gerarIndividuoCodificado() {
    	final IndividuoCodificado individuoCodificado = new IndividuoCodificado();
    	individuoCodificado.popularGenesAleatoriamente();
    	addIndividuo(individuoCodificado);
    }

	public void avaliarIndividuos() {
		individuos.forEach(i -> {
			i.decodificar();
			i.verificaFactibilidade();
			i.calculaValorFitness();	   
		});
	}
	
	public void ordenarIndividuos() {
		individuos.stream().sorted(individuoComparator());
	}
	
	public List<IndividuoCodificado> selecionarIndividuosMaisAptos() {
		final List<IndividuoCodificado> individuosElite = Lists.newArrayList();
		for (int i = 0; i < tamanhoGrupoElite; i++) {
			individuosElite.add(individuos.get(i));
		}
		
		return individuosElite;
	}
	
	public IndividuoCodificado getIndividuoAleatorio() {
		return individuos.get(RandomFactory.getInstance().nextInt(individuos.size() - 1));
	}
	
	public IndividuoCodificado getIndividuoEliteAleatorio() {
		return individuos.get(RandomFactory.getInstance().nextInt(tamanhoGrupoElite - 1));
	}

	public void efetuarCruzamento(Double probabilidadeHerancaElite) {
		while (individuos.size() < tamanhoPopulacao) {
			final IndividuoCodificado paiElite = getIndividuoEliteAleatorio();
			final IndividuoCodificado paiOutro = getIndividuoAleatorio();
			
			final IndividuoCodificado novoFilho = efetuaCrossover(paiElite, paiOutro, probabilidadeHerancaElite);
			individuos.add(novoFilho);
		}
	}

	private int getNumeroIndividuosBy(Double tamanhoGrupo) {
		return Double.valueOf(tamanhoPopulacao * (tamanhoGrupo/100)).intValue();
	}

	public IndividuoCodificado efetuaCrossover(IndividuoCodificado paiElite, IndividuoCodificado paiOutro, Double probabilidadeHerancaElite) {
		final IndividuoCodificado novoFilho = new IndividuoCodificado();
		
		for (int i = 0; i < ProblemaBuilder.getInstance().getNumeroTasks(); i++) {
			final Double sorteio = RandomFactory.getInstance().randomDoubleRange1();
			if (sorteio < probabilidadeHerancaElite/100) {
				novoFilho.setGene(i, new Double(paiElite.getValor(i)));
			} else {
				novoFilho.setGene(i, new Double(paiOutro.getValor(i)));
			}
		}
		
		return novoFilho;
	}
	
	public void gerarMutantes() {
		for (int i = 0; i < tamanhoGrupoMutantes; i++) {
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

	private Comparator<? super IndividuoCodificado> individuoComparator() {
		return (i1, i2) -> i1.getIndividuo().getValorFitness().compareTo(i2.getIndividuo().getValorFitness());
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
	
	public void addIndividuos(List<IndividuoCodificado> individuosCodificados) {
		this.individuos.addAll(individuosCodificados);
	}

	public List<IndividuoCodificado> getIndividuosCodificados() {
		return individuos;
	}
	
}
