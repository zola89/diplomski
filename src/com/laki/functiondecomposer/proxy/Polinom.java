package com.laki.functiondecomposer.proxy;

import org.jscience.mathematics.function.Polynomial;
import org.jscience.mathematics.function.Variable;
import org.jscience.mathematics.number.Complex;

public interface Polinom {

	Polynomial<Complex> create(Complex... a);
	Complex[] roots(Complex[] ca);
	Complex eval(Complex[] ca, Complex x);
	Polynomial<Complex> create(Variable<Complex> x, Complex... a);
	
}
