package uem.br.brkga.peps.genetico;

import static java.util.Collections.reverse;
import static java.util.Collections.sort;

import java.util.Comparator;
import java.util.List;

import uem.br.brkga.peps.utils.RandomFactory;

import com.google.common.collect.Lists;

public class Populacao {

	private int tamanhoPopulacao;
	
	private int tamanhoGrupoElite;
	
	private int tamanhoGrupoMutantes;
	
	private TipoCodificacao tipoCodificacao;
	
	private List<IndividuoCodificado> individuos = Lists.newArrayList();
	
	public Populacao(ParametrosAlgoritmo parametrosAlgoritmo) {
		this.tamanhoPopulacao = parametrosAlgoritmo.getTamanhoPopulacao();
		this.tamanhoGrupoElite = getNumeroIndividuosBy(parametrosAlgoritmo.getTamanhoGrupoElite());
		this.tamanhoGrupoMutantes = getNumeroIndividuosBy(parametrosAlgoritmo.getTamanhoGrupoMutantes());
		this.tipoCodificacao = parametrosAlgoritmo.getTipoCodificacao();
    }
 
    public void gerarIndividuos() {
        for (int i = 0; i < tamanhoPopulacao; i++) {
            gerarIndividuoCodificado();
        }
    }
    
    private void gerarIndividuoCodificado() {
    	final IndividuoCodificado individuoCodificado = IndividuoCodificadoHelper.getInstance().newIndividuoCodificado(tipoCodificacao);
    	individuoCodificado.codificar();
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
		sort(individuos, individuoComparator());
		reverse(individuos);
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
			novoFilho.decodificar();
			addIndividuo(novoFilho);
		}
	}

	private int getNumeroIndividuosBy(Double tamanhoGrupo) {
		return Double.valueOf(tamanhoPopulacao * (tamanhoGrupo/100)).intValue();
	}

	public IndividuoCodificado efetuaCrossover(IndividuoCodificado paiElite, IndividuoCodificado paiOutro, Double probabilidadeHerancaElite) {
		final IndividuoCodificado novoFilho = IndividuoCodificadoHelper.getInstance().newIndividuoCodificado(tipoCodificacao);
		
		for (int i = 0; i < paiElite.getGenes().length; i++) {
			final Integer sorteio = RandomFactory.getInstance().nextInt(100);
			if (sorteio < probabilidadeHerancaElite) {
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
	
	public void aplicarBuscaLocal() {
		for (IndividuoCodificado individuoCodificado : individuos) {
			final Individuo individuo = individuoCodificado.getIndividuo();
			final Individuo novaSolucao = individuo.clone().buscarSolucaoVizinha();
			
			if (novaSolucao.getValorFitness() > individuo.getValorFitness()) {
				individuoCodificado.setIndividuo(novaSolucao);
				individuoCodificado.recodificar();
			}
		}
	}

	public IndividuoCodificado getMelhorIndividuo() {
		return individuos.stream()
			.max(individuoComparator())
			.get();
	}
	
	public IndividuoCodificado getPiorIndividuo() {
		return individuos.stream()
			.min(individuoComparator())
			.get();
	}

	private Comparator<? super IndividuoCodificado> individuoComparator() {
		return (i1, i2) -> i1.getValorFitness().compareTo(i2.getValorFitness());
	}
	
	public Double getMaiorValorFitness() {
		return individuos.stream()
			.mapToDouble(IndividuoCodificado::getValorFitness)
			.max()
			.getAsDouble();
	}
	
	public Double getMenorValorFitness() {
		return individuos.stream()
			.mapToDouble(IndividuoCodificado::getValorFitness)
			.min()
			.getAsDouble();
	}
	
	public Double getMediaValorFitness() {
		return individuos.stream()
			.mapToDouble(IndividuoCodificado::getValorFitness)
			.average()
			.getAsDouble();
	}
	
	public Double getMenorValorCustoProjeto() {
		return individuos.stream()
			.mapToDouble(i -> i.getCustoTotalProjeto())
			.min()
			.getAsDouble();
	}
	
	public Double getMaiorValorCustoProjeto() {
		return individuos.stream()
			.mapToDouble(i -> i.getCustoTotalProjeto())
			.max()
			.getAsDouble();
	}
	
	public Double getMediaValorCustoProjeto() {
		return individuos.stream()
			.mapToDouble(i -> i.getCustoTotalProjeto())
			.average()
			.getAsDouble();
	}
	
	public Double getMenorDuracaoProjeto() {
		return individuos.stream()
			.mapToDouble(i -> i.getDuracaoTotalProjeto())
			.min()
			.getAsDouble();
	}
	
	public Double getMaiorDuracaoProjeto() {
		return individuos.stream()
			.mapToDouble(i -> i.getDuracaoTotalProjeto())
			.max()
			.getAsDouble();
	}
	
	public Double getMediaDuracaoProjeto() {
		return individuos.stream()
			.mapToDouble(i -> i.getDuracaoTotalProjeto())
			.average()
			.getAsDouble();
	}
	
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
