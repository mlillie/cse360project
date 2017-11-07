import java.awt.FlowLayout;
import javax.swing.*;

/**
* used when student could be performing better. decorates BasicCompanion
* Rec Project 4
*
* @author Ashley Goernitz
* @interface Companion:
* @version 11/6/17
*/

//used when student isn't doing so well
public class WorriedCompanion extends CompanionDecorator {
	@Override
    public void speak(JComponent panel){
		super.speak(panel);
		JLabel label = new JLabel();
		label.setHorizontalTextPosition(JLabel.CENTER);
    	label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setText("You can do this!");
		panel.setLayout(new FlowLayout());
		panel.add(label);
    		
    }
}
