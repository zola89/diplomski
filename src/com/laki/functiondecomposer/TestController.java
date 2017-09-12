package com.laki.functiondecomposer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.laki.functiondecomposer.factoriel.FactorielCalculatorStrategy;
import com.laki.functiondecomposer.factoriel.IterativeFactorielCalculatorStrategy;
import com.laki.functiondecomposer.factoriel.RecursiveFactorielCalculatorStrategy;

import strategy.FunctionPolynomialParsingStrategy;
import strategy.ParamsPolynomialParsingStrategy;
import strategy.PolynomialParsingStrategy;

public class TestController implements ActionListener {
	
	private CalculationDataProvider calculationDataProvider;
	
	private List<CalculationFinishedListener> listeners = new ArrayList<>();
	
	public void addCalculationFinishedListener(CalculationFinishedListener calculationFinishedListener) {
		this.listeners.add(calculationFinishedListener);
	}
	
	private void fireCalculationListeners(String calculationResult) {
		listeners.forEach(listener -> listener.calculationFinished(calculationResult));
	}
	
	public void setCalculationDataProvider(CalculationDataProvider calculationDataProvider) {
		this.calculationDataProvider = calculationDataProvider;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		PolynomialParsingStrategy strategy = calculationDataProvider.getFlag() ? 
				new FunctionPolynomialParsingStrategy() : new ParamsPolynomialParsingStrategy();
		
		FactorielCalculatorStrategy factorielCalculatorStrategy = calculationDataProvider.getFlag() ? 
				new RecursiveFactorielCalculatorStrategy() : new IterativeFactorielCalculatorStrategy();
				
		String str=TestMain.getInstance().testiranje(factorielCalculatorStrategy, strategy.parse(calculationDataProvider));
		//str= Test.testiranje(8, 1, -3, 5, -7, 7, -5, 3,-1, 7, 2, -4, 5,-3, 1, 3, 0);
	       //writer.clear();
	       //writer.println("Nesredjeni\\quad parcijalni\\quad razlomci\\quad u\\quad kompleksnom\\quad obliku:", 15);
	       //writer.println( "\\frac {V_m} {K_M+S}",15);
		
		fireCalculationListeners(str);
		
	}
	
	
}
