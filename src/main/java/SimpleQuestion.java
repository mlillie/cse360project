import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.ldap.Control;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;

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
	protected boolean complete;
	protected int answerKey = 0;
	protected int currentAnswer = -1;
	protected final int questionId;
	
	
	public SimpleQuestion(int questionId) {
		this.questionId = questionId;
		
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
	
	@Override
	public void pause() {
		ControlCenter.getInstance().getStopwatches()[getQuestionId()].stop();
	}
	
	@Override
	public void unpause() {
		ControlCenter.getInstance().getStopwatches()[getQuestionId()].start();
	}

	@Override
	public int getQuestionId() {
		return questionId;
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

	public boolean isComplete() {
		return complete;
	}

	public int getAttempts() {
		return ControlCenter.getInstance().getCorrectAnswers()[getQuestionId()] +
				ControlCenter.getInstance().getIncorrectAnswers()[getQuestionId()];
	}

	@Override
	public boolean checkanswer() {
		if (answerKey == currentAnswer && complete == false) {
			complete = true;
			ControlCenter.getInstance().getStopwatches()[getQuestionId()].stop();
			ControlCenter.getInstance().getCorrectAnswers()[getQuestionId()]++;
			long msSpent = ControlCenter.getInstance().getStopwatches()[getQuestionId()].elapsedTime();
			long secondsSpent = msSpent / 1000;
			msSpent %= 1000;
			long minutesSpent = secondsSpent / 60;
			secondsSpent %= 60;
			submitButton.setText("Completed in "+ minutesSpent + ":" + secondsSpent + ":" + msSpent);
			submitButton.setBackground(Color.GREEN);
			ControlCenter.getInstance().updateImage();
		} else if(answerKey != currentAnswer) {
			ControlCenter.getInstance().getIncorrectAnswers()[getQuestionId()]++;
		}
		return (answerKey == currentAnswer);
	}


}
