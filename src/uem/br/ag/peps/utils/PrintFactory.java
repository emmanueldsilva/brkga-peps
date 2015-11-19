package uem.br.ag.peps.utils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import uem.br.ag.peps.entidade.Employee;
import uem.br.ag.peps.entidade.Task;
import uem.br.ag.peps.genetico.Individuo;
import uem.br.ag.peps.genetico.MatrizDedicacao;
import uem.br.ag.peps.genetico.Populacao;
import uem.br.ag.peps.problema.ProblemaBuilder;

public class PrintFactory {

	private static final NumberFormat CURRENCY_INSTANCE = NumberFormat.getCurrencyInstance(Locale.US);

	public static void imprimePopulacao(List<Individuo> individuos) {
		System.out.println("--------------------------------------------------------------");
		System.out.println("INDIVÍDUOS");
		System.out.println();
		individuos.forEach(printIndividuo());
	}

	private static Consumer<Individuo> printIndividuo() {
		return new Consumer<Individuo>() {

			@Override
			public void accept(Individuo individuo) {
				System.out.println("==================================================================================");
				System.out.println("FITNESS: " + individuo.fitnessToString());
				
				final MatrizDedicacao matrizDedicacao = individuo.getMatrizDedicacao();
				if (individuo.isFactivel() != null) {
					System.out.println("FACTIVEL: " + individuo.isFactivel());
					System.out.println("TAREFAS NÃO REALIZADAS: " + matrizDedicacao.getTarefasNaoRealizadas());
					System.out.println("HABILIDADES NECESSARIAS: " + matrizDedicacao.getHabilidadesNecessarias());
					System.out.println("TRABALHO EXTRA: " + matrizDedicacao.getTrabalhoExtra());
					System.out.println("CUSTO PROJETO: " + CURRENCY_INSTANCE.format(matrizDedicacao.getCustoTotalProjeto()));
					System.out.println("DURAÇÃO PROJETO: " + matrizDedicacao.getDuracaoTotalProjeto());
				}

				for (Employee employee : ProblemaBuilder.getInstance().getEmployees()) {
					for (Task task: ProblemaBuilder.getInstance().getTasks()) {
						System.out.print(matrizDedicacao.getGrauDedicacao(employee, task).getValor() + "\t");
					}
					
					System.out.println();
				}
				System.out.println("==================================================================================");
				System.out.println();
				System.out.println();
			}
		};
	}

	public static void imprimeEstatisticas(Populacao populacao) {
		System.out.println("--------------------------------------------------------------");
		System.out.println("ESTATÍSTICAS GERAÇÃO");
		System.out.println();
		System.out.println("MELHOR FITNESS: " + populacao.getMaiorValorFitness());
		System.out.println("MEDIA FITNESS: " + populacao.getMediaValorFitness());
		System.out.println("MENOR FITNESS: " + populacao.getMenorValorFitness());
		System.out.println("--------------------------------------------------------------");
		System.out.println("MAIOR CUSTO PROJETO: " + CURRENCY_INSTANCE.format(populacao.getMaiorValorCustoProjeto()));
		System.out.println("MEDIA CUSTO PROJETO: " + CURRENCY_INSTANCE.format(populacao.getMediaValorCustoProjeto()));
		System.out.println("MENOR CUSTO PROJETO: " + CURRENCY_INSTANCE.format(populacao.getMenorValorCustoProjeto()));
		System.out.println("--------------------------------------------------------------");
		System.out.println("MAIOR DURAÇÃO PROJETO: " + populacao.getMaiorDuracaoProjeto());
		System.out.println("MEDIA DURAÇÃO PROJETO: " + populacao.getMediaDuracaoProjeto());
		System.out.println("MENOR DURAÇÃO PROJETO: " + populacao.getMenorDuracaoProjeto());
		System.out.println("--------------------------------------------------------------");
		System.out.println();
		System.out.println();
	}
	
}
