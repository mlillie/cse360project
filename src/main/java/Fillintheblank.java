import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 * 
 * @author Seth Turnage
 * @serial 1L
 * @class SimpleQuestion:
* @class MultipleChoice:
 * -Inherits Decorator and adds fill-in-the-blank functionality.
 * 
 * 
 * 					            | Component |<-----------------|
 * 						            /    \                     |
 *	 		  | Concrete Component |  |  Decorator  |<>------| 
 *                                           		  |
 *                                  		  |* Concrete Decorator *|
 * 
 * 
 */
public class Fillintheblank extends QuestionDecorator implements FocusListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8659946918957982111L;
	private JPanel questionPanel;
	private ArrayList<JRadioButton> answerlist;
	private JTextArea jArea;
	private String answer;
	
	public Fillintheblank(JPanel otherpanel,String Question, String answers){
		super(otherpanel);
		setQuestion(Question);
		setAnswer(0);
		answer = answers;
		questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
		jArea = new JTextArea();
		
		jArea.addFocusListener(this);

		questionPanel.add(jArea);
		
		addtoQuestion(questionPanel);
	}
	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		String y = jArea.getText();
		y.replaceAll("\\s+"," ");
		y.trim();
				
		if (answer.equals(y)){
			setAttempt(0);
		}
		else {
			setAttempt(-1);
		}
	}
	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		String y = jArea.getText();
		y.replaceAll("\\s+"," ");
		y = y.trim();
				
		if (answer.equals(y)){
			setAttempt(0);
		}
		else {
			setAttempt(-1);
		}
	}


	
}

	

