import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.*;

/**
 * This class is the window for the instructor's classroom.
 * It will allow the user to:
 * 	- view current assignments
 * 	- edit assignments
 * 	- view questions from the Question Bank
 * 
 * @author Jacqueline Fonseca
 * @version 10/10/17
 */
public class MainClassroomPanel extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private static final Path FILES_PATH = Paths.get(AssignmentQuestions.class.getResource("assignment_files").getPath().
			replaceFirst("^/(.:/)", "$1"));
	private static final Dimension PREFERRED_RESOLUTION = new Dimension(1280, 720);
	private JButton viewQuestions, exitQuestions, exit, sortList; 
	private boolean sorted = false;
	private JPanel main;
	private List<String> lines;
	private Assignments assignment;
	private AssignmentQuestions questions;
	private CardLayout cardLayout;
	
	
	MainClassroomPanel()
	{
		this.setPreferredSize(PREFERRED_RESOLUTION);
		setDefaultCloseOperation(MainClassroomPanel.DISPOSE_ON_CLOSE);
		setTitle("Instructor's Classroom");
				
		 //Creates panels for viewing assignments and questions 
		assignment = new Assignments();
		questions = new AssignmentQuestions();
		
		//Creates all buttons used for alternating between panels and exiting the program
		JPanel questionsPanel = new AssignmentQuestions();
        viewQuestions = new JButton("View all questions");
        viewQuestions.addActionListener(this);
        exitQuestions = new JButton("Exit Questions");
        exitQuestions.addActionListener(this);
        exitQuestions.setVisible(false);
        exit = new JButton("Exit Classroom");
        exit.addActionListener(this);
        sortList = new JButton("Sort Assignments"); //
        sortList.addActionListener(this); //
        sortList.setVisible(true); //

        
        //Buttons added components to panel and placed on top of the window
        JPanel addButtons = new JPanel();
        addButtons.add(sortList);
        addButtons.add(viewQuestions);
        addButtons.add(exitQuestions);
        addButtons.add(exit);
        add(addButtons, BorderLayout.NORTH);        
        
        //Card layout used to alternate the panel being shown 
        main = new JPanel(new CardLayout());
		main.add(assignment, "1");
		main.add(questionsPanel, "2");
		add(main, BorderLayout.CENTER);
        
		//Reads all of the files containing assignments
        try (Stream<Path> assignmentPath = Files.walk(FILES_PATH)) {
          assignmentPath.filter(Files::isRegularFile).forEach(path -> {
        	  
    				  try {
    					//stores each line in a file into an ArrayList
					this.lines = Files.readAllLines(path);
					
					//Adds name, date, and points from the ArrayList into a new object 
		        		AssignmentList al = new AssignmentList();
		        		al.setName((lines.get(0)).replace("Name: ", ""));
		        		al.setDate((lines.get(1)).replace("Deadline: ", ""));
		        		al.setPoints(Integer.parseInt((lines.get(2)).replace("Points: ", "")));
		        		
		        		//sends to assignment panel to be displayed
		        		assignment.loadAssignments(al);						
					} catch (IOException e) {
						e.printStackTrace();
					}
    			});
    		} catch (IOException e) {
          e.printStackTrace();
        }
        
		pack();
		setVisible(true);
	
	
	this.addWindowListener(new java.awt.event.WindowAdapter(){
	@Override
	public void windowClosing(java.awt.event.WindowEvent ev)
	{
		//Prompts confirmation on exit
		if (JOptionPane.showConfirmDialog(null, 
	            "Are you sure to exit?", "", 
	            JOptionPane.YES_NO_OPTION,
	            JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)
		{
			//Gets the current list of assignments
			List<AssignmentList> list = (assignment).returnAssignments();
			int index = 0;
			//Goes through all of the files containing assignments
			try (Stream<Path> assignmentPath = Files.walk(FILES_PATH)) {
			    assignmentPath.filter(Files::isRegularFile).forEach(path -> {
			    		try {
			    			//stores assignment into a string
			    			String assignmentInfo = "Name: " + (list.get(index)).getName() + "\nDeadline: " + (list.get(index)).getDate() +
			    					"\nPoints: " + (list.get(index)).getPoints();
			    			FileWriter writer = new FileWriter(path.toString());
			    			
			    			//writes the assignment into the file
			    			writer.write("");
			    			writer.append(assignmentInfo);
			    			writer.flush();
			    			writer.close();
			    			list.remove(index);						
						} catch (IOException ex) {
								ex.printStackTrace();
						}
			    			});
			    		} catch (IOException ex) {
			          ex.printStackTrace();
			    		}
			dispose();
		}
		else 
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			
	        }
	});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();

		//Changes panels to view questions
		if (source == viewQuestions)
		{
			viewQuestions.setVisible(false);
			exitQuestions.setVisible(true);
			sortList.setVisible(false);
			cardLayout = (CardLayout)(main.getLayout());
			cardLayout.show(main, "2");
		}
		
		//Changes panels to view current assignments
		if (source == exitQuestions)
		{	
			cardLayout = (CardLayout)(main.getLayout());
			cardLayout.show(main, "1");
			sortList.setVisible(true);
			viewQuestions.setVisible(true);
			exitQuestions.setVisible(false);
			return;
			
		}
		
		//Sorts the assignment list or reverts them to their original position
		if (source == sortList)
		{
			sorted = assignment.sortAssignments(sorted);
		}
		
		 //Exits the classroom and saves all of the current assignments
		if (source == exit)
		{
			//Prompts confirmation on exit
			if (JOptionPane.showConfirmDialog(null, 
		            "Are you sure to exit?", "", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)
			{
			//Gets the current list of assignments
			List<AssignmentList> list = (this.assignment).returnAssignments();
			int index = 0;
			//Goes through all of the files containing assignments
			try (Stream<Path> assignmentPath = Files.walk(FILES_PATH)) {
			    assignmentPath.filter(Files::isRegularFile).forEach(path -> {
			    		try {
			    			//stores assignment into a string
			    			String assignmentInfo = "Name: " + (list.get(index)).getName() + "\nDeadline: " + (list.get(index)).getDate() +
			    					"\nPoints: " + (list.get(index)).getPoints();
			    			FileWriter writer = new FileWriter(path.toString());
			    			
			    			//writes the assignment into the file
			    			writer.write("");
			    			writer.append(assignmentInfo);
			    			writer.flush();
			    			writer.close();
			    			list.remove(index);						
						} catch (IOException ex) {
								ex.printStackTrace();
						}
			    			});
			    		} catch (IOException ex) {
			          ex.printStackTrace();
			    		}
			this.dispose();
			}
		else
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}
	}
}
