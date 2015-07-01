package uem.br.ag.peps;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ParametroTaskSkill extends ParametroLinha {

	private final String TASK_PREFIX = "task.";
	private final String SKILL_PREFIX = ".skill.";
	private final String EQUALS = "=";

	private int task;
	
	private int skill;
	
	private int value;

	@Override
	public void readParameter(String linha) {
		task = valueOf(substringBetween(linha, TASK_PREFIX, SKILL_PREFIX));
		skill = valueOf(substringBetween(linha, SKILL_PREFIX, EQUALS));
		value = valueOf(substringAfter(linha, EQUALS));
	}
	
	@Override
	public String getPattern() {
		return TASK_PREFIX + "\\d+" + SKILL_PREFIX + "\\d+" + EQUALS + "\\d+";
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
	
}
