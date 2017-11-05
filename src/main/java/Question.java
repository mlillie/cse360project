import java.awt.Component;

/**
 * 
 * @author Seth Turnage
 * @serial 1L
 * @interface Question:
 * -implements Question interface and aggregates Question instances
 * 
 * 
 * 					            |* Component *|<---------------|
 * 						            /    \                     |
 *	 	        | Concrete Component |  |  Decorator  |<>------| 
 *                                             |
 *                                    | Concrete Decorator |
 * 
 */
public interface Question {
	public boolean isComplete();
	public int getAttempts();
	public boolean checkanswer();
	void addtoQuestion(Component c);
	void setAttempt(int answerKey);
	void setAnswer(int answerKey);
	void setQuestion(String question);
	public void pause();
	public void unpause();
}

