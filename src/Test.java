import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jscience.mathematics.function.Polynomial;
import org.jscience.mathematics.function.RationalFunction;
import org.jscience.mathematics.function.Term;
import org.jscience.mathematics.function.Variable;
import org.jscience.mathematics.number.Complex;

/**
 * Klasa <b>Test</b> predstavlja utility(usluznu) klasu koju koristimo za
 * rastavljanje racionalne funckije na parcijalne razlomke
 * 
 * Za izracunavanje nula imenioca, tj imenioca parcijalnih razlomaka koristimo
 * Durand-Kernerov Metod tj usluznu klasu Polinom. <br>
 * Za izracunavanje brojioca parcijalnih razlomaka koristimo metod rezidijuma.
 * 
 * <br>
 * Za manipulaciju racionalnim funkcijama i pomoc pri simbolickom diferenciranju
 * koristi se biblioteka <a href="http://jscience.org/"> JScience</a>
 * 
 * @author <a href="mailto:zola89@gmail.com">Lazar Bogdanovic</a>
 * @version 1.0 February 18, 2014
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Partial_fraction_decomposition#Residue_method">
 *      Wikipedia: Partial Fractions Decomposition</a>
 */

public class Test {
	private static double epsilon = 1E-6;
	private static double epsilon2 = 1E-3;
	private static final DecimalFormat form = new DecimalFormat(
			" #.####;-#.####");

	/**
	 * @param args
	 */

	private Test() {
	}

	public static void main(String[] args) {
		// testiranje(5, 1, 9, 24, 20, 0, 2, 1, 3);
		// testiranje(5, 1, 1, -3, -5, -2, 3, 1, 0, -2);
		// testiranje(5, 1, -4, 4, 4, -5, 2, 1, 2);
		// testiranje(4, 1, -4, 8, 0,3, 4, -8, 16);
		testiranje(8, 1, -3, 5, -7, 7, -5, 3, -1, 7, 2, -4, 5, -3, 1, 3, 0);
	}

	//
	public static void tesiranje(Complex... c) {
		Polynomial<Complex> px = Polinom.create(c);
		System.out.println("Polinom: " + px);
		Complex[] roots = Polinom.roots(c);
		for (Complex complex : roots) {
			System.out.println(complex.toText());
		}
	}

	/**
	 * Metoda testiranje(double... a) prima argumente u formi
	 * br_koeficijenata_imenioca, ... koeficijenti imenioca...,
	 * br_koeficijenata_brojioca, ...koeficijenti brojioca... Ispis se vrsi
	 * trenutno u konzoli
	 * 
	 * @param a
	 *            niz double koefincijenata imenioca i brojioca
	 */
	public static String testiranje(double... a) {
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
		StringBuilder str = new StringBuilder();
		System.out.println("Racionalna \\quad funkcija:" + rat);
		System.out.println("Polinom: " + px);

		str.append("\\textbf{\\underline{Racionalna\\quad funkcija:}}"+ " \\\\ " + rat + " \\\\ "+ " \\\\ "+ " \\\\ ");
		str.append("\\textbf{\\underline{Polinom\\quad imenioca\\quad cije\\quad nule\\quad nalazimo\\quad DK\\quad metodom:}}" + " \\\\ " + px + " \\\\ ");
		str.append("\\\\ ");

		Complex[] roots = Polinom.roots(niz);
		Arrays.sort(roots);
		int[] b = duplicates(roots);
		System.out.print("duplikati: ");
		for (int i = 0; i < b.length; i++) {
			System.out.print(b[i]+" ");
		}
		System.out.println("\n ");
		str.append(validate(niz, roots));

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

				ck = temp.evaluate(roots[i]);
				param[i + b[i] - 1] = ck;
				System.out.println("parametar:" + ck);
				for (int j = b[i] - 1; j > 0; j--) {
					// dupla for petlja za svaku visestruku nulu
					temp = temp.differentiate(temp.getVariable("x"));
					ck = temp.evaluate(roots[i]);
					ck = ck.times(1 / factoriel(b[i] - j));
					param[i + j - 1] = ck;
					System.out.println("parametar:" + ck);
				}
				i += b[i] - 1;

			} else {
				temp = RationalFunction.valueOf(px1, rat.getDivisor()
						.differentiate(px.getVariable("x")));
				ck = temp.getDividend().evaluate(roots[i])
						.divide(temp.getDivisor().evaluate(roots[i]));
				
				param[i] = ck;
				System.out.println("parametar:" + ck);

			}
		}
		System.out.println("\nkraj");
		str.append(" \\\\ "+ " \\\\ "+ " \\\\ ");
		str.append(ispis(roots, param, b));
		str.append(" \\\\ "+ " \\\\ "+ " \\\\ ");
		str.append(ispisFixed(roots, param, px1, b));
		str.append(" \\\\ "+ " \\\\ "+ " \\\\ ");

		return str.toString().replace("+ 0i", "").replace("+0i","").replace("- 0i","").replace("-0i","")
				.replace("(0","(").replace("( 0","(").replace(" - (  )", "");
	
	}
	
	/**
	 * Metoda za ispis nula imenioca racionalne funkcije tj, imenioca
	 * parcijalnih razlomaka Ispis se vrsi u konzoli trenutno
	 * 
	 * @param ca
	 *            niz kompleksnih koeficijenata imenioca racionalne funkcije
	 * @param r
	 *            niz kompleksnih nula imenioca racionalne funkcije
	 */
	private static String validate(Complex[] ca, Complex... r) {
		double max = 0.0;
		Arrays.sort(r);
		int ix = 0;
		StringBuilder str = new StringBuilder();
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
				str.append("Realna \\quad nula: " + form.format(re) + " \\\\ ");
			} else {
				if (ix + 1 < r.length && conjugate(r[ix], r[ix + 1])) {
					System.out.println("Comp: " + form.format(re) + " +-"
							+ form.format(Math.abs(im)) + "i");
					str.append("Konjugovano\\quad kompleksni\\quad par: "
							+ form.format(re) + " \\pm"
							+ form.format(Math.abs(im)) + "i" + " \\\\ ");
					ix++;
				} else {
					System.out.println("Comp: " + form.format(re)
							+ (im < 0 ? " -" : " +")
							+ form.format(Math.abs(im)) + "i");
					str.append("Kompleksna\\quad nula: " + form.format(re));
					if (im < 0)
						str.append(" -");
					else
						str.append(" +");
					str.append(form.format(Math.abs(im)) + "i" + " \\\\ ");

				}
			}
			ix++;
		}
		System.out.println("Error: "
				+ (max < epsilon ? "< " + epsilon : form.format(max)) + "\n");
		str.append(" \\\\ ");
		return str.toString();
	}

	/**
	 * Provera da li su brojevi a i b kompleksno konjugovani sa zadatom tacnoscu
	 * 
	 * @param a
	 *            prvi kompleksni broj za koji vrsimo poredjenje
	 * @param b
	 *            drugi kompleksni broj za koji vrsimo poredjenje
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
	 * @param a
	 *            prvi kompleksni broj za koji vrsimo poredjenje
	 * @param b
	 *            drugi kompleksni broj za koji vrsimo poredjenje
	 * @return boolean vrednost da li su brojevi jednaki
	 */
	private static boolean equalNumbers(Complex a, Complex b) {
		double dr = a.getReal() - b.getReal();
		double di = a.getImaginary() - b.getImaginary();
		return Math.abs(dr) < epsilon2 && Math.abs(di) < epsilon2;
	}

	/**
	 * Metoda divisor() je pomocna metoda koju koristimo prilikom izracunavanja
	 * koeficijenata brojioca parc. razlomaka visestrukih nula. <br>
	 * Njome izracunavamo imenilac racionalne funkcije.
	 * 
	 * @param a
	 *            sve nule imenioca
	 * @param b
	 *            nivo visestrukosti
	 * @param root
	 *            nula imenioca
	 * @param px
	 *            imenilac racionalne funkcije
	 * @return Vraca se polinom, pratkicno imenilac podeljen sa svim visetrukim
	 *         nulama odredjene nule imenioca
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
	 * @param n
	 *            broj za koji hocemo da izracunamo njegov faktorijel
	 * @return faktorijel broja n
	 */
	private static double factoriel(int n) {
		if (n <= 1)
			return 1;
		else
			return n * factoriel(n - 1);
	}

	/**
	 * Metoda duplicates(Complex[] c) je pomocna metoda koju koristimo za
	 * izracunavanje koeficijenata brojioca parcijalnih razlomaka prilikom
	 * javljanja visetrukih nula
	 * 
	 * @param c
	 *            niz kompleksnih brojeva
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

	/**
	 * metoda za spajanje prozivoljnog broja nizova
	 * 
	 * @param arrays
	 *            double nizovi
	 * @return spojeni niz
	 */
	public static double[] merge(final double[]... arrays) {
		int size = 0;
		for (double[] a : arrays)
			size += a.length;

		double[] res = new double[size];

		int destPos = 0;
		for (int i = 0; i < arrays.length; i++) {
			if (i > 0)
				destPos += arrays[i - 1].length;
			int length = arrays[i].length;
			System.arraycopy(arrays[i], 0, res, destPos, length);
		}

		return res;
	}

	
	/**
	 * nesredjen ispis, vraca neizmnozena konjugovano-kompleksna resenja
	 * 
	 * @param roots
	 *            niz korena u imenioncima
	 * @param param
	 *            niz parametara u brojiocima
	 * @param duplicates
	 *            niz duplikata
	 * @return latex ispis parcijalnih razlomaka
	 */
	private static String ispis(Complex[] roots, Complex[] param, int[] duplicates) {
		String ch, chR;
		StringBuilder s = new StringBuilder();
		s.append("\\textbf{\\underline{Nesredjeni\\quad parcijalni\\quad razlomci\\quad u\\quad kompleksnom\\quad obliku:}}");
		s.append(" \\\\ "+" \\\\ "+" \\\\ ");
		for (int i = 0; i < roots.length; i++) {

			double re = param[i].getReal();
			double im = param[i].getImaginary();
			double reR = roots[i].getReal();
			double imR = roots[i].getImaginary();
			if (Math.abs(reR) < epsilon)
				reR = 0;
			if (Math.abs(imR) < epsilon)
				imR = 0;

			if (im >= 0)
				ch = " +";
			else
				ch = " -";
			if (imR >= 0)
				chR = " +";
			else
				chR = " -";

			if (duplicates[i] > 0) {
				if (Math.abs(imR) > 0) {

				} else {
					for (int j = 0; j < duplicates[i]; j++) {
						re = param[i + j].getReal();
						im = param[i + j].getImaginary();

						if (j == 0)
							s.append("\\frac {" + form.format(re) + ch
									+ form.format(Math.abs(im)) + "i }" + "{"
									+ " " + "x" + " " + "-" + " ("
									+ form.format(reR) + chR
									+ form.format(Math.abs(imR)) + "i )" + "}");

						else {
							s.append("\\frac {" + form.format(re) + ch
									+ form.format(Math.abs(im)) + "i }" + "{"
									+ "( " + " " + "x" + " " + "-" + " ("
									+ form.format(reR) + chR
									+ form.format(Math.abs(imR)) + "i )"
									+ " )^" + (j + 1) + "}");

						}
						if ((i + j) != roots.length - 1) {
							s.append("+ ");
						}
					}
					i += duplicates[i] - 1;
					continue;
				}

			}


			s.append("\\frac {" + form.format(re) + ch
					+ form.format(Math.abs(im)) + "i }" + "{" + " " + "x" + " "
					+ "-" + " (" + form.format(reR) + chR
					+ form.format(Math.abs(imR)) + "i )" + "}");

			if (i != roots.length - 1) 
				s.append("+ ");

		}
		s.append(" \\\\ ");
		return s.toString();

	}

	/**
	 * sredjen ispis
	 * 
	 * @param roots
	 *            niz korena u imenioncima
	 * @param param
	 *            niz parametara u brojiocima
	 * @param px
	 *            polinom
	 * @param duplicates
	 *            niz duplikata
	 * @return latex ispis parcijalnih razlomaka
	 */
	public static String ispisFixed(Complex[] roots, Complex[] param,
			Polynomial<Complex> px, int[] duplicates) {
		RationalFunction<Complex> temp1, temp2;
		String ch, chR;
		StringBuilder s = new StringBuilder();
		s.append("\\textbf{\\underline{Sredjeni\\quad parcijalni\\quad razlomci\\quad u \\quad kanonskom \\quad obiliku:}}");
		s.append(" \\\\ "+" \\\\ "+" \\\\ ");
		Polynomial<Complex> broj = Polynomial.valueOf(Complex.ONE,
				Term.valueOf(px.getVariable("x"), 0));
		Polynomial<Complex> imen = Polynomial.valueOf(Complex.ONE,
				Term.valueOf(px.getVariable("x"), 1));
		Polynomial<Complex> broj1 = Polynomial.valueOf(Complex.ONE,
				Term.valueOf(px.getVariable("x"), 0));
		Polynomial<Complex> imen1 = Polynomial.valueOf(Complex.ONE,
				Term.valueOf(px.getVariable("x"), 1));
		for (int i = 0; i < roots.length; i++) {

			double re = param[i].getReal();
			double im = param[i].getImaginary();
			double reR = roots[i].getReal();
			double imR = roots[i].getImaginary();
			if (Math.abs(reR) < epsilon)
				reR = 0;
			if (Math.abs(imR) < epsilon)
				imR = 0;
			if (im >= 0)
				ch = " +";
			else
				ch = " -";
			if (imR >= 0)
				chR = " +";
			else
				chR = " -";
			
			
			
			if (duplicates[i] > 0) {
				if (Math.abs(imR) > 0) {
				} else {
					for (int j = 0; j < duplicates[i]; j++) {
						re = param[i + j].getReal();
						im = param[i + j].getImaginary();
						
						if (Math.abs(re) < epsilon2 && Math.abs(im) < epsilon2)continue;
						
						if (j == 0)
							s.append("\\frac {" + form.format(re) + ch
									+ form.format(Math.abs(im)) + "i }" + "{"
									+ " " + "x" + " " + "-" + " ("
									+ form.format(reR) + chR
									+ form.format(Math.abs(imR)) + "i )" + "}");

						else {
							s.append("\\frac {" + form.format(re) + ch
									+ form.format(Math.abs(im)) + "i }" + "{"
									+ "( " + " " + "x" + " " + "-" + " ("
									+ form.format(reR) + chR
									+ form.format(Math.abs(imR)) + "i )"
									+ " )^" + (j + 1) + "}");

						}
						if ((i + j) != roots.length - 1) {
							s.append("+ ");
						}
					}
					i += duplicates[i] - 1;
					continue;
				}

			}
			if (i + 1 < roots.length && conjugate(roots[i], roots[i + 1])
					&& duplicates[i] == 1) {
				
				imen = imen.minus(Polynomial.valueOf(roots[i],
						Term.valueOf(px.getVariable("x"), 0)));
				broj = Polynomial.valueOf(param[i],
						Term.valueOf(px.getVariable("x"), 0));
				imen1 = imen1.minus(Polynomial.valueOf(roots[i + 1],
						Term.valueOf(px.getVariable("x"), 0)));
				broj1 = Polynomial.valueOf(param[i + 1],
						Term.valueOf(px.getVariable("x"), 0));
				temp1 = RationalFunction.valueOf(broj, imen);
				temp2 = RationalFunction.valueOf(broj1, imen1);
				temp1 = temp1.plus(temp2);
				

				s.append(temp1.toString());
				if (i + 2 < roots.length) {
					
					s.append("+ ");
				}
				i++;
				continue;
			}

			
			s.append("\\frac {" + form.format(re) + ch
					+ form.format(Math.abs(im)) + "i }" + "{" + " " + "x" + " "
					+ "-" + " (" + form.format(reR) + chR
					+ form.format(Math.abs(imR)) + "i )" + "}");

			if (i != roots.length - 1) {
				
				s.append("+ ");
			}

		}
		return s.toString();

	}

	/**
	 * metoda za parsiranje jednog polinoma
	 * 
	 * @param par
	 *            polinom u string formi 4x^3+3x^2-5x+2 , bez razmaka sve
	 *            spojeno
	 * @return niz koeficijenata polinoma, pocinje brojem svih koeficijenata,
	 *         zatim redom koeficijenti pocevsi od najviseg
	 */

	public static double[] parseTextPolynomial(String par) {
		par = par.replace("(", "").replace(")", "");
		Pattern monomial = Pattern
				.compile("([+|-]?)([0-9]*)(x)(\\^([1-9][0-9]*))?|([+|-]?[0-9]+)");

		Matcher m = monomial.matcher(par);
		m.find();
		String pow = m.group(5);
		int powValue = (pow == null || pow.equals("")) ? 1 : Integer.parseInt(m
				.group(5));
		double[] temp = new double[powValue + 2];
		Arrays.fill(temp, 0);
		temp[0] = temp.length - 1;
		int value, powint;
		m.reset();
		while (m.find()) {
			String mul = m.group(2);
			value = (mul == null || mul.equals("")) ? 1 : Integer.parseInt(m
					.group(2));
			if (m.group(1) != null) {
				if (m.group(1).equals("-"))
					value *= -1;
			}
			pow = m.group(5);

			powint = (pow == null) ? 1 : Integer.parseInt(pow);
			if ("x".equals(m.group(3)))
				temp[temp.length - 1 - powint] = value;

			if (m.group(6) != null) {
				temp[temp.length - 1] = Integer.parseInt(m.group(6));
			}

			System.out.println(m);

		}

		return temp;
	}

}
