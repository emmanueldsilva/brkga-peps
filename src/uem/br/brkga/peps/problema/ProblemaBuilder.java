package uem.br.brkga.peps.problema;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Integer.compare;
import static java.math.BigDecimal.ONE;
import static java.util.Collections.emptyList;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import uem.br.brkga.peps.entidade.Employee;
import uem.br.brkga.peps.entidade.Skill;
import uem.br.brkga.peps.entidade.Task;
import uem.br.brkga.peps.parametros.ParametroEmployeeNumber;
import uem.br.brkga.peps.parametros.ParametroEmployeeSalary;
import uem.br.brkga.peps.parametros.ParametroEmployeeSkill;
import uem.br.brkga.peps.parametros.ParametroGraphArc;
import uem.br.brkga.peps.parametros.ParametroLinha;
import uem.br.brkga.peps.parametros.ParametroReader;
import uem.br.brkga.peps.parametros.ParametroSkillNumber;
import uem.br.brkga.peps.parametros.ParametroTaskCost;
import uem.br.brkga.peps.parametros.ParametroTaskNumber;
import uem.br.brkga.peps.parametros.ParametroTaskSkill;

public class ProblemaBuilder {

	private String parametrosPath;
	
	private List<ParametroLinha> parametros = emptyList();
	
	private List<Skill> skills = newArrayList();
	
	private List<Employee> employees = newArrayList();
	
	private List<Task> tasks = newArrayList();
	
	private List<String> atuacoesEmployees = newArrayList();
	
	public static ProblemaBuilder instance;
	
	private synchronized static void newInstance() {
		instance = new ProblemaBuilder();
	}

	public static ProblemaBuilder getInstance() {
		if (instance == null) {
			newInstance();
		}
		
		return instance;
	}

	private ProblemaBuilder() {	}

	public void readParametrosArquivo() {
		try {
			ParametroReader parametroReader = new ParametroReader();
			parametroReader.setFilePath(parametrosPath);
			parametroReader.readParametrosFile();
			parametros = parametroReader.getParametros();
			
			readSkills();
			readEmployees();
			buildAtuacoesEmployees();
			
			readTasks();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao ler o arquivo de parâmetros");
		}
	}

	private void readSkills() {
		final ParametroSkillNumber parametroSkillNumber = (ParametroSkillNumber) parametros.stream()
																						   .filter(p -> p instanceof ParametroSkillNumber)
																						   .findFirst().get();
		int quantidadeSkills = parametroSkillNumber.getValue();
		for (int i = 0; i < quantidadeSkills; i++) {
			skills.add(new Skill(i));
		}
	}
	
	private void readEmployees() {
		final ParametroEmployeeNumber parametroEmployeeNumber = (ParametroEmployeeNumber) parametros.stream()
																								    .filter(p -> p instanceof ParametroEmployeeNumber)
																								    .findFirst().get();
		
		int quantidadeEmployees = parametroEmployeeNumber.getValue();
		for (int i = 0; i < quantidadeEmployees; i++) {
			final Employee employee = new Employee();
			employee.setCodigo(i);
			employee.setSalario(readSalario(i));
			employee.setSkills(readEmployeeSkills(i));
			employees.add(employee);
		}
	}
	
	private void buildAtuacoesEmployees(){
		int numeroEmployees = ProblemaBuilder.getInstance().getNumeroEmployees();
		final String[] combinacoesAtuacoesEmployees = getAllLists(new String[]{"0", "1", "2", "3", "4", "5", "6", "7"}, numeroEmployees);
		for (String combinacao : combinacoesAtuacoesEmployees) {
			atuacoesEmployees.add(StringUtils.reverse(combinacao));
		}
	}
	
	public static String[] getAllLists(String[] elements, int lengthOfList)	{
	    //initialize our returned list with the number of elements calculated above
	    String[] allLists = new String[(int)Math.pow(elements.length, lengthOfList)];

	    //lists of length 1 are just the original elements
	    if(lengthOfList == 1) return elements; 
	    else {
	        //the recursion--get all lists of length 3, length 2, all the way up to 1
	        String[] allSublists = getAllLists(elements, lengthOfList - 1);

	        //append the sublists to each element
	        int arrayIndex = 0;

	        for(int i = 0; i < elements.length; i++) {
	            for(int j = 0; j < allSublists.length; j++) {
	                //add the newly appended combination to the list
	                allLists[arrayIndex] = elements[i] + allSublists[j];
	                arrayIndex++;
	            }
	        }

	        return allLists;
	    }
	}
	
	public static void main(String[] args) {
		System.out.println(Arrays.deepToString(getAllLists(new String[]{"0", "1", "2", "3", "4", "5", "6", "7"}, 10)));
	}
	
	
	private int calculaSomatorioCombinacoesEmpregados() {
		return new BigDecimal(8).pow(ProblemaBuilder.getInstance().getNumeroEmployees()).intValue();
	}
	
	private void readTasks() {
		final ParametroTaskNumber parametroTaskNumber = (ParametroTaskNumber) parametros.stream()
																						.filter(p -> p instanceof ParametroTaskNumber)
																						.findFirst().get();
		
		int quantidadeTasks = parametroTaskNumber.getValue();
		for (int i = 0; i < quantidadeTasks; i++) {
			final Task task = new Task();
			task.setNumero(i);
			task.setCusto(readCustoTask(i));
			task.setSkills(readTaskSkills(i));
			tasks.add(task);
		}
		
		buildHierarquiaEntreTasks();
	}

	private void buildHierarquiaEntreTasks() {
		parametros.stream()
				  .filter(p -> (p instanceof ParametroGraphArc))
				  .sorted((p1, p2)-> compare(((ParametroGraphArc) p1).getNumeroArco(), ((ParametroGraphArc) p2).getNumeroArco()))
				  .forEach(p -> buildRelacionamentoEntreTasks((ParametroGraphArc) p));
	}

	private void buildRelacionamentoEntreTasks(ParametroGraphArc p) {
		final Task taskInicial = tasks.get(p.getNo1());
		final Task taskFinal = tasks.get(p.getNo2());
		taskInicial.addNextTask(taskFinal);
		taskFinal.addPreviousTask(taskInicial);
	}

	private List<Skill> readTaskSkills(int numero) {
		final List<Skill> skills = new ArrayList<Skill>();
		parametros.stream()
				  .filter(p -> (p instanceof ParametroTaskSkill) && ((ParametroTaskSkill) p).getTask() == numero)
				  .sorted((p1, p2)-> compare(((ParametroTaskSkill) p1).getSkill(), ((ParametroTaskSkill) p2).getSkill()))
				  .forEach(p -> skills.add(this.skills.get(((ParametroTaskSkill) p).getValue())));
		
		return skills;
	}

	private List<Skill> readEmployeeSkills(int codigo) {
		final List<Skill> skills = new ArrayList<Skill>();
		parametros.stream()
				  .filter(p -> (p instanceof ParametroEmployeeSkill) && ((ParametroEmployeeSkill) p).getEmployee() == codigo)
				  .sorted((p1, p2) -> compare(((ParametroEmployeeSkill) p1).getSkill(), ((ParametroEmployeeSkill) p2).getSkill()))
				  .forEach(p -> skills.add(this.skills.get(((ParametroEmployeeSkill) p).getValue())));
		
		return skills;
	}

	private Double readSalario(int codigo) {
		return ((ParametroEmployeeSalary) parametros.stream()
													.filter(p -> p instanceof ParametroEmployeeSalary && ((ParametroEmployeeSalary) p).getEmployee() == codigo)
													.findFirst().get()).getValue();
	}
	
	private Double readCustoTask(int numero) {
		return ((ParametroTaskCost) parametros.stream()
											  .filter(p -> p instanceof ParametroTaskCost && ((ParametroTaskCost) p).getTask() == numero)
											  .findFirst().get()).getValue();
	}
	
	public Task getTask(int numero) {
		return tasks.stream()
					.filter(t -> t.getNumero() == numero)
					.findFirst().get();
	}
	
	public Employee getEmployee(int codigo) {
		return employees.stream()
				.filter(e -> e.getCodigo() == codigo)
				.findFirst().get();
	}
	
	public Skill getSkill(int codigo) {
		return skills.stream()
				.filter(s -> s.getCodigo() == codigo)
				.findFirst().get();
	}
	
	public void clear() {
		this.parametrosPath = null;
		this.employees = newArrayList();
		this.tasks = newArrayList();
		this.skills = newArrayList();
	}
	
	public void setParametrosPath(String parametrosPath) {
		this.parametrosPath = parametrosPath;
	}

	public List<ParametroLinha> getParametros() {
		return parametros;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public List<Employee> getEmployees() {
		return employees;
	}
	
	public List<String> getAtuacoesEmployees() {
		return atuacoesEmployees;
	}
	
	public String getAtuacoesEmployeesBy(Double valorCodificado){
		final BigDecimal somatorioCombinacoesEmpregados = new BigDecimal(calculaSomatorioCombinacoesEmpregados());
		final BigDecimal unidadeCombinatoriaEmpregados = ONE.divide(somatorioCombinacoesEmpregados, MathContext.DECIMAL32);
		final BigDecimal valorIndex = new BigDecimal(valorCodificado).divideToIntegralValue(unidadeCombinatoriaEmpregados);
		
		return getAtuacoesEmployeesBy(valorIndex.intValue());
	}
	
	public String getAtuacoesEmployeesBy(int task){
		return atuacoesEmployees.get(task);
	}
	
	public int getNumeroEmployees() {
		return employees.size();
	}

	public List<Task> getTasks() {
		return tasks;
	}
	
	public int getNumeroTasks() {
		return tasks.size();
	}

}
