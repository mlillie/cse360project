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
	public boolean answer();
	public void update();
}

