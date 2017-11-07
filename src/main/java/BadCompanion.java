import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
*  used when student is performing poorly
*  Rec Project 4
*
* @author Ashley Goernitz
* @class BadCompanion:
* @version 11/6/17
*/

//used when student is performing poorly
public class BadCompanion extends CompanionDecorator{
	@Override
	public void speak(JComponent panel){
		super.speak(panel);
		JLabel label = new JLabel();
		label.setHorizontalTextPosition(JLabel.CENTER);
    	label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setText("It's okay if you don't understand the material. We can try again tomorrow! ");
		panel.setLayout(new FlowLayout());
		panel.add(label);
	}
}
