package uem.br.ag.peps.genetico;

import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL32;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uem.br.ag.peps.entidade.Employee;
import uem.br.ag.peps.entidade.Skill;
import uem.br.ag.peps.entidade.Task;
import uem.br.ag.peps.problema.ProblemaBuilder;

import com.google.common.collect.Lists;

public class MatrizDedicacao {
	
	private GrauDedicacao[][] matrizDedicacao = null;
	
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
	
	/**
	 * tjduracao
	 */
	public void calculaDuracoesTasks() {
		for (int task = 0; task < matrizDedicacao[0].length; task++) {
			final BigDecimal esforcoTask = BigDecimal.valueOf(ProblemaBuilder.getInstance().getTask(task).getCusto());
			final BigDecimal duracaoTask = esforcoTask.divide(calculaSomatorioDedicacaoTask(task), DECIMAL32);
			duracoesTask.add(task, duracaoTask.doubleValue());
		}
	}

	public BigDecimal calculaSomatorioDedicacaoTask(Task task) {
		return calculaSomatorioDedicacaoTask(task.getNumero());
	}
	
	private BigDecimal calculaSomatorioDedicacaoTask(int task) {
		BigDecimal somatorioDedicacaoTask = BigDecimal.ZERO;
		
		for (int employee = 0; employee < matrizDedicacao.length; employee++) {
			final GrauDedicacao grauDedicacao = matrizDedicacao[employee][task];
			somatorioDedicacaoTask = somatorioDedicacaoTask.add(BigDecimal.valueOf(grauDedicacao.getValor()));
		}
		
		return somatorioDedicacaoTask;
	}
	
	public Double calculaCustoProjeto() {
		BigDecimal custoProjeto = BigDecimal.ZERO;
		
		for (int employee = 0; employee < matrizDedicacao.length; employee++) {
			final BigDecimal salario = BigDecimal.valueOf(ProblemaBuilder.getInstance().getEmployee(employee).getSalario());
			
			for (int task = 0; task < matrizDedicacao[employee].length; task++) {
				final BigDecimal duracaoTask = BigDecimal.valueOf(duracoesTask.get(task));
				final GrauDedicacao grauDedicacao = matrizDedicacao[employee][task];

				final BigDecimal dedicacao = BigDecimal.valueOf(grauDedicacao.getValor());
				custoProjeto = custoProjeto.add(salario.multiply(dedicacao).multiply(duracaoTask));
			}
		}
		
		return custoProjeto.doubleValue();
	}
	
	/**
	 * Restrição 1: Não pode haver tarefa sem ser realizada por algum empregado. Ou seja, não deve haver grau de dedicação <= 0 para uma dada tarefa.
	 * @return
	 */
	public boolean isSolucaoValidaPeranteRestricao1() {
		for (int task = 0; task < matrizDedicacao[0].length; task++) {
			if (calculaSomatorioDedicacaoTask(task).compareTo(ZERO) <= 0) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Restrição 2: Toda tarefa deve ter funcionários aptos a realizá-la. Ou seja, deve haver pelo menos um funcionário atuando sobre essa tarefa que 
	 * possua as habilidades necessárias para realizá-la.
	 * @return
	 */
	public boolean isSolucaoValidaPeranteRestricao2() {
		for (int task = 0; task < matrizDedicacao[0].length; task++) {
			final List<Skill> employeesSkills = getSkillsDosEmployeesQueAtuamNaTask(task);
			final List<Skill> taskSkills = ProblemaBuilder.getInstance().getTask(task).getSkills();
			employeesSkills.retainAll(taskSkills);
			
			
			if (!employeesSkills.equals(taskSkills)) return false;
		}
		
		return true;
	}
	
	public boolean isSolucaoValidaPeranteRestricao3() {
		return true;
	}
	
	private List<GrauDedicacao> getGrausDedicacao(int task) {
		final List<GrauDedicacao> grausDedicacao = Lists.newArrayList();
		for (int employee = 0; employee < matrizDedicacao.length; employee++) {
			grausDedicacao.add(employee, matrizDedicacao[employee][task]);
		}
		
		return grausDedicacao;
	}
	
	private List<Skill> getSkillsDosEmployeesQueAtuamNaTask(int task) {
		return getGrausDedicacao(task).stream()
									  .filter(g -> g.getValor() > DOUBLE_ZERO)
									  .map(g -> g.getEmployee())
									  .flatMap(e -> e.getSkills().stream())
									  .distinct()
									  .collect(Collectors.toList());
	}
	
	public GrauDedicacao getGrauDedicacao(Employee employee, Task task) {
		return matrizDedicacao[employee.getCodigo()][task.getNumero()];
	}

	public GrauDedicacao[][] getMatrizDedicacao() {
		return matrizDedicacao;
	}

	public List<Double> getDuracoesTask() {
		return duracoesTask;
	}
	
}
