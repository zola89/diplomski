import org.jscience.mathematics.function.Polynomial;
import org.jscience.mathematics.function.RationalFunction;
import org.jscience.mathematics.function.Term;
import org.jscience.mathematics.function.Variable;
import org.jscience.mathematics.number.Complex;


public class Polinom {
	 
	private static final int ITER = 999;
    private static double epsilon = 1E-15;
	    
	    
	private Polinom() {}
	
    public static Polynomial<Complex> create(Complex... a) {
        Variable<Complex> x = new Variable.Local<Complex>("x");
        Polynomial<Complex> px = Polynomial.valueOf(Complex.ZERO, x);
        for (int i = 0, e = a.length - 1; i < a.length; i++, e--) {
            px = px.plus(Polynomial.valueOf(a[i], Term.valueOf(x, e)));
        }
        return px;
    }
    
    public static Polynomial<Complex> create( Variable<Complex> x, Complex... a) {
        //Variable<Complex> x = new Variable.Local<Complex>("x");
        Polynomial<Complex> px = Polynomial.valueOf(Complex.ZERO, x);
        for (int i = 0, e = a.length - 1; i < a.length; i++, e--) {
            px = px.plus(Polynomial.valueOf(a[i], Term.valueOf(x, e)));
        }
        return px;
    }
    
    public static Complex eval(Complex[] ca, Complex x) {
        Complex result = ca[0];
        for (int i = 1; i < ca.length; i++) {
           result = result.times(x).plus(ca[i]);
        }
        return result;
    }
    
//    public static Complex koef(RationalFunction<Complex> c){
//    	Variable<Complex> x = new Variable.Local<Complex>("x");
//    	Complex temp = Complex.ONE;
//    	temp = (c.getDividend().)/
//    	return 0;
//    }
//    
//    public static Polynomial<Complex>diff(Polynomial<Complex> px){
//        Variable<Complex> x = new Variable.Local<Complex>("x");
//    	Polynomial<Complex> dx = px.differentiate(x);
//        return dx;
//    }
    
    public static Complex[] roots(Complex[] ca){
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
	    		a1[j]= Polinom.eval(ca, a0[j]).times(-1.0).divide(result);//deltaZj
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
