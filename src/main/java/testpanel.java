import javax.swing.JButton;
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
 *	 		  | Concrete Component |  |  Decorator  |<>------| 
 *                                             |
 *                                    |* Concrete Decorator *|
 * 
 */
public class testpanel extends QuestionDecorator{
	
	/**
	 * 
	 */
	

	public testpanel(JPanel otherpanel,String Question, String choices[],int answers[]){
		super(otherpanel);
		
		addtopanel(new JButton());
	}

	@Override
	protected int setcurrentAnswer(int answerKey) {
		// TODO Auto-generated method stub
		return 0;
	}

	


	
}
