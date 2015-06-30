package uem.br.ag.peps;

public abstract class ParametroLinha {
	
	protected abstract String getPattern();

	protected abstract void readParameter(String linha);
	
}
