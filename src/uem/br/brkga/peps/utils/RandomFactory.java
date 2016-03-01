package uem.br.brkga.peps.utils;

import java.util.Random;
 
public class RandomFactory {
     
    public static final int HORAS_EXPEDIENTE = 8;

	private final Random random;
     
    private static RandomFactory instance = null;
        
    private RandomFactory() { 
        this.random = new Random();
    }
    
    public synchronized static RandomFactory getInstance() {
        if (instance == null) {
            instance = new RandomFactory();
        }
         
        return instance;
    }
 
    public int nextInt(int n) {
        return this.random.nextInt(n);
    }
    
    public void setSeed(long seed) {
    	this.random.setSeed(seed);
    }
    
    public Double randomGrauDedicacao() {
    	return getValorGrauDedicacao(nextInt(HORAS_EXPEDIENTE));
    }

	public Double getValorGrauDedicacao(int index) {
		switch (index) {
		case 0:
			return 0.0;
		case 1:
			return 0.14;
		case 2:
			return 0.29;
		case 3:
			return 0.43;
		case 4:
			return 0.57;
		case 5:
			return 0.71;
		case 6:
			return 0.86;
		case 7:
		default:
			return 1.00;
		}
	}
    
    public Double randomDoubleRange1() {
    	return this.random.nextDouble();
    }
    
}