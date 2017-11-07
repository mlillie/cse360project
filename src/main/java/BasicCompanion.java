import java.awt.*;
import javax.swing.*;

/**
* @author Ashley Goernitz
* @interface Companion:
* @version 11/6/17
* the basic companion text which is to be decorated 
*/

public class BasicCompanion implements Companion {
	   @Override
	    public void speak(JComponent panel){
	    	JLabel label = new JLabel();
	    	label.setHorizontalTextPosition(JLabel.CENTER);
	    	label.setVerticalTextPosition(JLabel.BOTTOM);
	    	label.setText("Padawan, ");
	    	panel.setLayout(new BorderLayout());
			panel.add(label, BorderLayout.PAGE_END);
	    }
}
