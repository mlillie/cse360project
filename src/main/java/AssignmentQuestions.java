import java.awt.BorderLayout;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * This class will be the panel that contains a list of all of the questions
 * from the Question Bank.
 * @author Jacqueline Fonseca
 * @version 10/10/2017
 */

public class AssignmentQuestions extends JPanel {

	private final static Path QUESTION_BANK = Paths.get(AssignmentQuestions.class.getResource("Questions.txt").getPath().
            replaceFirst("^/(.:/)", "$1"));
	private JTextArea textArea;
	private static String[] assignmentQuestions, questionDetails;
	private JScrollPane scrollPane;
	
	AssignmentQuestions(){
		//Creates the panel
		TitledBorder border = BorderFactory.createTitledBorder("Current Available Questions");
		textArea = new JTextArea();
		textArea.setBorder(border);
		textArea.setEditable(false);
		setLayout(new BorderLayout());
		scrollPane = new JScrollPane(textArea);
		add(scrollPane, BorderLayout.CENTER);		
		
		//checks whether the path exists
		if (Files.exists(QUESTION_BANK));
		{
			String readLine;
			try (InputStream stream = Files.newInputStream(QUESTION_BANK);
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream)))
			{
				//reads the line of questions
				while((readLine = bufferedReader.readLine()) != null){
					//Splits each question into an array
					AssignmentQuestions.assignmentQuestions = readLine.split("/");
					//adds assignment to textArea
					for (int i = 0; i < assignmentQuestions.length; i++) {
						readLine = assignmentQuestions[i];
						questionDetails = readLine.split("%");
						textArea.append("Question: " + questionDetails[0] + "\n\tChoices: " + 
						questionDetails[1] + "\n\t\tAnswer: " + questionDetails[2] + "\n\n");
					}
				}
			}
			catch(IOException ex)
			{
				textArea.setText("");
				ex.printStackTrace();
			}
		}
	}
	
}
