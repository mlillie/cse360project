import javax.swing.*;
import java.awt.*;

/**
* used when student is performing well. decorates BasicCompanion
* Rec Project 4
*
* @author Ashley Goernitz
* @class GoodCompanion:
* @version 11/6/17
*/

public class GoodCompanion extends CompanionDecorator {
	@Override
	public void speak(JComponent panel){
		super.speak(panel);
		JLabel label = new JLabel();
		label.setHorizontalTextPosition(JLabel.CENTER);
    	label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setText("Good job! You know your stuff!");
		panel.setLayout(new FlowLayout());
		panel.add(label);
	}

}
