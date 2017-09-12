package com.laki.functiondecomposer.proxy;

import org.jscience.mathematics.function.Polynomial;
import org.jscience.mathematics.function.Variable;
import org.jscience.mathematics.number.Complex;

import com.laki.functiondecomposer.PolinomImpl;

public class Polinoms {

	public static Polinom createPolinom() {
		
		return new Polinom() {
			
			private PolinomImpl polinomImpl = new PolinomImpl();
			
			@Override
			public Complex[] roots(Complex[] ca) {
				logMethodCall("roots(Complex[])");
				return polinomImpl.roots(ca);
			}
			
			@Override
			public Complex eval(Complex[] ca, Complex x) {
				logMethodCall("eval(Complex[], Complex)");
				return polinomImpl.eval(ca, x);
			}
			
			@Override
			public Polynomial<Complex> create(Complex... a) {
				logMethodCall("create(Complex...)");
				return create(a);
			}
			
			@Override
			public Polynomial<Complex> create(Variable<Complex> x, Complex... a) {
				logMethodCall("create(Variable<Complex>, Complex...)");
				return polinomImpl.create(x, a);
			}
			
			private void logMethodCall(String methodName) {
				System.out.println("Poziv metode: " + polinomImpl.getClass().getName() + "." + methodName);
			}
			
		};
		
	}
	
}
