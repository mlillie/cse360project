import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
* used when student incorrect answers = correct answers. decorates BasicCompanion
* Rec Project 4
*
* @author Ashley Goernitz
* @class ThinkingCompanion:
* @version 11/6/17
*/

public class ThinkingCompanion extends CompanionDecorator {
	@Override
    public void speak(JComponent panel){
		super.speak(panel);
		JLabel label = new JLabel();
		//label.setHorizontalTextPosition(JLabel.CENTER);
    	label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setText("Think carefully about your answer!");
		panel.setLayout(new FlowLayout());
		panel.add(label);
    		
    }

}
