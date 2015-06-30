package uem.br.ag.peps;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringBetween;

import org.apache.commons.lang3.StringUtils;

public class ParametroTaskSkill extends ParametroLinha {

	private int task;
	
	private int skill;
	
	private int value;

	@Override
	protected void readParameter(String linha) {
		task = valueOf(substringBetween(linha, "task.", ".skill."));
		skill = valueOf(StringUtils.substringBetween(linha, "skill.", "="));
		value = valueOf(StringUtils.substringAfter(linha, "="));
	}
	
	@Override
	protected String getPattern() {
		return "task.\\d+.skill.\\d+=\\d+";
	}
	
	public int getSkill() {
		return skill;
	}
	
	public int getTask() {
		return task;
	}
	
	public int getValue() {
		return value;
	}
	
	public static void main(String[] args) {
		String linha = "task.3.skill.4=700";
		System.out.println(linha.matches("task.\\d.skill.\\d=\\d+"));
		
		System.out.println(valueOf(substringBetween(linha, "task.", ".skill.")));;
		System.out.println(valueOf(StringUtils.substringBetween(linha, "skill.", "=")));;
		System.out.println(valueOf(StringUtils.substringAfter(linha, "=")));;
	}
	
}
