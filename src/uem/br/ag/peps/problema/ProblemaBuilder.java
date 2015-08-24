package uem.br.ag.peps.problema;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import uem.br.ag.peps.entidade.Employee;
import uem.br.ag.peps.entidade.Skill;
import uem.br.ag.peps.parametros.ParametroEmployeeNumber;
import uem.br.ag.peps.parametros.ParametroEmployeeSalary;
import uem.br.ag.peps.parametros.ParametroEmployeeSkill;
import uem.br.ag.peps.parametros.ParametroEmployeeSkillNumber;
import uem.br.ag.peps.parametros.ParametroLinha;
import uem.br.ag.peps.parametros.ParametroReader;
import uem.br.ag.peps.parametros.ParametroSkillNumber;

public class ProblemaBuilder {
	
	private List<ParametroLinha> parametros = emptyList();
	
	private List<Skill> skills = new ArrayList<Skill>();;

	public ProblemaBuilder() {
		readParametrosArquivo();
	}

	private void readParametrosArquivo() {
		try {
			ParametroReader parametroReader = ParametroReader.getInstance();
			parametroReader.setFilePath("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst10-5-10-5.conf");
			parametroReader.readParametrosFile();
			parametros = parametroReader.getParametros();
			
			readSkills();
			readEmployees();
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
		}
	}

	private List<Skill> readEmployeeSkills(int codigo) {
		final Skill[] employeeSkills = new Skill[readNumberOfSkills(codigo)];
		parametros.stream()
				  .filter(p -> (p instanceof ParametroEmployeeSkill) && ((ParametroEmployeeSkill) p).getEmployee() == codigo)
				  .collect(Collectors.toList())
				  .forEach(addSkillToEmployeeSkills(codigo, employeeSkills));
		
		return asList(employeeSkills);
	}

	private int readNumberOfSkills(int codigo) {
		return ((ParametroEmployeeSkillNumber) parametros.stream()
														 .filter(p -> p instanceof ParametroEmployeeSkillNumber && ((ParametroEmployeeSkillNumber) p).getEmployee() == codigo)
														 .findFirst().get()).getValue();
	}

	private Consumer<ParametroLinha> addSkillToEmployeeSkills(int codigo, Skill[] employeeSkills) {
		return new Consumer<ParametroLinha>() {

			@Override
			public void accept(ParametroLinha parametroLinha) {
				ParametroEmployeeSkill parametroEmployeeSkill = (ParametroEmployeeSkill) parametroLinha;
				employeeSkills[parametroEmployeeSkill.getSkill()] = skills.get(parametroEmployeeSkill.getValue());
			}
		};
	}

	private Double readSalario(int codigo) {
		return ((ParametroEmployeeSalary) parametros.stream()
													.filter(p -> p instanceof ParametroEmployeeSalary && ((ParametroEmployeeSalary) p).getEmployee() == codigo)
													.findFirst().get()).getValue();
	}
	
	public static void main(String[] args) throws IOException {
		new ProblemaBuilder();
	}
}
