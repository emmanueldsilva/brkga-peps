package uem.br.brkga.peps.parametros;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ParametroTaskSkillNumber extends ParametroLinha {

	private static final String TASK_PREFIX = "task.";
	private static final String SKILL_NUMBER = ".skill.number";
	private static final String EQUALS = "=";

	private int task;
	
	private int value;
	
	public ParametroTaskSkillNumber(String linha) {
		task = valueOf(substringBetween(linha, TASK_PREFIX, SKILL_NUMBER));
		value = valueOf(substringAfter(linha, EQUALS));
	}

	public static String pattern() {
		return TASK_PREFIX + "\\d+" + SKILL_NUMBER + EQUALS + "\\d+";
	}
	
	public int getTask() {
		return task;
	}
	
	public int getValue() {
		return value;
	}
	
}
