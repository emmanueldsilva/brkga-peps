package uem.br.ag.peps.utils;

import java.util.List;
import java.util.function.Consumer;

import uem.br.ag.peps.entidade.Employee;
import uem.br.ag.peps.entidade.Task;
import uem.br.ag.peps.genetico.Individuo;
import uem.br.ag.peps.genetico.MatrizDedicacao;
import uem.br.ag.peps.problema.ProblemaBuilder;

public class PrintFactory {

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
	
}
