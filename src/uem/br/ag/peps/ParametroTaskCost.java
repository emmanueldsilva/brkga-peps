package uem.br.ag.peps;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ParametroTaskCost extends ParametroLinha {

	private final String TASK_PREFIX = "task.";
	private final String COST_PREFIX = ".cost";
	private final String EQUALS = "=";

	private int task;
	
	private Double value;

	@Override
	public void readParameter(String linha) {
		task = valueOf(substringBetween(linha, TASK_PREFIX, COST_PREFIX));
		value = Double.valueOf(substringAfter(linha, EQUALS));
	}
	
	@Override
	public String getPattern() {
		return TASK_PREFIX + "\\d+" + COST_PREFIX + EQUALS + "\\d+.\\d+";
	}
	
	public int getTask() {
		return task;
	}
	
	public Double getValue() {
		return value;
	}
	
}
