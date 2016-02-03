package uem.br.brkga.peps.parametros;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;

public class ParametroGraphArcNumber extends ParametroLinha {

	private static final String GRAPH_ARC_NUMBER = "graph.arc.number";
	private static final String EQUALS = "=";

	private int value;
	
	public ParametroGraphArcNumber(String linha) {
		value = valueOf(substringAfter(linha, EQUALS));
	}

	public static String pattern() {
		return GRAPH_ARC_NUMBER + EQUALS + "\\d+";
	}
	
	public int getValue() {
		return value;
	}
	
}
