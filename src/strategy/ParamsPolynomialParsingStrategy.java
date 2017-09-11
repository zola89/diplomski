package strategy;

import com.laki.functiondecomposer.CalculationDataProvider;

public class ParamsPolynomialParsingStrategy implements PolynomialParsingStrategy {
	
	/**
	 * parsirananje niza koeficijanata brojica i imenioca  
	 * @return niz koeficijenata u formi potrebnoj za arugment metode testiranje(double...)
	 */
	@Override
	public double[] parse(CalculationDataProvider calculationDataProvider) {
		String[] strbr = calculationDataProvider.getBrojText().split(",");
		String[] strim = calculationDataProvider.getImeText().split(",");
		
		int i=1;
		double[] temp = new double[strbr.length+strim.length+2];
		temp[0]=strim.length;
		for (String line : strim) {
			temp[i]= Double.parseDouble(line);
			i++;
		}
		temp[i]=strbr.length;
		i++;
		for (String line : strbr) {
			temp[i]= Double.parseDouble(line);
			i++;
		}
		return temp;
	}
}
