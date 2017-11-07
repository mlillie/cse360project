import javax.swing.*;
import java.awt.*;

/**
* @author Ashley Goernitz
* @class GoodCompanion:
* @version 11/6/17
* used when student is performing well. decorates BasicCompanion
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
