import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

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

import LatexWrapper.LatexPaneResizable;
import LatexWrapper.LatexWriter;


public class UserInterface extends JFrame implements ActionListener {
	JPanel p = new JPanel();
	//center
	LatexPaneResizable latexPanel = new LatexPaneResizable();
	//JTextArea pTextArea =  new JTextArea();
	//JScrollPane pScroll = new JScrollPane(
	//		pTextArea,
	//		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	//		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
			
	//);
	
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
	JLabel imenilac = new JLabel("Imenilac: ");
	JTextField brojText = new JTextField(10);
	JTextField imeText = new JTextField(10);
	public UserInterface(){
		super("Partial Fractions Decomposition");
		setSize(600,400);
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
		south.setLayout(new GridLayout(2,2,5,5));
		south.add(brojilac);
		south.add(brojText);
		south.add(imenilac);
		south.add(imeText);
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
			
			testParam = parseText(); 
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
	public static void main(String[] args){
		new UserInterface();
	}
	
}
