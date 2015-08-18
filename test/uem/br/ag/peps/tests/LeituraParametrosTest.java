package uem.br.ag.peps.tests;

import static java.lang.Double.valueOf;

import org.junit.Assert;
import org.junit.Test;

import uem.br.ag.peps.parametros.ParametroEmployeeNumber;
import uem.br.ag.peps.parametros.ParametroEmployeeSalary;
import uem.br.ag.peps.parametros.ParametroEmployeeSkill;
import uem.br.ag.peps.parametros.ParametroEmployeeSkillNumber;
import uem.br.ag.peps.parametros.ParametroGraphArc;
import uem.br.ag.peps.parametros.ParametroGraphArcNumber;
import uem.br.ag.peps.parametros.ParametroSkillNumber;
import uem.br.ag.peps.parametros.ParametroTaskCost;
import uem.br.ag.peps.parametros.ParametroTaskNumber;
import uem.br.ag.peps.parametros.ParametroTaskSkill;
import uem.br.ag.peps.parametros.ParametroTaskSkillNumber;

public class LeituraParametrosTest {

	@Test
	public void deveEfetuarLeituraParametroTaskSkill(){
		final ParametroTaskSkill parametroTaskSkill = new ParametroTaskSkill("task.3.skill.4=700");
		
		Assert.assertEquals(3, parametroTaskSkill.getTask());
		Assert.assertEquals(4, parametroTaskSkill.getSkill());
		Assert.assertEquals(700, parametroTaskSkill.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroEmployeeSkill(){
		final ParametroEmployeeSkill parametroEmployeeSkill = new ParametroEmployeeSkill("employee.3.skill.4=5");
		
		Assert.assertEquals(3, parametroEmployeeSkill.getEmployee());
		Assert.assertEquals(4, parametroEmployeeSkill.getSkill());
		Assert.assertEquals(5, parametroEmployeeSkill.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroEmployeeSalary(){
		final ParametroEmployeeSalary parametroEmployeeSalary = new ParametroEmployeeSalary("employee.4.salary=10448.133483293168");
		
		Assert.assertEquals(4, parametroEmployeeSalary.getEmployee());
		Assert.assertEquals(valueOf(10448.133483293168), parametroEmployeeSalary.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroTaskSkillNumber(){
		final ParametroTaskSkillNumber parametroTaskSkillNumber = new ParametroTaskSkillNumber("task.6.skill.number=3");
		
		Assert.assertEquals(6, parametroTaskSkillNumber.getTask());
		Assert.assertEquals(3, parametroTaskSkillNumber.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroEmployeeSkillNumber(){
		final ParametroEmployeeSkillNumber parametroEmployeeSkillNumber = new ParametroEmployeeSkillNumber("employee.4.skill.number=4");
		
		Assert.assertEquals(4, parametroEmployeeSkillNumber.getEmployee());
		Assert.assertEquals(4, parametroEmployeeSkillNumber.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroTaskCost(){
		final ParametroTaskCost parametroTaskCost = new ParametroTaskCost("task.4.cost=7.0");
		
		Assert.assertEquals(4, parametroTaskCost.getTask());
		Assert.assertEquals(valueOf(7.0), parametroTaskCost.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroGraphArc(){
		final ParametroGraphArc parametroGraphArc = new ParametroGraphArc("graph.arc.19=6 9");
		
		Assert.assertEquals(19, parametroGraphArc.getNumeroArco());
		Assert.assertEquals(6, parametroGraphArc.getNo1());
		Assert.assertEquals(9, parametroGraphArc.getNo2());
	}
	
	@Test
	public void deveEfetuarLeituraParametroSkillNumber(){
		final ParametroSkillNumber parametroSkillNumber = new ParametroSkillNumber("skill.number=10");
		
		Assert.assertEquals(10, parametroSkillNumber.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroTaskNumber(){
		final ParametroTaskNumber parametroTaskNumber = new ParametroTaskNumber("task.number=10");
		
		Assert.assertEquals(10, parametroTaskNumber.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroGraphArcNumber(){
		final ParametroGraphArcNumber parametroGraphArcNumber = new ParametroGraphArcNumber("graph.arc.number=21");
		
		Assert.assertEquals(21, parametroGraphArcNumber.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroEmployeeNumber(){
		final ParametroEmployeeNumber parametroEmployeeNumber = new ParametroEmployeeNumber("employee.number=5");
		
		Assert.assertEquals(5, parametroEmployeeNumber.getValue());
	}
	
}
