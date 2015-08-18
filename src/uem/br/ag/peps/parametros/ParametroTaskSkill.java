package uem.br.ag.peps.parametros;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ParametroTaskSkill extends ParametroLinha {

	private static final String TASK_PREFIX = "task.";
	private static final String SKILL_PREFIX = ".skill.";
	private static final String EQUALS = "=";

	private int task;
	
	private int skill;
	
	private int value;
	
	public ParametroTaskSkill(String linha) {
		task = valueOf(substringBetween(linha, TASK_PREFIX, SKILL_PREFIX));
		skill = valueOf(substringBetween(linha, SKILL_PREFIX, EQUALS));
		value = valueOf(substringAfter(linha, EQUALS));
	}

	public static String pattern() {
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
