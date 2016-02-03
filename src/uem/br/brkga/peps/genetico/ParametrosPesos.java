package uem.br.brkga.peps.genetico;

public class ParametrosPesos {
	
	private Double pesoCustoProjeto;
	
	private Double pesoDuracaoProjeto;
	
	private Double pesoPenalidade;
	
	private Double pesoTrabalhoNaoRealizado;
	
	private Double pesoHabilidadesNecessarias;
	
	private Double pesoTrabalhoExtra;
	
	public static ParametrosPesos instance;
	
	private synchronized static void newInstance() {
		instance = new ParametrosPesos();
	}

	public static ParametrosPesos getInstance() {
		if (instance == null) {
			newInstance();
		}
		
		return instance;
	}

	private ParametrosPesos() {	}
	
	public void atribuiParametros(Double pesoCustoProjeto, Double pesoDuracaoProjeto, Double pesoPenalidade, 
			Double pesoTrabalhoNaoRealizado, Double pesoHabilidadesNecessarias, Double pesoTrabalhoExtra) {
		this.pesoCustoProjeto = pesoCustoProjeto;
		this.pesoDuracaoProjeto = pesoDuracaoProjeto;
		this.pesoPenalidade = pesoPenalidade;
		this.pesoTrabalhoNaoRealizado = pesoTrabalhoNaoRealizado;
		this.pesoHabilidadesNecessarias = pesoHabilidadesNecessarias;
		this.pesoTrabalhoExtra = pesoTrabalhoExtra;
	}

	public Double getPesoCustoProjeto() {
		return pesoCustoProjeto;
	}

	public Double getPesoDuracaoProjeto() {
		return pesoDuracaoProjeto;
	}

	public Double getPesoPenalidade() {
		return pesoPenalidade;
	}

	public Double getPesoTrabalhoNaoRealizado() {
		return pesoTrabalhoNaoRealizado;
	}

	public Double getPesoHabilidadesNecessarias() {
		return pesoHabilidadesNecessarias;
	}

	public Double getPesoTrabalhoExtra() {
		return pesoTrabalhoExtra;
	}

}

