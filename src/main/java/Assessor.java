import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *  The edu.asu.CSE360.recitation01.group05.Assessor class is one of the panels implemented into the edu.asu.CSE360.recitation01.group05.Universe JFrame.
 *  The panel's content will change in relation to the slider's position.
 *  Recitation Project 1
 *  Completion Time: 7 hours
 *
 *  @author Jacqueline Fonseca
 *  @version 1.0 9/9/2017
 */
public class Assessor extends TutoringPanel implements ActionListener
{

	static final long serialVersionUID = 1L;

	/*
	 *  Elements to be added into the edu.asu.CSE360.recitation01.group05.Assessor panel
	 */
	JPanel panel;
	JButton b1, b2, b3;
	JMenuItem item1, item2, item3;
	JCheckBox i1, i2, i3;
	JOptionPane dialog;
	JTextArea question;
	static int state;

	/*
	 * Creates panel
	 */
	public Assessor()
	{	
	}
	
	/*
	 * Establishes what should be shown in the panel
	 * @param stateChange current state of slider
	 */
	@Override
	 public void update(int state)
	 {
		 
		//Current state of slider
		this.state = state;
		
		//Layout of elements
		setLayout(new BorderLayout());
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		
		//Name label is visible
		if (state == 0)
		{
			removeAll();
			
			Color newColor = new Color(165, 200, 180);
			
			JLabel name = new JLabel("<html> <b>Assessor: </b> Jacqueline Fonseca </html>");
			
			panel.setBackground(newColor);
			panel.add(name);
			add(panel);
			}
		
		//Menu holding question 1 is visible
		if (state == 1)
		{
			removeAll();
			
			Color newColor = new Color(180, 200, 220);
			
			question = new JTextArea("Question 1");
			add(question, BorderLayout.NORTH);
			question.setEditable(false);
			
			JMenuBar menubar = new JMenuBar();
			JMenu menu = new JMenu("Who is the Mother of Dragons?");
			
			item1 = new JMenuItem("Barney");
			item2 = new JMenuItem("Daenerys Targaryen");
			item3 = new JMenuItem("Hodor");
			
			menu.add(item1);
			menu.add(item2);
			menu.add(item3);
			
			menubar.add(menu);
			panel.add(menubar);
			panel.setBackground(newColor);
			add(panel, BorderLayout.CENTER);
			
			item1.addActionListener(this);
			item2.addActionListener(this); 
			item3.addActionListener(this);	
		}
		
		//Checkboxes and question 2 are visible
		if (state == 2)
		{
			removeAll();
			
			Color newColor = new Color(175, 160, 190);
			
			question = new JTextArea("Question 2");
			add(question, BorderLayout.NORTH);
			question.setEditable(false);
			
			JLabel prompt = new JLabel("What are your favorite subjects?");
			prompt.setHorizontalAlignment(JLabel.CENTER);
			
			i1 = new JCheckBox("Math");
			i2 = new JCheckBox("English");
			i3 = new JCheckBox("Science");
			
			i1.addActionListener(this);			
			i2.addActionListener(this); 			
			i3.addActionListener(this); 
			
			i1.setSelected(false);
			i2.setSelected(false);
			i3.setSelected(false);
			
			panel.add(prompt, gbc);
			panel.add(i1, gbc);
			panel.add(i2, gbc);
			panel.add(i3, gbc);
			
			panel.setBackground(newColor);
			
			add(panel);
		}
		
		//Buttons and question 3 are visible
		if (state == 3)
		{
			this.removeAll();
			
			question = new JTextArea("Question 3");
			add(question, BorderLayout.NORTH);
			question.setEditable(false);
			
			Color newColor = new Color(205, 85, 90);
			
			JLabel prompt = new JLabel("Choose a university.");
			prompt.setHorizontalAlignment(JLabel.CENTER);
			
			b1 = new JButton("Arizona State University");
			b2 = new JButton("Univesity of Arizona");
			b3 = new JButton("Other");
			
			panel.add(prompt, gbc);
			panel.add(b1, gbc);
			panel.add(b2, gbc);
			panel.add(b3, gbc);			
			
			panel.setBackground(newColor);
			
			b1.addActionListener(this);
			b2.addActionListener(this); 
			b3.addActionListener(this);
			
			add(panel);
		}
		
		//Text area and prompt are visible
		if (state == 4)
		{
			this.removeAll();
			
			Color newColor = new Color(230, 230, 170);
			
			question = new JTextArea("Question 4");
			add(question, BorderLayout.NORTH);
			question.setEditable(false);
			
			TitledBorder tBorder = BorderFactory.createTitledBorder("Write your name and hit enter: ");
			JTextArea jArea = new JTextArea();
			jArea.setPreferredSize(new Dimension (250,40));
			jArea.setBorder(tBorder);
			
			panel.setBackground(newColor);
			
			//Creates a pop-up containing input when 'enter' is pressed
			jArea.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent enter)
				{
					if (enter.getKeyCode() == KeyEvent.VK_ENTER)
					{
						String name = jArea.getText();
						JOptionPane.showMessageDialog(panel, "Your name is: " + name);
						jArea.setText("");	
					}
				}

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub	
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub	
				}
			});			
			//panel.add(prompt);
			panel.add(jArea);
			add(panel);
		}
		
	 }

	@Override
	public void onLogout() {

	}

	/*
	 * Handles buttons, items, and checkboxes being selected.
	 * Creates a pop-up of what was pressed.
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
			
		if (source == item1 || source == item3)
			JOptionPane.showMessageDialog(panel, "Wrong Answer!");
		if (source == item2)
			JOptionPane.showMessageDialog(panel, "Correct!");
		
		if (source == b1)
			JOptionPane.showMessageDialog(panel,"Good Choice!");
		if (source == b2 || source == b3)
			JOptionPane.showMessageDialog(panel,"Wrong Choice!");		
			
		if (source == i1)
			if (i1.isSelected() == true) 
				JOptionPane.showMessageDialog(panel,"You selected " + i1.getText());
		if (source == i2)
			if (i2.isSelected() == true)
				JOptionPane.showMessageDialog(panel,"You selected " + i2.getText());		
		if (source == i3)
			if (i3.isSelected() == true)
				JOptionPane.showMessageDialog(panel,"You selected " + i3.getText());		 
	}
}
