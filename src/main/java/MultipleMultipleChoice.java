import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Seth Turnage
 * @serial 1L
 * @class SimpleQuestion:
* @class MultipleChoice:
 * -Inherits Decorator and adds multiple-multiple choice functionality.
 * 
 * 
 * 					            | Component |<-----------------|
 * 						            /    \                     |
 *	 		  | Concrete Component |  |  Decorator  |<>------| 
 *                                           		  |
 *                                  		  |* Concrete Decorator *|
 * 
 */
public class MultipleMultipleChoice extends QuestionDecorator implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8659946918957982111L;
	private JPanel questionPanel;
	private ArrayList<JCheckBox> answerlist;
	
	public MultipleMultipleChoice(JPanel otherpanel,String Question, String choices[],int answers[]){
		super(otherpanel);
		setAnswer(parseMultipleChoiceFromArray(answers));
		questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
		answerlist = new ArrayList<JCheckBox>();
		setQuestion(Question);
		
		for(int i = 0;i < choices.length;i++) {
			JCheckBox q = new JCheckBox(choices[i]);
			questionPanel.add(q);
			answerlist.add(q);
			q.addActionListener((ActionListener) this);
		}
		
		addtoQuestion(questionPanel);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		int length = answerlist.size();
		int[] answers = new int [length];
		
		for(int index = 0;index < answerlist.size();index++) {
			if (answerlist.get(index).isSelected()) {
				answers[index] = 1;
			}else {
				answers[index] = 0;
			}
		}
		setAttempt(parseMultipleChoiceFromArray(answers));
	}

	@Override 
	public void pause() {
		super.pause();
	}
	
	@Override
	public void unpause() {
		super.unpause();
	}


}

	

