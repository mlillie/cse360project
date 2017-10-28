import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
	protected ArrayList<Question> questionList;
	private JScrollPane boxpane;
	private JButton submitButton;
	protected int answerKey = 0;
	protected int currentAnswer = 0;
	
	public QuestionDecorator(Question questiontobedecorated) {
		//instantiate list and add question to list
		//setMinimumSize(this.getParent().getMinimumSize());
		questionList = new ArrayList<Question>();
		questionList.add(questiontobedecorated);
		
		//relevant JScrollBar Properties
		boxpane = new JScrollPane();
		//boxpane.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		boxpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		boxpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		setBackground(Color.white);
		
		//ensure that when placed in the box layout, the question expands to its maximum width
		((SimpleQuestion) questiontobedecorated).setMaximumSize(new Dimension(Integer.MAX_VALUE, this.getMinimumSize().height));
		
		//add question to panel
		boxpane.add((SimpleQuestion)questiontobedecorated);
		add(boxpane, BorderLayout.CENTER);
		
		submitButton = new JButton("Submit");
		
		submitButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
		    //answer when submit button is pressed
		    answer();
		  }
		});
		
		add(submitButton, BorderLayout.SOUTH);
	}
	
	public void add(Question added_question){
		//add question to list
		questionList.add(added_question);
		//add question to panel
		add(added_question);
	}
	
	public boolean answer(int index) {
		if (index >= questionList.size()) {
			return false;
		}
		return ((SimpleQuestion) questionList.get(index)).answer();
	}
	
	public int getAttempts(int index) {
		if (index >= questionList.size()) {
			return -1;
		}
		return ((SimpleQuestion) questionList.get(index)).getAttempts();
	}
	
	public boolean isComplete(int index) {
		if (index >= questionList.size()) {
			return false;
		}
		return ((SimpleQuestion) questionList.get(index)).isComplete();
	}
	
	
	@Override 
	public boolean isComplete() {
		for (int index = 0; index < questionList.size(); index++) {
			if (((SimpleQuestion) questionList.get(index)).isComplete() == false && index == questionList.size() - 1) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int getAttempts() {
		int attempts = 0;
		for (int index = 0; index < questionList.size(); index++) {
			attempts = attempts + ((SimpleQuestion) questionList.get(index)).getAttempts();
		}
		return attempts;
	}
	
	@Override
	public boolean answer() {
		for (int index = 0; index < questionList.size(); index++) {
			if (((SimpleQuestion) questionList.get(index)).answer() == false && index == questionList.size() - 1) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void update() {
		for (int index = 0; index < questionList.size(); index++) {
			((SimpleQuestion) questionList.get(index)).update();
		}
	}
}
