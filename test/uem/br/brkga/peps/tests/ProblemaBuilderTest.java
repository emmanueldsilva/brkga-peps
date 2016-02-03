package uem.br.brkga.peps.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uem.br.brkga.peps.entidade.Employee;
import uem.br.brkga.peps.entidade.Skill;
import uem.br.brkga.peps.entidade.Task;
import uem.br.brkga.peps.problema.ProblemaBuilder;

public class ProblemaBuilderTest {

	private final ProblemaBuilder problemaBuilder = ProblemaBuilder.getInstance();
	
	@Before
	public void before() {
		problemaBuilder.clear();
		problemaBuilder.setParametrosPath("/home/emmanuel/projetos/ag-peps/test-resources/test-intances/peps-4-tasks-2-employees.conf");
		problemaBuilder.readParametrosArquivo();
	}

	@Test
	public void testCriacaoProblema() {
		assertEmployees(problemaBuilder);
		assertSkills(problemaBuilder);
		assertTasks(problemaBuilder);
		assertGrafoTasks(problemaBuilder);
	}

	private void assertEmployees(ProblemaBuilder problemaBuilder) {
		assertEquals(2, problemaBuilder.getEmployees().size());
		
		final Employee employee1 = problemaBuilder.getEmployee(0);
		assertEquals(Double.valueOf(3000.0), employee1.getSalario());
		assertEquals(2, employee1.getSkills().size());
		
		assertEquals(0, employee1.getSkills().get(0).getCodigo());
		assertEquals(1, employee1.getSkills().get(1).getCodigo());
		
		final Employee employee2 = problemaBuilder.getEmployee(1);
		assertEquals(Double.valueOf(2000.0), employee2.getSalario());
		assertEquals(2, employee2.getSkills().size());
		
		assertEquals(0, employee2.getSkills().get(0).getCodigo());
		assertEquals(2, employee2.getSkills().get(1).getCodigo());
	}
	
	private void assertSkills(ProblemaBuilder problemaBuilder) {
		final List<Skill> skills = problemaBuilder.getSkills();
		assertEquals(3, skills.size());
		assertEquals(0, problemaBuilder.getSkill(0).getCodigo());
		assertEquals(1, problemaBuilder.getSkill(1).getCodigo());
		assertEquals(2, problemaBuilder.getSkill(2).getCodigo());
	}
	
	private void assertTasks(ProblemaBuilder problemaBuilder) {
		final List<Task> tasks = problemaBuilder.getTasks();
		assertEquals(4, tasks.size());
		
		final Task task1 = problemaBuilder.getTask(0);
		assertEquals(0, task1.getNumero());
		assertEquals(Double.valueOf(4.0), task1.getCusto());
		assertEquals(1, task1.getSkills().size());
		assertEquals(1, task1.getSkills().size());
		assertEquals(0, task1.getSkills().get(0).getCodigo());
		
		final Task task2 = problemaBuilder.getTask(1);
		assertEquals(1, task2.getNumero());
		assertEquals(Double.valueOf(3.0), task2.getCusto());
		assertEquals(1, task2.getSkills().size());
		assertEquals(1, task2.getSkills().get(0).getCodigo());
		
		final Task task3 = problemaBuilder.getTask(2);
		assertEquals(2, task3.getNumero());
		assertEquals(Double.valueOf(5.0), task3.getCusto());
		assertEquals(1, task3.getSkills().size());
		assertEquals(2, task3.getSkills().get(0).getCodigo());
		
		final Task task4 = problemaBuilder.getTask(3);
		assertEquals(3, task4.getNumero());
		assertEquals(Double.valueOf(1.0), task4.getCusto());
		assertEquals(1, task4.getSkills().size());
		assertEquals(1, task4.getSkills().get(0).getCodigo());
	}
	
	private void assertGrafoTasks(ProblemaBuilder problemaBuilder) {
		final Task task0 = problemaBuilder.getTask(0);
		assertTrue(task0.getPreviousTasks().isEmpty());
		assertEquals(1, task0.getNextTasks().size());
		assertEquals(1, task0.getNextTasks().get(0).getNumero());
		
		final Task task1 = problemaBuilder.getTask(1);
		assertEquals(1, task1.getPreviousTasks().size());
		assertEquals(0, task1.getPreviousTasks().get(0).getNumero());
		assertEquals(1, task1.getNextTasks().size());
		assertEquals(2, task1.getNextTasks().get(0).getNumero());
		
		final Task task2 = problemaBuilder.getTask(2);
		assertEquals(1, task2.getPreviousTasks().size());
		assertEquals(1, task2.getPreviousTasks().get(0).getNumero());
		assertEquals(1, task2.getNextTasks().size());
		assertEquals(3, task2.getNextTasks().get(0).getNumero());
		
		final Task task3 = problemaBuilder.getTask(3);
		assertEquals(1, task3.getPreviousTasks().size());
		assertEquals(2, task3.getPreviousTasks().get(0).getNumero());
		assertTrue(task3.getNextTasks().isEmpty());
	}
	
}
