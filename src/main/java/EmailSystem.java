import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This class represents the ITS's help message system, a gmail-server based JavaMail interface.
 * - Displays text field where the message can be typed.
 * - Sends message to the instructor selected. 
 *
 * @author Seth Turnage
 * @version 10/10/17
 */
public class EmailSystem extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JPanel HeaderPanel, BodyPanel, FooterPanel;
	private JLabel userEmailLabel;
	private JButton closeButton, sendButton;
	private JComboBox<Object> instructorEmail;
	private JScrollPane bodyScrollPane;
	private JTextArea bodyTextArea;
	private String currentInstructor, currentInstructorEmail, currentStudent, currentStudentEmail, currentStudentPassword, host;
	private Session session;
	
	/*
	 * - Obviously, this data would be external to the application. This is just a sample table of users.
	 * - In the future, we will integrate this table with a DataManager class, which uses encryption.
	 */
	
	private String[][] userTable = {
		    //first and last name
			{"Seth Turnage", "Jacky Fonseca" , "Matt Lillie", "Ashley Goernitz"
		    	},
		    //test sender address
		    {"testsender.cse360@gmail.com", "testsender.cse360@gmail.com","testsender.cse360@gmail.com","testsender.cse360@gmail.com",
		    		},
		    //test sender can be signed into with password "testsendercse"
		    {"testsendercse", "testsendercse","testsendercse","testsendercse",
    		},	    
	};
	
	private String[][] instructorTable = {
			//our course instructors and a test recipient 
		    {"Javier Sanchez", "Natasha Mittal","Test Recipient"},
		    //test recipient can be signed into with password "testrecipient"
		    {"javiergs@asu.edu", "nmittal4@asu.edu","testrecipient.cse360@gmail.com"},
	};

	
	
	
	  
	public EmailSystem(String user) {
	
	setLayout(new BorderLayout());
	setMinimumSize(new Dimension(800, 500));
        setResizable(true);
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Contact Professor");
        
        //header
        HeaderPanel = new JPanel();
        HeaderPanel.setLayout(new BorderLayout());
        userEmailLabel = new JLabel(user);
        
        
        instructorEmail = new JComboBox<Object>(instructorTable[0]);
    
        instructorEmail.addActionListener(new RecipientListener());
        HeaderPanel.add(userEmailLabel, BorderLayout.NORTH);
        HeaderPanel.add(instructorEmail, BorderLayout.SOUTH);
        
        add(HeaderPanel, BorderLayout.NORTH); 
        
        //body
        BodyPanel = new JPanel();
        bodyTextArea = new JTextArea();
        bodyTextArea.setLineWrap(true);
        bodyTextArea.setWrapStyleWord(true);
        bodyTextArea.setEditable(true);
        BodyPanel.setLayout(new GridLayout(1,1));
        BodyPanel.add(bodyTextArea);
        bodyScrollPane = new JScrollPane(BodyPanel);
        bodyScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
        bodyScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        add(bodyScrollPane, BorderLayout.CENTER);
        
        //footer
        FooterPanel = new JPanel();
        FooterPanel.setLayout(new GridLayout(1,2));
        closeButton = new JButton("Close");
        closeButton.addActionListener(new closeListener());
        sendButton = new JButton("Send");
        sendButton.addActionListener(new sendListener());
        FooterPanel.add(closeButton);
        FooterPanel.add(sendButton);
        
        add(FooterPanel, BorderLayout.SOUTH);
        
        setVisible(true);
        
        //get user info from table
        queryUser(user);
        updateInstructorInfo();

        /**
         * This is where JavaMail is configured for gmail.
         */
        // Assuming you are sending email through relay.jangosmtp.net
        host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        session = Session.getInstance(props,
           new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(currentStudentEmail, currentStudentPassword);
  	   }
           });
	}
	
	/**
	 * The table is queried to update the sender email fields based on the current sender.
	 * @param The user, passed from universe, to query the database for.
	 */
	private void queryUser(final String user) {
		for(int i = 0;i < userTable[0].length;i++) {
			if (userTable[0][i] == user) {
				currentStudent = userTable[0][i];
				currentStudentEmail = userTable[1][i];
				currentStudentPassword = userTable[2][i];
			}
		}
	}
    
	/**
	 * @attemptsend() attempts to send the javamail message. 
	 * -It displays a Dialog box based on failure or success.
	 */
    private void attemptsend() {
    	try{
    		 MimeMessage message=new MimeMessage(session);
    		 
    		 System.out.print("Recipient: "+currentInstructor+"\n");
    		    message.setText(currentInstructor+",\n\n\t"+bodyTextArea.getText());
    		 System.out.print("Recipient Email: "+currentInstructorEmail+"\n");
    		 	//RecipientTypes are TO, CC, BCC -just like a normal email.
    		    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(currentInstructorEmail));
    		 System.out.print("Sender: "+currentStudent+"\n");
    		 	message.setSubject(currentStudent+" requests help in Java Tutor Deluxe.");
    		 System.out.print("Sender Email: "+currentStudentEmail+"\n");
    		 	message.setFrom(new InternetAddress(currentStudentEmail)); 
    		 
    		 Transport.send(message);
    		 
    		 System.out.print(".. Email sent.");
    		 JOptionPane.showMessageDialog(EmailSystem.this, ".. Message sent.\n");
    	}
    	catch(MessagingException e) {
    		 System.out.print(".. Message not sent. Make sure your connection is not blocked by a firewall or proxy.\n");
    		 JOptionPane.showMessageDialog(EmailSystem.this, "Message not sent. Error: " + e);
    	}
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	    Object source = e.getSource();
        if(source == null) {
            return;
        }
      if(source.equals(sendButton)) {
            attemptsend();
        } else if (source.equals(closeButton)) {
            System.exit(0);
            dispose();
            System.out.print("close");
        }
	}
	
	/**
	 * @updateInstructorInfo() Traverses the table and updates the message recipient information.
	 */
	private void updateInstructorInfo() {
		for(int i = 0;i < instructorTable[0].length;i++) {
			if (instructorTable[0][i] == instructorEmail.getSelectedItem()) {
				currentInstructor = instructorTable[0][i];
				currentInstructorEmail = instructorTable[1][i];
			}
		}	
	}
	
	/**
	 *@RecipientListener The ActionListener for the JComboBox; calls @updateInstructorInfo()
	 */
	private class RecipientListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			updateInstructorInfo();
		}
	}
	
	/**
	 * @author Seth
	 * Closes the program.
	 */
	private class closeListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			  Object source = e.getSource();
		        if(source == null) {
		            return;
		        }
		      if(source.equals(closeButton)) {
		            System.exit(0);
		            dispose();
		            System.out.print("close");
		        }
		}
		
	}

	private class sendListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			 Object source = e.getSource();
		        if(source == null) {
		            return;
		        }
		      if(source.equals(sendButton)) {
		    	  if("".equals(bodyTextArea.getText()))
		    	  {
		    		  JOptionPane.showMessageDialog(EmailSystem.this, "Please enter a message.");
		    	  }
		    	  else{
		    		System.out.print("Sending Message .. \n");
		    	  	attemptsend();
		    	  }
		        }
		}
	}
	
    public static void main(String[] args) {
        //Using this to ensure that this will run on the AWT dispatch thread.
       SwingUtilities.invokeLater(() -> new EmailSystem("Seth Turnage"));
    }

}
