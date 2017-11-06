import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * This class will be the panel that will display all of the assignments. Once an 
 * assignment is clicked, the user will be able to edit its information.
 * 
 * @author Jacqueline Fonseca
 * @version 10/10/2017
 */

public class Assignments extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<AssignmentList> assignmentList = new ArrayList<AssignmentList>(30);
	private ArrayList<AssignmentList> sortedList = new ArrayList<AssignmentList>(30);
	private ArrayList<AssignmentList> originalList = new ArrayList<AssignmentList>(30);
	private JPanel main;
	private DefaultListModel<AssignmentList> assignmentModel; 
	private JLabel dateWarning, pointWarning,  textAreaWarning;
	private JButton saveChanges;
	private JList<AssignmentList> list;
	private JTextField itemName, itemPoints;
	private JFormattedTextField itemDeadline;
	private JScrollPane listPane;
	private Font font;
	private int index, selectedIndex;
	private Color prompt = new Color (0, 0, 132);
	private AssignmentList al;
	
    private static final Dimension PREFERRED_RESOLUTION = new Dimension(1280, 720); //720
    private static final Dimension PANEL_DIMENSION = new Dimension(480,720);
    private static final Dimension TEXTAREA_DIMENSION = new Dimension(400,50);
    private static final Dimension DATE_TEXTAREA_DIMENSION = new Dimension(120,50);
    private static final Dimension PANE_DIMENSION = new Dimension(800,600);


	public Assignments()
	{	
		//Creating the panel
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5,0,5,0);
		
		this.setPreferredSize(PREFERRED_RESOLUTION);
	
		main = new JPanel(new BorderLayout());
          
        JPanel addFields = new JPanel(new GridLayout(1,3));
		JPanel itemsPanel = new JPanel(new GridBagLayout());
		itemsPanel.setPreferredSize(PANEL_DIMENSION);     
        
		//Creates assignment list
        list = new JList(assignmentList.toArray());
        list.setModel(new DefaultListModel());
        assignmentModel = (DefaultListModel<AssignmentList>)list.getModel();
        assignmentList = Collections.list(assignmentModel.elements());
              
        //Creates pane for list
        list.setVisible(true);
        listPane = new JScrollPane(list);
        listPane.setBorder(BorderFactory.createTitledBorder ("Current Assignment Descriptions"));
        listPane.setPreferredSize(PANE_DIMENSION);
        
        //Font to be used in JList
        font = new Font("Times New Roman", Font.BOLD,15);
        list.setFont(font);
        
        //Creates textfields and their borders
        TitledBorder ntb = BorderFactory.createTitledBorder("Assignment Name:");
        itemName = new JTextField();
        itemName.setBorder(ntb);
        itemName.setPreferredSize(TEXTAREA_DIMENSION);
        itemName.setVisible(false);
        
        TitledBorder deadlinetb = BorderFactory.createTitledBorder("Assignment Deadline:");
        SimpleDateFormat deadlineFormat = new SimpleDateFormat("MM/dd/yyyy");
        itemDeadline = new JFormattedTextField(deadlineFormat);
        itemDeadline.setColumns(10);
        itemDeadline.setToolTipText("MM/DD/yyyy format");
        itemDeadline.setValue(new Date());
        itemDeadline.setBorder(deadlinetb);
        itemDeadline.setMinimumSize(DATE_TEXTAREA_DIMENSION);
        itemDeadline.setVisible(false);
        
        TitledBorder ptb = BorderFactory.createTitledBorder("Total Points:");
        itemPoints = new JTextField();
        itemPoints.setBorder(ptb);
        itemPoints.setPreferredSize(TEXTAREA_DIMENSION);
        itemPoints.setVisible(false);
        
        //Creates button
        saveChanges = new JButton("Save Changes");
        saveChanges.addActionListener(this);
        saveChanges.setVisible(false);
        
        //Creates labels
        textAreaWarning = new JLabel("Add name.");
        textAreaWarning.setForeground(prompt);
        textAreaWarning.setVisible(false);
        dateWarning = new JLabel("Add a date:");
        dateWarning.setForeground(prompt);
        dateWarning.setVisible(false);
        pointWarning = new JLabel("Add a number for possible points:");
        pointWarning.setForeground(prompt);
        pointWarning.setVisible(false);
        	
        //Adds textfields, labels, and buttons to corresponding panels
		addFields.add(itemDeadline);
		itemsPanel.add(textAreaWarning, gbc);
		itemsPanel.add(itemName, gbc);
		itemsPanel.add(dateWarning, gbc);
		itemsPanel.add(addFields, gbc);
		itemsPanel.add(pointWarning, gbc);
		itemsPanel.add(itemPoints, gbc);
		itemsPanel.add(saveChanges);
		
		main.add(listPane, BorderLayout.CENTER);
		main.add(itemsPanel, BorderLayout.EAST);
		
		
		selectedIndex = -1; //will be used to determine which assignment is selected
		//Will allow for selected assignments to be edited
        list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				//sets fields and prompts visible to the use
				itemName.setVisible(true);
				itemDeadline.setVisible(true);
				dateWarning.setVisible(true);
				dateWarning.setForeground(prompt);
				itemPoints.setVisible(true);
				pointWarning.setVisible(true);
				pointWarning.setForeground(prompt);
				textAreaWarning.setVisible(false);
				
				itemName.setEditable(true);
				dateWarning.setText("Change assignment date:");
				pointWarning.setText("Change assignment points:");
				
				//fills in fields with current assignment information
				index = list.getSelectedIndex();
				itemName.setText((assignmentModel.get(index)).getName());
				itemDeadline.setText((assignmentModel.get(index)).getDate());
				int selectedPoints = (assignmentModel.get(index)).getPoints();
				itemPoints.setText(Integer.toString(selectedPoints));
				setSelectedIndex(index);				
				saveChanges.setVisible(true);
				
			}
		});

        //adds all panels to frame
		add(main);
		setVisible(true);	
	}
	/**
	 * Will handle the editing of assignments
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
			
		if (source == saveChanges)
		{
			//Shows error message if a field is empty
			if (itemName.getText().isEmpty() == true || itemDeadline.getText().isEmpty() == true || itemPoints.getText().isEmpty() == true)
			{
				 JOptionPane.showConfirmDialog(this, 
				            "Fill in every field.", "Error!", 
				            JOptionPane.DEFAULT_OPTION,
				            JOptionPane.WARNING_MESSAGE);
				 return;
			}
			//shows an error if "points" is not a number
			if (isInt(itemPoints.getText()) == false)
			{
				JOptionPane.showConfirmDialog(this, 
			            "Input a number for total points.", "Error!", 
			            JOptionPane.DEFAULT_OPTION,
			            JOptionPane.WARNING_MESSAGE);
				return;
			}
			pointWarning.setVisible(false);
			textAreaWarning.setVisible(false);
			(assignmentModel.get(index)).setName(itemName.getText());
			(assignmentModel.get(index)).setDate(itemDeadline.getText());
			(assignmentModel.get(index)).setPoints(Integer.parseInt(itemPoints.getText()));
			JOptionPane.showMessageDialog(this, "Assignment edit saved!");
		}	
		
	}
	
	/**
	 * Will go through the list of assignments and will return a copy
	 * @return List<AssignmentList>
	 */
	public List<AssignmentList> returnAssignments()
	{
		List<AssignmentList> getList = new ArrayList<AssignmentList>();
		for (int i = 0; i < (this.assignmentModel).getSize(); i++)
		{
			getList.add((this.originalList).get(i));
		}
		return getList;
	}
	/**
	 * Will set the index of the assignment selected
	 * @param index Current index
	 */
	public void setSelectedIndex(int index)
	{
		selectedIndex = index;
	}
	/**
	 * Will return the index of the assignment selected
	 * @return selectedIndex 
	 */
	public int getSelectedIndex()
	{
		return selectedIndex;
	}
	
	/**
	 * Will add an assignment into the list
	 * @param al Assignment to be added
	 */
	public void loadAssignments(AssignmentList al)
	{
		assignmentModel.addElement(al);
		originalList.add(al);
	}

	/**
	 * Will determine whether the parameter is a integer
	 * @param s String to be checked
	 * @return True if it is an integer, false otherwise
	 */
	public static boolean isInt(String s)
	{
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException nf) {
			return false;
		}
	}
	/**
	 * Will sort assignments or revert them to their original positions 
	 * @param flag boolean status of sorting
	 * @return True if assignment is sorted, false otherwise
	 */
	public boolean sortAssignments(boolean flag)
	{
		if (flag == false)
		{
			sortedList.clear();
			for (int i = 0; i < assignmentModel.size(); i++)
			{
				sortedList.add(assignmentModel.get(i));
			}
			Collections.sort(sortedList, new Comparator<AssignmentList>() {
				public int compare(AssignmentList o1, AssignmentList o2) {
					return o1.getName().compareTo(o2.getName());
				}
		    });		
			
				assignmentModel.removeAllElements();
			for (int i = 0; i < sortedList.size(); i++)
			{
				System.out.println(sortedList.get(i));
				assignmentModel.addElement(sortedList.get(i));
			}
			return true;
		}
		else
		{
			System.out.println("/n");
			for (int i = 0; i < assignmentModel.size(); i++)
			{
				System.out.println(originalList.get(i));
			}
			assignmentModel.removeAllElements();
			for (int i = 0; i < originalList.size(); i++) {
				assignmentModel.addElement(originalList.get(i));
			}
			return false;
		}
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(Assignments::new);
	}
}

