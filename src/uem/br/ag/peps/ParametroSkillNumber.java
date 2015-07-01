package uem.br.ag.peps;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;

public class ParametroSkillNumber extends ParametroLinha {

	private final String SKILL_NUMBER = "skill.number";
	private final String EQUALS = "=";

	private int value;

	@Override
	public void readParameter(String linha) {
		value = valueOf(substringAfter(linha, EQUALS));
	}
	
	@Override
	public String getPattern() {
		return SKILL_NUMBER + EQUALS + "\\d+";
	}
	
	public int getValue() {
		return value;
	}
	
}
