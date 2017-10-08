import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

class AssignmentList {
	private String name, date;
	private int points;
    
	List<AssignmentFiles> assignmentFiles = new ArrayList<AssignmentFiles>();
	public boolean setName(String name)
	{
		this.name = name;
		return true;
	}
	public String getName()
	{
		return this.name;
	}
	
	public boolean setDate(String date)
	{
		this.date = date;
		return true;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public boolean setPoints(int points)
	{
		this.points = points;
		return true;
	}
	
	public int getPoints()
	{
		return this.points;
	}
	
	@Override
	public String toString()
	{
		return "Assignment Name: " + getName() + "\t\t" + "Assignment Due Date: " + getDate() 
			+ "\t\t" + "Total Points: "+ Integer.toString(getPoints());
	}

}
