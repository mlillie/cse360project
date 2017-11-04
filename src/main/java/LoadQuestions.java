/**
 * This class will create an object used to store question details:
 * question, choices and answers.
 * Getting and setting each are available
 * @author Jacqueline Fonseca
 * @version 11/3/2017
 */
public class LoadQuestions {
	private String question, answer;
	private String[] choices;
	
	/**
	 * Sets question
	 * @param  assessment question
	 */
	public void setQuestion(String question)
	{
		this.question = 	question;
	}
	/**
	 * Gets the question
	 * @return assessment question
	 */
	public String getQuestion()
	{
		return this.question;
	}
	/**
	 * Sets answer
	 * @param assessment answer
	 */
	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
	/**
	 * Gets answer
	 * @return assessment answer
	 */
	public String getAnswer()
	{
		return this.answer;
	}
	/**
	 * Sets choices
	 * @param assessment choices
	 */
	public void setChoices(String[] choices)
	{
		this.choices = choices;
	}
	/**
	 * Gets choices
	 * @return assessment choices
	 */
	public String[] getChoices()
	{
		return this.choices;
	}
}
