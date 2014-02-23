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
		if(src.equals(test)){
			
			//int num=Integer.parseInt(input.getText());
			//ensures that primes object has a long enough range
			//if(primes.getLength()<num){
			//	primes=new Primes(num,primes);
			//}
			//finds prime from object
			//if(primes.test(num)){
			//	print(num+" is prime");
			//	primesFound.setText("1");
			//}else{
			//	print(num+" is not prime");
			//	primesFound.setText("0");
			//}
			//printEnd();
			//process=0;
			 
			str= Test.testiranje(8, 1, -3, 5, -7, 7, -5, 3,-1, 7, 2, -4, 5,-3, 1, 3, 0);
			LatexWriter writer;
		       writer = this.latexPanel.getWriter();
		       //writer.clear();
			 
		       writer.println( "\\frac {V_m} {K_M+S}",15);
		       writer.println(str,15);
		       //writer.println("\n\nRezultati:", 15);
		       this.latexPanel.repaint();
		}
		if(src.equals(clear)){
			LatexWriter writer;
	        writer = this.latexPanel.getWriter();
	        writer.clear();
	        this.latexPanel.repaint();
		}
	}
	
	public static void main(String[] args){
		new UserInterface();
	}
	
}
