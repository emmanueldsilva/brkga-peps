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
		final String linha = "task.3.skill.4=700";
		Assert.assertTrue(linha.matches(ParametroTaskSkill.pattern()));
		
		final ParametroTaskSkill parametroTaskSkill = new ParametroTaskSkill(linha);
		Assert.assertEquals(3, parametroTaskSkill.getTask());
		Assert.assertEquals(4, parametroTaskSkill.getSkill());
		Assert.assertEquals(700, parametroTaskSkill.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroEmployeeSkill(){
		final String linha = "employee.3.skill.4=5";
		Assert.assertTrue(linha.matches(ParametroEmployeeSkill.pattern()));
		
		final ParametroEmployeeSkill parametroEmployeeSkill = new ParametroEmployeeSkill(linha);
		Assert.assertEquals(3, parametroEmployeeSkill.getEmployee());
		Assert.assertEquals(4, parametroEmployeeSkill.getSkill());
		Assert.assertEquals(5, parametroEmployeeSkill.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroEmployeeSalary(){
		final String linha = "employee.4.salary=10448.133483293168";
		Assert.assertTrue(linha.matches(ParametroEmployeeSalary.pattern()));
		
		final ParametroEmployeeSalary parametroEmployeeSalary = new ParametroEmployeeSalary(linha);
		Assert.assertEquals(4, parametroEmployeeSalary.getEmployee());
		Assert.assertEquals(valueOf(10448.133483293168), parametroEmployeeSalary.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroTaskSkillNumber(){
		final String linha = "task.6.skill.number=3";
		Assert.assertTrue(linha.matches(ParametroTaskSkillNumber.pattern()));
		
		final ParametroTaskSkillNumber parametroTaskSkillNumber = new ParametroTaskSkillNumber(linha);
		Assert.assertEquals(6, parametroTaskSkillNumber.getTask());
		Assert.assertEquals(3, parametroTaskSkillNumber.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroEmployeeSkillNumber(){
		final String linha = "employee.4.skill.number=4";
		Assert.assertTrue(linha.matches(ParametroEmployeeSkillNumber.pattern()));
		
		final ParametroEmployeeSkillNumber parametroEmployeeSkillNumber = new ParametroEmployeeSkillNumber(linha);
		Assert.assertEquals(4, parametroEmployeeSkillNumber.getEmployee());
		Assert.assertEquals(4, parametroEmployeeSkillNumber.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroTaskCost(){
		final String linha = "task.4.cost=7.0";
		Assert.assertTrue(linha.matches(ParametroTaskCost.pattern()));
		
		final ParametroTaskCost parametroTaskCost = new ParametroTaskCost(linha);
		Assert.assertEquals(4, parametroTaskCost.getTask());
		Assert.assertEquals(valueOf(7.0), parametroTaskCost.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroGraphArc(){
		final String linha = "graph.arc.19=6 9";
		Assert.assertTrue(linha.matches(ParametroGraphArc.pattern()));
		
		final ParametroGraphArc parametroGraphArc = new ParametroGraphArc(linha);
		Assert.assertEquals(19, parametroGraphArc.getNumeroArco());
		Assert.assertEquals(6, parametroGraphArc.getNo1());
		Assert.assertEquals(9, parametroGraphArc.getNo2());
	}
	
	@Test
	public void deveEfetuarLeituraParametroSkillNumber(){
		final String linha = "skill.number=10";
		Assert.assertTrue(linha.matches(ParametroSkillNumber.pattern()));
		
		final ParametroSkillNumber parametroSkillNumber = new ParametroSkillNumber(linha);
		Assert.assertEquals(10, parametroSkillNumber.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroTaskNumber(){
		final String linha = "task.number=10";
		Assert.assertTrue(linha.matches(ParametroTaskNumber.pattern()));
		
		final ParametroTaskNumber parametroTaskNumber = new ParametroTaskNumber(linha);
		Assert.assertEquals(10, parametroTaskNumber.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroGraphArcNumber(){
		final String linha = "graph.arc.number=21";
		Assert.assertTrue(linha.matches(ParametroGraphArcNumber.pattern()));
		
		final ParametroGraphArcNumber parametroGraphArcNumber = new ParametroGraphArcNumber(linha);
		Assert.assertEquals(21, parametroGraphArcNumber.getValue());
	}
	
	@Test
	public void deveEfetuarLeituraParametroEmployeeNumber(){
		final String linha = "employee.number=5";
		Assert.assertTrue(linha.matches(ParametroEmployeeNumber.pattern()));
		
		final ParametroEmployeeNumber parametroEmployeeNumber = new ParametroEmployeeNumber(linha);
		Assert.assertEquals(5, parametroEmployeeNumber.getValue());
	}
	
}
