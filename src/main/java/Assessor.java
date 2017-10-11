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
 *  @version 1.1 10/11/2017
 */
public class Assessor extends TutoringPanel implements ActionListener
{

	static final long serialVersionUID = 1L;

	/*
	 *  Elements to be added into the edu.asu.CSE360.recitation01.group05.Assessor panel
	 */
	JPanel panel;
	JButton b1, b2, b3, submit;
	JMenuItem item1, item2, item3;
	JCheckBox i1, i2, i3;
	JOptionPane dialog;
	JTextArea question;
	static int state;
	public static int q1correct = 0, q1wrong = 0;
	public static int q2correct = 0, q2wrong = 0;
	public static int q3correct = 0, q3wrong = 0;
	public static int q4correct = 0, q4wrong = 0;
	private boolean question1complete = false, question2complete = false, question3complete = false, question4complete = false;
	private JTextArea jArea;

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
		Assessor.state = state;
		
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
		//Text area and prompt are visible
		if (state == 1)
		{
			this.removeAll();
			if(question1complete == true) {
				question = new JTextArea("Question 1");
				add(question, BorderLayout.NORTH);
				Color newColor = new Color(165, 200, 180);
				
				JLabel name = new JLabel("<html> <b>Correct Attempts: </b>"+q1correct+"<b>Incorrect Attempts: </b>"+q1wrong+"</html>");
				
				panel.setBackground(newColor);
				panel.add(name);
				add(panel);
			}
			else{
				Color newColor = new Color(230, 230, 170);
			
				question = new JTextArea("Question 1");
				add(question, BorderLayout.NORTH);
				question.setEditable(false);
			
				TitledBorder tBorder = BorderFactory.createTitledBorder("What is the file extension for a java file?\n");
				jArea = new JTextArea();
				jArea.setPreferredSize(new Dimension (400,60));
				jArea.setBorder(tBorder);
			
				panel.setBackground(newColor);
			
			//Creates a pop-up containing input when 'enter' is pressed
				jArea.addKeyListener(new KeyListener() {
				
			public void keyPressed(KeyEvent enter)
				{
					
						
					if (enter.getKeyCode() == KeyEvent.VK_ENTER)
					{
						String entry = jArea.getText();
						System.out.print(jArea.getText());
						if (jArea.getText().equals(".java")) {
							JOptionPane.showMessageDialog(panel, "file" + entry+" is correct.");
							q1correct++;
							
							question1complete = true;
						}
						else if (jArea.getText().compareTo("java") == 0) {
							jArea.setText("");
							JOptionPane.showMessageDialog(panel, "You forgot the '.' ");
						}
						else {
							jArea.setText("");							
							JOptionPane.showMessageDialog(panel, "Wrong.");
							q1wrong++;							
						}
							
					jArea.setText("");
					enter.consume(); //takes care of new line char caused by enter
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
		
		
		
		//Checkboxes and question 2 are visible
		if (state == 2)
		{
			this.removeAll();
			if(question2complete == true) {
				question = new JTextArea("Question 2");
				add(question, BorderLayout.NORTH);
				Color newColor = new Color(165, 200, 180);
				
				JLabel name = new JLabel("<html> <b>Correct Attempts: </b>"+q2correct+"<b>Incorrect Attempts: </b>"+q2wrong+"</html>");
				
				panel.setBackground(newColor);
				panel.add(name);
				add(panel);
			}
			else{
			Color newColor = new Color(175, 160, 190);
			
			question = new JTextArea("Question 2");
			add(question, BorderLayout.NORTH);
			question.setEditable(false);
			
			JLabel prompt = new JLabel("Which are valid java statements?");
			prompt.setHorizontalAlignment(JLabel.CENTER);
			
			i1 = new JCheckBox("cout<< 'hello world' ");
			i2 = new JCheckBox("public static class {}");
			i3 = new JCheckBox("switch(i) {case0: return 1;default: break;}");
			
			submit = new JButton("Submit");
			
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
			panel.add(submit, gbc);
			
			panel.setBackground(newColor);
			
			add(panel);
			
			submit.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e)
				{		
					System.out.print(jArea.getText());
							
					if (i3.isSelected() && !i1.isSelected() && !i2.isSelected()) {
						JOptionPane.showMessageDialog(panel, "Selection 3 is correct.");
						q3correct++;
						question3complete = true;
					}
					else {					
						JOptionPane.showMessageDialog(panel, "Wrong.");
						q3wrong++;							
					}
								
					jArea.setText("");
				}				
			});	
		}
	}
		
		//Buttons and question 3 are visible
		if (state == 3)
		{
			this.removeAll();
			
			question = new JTextArea("Question 3");
			add(question, BorderLayout.NORTH);
			question.setEditable(false);
			
			Color newColor = new Color(205, 85, 90);
			
			JLabel prompt = new JLabel("What is the access time for a linked list?");
			prompt.setHorizontalAlignment(JLabel.CENTER);
			
			b1 = new JButton("O(1)");
			b2 = new JButton("O(n)");
			b3 = new JButton("O(n^2)");
			
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
		
		if (state == 4)
		{
			removeAll();
			if (question4complete == true) {
				removeAll();
				
				Color newColor = new Color(165, 200, 180);
				
				JLabel name = new JLabel("<html> <b>Question 4: </b> Complete </html>");
				
				panel.setBackground(newColor);
				panel.add(name);
				add(panel);
			}
			else {
			
			Color newColor = new Color(180, 200, 220);
			
			question = new JTextArea("Question 4");
			add(question, BorderLayout.NORTH);
			question.setEditable(false);
			
			JMenuBar menubar = new JMenuBar();
			JMenu menu = new JMenu("_______");
			
			item1 = new JMenuItem(".length");
			item2 = new JMenuItem(".size()");
			item3 = new JMenuItem(".length()");
			
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
		}
	 }

	@Override
	public void onLogout() {

	}

	@Override
	public void onSave() {

	}

	/*
	 * Handles buttons, items, and checkboxes being selected.
	 * Creates a pop-up of what was pressed.
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
			
		if (source == item1 || source == item3) {
			JOptionPane.showMessageDialog(panel, "Wrong Answer!");
			q2wrong++;}
		if (source == item2) {
			JOptionPane.showMessageDialog(panel, "Correct!");
			question2complete = true;
			q2correct++;}
		if (source == b1) {
			JOptionPane.showMessageDialog(panel,"Good Choice!");
			question3complete = true;
			q3correct++;}
		if (source == b2 || source == b3) {
			JOptionPane.showMessageDialog(panel,"Wrong Choice!");		
			q3wrong++;}
	
		
		
		
	}
}
