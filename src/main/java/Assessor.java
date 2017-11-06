import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.border.TitledBorder;


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
	public static int q5attempts = 0;
	public static int q6attempts = 0;
	public static int q7attempts = 0;
	public static int q8attempts = 0;
	public static int totalcorrect = 0;
	private boolean question1complete = false, question2complete = false, question3complete = false, question4complete = false, question5complete = false, 
			question6complete = false, question7complete = false, question8complete = false;
	
	private Fillintheblank q1, q6;
	private MultipleChoice q3, q7, q4;
	private MultipleMultipleChoice q2, q5, q8;
	private ControlCenter controlcenter;
	
	/**
	 * Creates panel and instantiates components
	 */
	public Assessor()
	{	
		//instantiate singleton controlcenter
		controlcenter = ControlCenter.getInstance();
		controlcenter.getStopwatches()[0].start();
		
		setLayout(new CardLayout());
		//namelabel
		Color newColor = new Color(165, 200, 180);
		JLabel name = new JLabel("<html><div style='text-align: center;'>" + "<b>Assessor: </b> Jacqueline Fonseca" + "</div></html>");
		setBackground(newColor); 
		add(name, "Name");
		
		//question1, assembled from Question decorator pattern
		q1 = new Fillintheblank(new SimpleQuestion(),"What is the extension for a java file?",".java");
		add(q1, "Question 1");

		//question2, assembled from Question decorator pattern
		String choices[]= {"cout<< 'hello world' ","public static class {}","switch(i) {case0: return 1;default: break;}"};
		int answers[]= {0,0,1};
		q2 = new MultipleMultipleChoice(new SimpleQuestion(),"Which are valid java statements?",choices,answers);
		add(q2,"Question 2");
		
		//question3, assembled from Question decorator pattern	
		String choices3[]= {"O(1) ","O(n)","O(n^2)"};
		int answers3[]= {1,0,0};
		q3 = new MultipleChoice(new SimpleQuestion(),"What is the access time for a linked list?",choices3,answers3);
		add(q3, "Question 3");

		//question4, assembled from Question decorator pattern
		String choices4[]= {".length",".size()",".length()"};
		int answers4[]= {0,0,1};
		q4 = new MultipleChoice(new SimpleQuestion(),"The number of elements in an ArrayList is returned by ArrayList_______ .",choices4,answers4);
		add(q4, "Question 4");
		
		//question5, assembled from Question decorator pattern
		String choices5[]= {"+","%","!","/","-"};
		int answers5[]= {1,1,1,1,1};
		q5 = new MultipleMultipleChoice(new SimpleQuestion(),"Which are valid arithmetic operators in java?",choices5,answers5);
		add(q5,"Question 5");
		
		//question6, assembled from Question decorator pattern
		q6 = new Fillintheblank(new SimpleQuestion(),"Write a blank class named 'A' in java.","public class A {}");
		add(q6, "Question 6");
		
		//question7, assembled from Question decorator pattern
		String choices7[]= {"the end of the switch statement","the end of each case","the end of each discrete case","wherever"};
		int answers7[]= {0,0,1,0};
		q7 = new MultipleChoice(new SimpleQuestion(),"Where does the 'break;' go in a switch statement?",choices7,answers7);
		add(q7, "Question 7");
		
		//question8, assembled from Question decorator pattern
		String choices8[]= {"protected","private","public","package","friend"};
		int answers8[]= {1,1,1,1,0};
		q8 = new MultipleMultipleChoice(new SimpleQuestion(),"Which are valid visiblity modifiers in java?",choices8,answers8);
		add(q8,"Question 8");
		
		
		//unpausing all questions
		q1.pause();
		q2.pause();
		q3.pause();
		q4.pause();
		q5.pause();
		q6.pause();
		q7.pause();
		q8.pause();
	}
	
	/**
	 * Establishes what should be shown in the panel
	 * @param stateChange current state of slider
	 */
	@Override
	 public void update(int state)
	 {
		//pause all questions during slider transition
		q1.pause();
		q2.pause();
		q3.pause();
		q4.pause();
		q5.pause();
		q6.pause();
		q7.pause();
		q8.pause();
		//Current state of slider
		Assessor.state = state;
		
				/*
				 * Switch statement that alters the question displayed using cardlayout.
				 */
				CardLayout layout = (CardLayout) getLayout();
				switch(state) {
					case 0:
						layout.show(this, "Name");
						break;
					case 1:
						layout.show(this, "Question 1");
						q1.unpause();
						
						break;
					case 2:
						layout.show(this, "Question 2");
						q2.unpause();
						
						break;
					case 3:
						layout.show(this, "Question 3");
						q3.unpause();
						
						break;
					case 4:
						layout.show(this, "Question 4");
						q4.unpause();
						
						break;
					case 5:
						layout.show(this, "Question 5");
						q5.unpause();
						break;
					case 6:
						layout.show(this, "Question 6");
						q6.unpause();
						break;
					case 7:
						layout.show(this, "Question 7");
						q7.unpause();
						break;
					case 8:
						layout.show(this, "Question 8");
						q8.unpause();
						break;
					default:
						System.out.println("Invalid state sent to Assessor");
						break;
					}
	 }
		



	@Override
	public void onLogout() {
		q1attempts = q2attempts = q3attempts = q4attempts = q5attempts = q6attempts = q7attempts =q8attempts= 0;
		question1complete = question2complete = question3complete = question4complete = question5complete = question6complete = question7complete = question8complete = false;
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
		question5complete = q5.isComplete();
		question6complete = q6.isComplete();
		question7complete = q7.isComplete();
		question8complete = q8.isComplete();
		q1attempts = q1.getAttempts();
		
		q2attempts = q2.getAttempts();
		
		q3attempts = q3.getAttempts();
		
		q4attempts = q4.getAttempts();
		
		q5attempts = q5.getAttempts();
		
		q6attempts = q6.getAttempts();
		
		q7attempts = q7.getAttempts();
		
		q8attempts = q8.getAttempts();
		/*
		 *-Logic to set the totalcorrect integer.
		 */
		int temptotal = 0;
			if(question1complete) {temptotal++;}
			if(question2complete) {temptotal++;}
			if(question3complete) {temptotal++;}
			if(question4complete) {temptotal++;}
			if(question5complete) {temptotal++;}
			if(question6complete) {temptotal++;}
			if(question7complete) {temptotal++;}
			if(question8complete) {temptotal++;}
		totalcorrect = temptotal;
		
		//simple controlcenter implementation
		controlcenter.getCorrectAnswers()[0] = totalcorrect;
		if (totalcorrect == 8) {controlcenter.getStopwatches()[0].stop();}
		
		update(state);
		revalidate();
		repaint();		
	}
}
