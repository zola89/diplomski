package strategy;

import com.laki.functiondecomposer.CalculationDataProvider;
import com.laki.functiondecomposer.Test;

public class FunctionPolynomialParsingStrategy implements PolynomialParsingStrategy {

	@Override
	public double[] parse(CalculationDataProvider calculationDataProvider) {
		String[] oba = calculationDataProvider.getParsedText().split("/");
		return Test.merge(Test.parseTextPolynomial(oba[1]),Test.parseTextPolynomial(oba[0]));
	}

}
