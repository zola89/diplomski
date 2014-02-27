import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.sun.org.apache.xpath.internal.operations.Or;

import LatexWrapper.LatexPaneResizable;
import LatexWrapper.LatexWriter;


public class UserInterface extends JFrame implements ActionListener {
	JPanel p = new JPanel();
	//center
	LatexPaneResizable latexPanel = new LatexPaneResizable();

	
	//menu
	JMenuBar menuBar = new JMenuBar();
	JMenu menu1 = new JMenu("Primeri");
	JMenuItem primer1 = new JMenuItem("Primer 1");
	JMenuItem primer2 = new JMenuItem("Primer 2");
	JMenuItem primer3 = new JMenuItem("Primer 3");
	JMenuItem primer4 = new JMenuItem("Primer 4");
	
	JMenu menu2 = new JMenu("Pomoc");
	JMenuItem uputstvo = new JMenuItem("Uputstvo");
	JMenuItem author = new JMenuItem("O autoru");
	
	
	//west
	JPanel west = new JPanel();
	JButton test = new JButton("Test");
	JButton clear = new JButton("Clear");
	
	
	//south
	JPanel south = new JPanel();
	JLabel brojilac = new JLabel("Brojilac: ");
	JLabel pars = new JLabel("Pars: ");
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
		//pTextArea.setEditable(false);
		//pTextArea.setLineWrap(true);
		//pTextArea.setWrapStyleWord(true);
		latexPanel.setBackgroundColor(Color.WHITE);
		p.add(latexPanel,BorderLayout.CENTER);
		
		//north
		
		menu1.add(primer1);
		menu1.add(primer2);
		menu1.add(primer3);
		menu1.add(primer4);
		
		primer1.addActionListener(this);
		primer2.addActionListener(this);
		primer3.addActionListener(this);
		primer4.addActionListener(this);
		
		menu2.add(uputstvo);
		menu2.add(author);
		
		menuBar.add(menu1);
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
		//south
		south.setLayout(new GridLayout(3,2,5,5));
		south.add(brojilac);
		south.add(brojText);
		south.add(imenilac);
		south.add(imeText);
		south.add(pars);
		south.add(parsText);
		south.setBorder(new EmptyBorder(10, 10, 10, 10) );
		
		p.add(south,BorderLayout.SOUTH);
		add(p);
		setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src=e.getSource();
		//test
		String str;
		double[] testParam;
		if(src.equals(test)){
			double[] test;
			
			test= parseText21();
			testParam = parseText();
			String[] oba = this.parsText.getText().split("/");
			str=Test.testiranje(parseText2(oba[1]),parseText2(oba[0]));
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
		
		if(src.equals(primer1)){
			this.brojText.setText("");
	        this.brojText.setText("1,3");
	        this.imeText.setText("");
	        this.imeText.setText("1,9,24,20,0");
		}
		
		if(src.equals(primer2)){
			this.brojText.setText("");
			this.brojText.setText("1, 0, -2");
			this.imeText.setText("");
			this.imeText.setText("1, 1, -3, -5, -2");
		}
		
		if(src.equals(primer3)){
			this.brojText.setText("");
	        this.brojText.setText("2, -4, 5,-3, 1, 3, 0");
	        this.imeText.setText("");
	        this.imeText.setText("1, -3, 5, -7, 7, -5, 3,-1");
		}
		
		if(src.equals(primer4)){
			this.brojText.setText("");
	        this.brojText.setText("4, -8, 16");
	        this.imeText.setText("");
	        this.imeText.setText("1, -4, 8, 0");
		}
		
	}
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
	
	private double[] parseText2(String par){
		Pattern monomial = Pattern.compile("([+|-]?)([0-9]*)(x)(\\^([1-9][0-9]*))?|([+|-]?[0-9]+)");
		
	    Matcher m = monomial.matcher(par);
	    //int p = m.groupCount();
	    //int x = Integer.parseInt(valx);
	    //int value;
	    //int total = 0;
	    m.find();
	    String pow = m.group(5);
	    double[] temp = new double[Integer.parseInt(pow)+2];
	    Arrays.fill(temp, 0);
	    temp[0]= temp.length-1;
	    int value,powint;
	    m.reset();
	    while (m.find()) {
	    	String mul = m.group(2);
	        value = (mul == null || mul.equals("")) ? 1 : Integer.parseInt(m.group(2));
	        if(m.group(1)!=null){
	        	if (m.group(1).equals("-") ) value*=-1;
	        }
	        pow = m.group(5);
	        
	        powint = (pow == null) ? 1: Integer.parseInt(pow);
	        if ("x".equals(m.group(3)) )
	        	temp[temp.length-1-powint]=value;
	        
	        if (m.group(6)!= null){
	        	temp[temp.length-1]=Integer.parseInt(m.group(6));
	        }
	        
//	        if ("-".equals(m.group(1)))
//	            value = -value;
//
//	        total += value;
	    	System.out.println(m);
	    	
	    }

	    
	    //return total;
		 
//		String[] strbr = this.brojText.getText().split(",");
//		String[] strim = this.imeText.getText().split(",");
//		int i=1;
//		double[] temp = new double[strbr.length+strim.length+2];
//		temp[0]=strim.length;
//		for (String line : strim) {
//			temp[i]= Double.parseDouble(line);
//			i++;
//		}
//		temp[i]=strbr.length;
//		i++;
//		for (String line : strbr) {
//			temp[i]= Double.parseDouble(line);
//			i++;
//		}
		return temp;
	}  
	
	private double[] parseText21(){
		String par = this.parsText.getText();
		String[] oba = this.parsText.getText().split("/");
//		
		double[] temp = new double[100];
////		double[] drugi = oba[1];
//		  List<String> l1 = Arrays.asList(healthMessages1);
//		  l1.addAll(Arrays.asList(healthMessages2));
//		  HealthMessage[] result = l1.toArray();
//		
//		char result[] = new char[info.length + data.length];
//		System.arraycopy(info, 0, result, 0, info.length);
//		System.arraycopy(data, 0, result, info.length, data.length);		

		
		double[] temp1,temp2;
		for (int j = 0; j < oba.length; j++) {
			if(j==0)temp1=parseText2(oba[j]);
			if(j==1)temp2=parseText2(oba[j]);
			temp[0]=1;
			
		}
		
		double[] rez = new double[100];
//		double[] rez = new double[temp1.length + temp2.length];
//		System.arraycopy(temp1, 0, rez, 0, temp1.length);
//		System.arraycopy(temp2, 0, rez, temp1.length, temp2.length);	
		
//		List<double[]> l1 = new List<double[]>() {
//		};
//		for (String string : oba) {
//			l1.addAll(Arrays.asList(parseText2(string)));
//		}
//		int i=0,k=0;
//
//		for (double[] ds : l1) {
//			i+=ds.length;
//		}
//		
//		double[] d= new double[i];
//		for (double[] ds : l1) {
//			for (int j = 0; j < ds.length; j++) {
//				d[k]=ds[j];
//				k++;
//			}
//		}
//		
//		
//		Object[] rezultat = l1.toArray();
		
		return rez;
	}  
	
	public static void main(String[] args){
		new UserInterface();
	}
	
}
