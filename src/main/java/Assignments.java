import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class Assignments extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private List<AssignmentList> assignmentList = new ArrayList<AssignmentList>(30);
	private JPanel main;
	private DefaultListModel<AssignmentList> assignmentModel; 
	private JLabel nameWarning, dateWarning, pointWarning,  textAreaWarning;
	private JButton enter, saveChanges;
	private JList<AssignmentList> list;
	private JTextField itemName, itemPoints;
	private JFormattedTextField itemDeadline;
	private JScrollPane listPane;
	private Font font;
	private int index, selectedIndex;
	private Color warning = new Color(145, 16, 22);
	private Color prompt = new Color (0, 0, 132);
	
    private static final Dimension PREFERRED_RESOLUTION = new Dimension(1280, 720); //720
    private static final Dimension PANEL_DIMENSION = new Dimension(480,720);
    private static final Dimension TEXTAREA_DIMENSION = new Dimension(400,50);
    private static final Dimension DATE_TEXTAREA_DIMENSION = new Dimension(120,50);
    private static final Dimension PANE_DIMENSION = new Dimension(800,600);


	public Assignments()
	{	
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5,0,5,0);
		
		this.setPreferredSize(PREFERRED_RESOLUTION);
	
		main = new JPanel(new BorderLayout());
          
        JPanel addFields = new JPanel(new GridLayout(1,3));
		JPanel bottom = new JPanel(new GridBagLayout());
		bottom.setPreferredSize(PANEL_DIMENSION);
        
        JList <AssignmentList>list = new JList(assignmentList.toArray());
        list.setModel(new DefaultListModel());
        assignmentModel = (DefaultListModel<AssignmentList>)list.getModel();
        assignmentList = Collections.list(assignmentModel.elements());
        
        selectedIndex = -1;
        list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				itemName.setVisible(true);
				nameWarning.setVisible(true);
				itemDeadline.setVisible(true);
				dateWarning.setVisible(true);
				itemPoints.setVisible(true);
				pointWarning.setVisible(true);
				
				nameWarning.setText("Change assignment name:");
				nameWarning.setForeground(warning);
				dateWarning.setText("Change assignment date:");
				dateWarning.setForeground(warning);
				pointWarning.setText("Change assignment points:");
				pointWarning.setForeground(warning);
				
				index = list.getSelectedIndex();
				itemName.setText((assignmentModel.get(index)).getName());
				itemDeadline.setText((assignmentModel.get(index)).getDate());
				int selectedPoints = (assignmentModel.get(index)).getPoints();
				itemPoints.setText(Integer.toString(selectedPoints));
				setSelectedIndex(index);				
				saveChanges.setVisible(true);
				
			}
		});
        
        list.setVisible(true);
        listPane = new JScrollPane(list);
        listPane.setBorder(BorderFactory.createTitledBorder ("Current Assignment Descriptions"));
        listPane.setPreferredSize(PANE_DIMENSION);
        
        TitledBorder ntb = BorderFactory.createTitledBorder("Type the name of the new assignment");
        itemName = new JTextField();
        itemName.setBorder(ntb);
        itemName.setPreferredSize(TEXTAREA_DIMENSION);
        itemName.setVisible(false);
        
        TitledBorder deadlinetb = BorderFactory.createTitledBorder("Deadline Date:");
        SimpleDateFormat deadlineFormat = new SimpleDateFormat("MM/dd/yyyy");
        itemDeadline = new JFormattedTextField(deadlineFormat);
        itemDeadline.setColumns(10);
        itemDeadline.setToolTipText("MM/DD/yyyy format");
        itemDeadline.setValue(new Date());
        itemDeadline.setBorder(deadlinetb);
        itemDeadline.setMinimumSize(DATE_TEXTAREA_DIMENSION);
        itemDeadline.setVisible(false);
        
        TitledBorder ptb = BorderFactory.createTitledBorder("Type the total points of the new assignment");
        itemPoints = new JTextField();
        itemPoints.setBorder(ptb);
        itemPoints.setPreferredSize(TEXTAREA_DIMENSION);
        itemPoints.setVisible(false);
        
        font = new Font("Times New Roman", Font.BOLD,15);
        list.setFont(font);
        
        
        enter = new JButton("Create New Assignment");
        enter.addActionListener(this);
        enter.setVisible(false);
        saveChanges = new JButton("Save Changes");
        saveChanges.addActionListener(this);
        saveChanges.setVisible(false);
        
        
        textAreaWarning = new JLabel("Fill in every field.");
        textAreaWarning.setForeground(warning);
        textAreaWarning.setVisible(false);
        nameWarning = new JLabel("Add a name:");
        nameWarning.setForeground(prompt);
        nameWarning.setVisible(false);
        dateWarning = new JLabel("Add a date:");
        dateWarning.setForeground(prompt);
        dateWarning.setVisible(false);
        pointWarning = new JLabel("Add a number for possible points:");
        pointWarning.setForeground(prompt);
        pointWarning.setVisible(false);
        			
		addFields.add(itemDeadline);
		bottom.add(textAreaWarning, gbc);
		bottom.add(nameWarning, gbc);
		bottom.add(itemName, gbc);
		bottom.add(dateWarning, gbc);
		bottom.add(addFields, gbc);
		bottom.add(pointWarning, gbc);
		bottom.add(itemPoints, gbc);
		bottom.add(enter);
		bottom.add(saveChanges);
		
		main.setName("Assignments");
		main.add(listPane, BorderLayout.CENTER);
		main.add(bottom, BorderLayout.EAST);	

		add(main);
		setVisible(true);
		
		
	}
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source == enter)
		{
			textAreaWarning.setVisible(false);
			
			if (itemName.getText().isEmpty() == true || itemDeadline.getText().isEmpty() == true || itemPoints.getText().isEmpty() == true)
			{
				textAreaWarning.setVisible(true);
				return;
			}
			
			else if (isInt(itemPoints.getText()) == false )
			{
				pointWarning.setForeground(warning);
				return;
			}
			
			else {
				AssignmentList al = new AssignmentList();
				al.setName(itemName.getText());
				al.setDate(itemDeadline.getText());
				al.setPoints(Integer.parseInt(itemPoints.getText()));	
				assignmentModel.addElement(al);
				
				itemName.setText("");
				itemDeadline.setText("");
				itemPoints.setText("");
				pointWarning.setForeground(prompt);
				itemName.setVisible(false);
				nameWarning.setVisible(false);
				itemDeadline.setVisible(false);
				dateWarning.setVisible(false);
				itemPoints.setVisible(false);
				enter.setVisible(false);
				pointWarning.setVisible(false);
				
				JOptionPane.showMessageDialog(listPane, "Assignment added!");
			}			
		}
			
		if (source == saveChanges)
		{
			(assignmentModel.get(index)).setName(itemName.getText());
			(assignmentModel.get(index)).setDate(itemDeadline.getText());
			(assignmentModel.get(index)).setPoints(Integer.parseInt(itemPoints.getText()));
		}
		
		
	}
	
	public void setSelectedIndex(int index)
	{
		selectedIndex = index;
	}
	public int getSelectedIndex()
	{
		return selectedIndex;
	}
		
	public void addItems() {
		itemDeadline.setText("");
		itemPoints.setText("");
		itemName.setText("");
		itemPoints.setText(""); 
		itemName.setEditable(true);
		itemDeadline.setEditable(true);
		itemPoints.setEditable(true);
		
		saveChanges.setVisible(false);
		itemName.setVisible(true);
		nameWarning.setVisible(true);
		itemDeadline.setVisible(true);
		dateWarning.setVisible(true);
		itemPoints.setVisible(true);
		enter.setVisible(true);
		pointWarning.setVisible(true);
	}
	
	public static boolean isInt(String s)
	{
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException nf) {
			return false;
		}
	}


	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(Assignments::new);
	}
}