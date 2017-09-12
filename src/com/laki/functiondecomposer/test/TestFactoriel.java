package com.laki.functiondecomposer.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.laki.functiondecomposer.factoriel.FactorielCalculatorStrategy;
import com.laki.functiondecomposer.factoriel.IterativeFactorielCalculatorStrategy;
import com.laki.functiondecomposer.factoriel.RecursiveFactorielCalculatorStrategy;

@RunWith(Theories.class)
public class TestFactoriel {
	
	@Theory
	public void testCalculateFactorielOfOne(FactorielCalculatorStrategy strategy) {
		double result = strategy.calculate(1);
		assertTrue(result == 1D);
	}
	
	@Theory
	public void testCalculateFactoriel(FactorielCalculatorStrategy strategy) {
		double result = strategy.calculate(4);
		assertTrue(result == 24D);
	}
	
	@Theory
	public void testCalculateFactorielOfZero(FactorielCalculatorStrategy strategy) {
		double result = strategy.calculate(0);
		assertTrue(result == 1D);
	}
	
	@Theory
	@Test(expected=UnsupportedOperationException.class)
	public void testCalculateFactorielOfMinusOne(FactorielCalculatorStrategy strategy) {
		strategy.calculate(-1);
	}
	
	public static @DataPoints FactorielCalculatorStrategy[] candidates = 
			new FactorielCalculatorStrategy[] {
					new IterativeFactorielCalculatorStrategy(),
					new RecursiveFactorielCalculatorStrategy()
					};
	
}
