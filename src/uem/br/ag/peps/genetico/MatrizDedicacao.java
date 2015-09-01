package uem.br.ag.peps.genetico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import uem.br.ag.peps.entidade.Employee;
import uem.br.ag.peps.entidade.Task;
import uem.br.ag.peps.problema.ProblemaBuilder;

public class MatrizDedicacao {
	
	private GrauDedicacao[][] matrizDedicacao;
	
	private List<Double> duracoesTask = new ArrayList<Double>();

	public MatrizDedicacao() {
		int numeroEmpregados = ProblemaBuilder.getInstance().getEmployees().size();
		int numeroTarefas = ProblemaBuilder.getInstance().getTasks().size();
		
		this.matrizDedicacao = new GrauDedicacao[numeroEmpregados][numeroTarefas];
	}
	
	public void addGrauDedicacao(Employee employee, Task task, Double value) {
		final GrauDedicacao grauDedicacao = new GrauDedicacao();
		grauDedicacao.setEmployee(employee);
		grauDedicacao.setTask(task);
		grauDedicacao.setValor(value);
		
		this.matrizDedicacao[employee.getCodigo()][task.getNumero()] = grauDedicacao;
	}
	
	
	public void calculaDuracoesTasks() {
		for (int col = 0; col < matrizDedicacao.length; col++) {
			BigDecimal duracaoTask = BigDecimal.ZERO;
			BigDecimal dedicacaoTask = BigDecimal.ZERO;
			BigDecimal esforcoTask = BigDecimal.valueOf(ProblemaBuilder.getInstance().getTask(col).getCusto());
			for (int row = 0; row < matrizDedicacao.length; row++) {
				dedicacaoTask = dedicacaoTask.add(BigDecimal.valueOf(matrizDedicacao[row][col].getValor()));
			}
			
			esforcoTask = esforcoTask.divide(dedicacaoTask, RoundingMode.HALF_EVEN);
			duracoesTask.add(col, duracaoTask.doubleValue());
		}
	}
	
}
