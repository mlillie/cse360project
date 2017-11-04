import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * This class is the assignments window for the instructor.It will allow 
 * the instructor to view how an assignment will be seen. This class does
 *  not keep track of the number of correct or incorrect answers.
 * @author Jacqueline Fonseca
 * @version 11/03/2017
 */
public class InstructorsAssignments extends JFrame{
	private final Dimension FRAME_DIMENSION = new Dimension(500,300);
	private final Dimension PANEL_DIMENSION = new Dimension(300,600);
	private final Dimension BUTTON = new Dimension(50,25);
	private final Dimension FIELD = new Dimension(300,25);
	private JButton button, next;
	private JRadioButton choice1, choice2,choice3;
	private JTextField textField;
	private JTextArea question;
	private ButtonGroup group;
	private JPanel panel, choicesPanel;
	private int currentQuestion = 0;
	private ArrayList<LoadQuestions> questionList;
	private GridBagConstraints gbc;
	
	InstructorsAssignments()
	{	//creating the panel
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		setTitle("View of Current Assignment");
		setPreferredSize(FRAME_DIMENSION);
		setMinimumSize(FRAME_DIMENSION);
		
		panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(PANEL_DIMENSION);
		
		//Panel to add current choices
		choicesPanel = new JPanel(new GridBagLayout());
		
		//JTextArea to display current questions
		question = new JTextArea("");
		question.setPreferredSize(new Dimension(300,50));
		question.setMinimumSize(new Dimension(300,50));
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setEditable(false);
		question.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		question.setBackground(panel.getBackground());
		
		//creates JRadioButtons to allow for the user to select their answer
		choice1 = new JRadioButton("");
		
		choice1.addActionListener(e -> {
 
			if (choice1.getText().equalsIgnoreCase(questionList.get(currentQuestion).getAnswer()))
			{
				
				JOptionPane.showConfirmDialog(panel, 
			            "Correct!", "", 
			            JOptionPane.DEFAULT_OPTION,
			            JOptionPane.INFORMATION_MESSAGE);	

			}
			
			else
			{
				JOptionPane.showConfirmDialog(panel, 
			            "Incorrect!", "", 
			            JOptionPane.DEFAULT_OPTION,
			            JOptionPane.INFORMATION_MESSAGE);	

			}
		});
		choice2 = new JRadioButton("");
		choice2.addActionListener(e -> {
			if (choice2.getText().equalsIgnoreCase(questionList.get(currentQuestion).getAnswer()))
			{
				JOptionPane.showConfirmDialog(panel, 
			            "Correct!", "", 
			            JOptionPane.DEFAULT_OPTION,
			            JOptionPane.INFORMATION_MESSAGE);	

			}
			
			else
			{
				JOptionPane.showConfirmDialog(panel, 
			            "Incorrect!", "", 
			            JOptionPane.DEFAULT_OPTION,
			            JOptionPane.INFORMATION_MESSAGE);	

			}
		});
		choice3 = new JRadioButton("");	
		choice3.addActionListener(e -> {
			if (choice3.getText().equalsIgnoreCase(questionList.get(currentQuestion).getAnswer()))
			{
				JOptionPane.showConfirmDialog(panel, 
			            "Correct!", "", 
			            JOptionPane.DEFAULT_OPTION,
			            JOptionPane.INFORMATION_MESSAGE);	

			}
			
			else
			{
				JOptionPane.showConfirmDialog(panel, 
			            "Incorrect!", "", 
			            JOptionPane.DEFAULT_OPTION,
			            JOptionPane.INFORMATION_MESSAGE);	

			}
		});
		
		//ButtonGroup for the three assessment choices
		group = new ButtonGroup();
		group.add(choice1);
		group.add(choice2);
		group.add(choice3);
		
		//loads the questions into an ArrayList
		questionList = (new LoadAssignments().getQuestionList());		
		
		//Button to allow the user to start viewing the assessment
		button = new JButton("Start");
		button.setPreferredSize(button.getPreferredSize());
		//when clicked, questions will be shuffled and displayed
		button.addActionListener(e -> {
			shuffleQuestions();
			createAssessment(currentQuestion);
        });
		
		//Button to allow the user to go to the next question
		next = new JButton("Next");
		next.setMaximumSize(BUTTON);
		next.setPreferredSize(BUTTON);
		//when clicked, user will be sent to the next question until they reach the end of the
		//assessment
		next.addActionListener(e->{
			if (currentQuestion > (questionList.size() - 3))
			{
				panel.removeAll();
				question.setText("No more questions.");
				panel.add(question, gbc);
				panel.revalidate();
				panel.repaint();
			}
			else
			{
				currentQuestion++;
				createAssessment(currentQuestion);
			}
		});
		//adds each component to main JPanel
		choicesPanel.add(button);
		next.setVisible(false);
		panel.add(question, gbc);
		panel.add(choicesPanel,gbc);
		panel.add(next,gbc);

		//JTextField for user input
		textField = new JTextField();
		textField.setPreferredSize(FIELD);
		textField.addKeyListener(new KeyListener() {
			//Will submit and evaluate answer when enter is  pressed
			public void keyPressed(KeyEvent enter)
				{
					if (enter.getKeyCode() == KeyEvent.VK_ENTER)
					{
						String submitted = textField.getText();
						if (submitted.equalsIgnoreCase(questionList.get(currentQuestion).getAnswer()))
						{
							JOptionPane.showConfirmDialog(panel, 
						            "Correct!", "", 
						            JOptionPane.DEFAULT_OPTION,
						            JOptionPane.INFORMATION_MESSAGE);	
						}
						else
						{
							JOptionPane.showConfirmDialog(panel, 
						            "Incorrect!", "", 
						            JOptionPane.DEFAULT_OPTION,
						            JOptionPane.INFORMATION_MESSAGE);
						}
							
					}
				}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		add(panel);
		pack();
		setVisible(true);
	}
	
	/**
	 * Will update the window to show the correct question and format
	 * @param Index the current question
	 */
	public void createAssessment(int index)
	{
		choicesPanel.removeAll();
		//Updates question
		this.question.setText(questionList.get(index).getQuestion()); 
		//If there are no choices, creates a fill-in-the-blank question 
		if (questionList.get(index).getChoices().length == 1)
		{
			createFillIn( index);
			next.setVisible(true);
			panel.revalidate();
			return;
		}
		//if there are three choices, create a multiple choice question
		else
		{
			createMultipleChoice(index);
			next.setVisible(true);
			panel.revalidate();
		}
	}
	/**
	 * Creates the multiple choice question
	 * @param index Current question index
	 */
	public void createMultipleChoice(int index)
	{
		//clears any chosen option
		group.clearSelection();
		//gets the choices
		choice1.setText(questionList.get(index).getChoices()[0]);
		choice2.setText(questionList.get(index).getChoices()[1]);
		choice3.setText(questionList.get(index).getChoices()[2]);
		//adds choices to the panel
		choicesPanel.add(choice1, gbc);
		choicesPanel.add(choice2, gbc);
		choicesPanel.add(choice3, gbc);
		choicesPanel.revalidate();
		choicesPanel.repaint();
	}
	public void createFillIn(int index)
	{	
		//clears textfield
		textField.setText("");
		//adds the textfield to the panel
		choicesPanel.removeAll();
		choicesPanel.add(textField);

	}

	/**
	 * Will randomize the questions in the ArrayList
	 */
	public void shuffleQuestions()
	{
		Collections.shuffle(questionList);
	}
	
}
