import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *  The edu.asu.CSE360.recitation01.group05.Assessor class is one of the panels implemented into the edu.asu.CSE360.recitation01.group05.Universe JFrame.
 *  The panel's content will change in relation to the slider's position.
 *  Recitation Project 1
 *  Completion Time: 7 hours
 *
 *  @author Jacqueline Fonseca
 *  @version 1.1 10/11/2017
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
	public static int q1correct = 0, q1wrong = 0;
	public static int q2correct = 0, q2wrong = 0;
	public static int q3correct = 0, q3wrong = 0;
	public static int q4correct = 0, q4wrong = 0;
	private boolean question1complete = false, question2complete = false, question3complete = false, question4complete = false;
	private JTextArea jArea;
	private Fillintheblank q1;
	private MultipleChoice q3;
	private MultipleMultipleChoice q2;
	private MultipleChoice q4;
	/*
	 * Creates panel
	 */
	public Assessor()
	{	
	}
	
	/*
	 * Establishes what should be shown in the panel
	 * @param stateChange current state of slider
	 */
	@Override
	 public void update(int state)
	 {
		 
		//Current state of slider
		Assessor.state = state;
		
		//Layout of elements
		setLayout(new BorderLayout());
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		
		//Name label is visible
		if (state == 0)
		{
			removeAll();
			
			Color newColor = new Color(165, 200, 180);
			
			JLabel name = new JLabel("<html> <b>Assessor: </b> Jacqueline Fonseca </html>");
			
			panel.setBackground(newColor);
			panel.add(name);
			add(panel);
			}
		
		//Menu holding question 1 is visible
		//Text area and prompt are visible
		if (state == 1)
		{
			
			this.removeAll();
			if(question1complete == true) {
				question = new JTextArea("Question 1");
				add(question, BorderLayout.NORTH);
				Color newColor = new Color(165, 200, 180);
				
				JLabel name = new JLabel("<html> <b>Correct Attempts: </b>"+1+"<b>Incorrect Attempts: </b>"+(q1.getAttempts() -1)+"</html>");
				
				panel.setBackground(newColor);
				panel.add(name);
				add(panel);
			}
			else{
				//new MultipleMultipleChoice(JPanel otherpanel,String Question, String choices[],int answers[]){
				
				q1 = new Fillintheblank(new SimpleQuestion(),"What is the extension for a java file?",".java");
				add(q1);
				 
		}
		}
		
		
		
		//Checkboxes and question 2 are visible
		if (state == 3)
		{
			
			this.removeAll();
			if(question1complete == true) {
				question = new JTextArea("Question 2");
				add(question, BorderLayout.NORTH);
				Color newColor = new Color(165, 200, 180);
				
				JLabel name = new JLabel("<html> <b>Correct Attempts: </b>"+2+"<b>Incorrect Attempts: </b>"+(q2.getAttempts() -1)+"</html>");
				
				panel.setBackground(newColor);
				panel.add(name);
				add(panel);
			}
			else{
				//new MultipleMultipleChoice(JPanel otherpanel,String Question, String choices[],int answers[]){
				String choices[]= {"O(1) ","O(n)","O(n^2)"};
				int answers[]= {1,0,0};
				q3 = new MultipleChoice(new SimpleQuestion(),"What is the access time for a linked list?",choices,answers);
				add(q3);
				
		}
}
	
		
		//Buttons and question 3 are visible
		if (state == 2)
		{
			
			this.removeAll();
			if(question1complete == true) {
				question = new JTextArea("Question 1");
				add(question, BorderLayout.NORTH);
				Color newColor = new Color(165, 200, 180);
				
				JLabel name = new JLabel("<html> <b>Correct Attempts: </b>"+q1correct+"<b>Incorrect Attempts: </b>"+q1wrong+"</html>");
				
				panel.setBackground(newColor);
				panel.add(name);
				add(panel);
			}
			else{
				//new MultipleMultipleChoice(JPanel otherpanel,String Question, String choices[],int answers[]){
				String choices[]= {"cout<< 'hello world' ","public static class {}","switch(i) {case0: return 1;default: break;}"};
				int answers[]= {0,0,1};
				q2 = new MultipleMultipleChoice(new SimpleQuestion(),"Which are valid java statements?",choices,answers);
				add(q2);
				
				}
		}
		
		if (state == 4){
			
			this.removeAll();
			if(question1complete == true) {
				question = new JTextArea("Question 1");
				add(question, BorderLayout.NORTH);
				Color newColor = new Color(165, 200, 180);
				
				JLabel name = new JLabel("<html> <b>Correct Attempts: </b>"+q1correct+"<b>Incorrect Attempts: </b>"+q1wrong+"</html>");
				
				panel.setBackground(newColor);
				panel.add(name);
				add(panel);
			}
			else{
				//new MultipleMultipleChoice(JPanel otherpanel,String Question, String choices[],int answers[]){
				String choices[]= {".length",".size()",".length()"};
				int answers[]= {0,0,1};
				q4 = new MultipleChoice(new SimpleQuestion(),"The number of elements in an ArrayList is returned by ArrayList_______ .",choices,answers);
				add(q4);
				
				}		
			}
	 }
		



	@Override
	public void onLogout() {
		q1correct = q2correct = q3correct = q4correct = q1wrong = q2wrong = q3wrong = q4wrong = 0;
		question1complete = question2complete = question3complete = question4complete = false;
	}

	@Override
	public void onSave() {

	}
	/*
	 * Handles buttons, items, and checkboxes being selected.
	 * Creates a pop-up of what was pressed.
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		//depricated and totally unnecessary. Commented as a courtesy.
		
		/*
		 * Object source = e.getSource();

		if (source == item1 || source == item3) {
			JOptionPane.showMessageDialog(panel, "Wrong Answer!");
			if(question.getText().equalsIgnoreCase("Question 4"))
				q4wrong++;
		}
		if (source == item2) {
			JOptionPane.showMessageDialog(panel, "Correct!");
			if(question.getText().equalsIgnoreCase("Question 4")) {
				question4complete = true;
				q4correct++;
			} else {
				question2complete = true;
				q2correct++;
			}
		}
		if (source == b1) {
			JOptionPane.showMessageDialog(panel,"Good Choice!");
			question3complete = true;
			q3correct++;}
		if (source == b2 || source == b3) {
			JOptionPane.showMessageDialog(panel,"Wrong Choice!");
			q3wrong++;}
		 * */
		update(state);
		revalidate();
		repaint();		
	}
}
