import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.*;
/**
 * This class will read the questions from the Question Bank and
 * place them into an ArrayList.
 * @author Jacqueline Fonseca
 * @version 11/03/2017
 */
public class LoadAssignments{
	
	private final static Path QUESTION_BANK = Paths.get(AssignmentQuestions.class.getResource("Questions.txt").getPath().
            replaceFirst("^/(.:/)", "$1"));
	private static String[] assignmentQuestions, questionDetails;
	static ArrayList<LoadQuestions> questionList = new ArrayList<LoadQuestions>(10);
	
	LoadAssignments()
	{
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
					assignmentQuestions = readLine.split("/");
					//splits each question's prompt, choices, and answer into an arraylist of LoadQuestions
					for (int i = 0; i < assignmentQuestions.length; i++) {
						readLine = assignmentQuestions[i];
						questionDetails = readLine.split("%");
						LoadQuestions loadQuestion = new LoadQuestions();
						loadQuestion.setQuestion(questionDetails[0]);
						loadQuestion.setAnswer(questionDetails[2]);
						readLine = questionDetails[1];
						questionDetails = readLine.split(",");
						loadQuestion.setChoices(questionDetails);
						
						questionList.add(loadQuestion);
					}
					
				}
				
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}
			
	}
	/**
	 * Will return the ArrayList of questions
	 * @return questionList The ArrayList of questions
	 */
	public ArrayList<LoadQuestions> getQuestionList()
	{
		return LoadAssignments.questionList;
	}
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(Assignments::new);
	}
}
