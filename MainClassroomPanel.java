import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainClassroomPanel extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private static final Dimension PREFERRED_RESOLUTION = new Dimension(1280, 720);
	private JButton addItem, editQuestions, editAssignments; 
	private JPanel main;
	Assignments assignment;
	AssignmentQuestions questions;
	
	CardLayout cardLayout;
	MainClassroomPanel()
	{
		this.setPreferredSize(PREFERRED_RESOLUTION);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Instructor's Classroom");
		
		assignment = new Assignments();
		JPanel questions = new AssignmentQuestions();
		addItem = new JButton("Add an assignment");
        addItem.addActionListener(this);
        editQuestions = new JButton("Edit assignment questions");
        editQuestions.addActionListener(this);
        editAssignments = new JButton("Save & Exit");
        editAssignments.addActionListener(this);
        editAssignments.setVisible(false);
        JPanel addButtons = new JPanel();
        addButtons.add(addItem);
        addButtons.add(editQuestions);
        addButtons.add(editAssignments);
		
        main = new JPanel(new CardLayout());
		main.add(assignment, "1");
		main.add(questions, "2");
		add(main, BorderLayout.CENTER);
		add(addButtons, BorderLayout.NORTH);
		pack();
		setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		if (source == addItem)
			assignment.addItems();
		if (source == editQuestions)
		{
			if (assignment.getSelectedIndex() < 0)
			{
				JOptionPane.showMessageDialog(this, "Select an assignment.");
				return;
			}
			cardLayout = (CardLayout)(main.getLayout());
			cardLayout.show(main, "2");
			editQuestions.setVisible(false);
			addItem.setVisible(false);
			editAssignments.setVisible(true);
		}
		
		if (source == editAssignments)
		{	
			cardLayout = (CardLayout)(main.getLayout());
			cardLayout.show(main, "1");
			editQuestions.setVisible(true);
			editAssignments.setVisible(false);
		}

	}
	
	
}
