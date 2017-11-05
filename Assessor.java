import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;


/**
 * 
 * @authors Seth Turnage, Jacqueline Fonseca
 * @version 2.0 11/5/2017
 * 
 */

public class Assessor extends TutoringPanel implements ActionListener
{
	
	static final long serialVersionUID = 1L;

	/*
	 *  Elements to be added into the edu.asu.CSE360.recitation01.group05.Assessor panel
	 */
	JPanel panel;
	JButton b1, b2, b3, submit;
	JMenuItem item1, item2, item3;
	JCheckBox i1, i2, i3;
	JOptionPane dialog;
	JTextArea question;
	static int state;
	public static int q1attempts = 0; 
	public static int q2attempts = 0;
	public static int q3attempts = 0;
	public static int q4attempts = 0;
	public static int totalcorrect = 0;
	private boolean question1complete = false, question2complete = false, question3complete = false, question4complete = false;
	private Fillintheblank q1;
	private MultipleChoice q3;
	private MultipleMultipleChoice q2;
	private MultipleChoice q4;
	
	/**
	 * Creates panel and instantiates components
	 */
	public Assessor()
	{	
		
		setLayout(new CardLayout());
		
		Color newColor = new Color(165, 200, 180);
		JLabel name = new JLabel("<html><div style='text-align: center;'>" + "<b>Assessor: </b> Jacqueline Fonseca" + "</div></html>");
		setBackground(newColor); 
		add(name, "Name");
		
		q1 = new Fillintheblank(new SimpleQuestion(),"What is the extension for a java file?",".java");
		add(q1, "Question 1");
		
		String choices[]= {"cout<< 'hello world' ","public static class {}","switch(i) {case0: return 1;default: break;}"};
		int answers[]= {0,0,1};
		q2 = new MultipleMultipleChoice(new SimpleQuestion(),"Which are valid java statements?",choices,answers);
		add(q2,"Question 2");
		
		
		String choices3[]= {"O(1) ","O(n)","O(n^2)"};
		int answers3[]= {1,0,0};
		q3 = new MultipleChoice(new SimpleQuestion(),"What is the access time for a linked list?",choices3,answers3);
		add(q3, "Question 3");
		
		String choices4[]= {".length",".size()",".length()"};
		int answers4[]= {0,0,1};
		q4 = new MultipleChoice(new SimpleQuestion(),"The number of elements in an ArrayList is returned by ArrayList_______ .",choices4,answers4);
		add(q4, "Question 4");
		
	}
	
	/**
	 * Establishes what should be shown in the panel
	 * @param stateChange current state of slider
	 */
	@Override
	 public void update(int state)
	 {
		 
		//Current state of slider
		Assessor.state = state;
		
				CardLayout layout = (CardLayout) getLayout();
				switch(state) {
					case 0:
						layout.show(this, "Name");
						break;
					case 1:
						layout.show(this, "Question 1");
						break;
					case 2:
						layout.show(this, "Question 2");
						break;
					case 3:
						layout.show(this, "Question 3");
						break;
					case 4:
						layout.show(this, "Question 4");
						break;
					default:
						System.out.println("Invalid state sent to Assessor");
						break;
					}
	 }
		



	@Override
	public void onLogout() {
		q1attempts = q2attempts = q3attempts = q4attempts = 0;
		question1complete = question2complete = question3complete = question4complete = false;
	}

	@Override
	public void onSave() {

	}
	/**
	 * simple update method.
	 * calls update and revalidate since this is a child of JPanel
	 */
	public void actionPerformed(ActionEvent e)
	{
		/*
		 *-Accesses methods from questions (decorator pattern) to pass information to parent..
		 *-.. so that they can be accessed as public variables by the companion class. 
		 */
		question1complete = q1.isComplete();
		question2complete = q2.isComplete();
		question3complete = q3.isComplete();
		question4complete = q4.isComplete();
		q1attempts = q1.getAttempts();
		q2attempts = q2.getAttempts();
		q3attempts = q3.getAttempts();
		q4attempts = q4.getAttempts();
				
		/*
		 *-Logic to set the totalcorrect integer.
		 */
		int temptotal = 0;
			if(question1complete) {temptotal++;}
			if(question2complete) {temptotal++;}
			if(question3complete) {temptotal++;}
			if(question4complete) {temptotal++;}
		totalcorrect = temptotal;
		
		update(state);
		revalidate();
		repaint();		
	}
}
