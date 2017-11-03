import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * 
 * @author Seth Turnage
 * @serial 1L
 * @class MultipleChoice:
 * -Inherits Decorator and adds multiple choice functionality.
 * 
 * 
 * 					            | Component |<-----------------|
 * 						            /    \                     |
 *	 		  | Concrete Component |  |  Decorator  |<>------| 
 *                                           		  |
 *                                  		  |* Concrete Decorator *|
 * 
 */
public class MultipleChoice extends QuestionDecorator implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8659946918957982111L;
	private JPanel questionPanel;
	private ArrayList<JRadioButton> answerlist;
	
	public MultipleChoice(JPanel otherpanel,String Question, String choices[],int answers[]){
		super(otherpanel);
		setAnswer(parseMultipleChoiceFromArray(answers));
		questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
		answerlist = new ArrayList<JRadioButton>();
		setQuestion(Question);
		ButtonGroup newgroup = new ButtonGroup();
		
		for(int i = 0;i < choices.length;i++) {
			JRadioButton q = new JRadioButton(choices[i]);
			newgroup.add(q);
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
}

	

