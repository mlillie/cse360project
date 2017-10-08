import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
public class AssignmentQuestions extends JPanel implements ActionListener{
	
    private static final Dimension PREFERRED_RESOLUTION = new Dimension(1280, 720); 
   
	AssignmentQuestions(){
		TitledBorder border = BorderFactory.createTitledBorder("Add Questions");
		JTextArea addQuestions = new JTextArea(30,100);
		addQuestions.setBorder(border);
		JPanel buttonPanel = new JPanel();
		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.SOUTH);
		add(addQuestions, BorderLayout.CENTER);		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
        
	}
}
