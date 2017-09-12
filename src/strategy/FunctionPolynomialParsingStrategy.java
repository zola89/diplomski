package strategy;

import com.laki.functiondecomposer.CalculationDataProvider;
import com.laki.functiondecomposer.TestMain;

public class FunctionPolynomialParsingStrategy implements PolynomialParsingStrategy {

	@Override
	public double[] parse(CalculationDataProvider calculationDataProvider) {
		String[] oba = calculationDataProvider.getParsedText().split("/");
		return TestMain.getInstance().merge(TestMain.getInstance().parseTextPolynomial(oba[1]),
				TestMain.getInstance().parseTextPolynomial(oba[0]));
	}

}
