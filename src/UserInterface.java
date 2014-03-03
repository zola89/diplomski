import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


import LatexWrapper.LatexPaneResizable;
import LatexWrapper.LatexWriter;


public class UserInterface extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel p = new JPanel();
	//center
	LatexPaneResizable latexPanel = new LatexPaneResizable();

	
	boolean flag = false;
	//menu
	JMenuBar menuBar = new JMenuBar();
	JMenu menu1 = new JMenu("Primeri");
	JMenuItem primer1 = new JMenuItem("Primer 1");
	JMenuItem primer2 = new JMenuItem("Primer 2");
	JMenuItem primer3 = new JMenuItem("Primer 3");
	JMenuItem primer4 = new JMenuItem("Primer 4");
	JMenuItem primer5 = new JMenuItem("Primer 5");
	
	JMenu menu2 = new JMenu("Pomoc");
	JMenuItem uputstvo = new JMenuItem("Uputstvo");
	JMenuItem author = new JMenuItem("O autoru");
	
	JMenu menu3 = new JMenu("Unos");
	JMenuItem koef = new JMenuItem("Koeficijenti");
	JMenuItem poli = new JMenuItem("Tekstualano");
	
	
	//west
	JPanel west = new JPanel();
	JButton test = new JButton("Test");
	JButton clear = new JButton("Clear");
	
	
	//south
	JPanel south = new JPanel();
	JLabel brojilac = new JLabel("Brojilac: ");
	JLabel pars = new JLabel("Racionalna Funkcija: ");
	JLabel imenilac = new JLabel("Imenilac: ");
	JTextField brojText = new JTextField(10);
	JTextField imeText = new JTextField(10);
	JTextField parsText = new JTextField(10);
	public UserInterface(){
		super("Partial Fractions Decomposition");
		setSize(800,400);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		p.setLayout(new BorderLayout());
		p.setBorder(new EmptyBorder(5, 0, 0, 5));
		latexPanel.setBackgroundColor(Color.WHITE);
		p.add(latexPanel,BorderLayout.CENTER);
		
		//north
		
		menu1.add(primer1);
		menu1.add(primer2);
		menu1.add(primer3);
		menu1.add(primer4);
		menu1.add(primer5);
		
		primer1.addActionListener(this);
		primer2.addActionListener(this);
		primer3.addActionListener(this);
		primer4.addActionListener(this);
		primer5.addActionListener(this);
		
		menu2.add(uputstvo);
		menu2.add(author);
		
		uputstvo.addActionListener(this);
		author.addActionListener(this);
		
		menu3.add(koef);
		menu3.add(poli);
		
		koef.addActionListener(this);
		poli.addActionListener(this);
		
		menuBar.add(menu1);
		menuBar.add(menu3);
		menuBar.add(menu2);
		
		setJMenuBar(menuBar);
		
		//buttons west
		
		west.setLayout(new GridLayout(10,1,5,5));
		west.setBorder(new EmptyBorder(10, 10, 0, 10));
		test.addActionListener(this);
		clear.addActionListener(this);
		west.add(test);
		west.add(clear);
		p.add(west,BorderLayout.WEST);
		brojilac.setHorizontalAlignment(SwingConstants.RIGHT);
		imenilac.setHorizontalAlignment(SwingConstants.RIGHT);
		pars.setHorizontalAlignment(SwingConstants.RIGHT);
		//south
		south.setLayout(new GridLayout(3,2,5,5));
		south.add(brojilac);
		south.add(brojText);
		south.add(imenilac);
		south.add(imeText);
		south.add(pars);
		south.add(parsText);
		south.setBorder(new EmptyBorder(10, 10, 10, 10) );
		
		parsText.setEditable(false);
		p.add(south,BorderLayout.SOUTH);
		add(p);
		setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object src=e.getSource();
		String str;
		
		if(src.equals(test)){
			double[] testParam;
			
			if (flag){
				String[] oba = this.parsText.getText().split("/");
				testParam = Test.merge(Test.parseTextPolynomial(oba[1]),Test.parseTextPolynomial(oba[0]));
			}else{
				testParam = parseText();
			}
			
			
			str=Test.testiranje(testParam);
			//str= Test.testiranje(8, 1, -3, 5, -7, 7, -5, 3,-1, 7, 2, -4, 5,-3, 1, 3, 0);
			LatexWriter writer;
		       writer = this.latexPanel.getWriter();
		       //writer.clear();
		       //writer.println("Nesredjeni\\quad parcijalni\\quad razlomci\\quad u\\quad kompleksnom\\quad obliku:", 15);
		       //writer.println( "\\frac {V_m} {K_M+S}",15);
		       writer.println(str,15);
		       
		       this.latexPanel.repaint();
		}
		if(src.equals(clear)){
			LatexWriter writer;
	        writer = this.latexPanel.getWriter();
	        writer.clear();
	        this.latexPanel.repaint();
		}
		
		if(src.equals(koef)){
			flag = false;
			this.parsText.setText("");
			this.parsText.setEditable(false);
			
			this.imeText.setEditable(true);
			this.brojText.setEditable(true);
		}
		
		
		if(src.equals(poli)){
			flag= true;
			this.imeText.setText("");
			this.imeText.setEditable(false);
			this.brojText.setText("");
			this.brojText.setEditable(false);
			
			this.parsText.setEditable(true);
		}
		
		if(src.equals(primer1)){
			if(!flag){
			this.brojText.setText("");
	        this.brojText.setText("1,3");
	        this.imeText.setText("");
	        this.imeText.setText("1,9,24,20,0");
			}
			else{
				this.parsText.setText("");
				this.parsText.setText("x+3/x^4+9x^3+24x^2+20x");
			}
		}
		
		if(src.equals(primer2)){
			if(!flag){
			this.brojText.setText("");
			this.brojText.setText("1, 0, -2");
			this.imeText.setText("");
			this.imeText.setText("1, 1, -3, -5, -2");
			}
			else{
				this.parsText.setText("");
				this.parsText.setText("x^2-2/x^4+x^3-3x^2-5x-2");
			}
		}
		
		if(src.equals(primer3)){
			if(!flag){
			this.brojText.setText("");
	        this.brojText.setText("2, -4, 5,-3, 1, 3, 0");
	        this.imeText.setText("");
	        this.imeText.setText("1, -3, 5, -7, 7, -5, 3,-1");
			}
			else{
				this.parsText.setText("");
				this.parsText.setText("2x^6-4x^5+5x^4-3x^3+x^2+3x/x^7-3x^6+5x^5-7x^4+7x^3-5x^2+3x-1");
			}
		}
		
		if(src.equals(primer4)){
			if(!flag){
			this.brojText.setText("");
	        this.brojText.setText("4, -8, 16");
	        this.imeText.setText("");
	        this.imeText.setText("1, -4, 8, 0");
			}
			else{
				this.parsText.setText("");
				this.parsText.setText("4x^2-8x+16/x^3-4x^2+8x");
			}
		}
		
		if(src.equals(primer5)){
			if(!flag){
			this.brojText.setText("");
	        this.brojText.setText("1, 2");
	        this.imeText.setText("");
	        this.imeText.setText("1, -4, 4, 4, -5");
			}
			else{
				this.parsText.setText("");
				this.parsText.setText("x+2/x^4-4x^3+4x^2+4x-5");
			}
		}		
		
		if(src.equals(author)){
			JOptionPane.showMessageDialog(
					UserInterface.this.getParent(),
					"Diplomski rad\n"
							+ "\nJava aplet"
							+ "\nza transoformaciju racionalne funkcije u parcijalne razlomke\n"
							+ "\nLazar Bogdanovic 367/08"
							+ "\nMart, 2014");
		}
		
		if(src.equals(uputstvo)){
			JOptionPane.showMessageDialog(
					UserInterface.this.getParent(),
					"Uputstvo za koriscenje apleta"
							+ "\n"
							+ "\nU padajucem meniju \"Unos\" odaberite "
							+ "\n"
							+ "\nili koeficijente za unos racionalne funkcije preko"
							+ "\nkoeficijenata polinoma brojioca i imenioca u formi"
							+ "\n npr 4,3,0,2 za polinom 4x^3+3x^2+2"
							+ "\nili tekstualan unos racionalne funkcije preko"
							+ "\nteksutalnog zapisa racionalne funkcije u formi"
							+ "\n4x^2-8x+16/x^3-4x^2+8x"
							+ "\nBez praznina izmedju!"
							+ "\n"
							+ "\nPritisnite dugme \"Test\" i trebalo bi da se u prozoru ispise postupak"
							+ "\ndobijanja parcijalnih razlomaka"
							+ "\n"
							+ "\nMozete takodje izabrati u padajucem meniju, neke od ponudjenih primera"
							+ "\n"
							+ "\nHvala!");
		}
	}
	
	/**
	 * parsirananje niza koeficijanata brojica i imenioca  
	 * @return niz koeficijenata u formi potrebnoj za arugment metode testiranje(double...)
	 */
			
	private double[] parseText(){
		String[] strbr = this.brojText.getText().split(",");
		String[] strim = this.imeText.getText().split(",");
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
	
	
	
	

	
	
	
	public static void main(String[] args){
		new UserInterface();
	}
	
}
