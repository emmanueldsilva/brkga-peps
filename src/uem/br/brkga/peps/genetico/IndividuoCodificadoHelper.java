package uem.br.brkga.peps.genetico;

public class IndividuoCodificadoHelper {

	public static IndividuoCodificadoHelper instance;
	
	private synchronized static void newInstance() {
		instance = new IndividuoCodificadoHelper();
	}

	public static IndividuoCodificadoHelper getInstance() {
		if (instance == null) {
			newInstance();
		}
		
		return instance;
	}

	private IndividuoCodificadoHelper() { }
	
	public IndividuoCodificado newIndividuoCodificado(TipoCodificacao tipoCodificacao) {
		switch (tipoCodificacao) {
		case MATRIZ_DEDICACAO: 
			return new IndividuoCodificadoMatriz();
		case VETOR_EMPREGADOS_ATUACAO:
			return new IndividuoCodificadoEmpregados();
		case MATRIZ_EMPREGADO_ATUA_GRAU:
			return new MatrizEmpregadoAtuaGrau();
		default:
			return null;
		}
	}
	
	public IndividuoCodificado newIndividuoCodificado(TipoCodificacao tipoCodificacao, Double[] genes) {
		switch (tipoCodificacao) {
		case MATRIZ_DEDICACAO: 
			return new IndividuoCodificadoMatriz(genes);
		case VETOR_EMPREGADOS_ATUACAO:
			return new IndividuoCodificadoEmpregados(genes);
		case MATRIZ_EMPREGADO_ATUA_GRAU:
			return new MatrizEmpregadoAtuaGrau(genes);
		default:
			return null;
		}
	}
	
}
