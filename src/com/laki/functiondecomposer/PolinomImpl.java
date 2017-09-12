package com.laki.functiondecomposer;
import org.jscience.mathematics.function.Polynomial;
import org.jscience.mathematics.function.Term;
import org.jscience.mathematics.function.Variable;
import org.jscience.mathematics.number.Complex;

import com.laki.functiondecomposer.proxy.Polinom;

/**
 * Klasa <b>Polinom</b> predstavlja utility(usluznu) klasu koju koristimo za manipulaciju polinomima u okviru zadatka rastavljanja racionalne funckije na parcijalne razlomke
 * <br>Za pronalazenje nula nelinearne polinomske funkcije koristimo Durand-Kernerov metod. Broj iteracija i tacnost su hardkodovani.
 * 
 * 
 * 
 * @author  <a href="mailto:zola89@gmail.com">Lazar Bogdanovic</a>
 * @version 1.0 February 18, 2014
 * @see <a href="http://en.wikipedia.org/wiki/Durand%E2%80%93Kerner_method">
 *      Wikipedia: Durand–Kerner method</a>
 */

public class PolinomImpl implements Polinom {
	 
	private static final int ITER = 99999;
    private static double epsilon = 1E-15;
	
	/**
	 * 
	 * @param a niz kompleksnih brojeva
	 * @return  Vraca polinom sa kompleksnim koeficijentima oblika Polynomial<Complex>
	 */
	@Override
    public Polynomial<Complex> create(Complex... a) {
        Variable<Complex> x = new Variable.Local<Complex>("x");
        Polynomial<Complex> px = Polynomial.valueOf(Complex.ZERO, x);
        for (int i = 0, e = a.length - 1; i < a.length; i++, e--) {
            px = px.plus(Polynomial.valueOf(a[i], Term.valueOf(x, e)));
        }
        return px;
    }
    /**
     * 
     * @param x Kreiranje polinoma po unapred zadatoj promenljivoj x
     * @param a Niz kompleksnih brojeva
     * @return  Vraca polinom sa kompleksnim koeficijentima oblika Polynomial<Complex>
     */
	@Override
    public Polynomial<Complex> create( Variable<Complex> x, Complex... a) {
        Polynomial<Complex> px = Polynomial.valueOf(Complex.ZERO, x);
        for (int i = 0, e = a.length - 1; i < a.length; i++, e--) {
            px = px.plus(Polynomial.valueOf(a[i], Term.valueOf(x, e)));
        }
        return px;
    }
    
    /**
     *  Evaluacija polinoma Hornerovom semom
     * @param ca Niz kompleksnih koeficijenata polinoma
     * @param x  Kompleksna vrednost x sa kojom vrsimo evaluaciju 
     * @return   Vraca kompleksnu vrednost polinoma u tacki x
     */
    @Override
    public Complex eval(Complex[] ca, Complex x) {
        Complex result = ca[0];
        for (int i = 1; i < ca.length; i++) {
           result = result.times(x).plus(ca[i]);
        }
        return result;
    }
    
    /**
     * Implementacija Durand-Kernerovog metoda 
     * 
     * @param ca Niz kompleksnih koeficijenata polinoma
     * @return   Vraca kompleksne nule polinoma
     */
    @Override
    public Complex[] roots(Complex[] ca){
    	Complex[] a0 = new Complex[ca.length - 1];
        Complex[] a1 = new Complex[ca.length - 1];

        // Divide by leading coefficient if not monic
        Complex leading = ca[0];
        if (!ca[0].equals(Complex.ONE)) {
            for (int i = 0; i < ca.length; i++) {
                ca[i] = ca[i].divide(leading);
            }
        }

        // Initialize a0
        Complex result = Complex.valueOf(0.4, 0.9);
        a0[0] = Complex.ONE;
        for (int i = 1; i < a0.length; i++) {
            a0[i] = a0[i - 1].times(result);
        }

    	double delta =0;
    	//iteracije
    	for (int k = 1; k < ITER; k++) {
			delta=0;
			for (int j =0; j < a1.length; j++) {
				result = Complex.ONE;
	    		for (int i = 0; i < a1.length; i++) {
					if (i!=j)result= a0[j].minus(a0[i]).times(result) ;//Q
					
				}
	    		a1[j]= eval(ca, a0[j]).times(-1.0).divide(result);//deltaZj
	    		if( a1[j].isLargerThan(Complex.ONE.times(delta))) 
	    			delta = a1[j].magnitude();//if abs(Zj)>delta; delta= abs(Zj);
			}
			for (int i = 0; i < a1.length; i++) {
				a0[i]=a0[i].plus(a1[i]);//Zj +=deltaZj
			}
			if (delta <= epsilon) break;
		}
    	
    	return a0;
    }

}
