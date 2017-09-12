package com.laki.functiondecomposer.factoriel;

public class IterativeFactorielCalculatorStrategy implements FactorielCalculatorStrategy {

	@Override
	public double calculate(int n) {
		
		if (n < 0) {
			throw new UnsupportedOperationException("Racunanje faktorijela negativnih brojeva nije podrzano");
		}
		
		int nf = 1;
        for (int i = 1; i <n+1; ++i){
            nf *= i;    
        }
        return nf;
	}

}
