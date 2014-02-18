import java.text.DecimalFormat;
import java.util.Arrays;
import org.jscience.mathematics.function.Polynomial;
import org.jscience.mathematics.function.RationalFunction;
import org.jscience.mathematics.function.Term;
import org.jscience.mathematics.function.Variable;
import org.jscience.mathematics.number.Complex;

/**
 * Klasa <b>Test</b> predstavlja utility(usluznu) klasu koju koristimo za rastavljanje racionalne funckije na parcijalne razlomke
 * 
 * Za izracunavanje nula imenioca, tj imenioca parcijalnih razlomaka koristimo Durand-Kernerov Metod tj usluznu klasu Polinom.
 * <br>Za izracunavanje brojioca parcijalnih razlomaka koristimo metod rezidijuma.
 * 
 * <br>Za manipulaciju racionalnim funkcijama i pomoc pri simbolickom diferenciranju koristi se biblioteka <a href="http://jscience.org/">
 *      JScience</a>
 * 
 * @author  <a href="mailto:zola89@gmail.com">Lazar Bogdanovic</a>
 * @version 1.0 February 18, 2014
 * @see <a href="http://en.wikipedia.org/wiki/Partial_fraction_decomposition#Residue_method">
 *      Wikipedia: Partial Fractions Decomposition</a>
 */

public class Test {
	private static double epsilon = 1E-6;
	private static double epsilon2 = 1E-3;
	private static final DecimalFormat form = new DecimalFormat(
			" 0.00000000000000E0;-0.00000000000000E0");

	/**
	 * @param args
	 */

	private Test() {
	}

	public static void main(String[] args) {
		//testiranje(5, 1, 9, 24, 20, 0, 2, 1, 3);
		//testiranje(5, 1, 1, -3, -5, -2, 3, 1, 0, -2);
		//testiranje(5, 1, -4, 4, 4, -5, 2, 1, 2);
		//testiranje(4, 1, -4, 8, 0,3, 4, -8, 16);
		testiranje(8, 1, -3, 5, -7, 7, -5, 3,-1, 7, 2, -4, 5,-3, 1, 3, 0);
	}
	//
	private static void tesiranje(Complex... c) {
		Polynomial<Complex> px = Polinom.create(c);
		System.out.println("Polinom: " + px);
		Complex[] roots = Polinom.roots(c);
		for (Complex complex : roots) {
			System.out.println(complex.toText());
		}
	}
	/**
	 * Metoda testiranje(double... a) prima argumente u formi  br_koeficijenata_imenioca, ... koeficijenti imenioca..., br_koeficijenata_brojioca, ...koeficijenti brojioca... 
	 * Ispis se vrsi trenutno u konzoli
	 * 
	 * @param a niz double koefincijenata imenioca i brojioca 
	 */
	private static void testiranje(double... a) {
		// da baci izuzetak ako a[0] i a[a[0]+1] nisu celi brojevi
		long duzina = Math.round(a[0]);
		Complex[] niz = new Complex[(int) duzina];
		Complex[] niz1 = new Complex[(int) a[(int) duzina + 1]];
		for (int i = 0; i < duzina; i++) {// imenilac
			niz[i] = Complex.valueOf(a[i + 1], 0);

		}
		for (int i = 0; i < a.length - ((int) duzina + 2); i++) {// brojilac
			niz1[i] = Complex.valueOf(a[i + (int) duzina + 2], 0);

		}
		Variable<Complex> x = new Variable.Local<Complex>("x");
		Polynomial<Complex> px = Polinom.create(x, niz);

		Polynomial<Complex> px1 = Polinom.create(x, niz1);
		RationalFunction<Complex> rat = RationalFunction.valueOf(px1, px);

		System.out.println("Racionalna funkcija:" + rat);
		System.out.println("Polinom: " + px);
		Complex[] roots = Polinom.roots(niz);
		Arrays.sort(roots);
		int[] b = duplicates(roots);
		System.out.println("duplikati:");
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);

		}
		validate(niz, roots);

		RationalFunction<Complex> temp = rat;

		Polynomial<Complex> t = Polynomial.valueOf(Complex.ONE,
				Term.valueOf(px1.getVariable("x"), 1));

		Complex ck = Complex.ONE;
		Complex[] param = new Complex[roots.length];
		for (int i = 0; i < b.length; i++) {
			if (b[i] > 1) {
				t = t.minus(Polynomial.valueOf(roots[i],
						Term.valueOf(px1.getVariable("x"), 0)));

				t = t.pow(b[i]);

				temp = RationalFunction.valueOf(px1,
						divisor(roots, b, roots[i], px1));
				// System.out.println("temp rational: "+temp);

				ck = temp.evaluate(roots[i]);
				param[i+b[i]-1]=ck;
				System.out.println("parametar:" + ck);
				for (int j = b[i] - 1; j > 0; j--) {
					// System.out.println("temp before diff: "+temp);
					temp = temp.differentiate(temp.getVariable("x"));// dupla
																		// for
																		// petlja
																		// za
																		// svaku
																		// visestruku nulu
					// System.out.println("temp diff: "+temp);

					ck = temp.evaluate(roots[i]);
					ck = ck.times(1 / factoriel(b[i] - j));
					param[i + j- 1]=ck;
					System.out.println("parametar:" + ck);
				}
				i += b[i] - 1;

			} else {
				temp = RationalFunction.valueOf(px1, rat.getDivisor()
						.differentiate(px.getVariable("x")));
				ck = temp.getDividend().evaluate(roots[i])
						.divide(temp.getDivisor().evaluate(roots[i]));
				param[i]=ck;
				System.out.println("parametar:" + ck);

			}
		}
		System.out.println("kraj");
		ispis(roots, param);

	}
	/**
	 * Metoda za ispis nula imenioca racionalne funkcije tj, imenioca parcijalnih razlomaka
	 * Ispis se vrsi u konzoli trenutno
	 * 
	 * @param ca niz kompleksnih koeficijenata imenioca racionalne funkcije
	 * @param r  niz kompleksnih nula imenioca racionalne funkcije
	 */
	private static void validate(Complex[] ca, Complex... r) {
		double max = 0.0;
		Arrays.sort(r);
		int ix = 0;
		while (ix < r.length) {
			Complex error = Polinom.eval(ca, r[ix]).minus(Complex.ZERO);
			max = Math.max(max, error.magnitude());
			double re = r[ix].getReal();
			double im = r[ix].getImaginary();
			if (Math.abs(re) < epsilon)
				re = 0;
			if (Math.abs(im) < epsilon)
				im = 0;
			if (im == 0) {
				System.out.println("Real: " + form.format(re));
			} else {
				if (ix + 1 < r.length && conjugate(r[ix], r[ix + 1])) {
					System.out.println("Comp: " + form.format(re) + " +-"
							+ form.format(Math.abs(im)) + "i");
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
				+ (max < epsilon ? "< " + epsilon : form.format(max)) + "\n");
	}
	/**
	 * Provera da li su brojevi a i b kompleksno konjugovani sa zadatom tacnoscu
	 * 
	 * @param a prvi kompleksni broj za koji vrsimo poredjenje
	 * @param b drugi kompleksni broj za koji vrsimo poredjenje
	 * @return boolean vrednost da li su brojevi kompleksno konjugovani
	 */
	private static boolean conjugate(Complex a, Complex b) {
		double dr = a.getReal() - b.getReal();
		double di = Math.abs(a.getImaginary()) - Math.abs(b.getImaginary());
		return Math.abs(dr) < epsilon && Math.abs(di) < epsilon;
	}
	/**
	 * Provera da li su brojevi a i b jednaki, sa zadatom tacnoscu
	 * 
	 * @param a prvi kompleksni broj za koji vrsimo poredjenje
	 * @param b drugi kompleksni broj za koji vrsimo poredjenje
	 * @return boolean vrednost da li su brojevi jednaki
	 */
	private static boolean equalNumbers(Complex a, Complex b) {
		double dr = a.getReal() - b.getReal();
		double di = a.getImaginary() - b.getImaginary();
		return Math.abs(dr) < epsilon2 && Math.abs(di) < epsilon2;
	}
	/**
	 * Metoda divisor() je pomocna metoda koju koristimo prilikom izracunavanja koeficijenata brojioca parc. razlomaka visestrukih nula.
	 * <br>Njome izracunavamo imenilac racionalne funkcije. 
	 * 
	 * @param a sve nule imenioca 
	 * @param b nivo visestrukosti
	 * @param root nula imenioca
	 * @param px imenilac racionalne funkcije
	 * @return Vraca se polinom, pratkicno imenilac podeljen sa svim visetrukim nulama odredjene nule imenioca 
	 */
	private static Polynomial<Complex> divisor(Complex[] a, int[] b,
			Complex root, Polynomial<Complex> px) {
		Polynomial<Complex> t;
		Polynomial<Complex> sum = Polynomial.valueOf(Complex.ONE,
				Term.valueOf(px.getVariable("x"), 0));

		for (int i = 0; i < b.length; i++) {
			if (!(equalNumbers(a[i], root) && b[i] > 1)) {
				t = Polynomial.valueOf(Complex.ONE,
						Term.valueOf(px.getVariable("x"), 1));
				t = t.minus(Polynomial.valueOf(a[i],
						Term.valueOf(px.getVariable("x"), 0)));
				sum = sum.times(t);
			}

		}
		return sum;

	}
	/**
	 * Metoda za izracunavanje faktorijela
	 * 
	 * @param n broj za koji hocemo da izracunamo njegov faktorijel
	 * @return faktorijel broja n
	 */
	private static double factoriel(int n) {
		if (n <= 1)
			return 1;
		else
			return n * factoriel(n - 1);
	}
	
	/**
	 * Metoda duplicates(Complex[] c) je pomocna metoda koju koristimo za izracunavanje koeficijenata brojioca parcijalnih razlomaka prilikom javljanja visetrukih nula
	 * 
	 * @param c niz kompleksnih brojeva
	 * @return vraca niz integera koliko se puta javlja ista nula
	 */
	private static int[] duplicates(Complex[] c) {
		int t = 1;
		int[] b = new int[c.length];
		b[0] = 1;
		for (int i = 1; i < c.length; i++) {
			b[i] = 1;
			if (equalNumbers(c[i], c[i - 1])) {
				b[i] = ++t;
			} else {
				t = 1;
			}

		}
		for (int i = b.length - 1; i > 0; i--) {
			if (b[i] > 1) {
				Arrays.fill(b, i - b[i] + 1, i, b[i]);
				i -= b[i] - 1;
			}

		}

		return b;

	}
	
	private static void ispis(Complex[] roots, Complex[] param){
		RationalFunction<Complex> temp;
		for (int i = 0; i < roots.length; i++) {
			double re = param[i].getReal();
			double im = param[i].getImaginary();
			double reR = roots[i].getReal();
			double imR = roots[i].getImaginary();
			if (Math.abs(re) < epsilon)
				re = 0;
			if (Math.abs(im) < epsilon)
				im = 0;
			Complex c = Complex.valueOf(reR, imR);
			
			System.out.print(Complex.valueOf(re, im) + "/" + "("+" "+ "x"+" "+"-"+" "+ c + ") ");
			if(i!=roots.length-1) System.out.print("+ ");
		}
		
	}

}
