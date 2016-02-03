package uem.br.brkga.peps.parametros;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ParametroTaskCost extends ParametroLinha {

	private static final String TASK_PREFIX = "task.";
	private static final String COST_PREFIX = ".cost";
	private static final String EQUALS = "=";

	private int task;
	
	private Double value;
	
	public ParametroTaskCost(String linha) {
		task = valueOf(substringBetween(linha, TASK_PREFIX, COST_PREFIX));
		value = Double.valueOf(substringAfter(linha, EQUALS));
	}
	
	public static String pattern() {
		return TASK_PREFIX + "\\d+" + COST_PREFIX + EQUALS + "\\d+.\\d+";
	}
	
	public int getTask() {
		return task;
	}
	
	public Double getValue() {
		return value;
	}
	
}
