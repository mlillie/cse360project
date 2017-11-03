import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author Seth Turnage
 * @serial 1L
 * @class SimpleQuestion:
 * -implements Question interface and extends JPanel
 * 
 * 
 * 					            | Component |<-----------------|
 * 						            /    \                     |
 *	 		  |* Concrete Component *|  |  Decorator  |<>------| 
 *                                             |
 *                                    | Concrete Decorator |
 * 
 */
public class SimpleQuestion extends JPanel implements Question {

	private static final long serialVersionUID = 1L;
	private JButton submitButton;
	private int attempts;
	private JPanel questionPanel;
	protected boolean complete;
	protected int answerKey = 0;
	protected int currentAnswer = 0;
	
	public SimpleQuestion() {
		attempts = 0;
		complete = false;
		setLayout(new BorderLayout());
		setBackground(Color.white);
		submitButton = new JButton("Submit");

		
		submitButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
		    //answer when submit button is pressed
		    checkanswer();
		    
		  }
		});
		
		add(submitButton, BorderLayout.SOUTH, JLayeredPane.DEFAULT_LAYER);
	
		setVisible(true);
	}
	
	public void setAnswer(int Answer) {
		this.answerKey = Answer;
	}
	
	public void setAttempt(int Answer) {
		this.currentAnswer = Answer;
	}
	
	public void setQuestion(String question) {
		add(new JLabel(question),BorderLayout.NORTH);
	}

	public void addtoQuestion(Component c) {
		add(c, BorderLayout.CENTER);
	}
	

	
	@Override
	public boolean isComplete() {
		return complete;
	}

	@Override
	public int getAttempts() {
		return attempts;
	}

	@Override
	public boolean checkanswer() {
		if (answerKey == currentAnswer) {
			complete = true;
			submitButton.setText("complete");
			submitButton.setBackground(Color.green);
		}
		return (answerKey == currentAnswer);
	}


}
