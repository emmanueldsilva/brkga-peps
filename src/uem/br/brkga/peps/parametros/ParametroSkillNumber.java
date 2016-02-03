package uem.br.brkga.peps.parametros;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;

public class ParametroSkillNumber extends ParametroLinha {

	private static final String SKILL_NUMBER = "skill.number";
	private static final String EQUALS = "=";

	private int value;
	
	public ParametroSkillNumber(String linha) {
		value = valueOf(substringAfter(linha, EQUALS));
	}

	public static String pattern() {
		return SKILL_NUMBER + EQUALS + "\\d+";
	}
	
	public int getValue() {
		return value;
	}
	
}
