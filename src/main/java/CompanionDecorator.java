import javax.swing.*;

/**
* parent to the different decorators
* Rec Project 4
*
* @author Ashley Goernitz
* @class CompanionDecorator
* @version 11/6/17
*/

public class CompanionDecorator implements Companion{
	protected Companion c;
	
	public void add(Companion c){
		this.c = c;
	}
	
	@Override
	public void speak(JComponent panel){
		this.c.speak(panel);
	}

}
