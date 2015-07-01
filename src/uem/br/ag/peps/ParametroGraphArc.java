package uem.br.ag.peps;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ParametroGraphArc extends ParametroLinha {

	private final String GRAPH_PREFIX = "graph.";
	private final String ARC = ".arc.";
	private final String EQUALS = "=";
	private final String WHITE_SPACE = " ";

	private int numeroArco;
	
	private int no1;
	
	private int no2;

	@Override
	public void readParameter(String linha) {
		numeroArco = valueOf(substringBetween(linha, ARC, EQUALS));
		no1 = valueOf(substringBetween(linha, EQUALS, WHITE_SPACE));
		no2 = valueOf(substringAfter(linha, WHITE_SPACE));
	}
	
	@Override
	public String getPattern() {
		return GRAPH_PREFIX + ARC + "\\d+" + EQUALS + "\\d+" + WHITE_SPACE + "\\d+";
	}
	
	public int getNumeroArco() {
		return numeroArco;
	}
	
	public int getNo1() {
		return no1;
	}
	
	public int getNo2() {
		return no2;
	}
	
}
