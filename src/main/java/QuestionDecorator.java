import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
/**
 * 
 * @author Seth Turnage
 * @serial 1L
 * @class QuestionDecorator:
 * -implements Question interface and aggregates Question instances
 * 
 * 
 * 					            | Component |<-----------------|
 * 						            /    \                     |
 *	 	        | Concrete Component |  |*  Decorator  *|<>----| 
 *                                             |
 *                                    | Concrete Decorator |
 * 
 */
@SuppressWarnings("unused")
public abstract class QuestionDecorator extends JPanel implements Question{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JScrollPane boxpane;
	//private JButton submitButton;
	protected int answerKey = 0;
	protected int currentAnswer = 0;
	//private boolean complete = false;
	private JPanel questiontobedecorated;
	
	public QuestionDecorator(JPanel questiontobedecorated){
		this.questiontobedecorated = questiontobedecorated;
		setLayout(new GridLayout(1,1));
		this.add(questiontobedecorated);
		
		setVisible(true);
	}
	
	public void addtoQuestion(Component c) {
		((Question) questiontobedecorated).addtoQuestion(c);
	}
	
	public void setQuestion(String question) {
		((Question) questiontobedecorated).setQuestion(question);
	}
	
	public void setAnswer(int answerKey) {
		((Question) questiontobedecorated).setAnswer(answerKey);
	}

	public void setAttempt(int answerKey) {
		((Question) questiontobedecorated).setAttempt(answerKey);
	}
	
	public void pause() {
		((Question) questiontobedecorated).pause();
	}
	
	public void unpause() {
		((Question) questiontobedecorated).unpause();
	}
	
	/**
	 * Utility function to help parse arrays of answers into a unique integer.
	 * 
	 */
	public int parseMultipleChoiceFromArray(int array[]) {
		int returnedInteger=0;
		
		for (int index = 0; index < array.length ; index++) {
			if (array[index] != 0) {
				returnedInteger += (int)Math.pow((double)2, (double)index);
			}
		}
		
		return returnedInteger;
	}


	@Override
	public int getQuestionId() {
		return ((Question) questiontobedecorated).getQuestionId();
	}

	@Override 
	public boolean isComplete() {
		return ((Question) questiontobedecorated).isComplete();
	}
	
	@Override
	public int getAttempts() {
		return ((Question) questiontobedecorated).getAttempts();
	}
	
	@Override
	public boolean checkanswer() {
		return ((Question) questiontobedecorated).checkanswer();
	}
	

}
