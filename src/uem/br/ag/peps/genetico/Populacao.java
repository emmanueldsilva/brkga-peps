package uem.br.ag.peps.genetico;

import java.util.List;

import com.google.common.collect.Lists;

public class Populacao {

	private int tamanhoPopulacao;
	
	private List<Individuo> individuos = Lists.newArrayList();
	
	public Populacao(int tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
		
        gerarIndividuos();
    }
 
    private void gerarIndividuos() {
        for (int i = 0; i < tamanhoPopulacao; i++) {
            gerarIndividuoAleatorio();
        }
    }
 
    private void gerarIndividuoAleatorio() {
    	MatrizDedicacao matrizDedicacao = buildMatrizDedicacaoAleatoria();
    	
        Individuo individuo = new Individuo(matrizDedicacao);
        addIndividuo(individuo);
    }

	private MatrizDedicacao buildMatrizDedicacaoAleatoria() {
		return null;
	}

	private void addIndividuo(Individuo individuo) {
		individuos.add(individuo);
	}

	public void avaliarIndividuos() {
		individuos.forEach(i -> { 
			i.verificaFactibilidade();
			i.calculaValorFitness();	   
		});
	}

	public void selecionarMaisAptosPorTorneio() {
		// TODO Auto-generated method stub
		
	}

	public void efetuarCruzamento() {
		// TODO Auto-generated method stub
		
	}

	public void efetuarMutacao(Double percentualMutacao) {
		// TODO Auto-generated method stub
		
	}

	public void imprimirPopulacao() {
		// TODO Auto-generated method stub
		
	}
	
	
}
