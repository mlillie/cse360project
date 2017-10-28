import java.awt.BorderLayout;
import java.awt.Color;
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
	private int attempts;
	private boolean complete;
	protected int answerKey = 0;
	protected int currentAnswer = 0;
	
	public SimpleQuestion() {
		attempts = 0;
		complete = false;
		setLayout(new BorderLayout());
		setBackground(Color.white);
	}
	
	@Override
	public void update() {
		if(complete == true) {
			setBackground(Color.GREEN);
			this.revalidate();
		}
		else {
			this.revalidate();
		}
	}
	
	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return complete;
	}

	@Override
	public int getAttempts() {
		// TODO Auto-generated method stub
		return attempts;
	}

	@Override
	public boolean answer() {
		// TODO Auto-generated method stub
		attempts++;
		if (answerKey == currentAnswer) {
			complete = true;
			update();
			return true;
		}
		update();
		return false;
	}


}
