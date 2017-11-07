import javax.swing.*;

/**
* @author Ashley Goernitz
* @class CompanionDecorator
* @version 11/6/17
* parent to the different decorators
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
