package uem.br.ag.peps.tests.utils;

import java.util.Random;

public class RandomFactoryTest {
	
    private final Random random;
    
    private static RandomFactoryTest instance = null;

	public RandomFactoryTest() {
		random = new Random(1);
	}
	
	public synchronized static RandomFactoryTest getInstance() {
		if (instance == null) {
			instance = new RandomFactoryTest();
		}

		return instance;
	}
	
    public int nextInt(int n) {
        return this.random.nextInt(n);
    }
	
}
