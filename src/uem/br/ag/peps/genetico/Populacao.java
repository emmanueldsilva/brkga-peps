package uem.br.ag.peps.genetico;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import uem.br.ag.peps.problema.ProblemaBuilder;
import uem.br.ag.peps.utils.RandomFactory;

import com.google.common.collect.Lists;

public class Populacao {

	private int tamanhoPopulacao;
	
	private List<Individuo> individuos = Lists.newArrayList();
	
	public Populacao(int tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
    }
 
    public void gerarIndividuos() {
        for (int i = 0; i < tamanhoPopulacao; i++) {
            gerarIndividuoAleatorio();
        }
    }
 
    private void gerarIndividuoAleatorio() {
    	final MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
    	matrizDedicacao.popularMatrizAleatoriamente();
    	matrizDedicacao.efetuaCalculosProjeto();
        final Individuo individuo = new Individuo(matrizDedicacao);
        addIndividuo(individuo);
    }

	public void avaliarIndividuos() {
		individuos.forEach(i -> { 
			i.verificaFactibilidade();
			i.calculaValorFitness();	   
		});
	}
	
	public void ordenarIndividuos() {
		individuos.stream().sorted((individuo1, individuo2) -> individuo1.getValorFitness().compareTo(individuo2.getValorFitness()));
	}

	public void selecionarMaisAptosPorTorneio() {
		while (individuos.size() > tamanhoPopulacao) {
			Individuo individuo1 = getIndividuoAleatorio();
			Individuo individuo2 = getIndividuoAleatorio();
			 
			if (individuo1.getValorFitness() < individuo2.getValorFitness()) {
			    individuos.remove(individuo2);
			} else {
			    individuos.remove(individuo1);
			}
		}
	}
	
	public Individuo getIndividuoAleatorio() {
		return individuos.get(RandomFactory.getInstance().nextInt(tamanhoPopulacao - 1));
	}

	public void efetuarCruzamento(Double percentualCruzamento) {
		int numeroIndividuosCruzamento = Double.valueOf(individuos.size() * (percentualCruzamento/100)).intValue();
		final List<Individuo> novosFilhos = newArrayList();
		for (int cont = 0; cont < numeroIndividuosCruzamento; cont++) {
			Individuo pai1 = getIndividuoAleatorio();
			Individuo pai2 = getIndividuoAleatorio();
			
			int count = 0;
			while (pai1.equals(pai2) && count < 5) {
				pai1 = getIndividuoAleatorio();
				count++;
			}
			
			efetuaCrossover(novosFilhos, pai1, pai2);
		}
		
		individuos.addAll(novosFilhos);
	}

	public void efetuaCrossover(List<Individuo> novosFilhos, Individuo pai1, Individuo pai2) {
		int numeroEmployees = ProblemaBuilder.getInstance().getEmployees().size();
		int linha = RandomFactory.getInstance().nextInt(numeroEmployees);
		
		int numeroTasks = ProblemaBuilder.getInstance().getTasks().size();
		int coluna = RandomFactory.getInstance().nextInt(numeroTasks);
		
		final MatrizDedicacao matrizDedicacaoFilho1 = buildMatrizDedicacaoFilho(pai1, pai2, linha, coluna);
		novosFilhos.add(new Individuo(matrizDedicacaoFilho1));
		
		final MatrizDedicacao matrizDedicacaoFilho2 = buildMatrizDedicacaoFilho(pai2, pai1, linha, coluna);
		novosFilhos.add(new Individuo(matrizDedicacaoFilho2));
	}

	private MatrizDedicacao buildMatrizDedicacaoFilho(Individuo pai1, Individuo pai2, int linha, int coluna) {
		MatrizDedicacao matrizDedicacao = new MatrizDedicacao();
		for (int i = 0; i < ProblemaBuilder.getInstance().getEmployees().size(); i++) {
			for (int j = 0; j < ProblemaBuilder.getInstance().getTasks().size(); j++) {
				GrauDedicacao grauDedicacao;
				if ((i <= linha && j <= coluna) || (i > linha && j > coluna)) {
					grauDedicacao = pai1.getMatrizDedicacao().getGrauDedicacao(i, j);
				} else {
					grauDedicacao = pai2.getMatrizDedicacao().getGrauDedicacao(i, j);
				}
				
				matrizDedicacao.setGrauDedicacao(i, j, grauDedicacao);
			}
		}
		
		matrizDedicacao.efetuaCalculosProjeto();
		return matrizDedicacao;
	}

	public void efetuarMutacao(Double percentualMutacao) {
		int numeroIndividuosMutacao = Double.valueOf(individuos.size() * (percentualMutacao/100)).intValue();
		for (int i = 0; i < numeroIndividuosMutacao; i++) {
			getIndividuoAleatorio().efetuarMutacao();
		}
	}

	public Individuo getMelhorIndividuo() {
		return individuos.stream()
				.max((i1, i2) -> i1.getValorFitness().compareTo(i2.getValorFitness()))
				.get();
	}
	
	public Double getMaiorValorFitness() {
		return individuos.stream()
			.mapToDouble(Individuo::getValorFitness)
			.max()
			.getAsDouble();
	}
	
	public Double getMenorValorFitness() {
		return individuos.stream()
			.mapToDouble(Individuo::getValorFitness)
			.min()
			.getAsDouble();
	}
	
	public Double getMediaValorFitness() {
		return individuos.stream()
			.mapToDouble(Individuo::getValorFitness)
			.average()
			.getAsDouble();
	}
	
	public Double getMenorValorCustoProjeto() {
		return individuos.stream()
			.mapToDouble(i -> i.getMatrizDedicacao().getCustoTotalProjeto())
			.min()
			.getAsDouble();
	}
	
	public Double getMaiorValorCustoProjeto() {
		return individuos.stream()
			.mapToDouble(i -> i.getMatrizDedicacao().getCustoTotalProjeto())
			.max()
			.getAsDouble();
	}
	
	public Double getMediaValorCustoProjeto() {
		return individuos.stream()
			.mapToDouble(i -> i.getMatrizDedicacao().getCustoTotalProjeto())
			.average()
			.getAsDouble();
	}
	
	public Double getMenorDuracaoProjeto() {
		return individuos.stream()
			.mapToDouble(i -> i.getMatrizDedicacao().getDuracaoTotalProjeto())
			.min()
			.getAsDouble();
	}
	
	public Double getMaiorDuracaoProjeto() {
		return individuos.stream()
			.mapToDouble(i -> i.getMatrizDedicacao().getDuracaoTotalProjeto())
			.max()
			.getAsDouble();
	}
	
	public Double getMediaDuracaoProjeto() {
		return individuos.stream()
			.mapToDouble(i -> i.getMatrizDedicacao().getDuracaoTotalProjeto())
			.average()
			.getAsDouble();
	}
	
	public void addIndividuo(Individuo individuo) {
		this.individuos.add(individuo);
	}

	public List<Individuo> getIndividuos() {
		return individuos;
	}
	
}
