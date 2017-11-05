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
import java.util.Timer;
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
	private long starttime, pausedtime = 0;
	private JPanel questionPanel;
	protected boolean complete;
	protected int answerKey = 0;
	protected int currentAnswer = -1;
	
	
	public SimpleQuestion() {
		
		starttime =  System.nanoTime();
		
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
		pausedtime =  System.nanoTime();
	}
	
	@Override
	public void unpause() {
	    starttime += pausedtime-starttime;
	    pausedtime = 0;
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
		return attempts;
	}

	@Override
	public boolean checkanswer() {
		if (answerKey == currentAnswer && complete == false) {
			complete = true;
			submitButton.setText("complete in "+ (TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-starttime))+ " seconds");
			submitButton.setBackground(Color.green);
		}
		return (answerKey == currentAnswer);
	}


}
