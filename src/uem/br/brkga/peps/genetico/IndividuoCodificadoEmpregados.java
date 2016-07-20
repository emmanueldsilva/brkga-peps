package uem.br.brkga.peps.genetico;

import static java.lang.Long.toBinaryString;
import static java.lang.Math.pow;
import static org.apache.commons.lang3.StringUtils.leftPad;
import uem.br.brkga.peps.problema.ProblemaBuilder;
import uem.br.brkga.peps.utils.RandomFactory;

public class IndividuoCodificadoEmpregados extends IndividuoCodificado {

	public IndividuoCodificadoEmpregados() {
		this(new Double[ProblemaBuilder.getInstance().getNumeroTasks()]);
	}
	
	public IndividuoCodificadoEmpregados(Double[] genes) {
		this.genes = genes;
	}
	
	@Override
	public void codificar() {
		for (int i = 0; i < ProblemaBuilder.getInstance().getNumeroTasks(); i++) {
			setGene(i, RandomFactory.getInstance().randomDoubleRange1());
		}
		
		decodificar();
	}

	@Override
	public void decodificar() {
		for (int i = 0; i < ProblemaBuilder.getInstance().getNumeroTasks(); i++) {
			String binary = leftPad(toBinaryString(normalizaValorCodificado(getValor(i))), ProblemaBuilder.getInstance().getNumeroEmployees(), '0');
			System.out.println(binary);
		}
		
		individuo = new Individuo(buildMatrizDedicacao());
	}

	private MatrizDedicacao buildMatrizDedicacao() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private long normalizaValorCodificado(Double valorCodificado) {
		return (long) (valorCodificado * (pow(2, ProblemaBuilder.getInstance().getNumeroEmployees())));
	}
	
}
