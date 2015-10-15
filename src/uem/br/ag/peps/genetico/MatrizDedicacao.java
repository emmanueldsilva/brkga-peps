package uem.br.ag.peps.genetico;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.math.MathContext.DECIMAL32;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
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
	
	private List<FaseProjeto> fasesProjeto = new ArrayList<FaseProjeto>();
	
	private List<TaskScheduling> escalaTarefas = new ArrayList<TaskScheduling>();

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
	
	public void efetuaCalculosProjeto() {
		calculaDuracoesTasks();
		calculaTaskScheduling();
		calculaFasesRealizacaoProjeto();
	}
	
	/**
	 * tjduracao
	 */
	public void calculaDuracoesTasks() {
		for (int taskNumero = 0; taskNumero < matrizDedicacao[0].length; taskNumero++) {
			final Task task = ProblemaBuilder.getInstance().getTask(taskNumero);
			final BigDecimal esforcoTask = BigDecimal.valueOf(task.getCusto());
			final BigDecimal duracaoTask = esforcoTask.divide(calculaSomatorioDedicacaoTask(taskNumero), DECIMAL32);
			
			escalaTarefas.add(taskNumero, new TaskScheduling(task, duracaoTask.doubleValue()));
		}
	}
	
	public void calculaTaskScheduling() {
		for (Task task : ProblemaBuilder.getInstance().getTasks()) {
			calculaTaskScheduling(task);
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
				final TaskScheduling taskScheduling = getTaskScheduling(ProblemaBuilder.getInstance().getTask(task));
				final BigDecimal duracaoTask = BigDecimal.valueOf(taskScheduling.getDuracao());
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
	
	/**
	 * Restrição 3: não pode haver trabalho extra no projeto. Trabalho extra ocorre quando um ou mais empregados tem um nível de dedicação
	 * maior que seu nível de dedicação máximo.
	 * @return
	 */
	public boolean isSolucaoValidaPeranteRestricao3() {
		calculaTaskScheduling();
		
		calculaFasesRealizacaoProjeto();
		
		final BigDecimal esforcoExtraTotalProjeto = calculaEsforcoExtraTotalProjeto();
		
		return esforcoExtraTotalProjeto.compareTo(ZERO) == 0;
	}

	private BigDecimal calculaEsforcoExtraTotalProjeto() {
		BigDecimal esforcoExtraTotalProjeto = ZERO;
		for (Employee employee : ProblemaBuilder.getInstance().getEmployees()) {
			esforcoExtraTotalProjeto = esforcoExtraTotalProjeto.add(calculaEsforcoExtraFuncionario(employee));
		}
		
		return esforcoExtraTotalProjeto;
	}

	private BigDecimal calculaEsforcoExtraFuncionario(Employee employee) {
		BigDecimal esforcoExtraFuncionario = ZERO;
		for (FaseProjeto faseProjeto : fasesProjeto) {
			esforcoExtraFuncionario = esforcoExtraFuncionario.add(calculaEsforcoExtraFuncionarioFase(employee, faseProjeto));
		}
		
		return esforcoExtraFuncionario;
	}

	private BigDecimal calculaEsforcoExtraFuncionarioFase(Employee employee, FaseProjeto faseProjeto) {
		final BigDecimal esforcoFase = calculaEsforcoFuncionarioFase(employee, faseProjeto);
		if (esforcoFase.compareTo(ONE) > 0) {
			return calculaEsforcoExtraFuncionarioProjeto(faseProjeto, esforcoFase);
		}
		
		return ZERO;
	}

	private BigDecimal calculaEsforcoExtraFuncionarioProjeto(FaseProjeto faseProjeto, BigDecimal esforcoFase) {
		//(esforcoFase - 1.0) * faseProjeto.getDuracaoFase() 
		//(esforcoFase - 1.0) pois o valor limite do esforço é 1.0, acima disso é esforço extra
		BigDecimal duracaoFase = BigDecimal.valueOf(faseProjeto.getDuracaoFase());
 		
		return (esforcoFase.subtract(ONE)).multiply(duracaoFase);
	}

	private BigDecimal calculaEsforcoFuncionarioFase(Employee employee, FaseProjeto faseProjeto) {
		BigDecimal esforcoFase = BigDecimal.ZERO;
		for (TaskScheduling taskScheduling : faseProjeto.getEscalasConcomitantes()) {
			final Double valorGrauDedicacao = getGrauDedicacao(employee, taskScheduling.getTask()).getValor();
			esforcoFase = esforcoFase.add(valueOf(valorGrauDedicacao));
		}
		
		return esforcoFase;
	}
	
	public void calculaFasesRealizacaoProjeto() {
		final Double tempoTotalProjeto = calculaTempoTotalProjeto();

		Double inicioFase = DOUBLE_ZERO;
		while (inicioFase < tempoTotalProjeto) {
			final TaskScheduling taskMaisCedoASerConcluida = getPrimeiraEscalaTarefaASerConcluida(inicioFase);
			final List<TaskScheduling> taskSchedulingConcomitantes = getTaskSchedulingConcomitantes(taskMaisCedoASerConcluida);
			taskSchedulingConcomitantes.add(taskMaisCedoASerConcluida);
			
			fasesProjeto.add(new FaseProjeto(inicioFase, taskMaisCedoASerConcluida.getTempoFim(), taskSchedulingConcomitantes));

			inicioFase = taskMaisCedoASerConcluida.getTempoFim();
		}
	}

	private TaskScheduling getPrimeiraEscalaTarefaASerConcluida(Double tempoInicioFase) {
		if (DOUBLE_ZERO.equals(tempoInicioFase)) {
			return escalaTarefas.stream()
                                .filter(rt -> DOUBLE_ZERO.equals(rt.getTempoInicio()))
                                .findFirst()
                                .get();
		}
		
		return escalaTarefas.stream()
                            .filter(rt -> rt.getTempoFim() > tempoInicioFase)
                            .min(comparingDouble(TaskScheduling::getTempoFim))
                            .get();
	}

	private List<TaskScheduling> getTaskSchedulingConcomitantes(TaskScheduling taskMaisCedoASerConcluida) {
		Double tempoFim = taskMaisCedoASerConcluida.getTempoFim();
		return escalaTarefas.stream()
                            .filter(rt -> rt.getTempoInicio() < tempoFim && tempoFim < rt.getTempoFim())
                            .collect(toList());
	}

	public Double calculaTempoTotalProjeto() {
		return escalaTarefas.stream()
                            .max((rt1, rt2) -> rt1.getTempoFim().compareTo(rt2.getTempoFim()))
                            .get()
                            .getTempoFim();
	}

	private TaskScheduling calculaTaskScheduling(Task task) {
		if (!task.hasPreviousTasks()) {
			return buildSchedulingTaskInicial(task);
		} 

		return buildSchedulingTaskDependente(task);
	}

	private TaskScheduling buildSchedulingTaskInicial(Task task) {
		return getTaskScheduling(task).buildSchedulingTaskInicial();
	}
	
	private TaskScheduling buildSchedulingTaskDependente(Task task) {
		final List<TaskScheduling> schedulingPrecedentes = new ArrayList<TaskScheduling>();
		for (Task previousTask : task.getPreviousTasks()) {
			schedulingPrecedentes.add(calculaTaskScheduling(previousTask));
		}
		
		final TaskScheduling schedulingPrecedenteMaisTardio = schedulingPrecedentes.stream()
				.max(comparingDouble(TaskScheduling::getTempoFim))
				.get();
		
		final Double tempoInicio = schedulingPrecedenteMaisTardio.getTempoFim();
		final TaskScheduling taskScheduling = getTaskScheduling(task);
		return taskScheduling.buildTaskScheduling(tempoInicio);
	}
	
	public TaskScheduling getTaskScheduling(Task task) {
		return escalaTarefas.stream()
                            .filter(r -> r.getTask().equals(task))
                            .findFirst().get();
	}

	private List<GrauDedicacao> getGrausDedicacao(int task) {
		final List<GrauDedicacao> grausDedicacao = Lists.newArrayList();
		for (int employee = 0; employee < matrizDedicacao.length; employee++) {
			grausDedicacao.add(employee, getGrauDedicacao(employee, task));
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
		return getGrauDedicacao(employee.getCodigo(), task.getNumero());
	}
	
	private GrauDedicacao getGrauDedicacao(int codigoEmployee, int taskNumber) {
		return matrizDedicacao[codigoEmployee][taskNumber];
	}
	
	public String toBinaryString() {
		String matrizDedicacaoBinaria = EMPTY;
		for (Employee employee : ProblemaBuilder.getInstance().getEmployees()) {
			for (Task task: ProblemaBuilder.getInstance().getTasks()) {
				final GrauDedicacao grauDedicacao = getGrauDedicacao(employee, task);
				
				matrizDedicacaoBinaria += grauDedicacao.getValorAsBinary();
			}
		}
		
		return matrizDedicacaoBinaria;
	}

	public GrauDedicacao[][] getMatrizDedicacao() {
		return matrizDedicacao;
	}

	public List<TaskScheduling> getEscalaTarefas() {
		return escalaTarefas;
	}
	
	public List<FaseProjeto> getFasesProjeto() {
		return fasesProjeto;
	}

}
