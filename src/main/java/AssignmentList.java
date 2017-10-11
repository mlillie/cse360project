class AssignmentList {
	/**
	 * This class will create an object used to store assignment information--name,
	 * deadline, and points.
	 * Getting and setting each are available
	 * @author Jacqueline Fonseca
	 * @version 10/10/2017
	 */
	
	private String name, date;
	private int points;	
	
	/**
	 * Sets the assignment name
	 * @param assuignment name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return assignment name
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Sets the assignment deadline
	 * @param assignment date
	 */
	public void setDate(String date)
	{
		this.date = date;
	}
	
	/**
	 * @return assignment deadline
	 */
	public String getDate()
	{
		return this.date;
	}
	/**
	 *  Sets the total points of an assignment
	 * @param assignment points
	 */
	public void setPoints(int points)
	{
		this.points = points;
	}
	
	/**
	 * @return assignment points
	 */
	public int getPoints()
	{
		return this.points;
	}
	
	/**
	 * Used to format the way assignment information will
	 * be displayed
	 */
	@Override
	public String toString()
	{
		return "<html> Assignment Name: " + getName() + "&#8195;" + "Assignment Due Date: " + getDate()
	    + "&#8195;" + "Total Points: "+ Integer.toString(getPoints()) + "</html>";
	}
}
