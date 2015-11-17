package uem.br.ag.peps.genetico;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import uem.br.ag.peps.problema.ProblemaBuilder;
import uem.br.ag.peps.utils.PrintFactory;
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
		while (individuos.size() >= tamanhoPopulacao) {
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

	public void efetuarCruzamento() {
		final List<Individuo> novosFilhos = newArrayList();
		for (int cont = 0; cont < tamanhoPopulacao/2; cont++) {
			Individuo pai1 = getIndividuoAleatorio();
			Individuo pai2 = getIndividuoAleatorio();
			
			int count = 0;
			while (pai1.equals(pai2) && count < 5) {
				pai1 = getIndividuoAleatorio();
				count++;
			}
			
			int numeroEmployees = ProblemaBuilder.getInstance().getEmployees().size();
			int linha = RandomFactory.getInstance().nextInt(numeroEmployees);
			
			int numeroTasks = ProblemaBuilder.getInstance().getTasks().size();
			int coluna = RandomFactory.getInstance().nextInt(numeroTasks);
			
			MatrizDedicacao matrizDedicacaoFilho1 = buildMatrizDedicacaoFilho(pai1, pai2, linha, coluna);
			novosFilhos.add(new Individuo(matrizDedicacaoFilho1));
			
			MatrizDedicacao matrizDedicacaoFilho2 = buildMatrizDedicacaoFilho(pai2, pai1, linha, coluna);
			novosFilhos.add(new Individuo(matrizDedicacaoFilho2));
		}
		
		individuos.addAll(novosFilhos);
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
		individuos.forEach(i -> {
            if (RandomFactory.getInstance().nextInt(100) < percentualMutacao) {
                i.efetuarMutacao();
            }
		});
	}

	public void imprimirPopulacao() {
		PrintFactory.imprimePopulacao(individuos);
	}
	
	public void addIndividuo(Individuo individuo) {
		this.individuos.add(individuo);
	}

	public List<Individuo> getIndividuos() {
		return individuos;
	}
	
}
