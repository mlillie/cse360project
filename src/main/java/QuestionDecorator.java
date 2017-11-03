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
public abstract class QuestionDecorator extends JPanel implements Question{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane boxpane;
	private JButton submitButton;
	protected int answerKey = 0;
	protected int currentAnswer = 0;
	private boolean complete = false;
	private JPanel questiontobedecorated;
	
	public QuestionDecorator(JPanel questiontobedecorated){
		this.questiontobedecorated = questiontobedecorated;
		setLayout(new GridLayout(1,1));
		this.add(questiontobedecorated);
		
		setVisible(true);
	}
	
	public void addtopanel(Component c) {
		((Question) questiontobedecorated).addtopanel(c);
	}
	
	public void setAnswer(int answerKey) {
		((Question) questiontobedecorated).setAnswer(answerKey);
	}

	
	
	protected abstract int setcurrentAnswer(int answerKey);
	
	@Override 
	public boolean isComplete() {
		
		return true;
	}
	
	@Override
	public int getAttempts() {
		
		return 0;
	}
	
	@Override
	public boolean checkanswer() {
		
		return true;
	}
	

}
