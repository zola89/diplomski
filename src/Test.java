import java.text.DecimalFormat;
import java.util.Arrays;
import org.jscience.mathematics.function.Polynomial;
import org.jscience.mathematics.function.RationalFunction;
import org.jscience.mathematics.function.Term;
import org.jscience.mathematics.function.Variable;
import org.jscience.mathematics.number.Complex;
public class Test {
    private static double epsilon = 1E-10;
    private static final DecimalFormat form =
            new DecimalFormat(" 0.00000000000000E0;-0.00000000000000E0");
    /**
	 * @param args
	 */
	
	private Test(){}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testiranje(5,1,9,24,20,0,2,1,3);
		
//		testiranje(1,-2,1);
//		
//		testiranje(1,0,0,-1);
	}
	private static void tesiranje(Complex... c){
		Polynomial<Complex> px = Polinom.create(c);
        System.out.println("Polinom: " + px);
        Complex[] roots = Polinom.roots(c);
        for (Complex complex : roots) {
			System.out.println(complex.toText());
		}
	}
	
	private static void testiranje(double... a){
		 //da baci izuzetak ako a[0] i a[a[0]+1] nisu celi brojevi
		long duzina = Math.round(a[0]);
		Complex[] niz = new Complex[(int) duzina];
		Complex[] niz1 = new Complex[(int) a[(int)duzina+1]];
		for (int i = 0; i < duzina; i++) {//imenilac
			niz[i] = Complex.valueOf(a[i+1], 0);
			
		}
		for (int i = 0; i < a.length-((int)duzina+2); i++) {//brojilac
			niz1[i] = Complex.valueOf(a[i+(int)duzina+2], 0);
			
		}
		Polynomial<Complex> px = Polinom.create(niz);

		Polynomial<Complex> px1 = Polinom.create(niz1);
		RationalFunction<Complex> rat = RationalFunction.valueOf(px1, px);
		//px1.times(px);
        
		System.out.println("Racionalna funkcija:" + rat);
        System.out.println("Polinom: " + px);
        Complex[] roots = Polinom.roots(niz);
        Arrays.sort(roots);
        int [] b = duplicates(roots);
		System.out.println("duplikati:" );
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
	        
		}
        validate(niz, roots);
		System.out.println("nediferenciran:"+px);
		//rat=rat.getDivisor().differentiate(px.getVariable("x"));
		System.out.println("diferenciran:"+px);
		RationalFunction<Complex> temp = rat;
		Variable<Complex> x = new Variable.Local<Complex>("x");
	    Polynomial<Complex> t =
	    		Polynomial.valueOf(Complex.ONE, Term.valueOf(px1.getVariable("x"), 1));
	   
	    Complex ck =Complex.ONE;
		
	    for (int i = 0; i < b.length; i++) {
			if (b[i]>1){
				t= t.minus(Polynomial.valueOf(roots[i], Term.valueOf(px1.getVariable("x"), 0 )));
				//t= t.plus(roots[i].times(-1));
				
				System.out.println("t minus: "+ t);
				t=t.pow(b[i]);
				temp =RationalFunction.valueOf(px1, px.times((Complex.ONE).divide(t)));
				ck=temp.getDividend().evaluate(roots[i]).divide(temp.getDivisor().evaluate(roots[i]));
				
				System.out.println("parametar:"+ck);
				for (int j = b[i]-1 ; j > 0 ; j--) {
					temp.differentiate(temp.getVariable("x"));//dupla for petlja za svaku nulu
					ck=temp.getDividend().evaluate(roots[i]).divide(rat.getDivisor().evaluate(roots[i]));
		        	ck=ck.times(1/factoriel(b[i]-j));
					System.out.println("parametar:"+ck);
				}
				//temp.evaluate(roots[i]);//dodaj promeljivu
				//i+=b[i];
				
			}
			else{
				rat= RationalFunction.valueOf(px1,rat.getDivisor().differentiate(px.getVariable("x")));
		        ck=rat.getDividend().evaluate(roots[i]).divide(rat.getDivisor().evaluate(roots[i]));
		        System.out.println("parametar:"+ck);
		        	
				
			}
		}
		
		
//        for (Complex complex : roots) {
//			System.out.println(complex.toText());
//		}
	}
	
	
	
    private static void validate(Complex[] ca, Complex... r) {
        double max = 0.0;
        Arrays.sort(r);
        int ix = 0;
        while (ix < r.length) {
            Complex error = Polinom.
                eval(ca, r[ix]).minus(Complex.ZERO);
            max = Math.max(max, error.magnitude());
            double re = r[ix].getReal();
            double im = r[ix].getImaginary();
            if (Math.abs(re) < epsilon) re = 0;
            if (Math.abs(im) < epsilon) im = 0;
            if (im == 0) {
                System.out.println("Real: " + form.format(re));
            } else {
                if (ix + 1 < r.length && conjugate(r[ix], r[ix + 1])) {
                    System.out.println("Comp: " + form.format(re)
                        + " +-" + form.format(Math.abs(im)) + "i");
                    ix++;
                } else {
                    System.out.println("Comp: " + form.format(re)
                        + (im < 0 ? " -" : " +")
                        + form.format(Math.abs(im)) + "i");
                }
            }
            ix++;
        }
        System.out.println("Error: "
            + (max < epsilon ? "< " + epsilon : form.format(max))
            + "\n");
    }
    
    
    private static boolean conjugate(Complex a, Complex b) {
        double dr = a.getReal() - b.getReal();
        double di = Math.abs(a.getImaginary()) - Math.abs(b.getImaginary());
        return Math.abs(dr) < epsilon && Math.abs(di) < epsilon;
    }

    private static boolean equalNumbers(Complex a, Complex b) {
        double dr = a.getReal() - b.getReal();
        double di = a.getImaginary() - b.getImaginary();
        return Math.abs(dr) < epsilon && Math.abs(di) < epsilon;
    }
    

    private static int factoriel(int n){
      if (n <= 1)
          return 1;

          else

              return n * factoriel(n-1);
    }
    
    private static int[] duplicates(Complex[] c){
		int t=1;
		int[] b = new int[c.length];
		b[0]=1;
		for (int i=1; i<c.length;i++) {
			 b[i]=1;
			 if (equalNumbers(c[i], c[i-1])){ b[i]=++t;}
			 else {
				 	 
				 	t=1;
			 }
			 
		}
		for (int i = b.length-1; i >0;i--) {
			if(b[i]>1){
				Arrays.fill(b, i-b[i]+1, i, b[i]);
				i-=b[i]-1;
			}
			
		}
    	
    	return b;
    	
    }

}
