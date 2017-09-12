package com.laki.functiondecomposer.factoriel;

public class RecursiveFactorielCalculatorStrategy implements FactorielCalculatorStrategy {

	@Override
	public double calculate(int n) {
		
		if (n < 0) {
			throw new UnsupportedOperationException("Racunanje faktorijela negativnih brojeva nije podrzano");
		}
		
		if (n <= 1)
			return 1;
		else
			return n * calculate(n - 1);
	}

}
