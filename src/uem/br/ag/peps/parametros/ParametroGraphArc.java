package uem.br.ag.peps.parametros;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ParametroGraphArc extends ParametroLinha {

	private static final String GRAPH_PREFIX = "graph.";
	private static final String ARC = "arc.";
	private static final String EQUALS = "=";
	private static final String WHITE_SPACE = " ";

	private int numeroArco;
	
	private int no1;
	
	private int no2;
	
	public ParametroGraphArc(String linha) {
		numeroArco = valueOf(substringBetween(linha, ARC, EQUALS));
		no1 = valueOf(substringBetween(linha, EQUALS, WHITE_SPACE));
		no2 = valueOf(substringAfter(linha, WHITE_SPACE));
	}

	public static String pattern() {
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
	
	public static void main(String[] args) {
		System.out.println("graph.arc.8=5 6".matches(ParametroGraphArc.pattern()));
	}
	
}
