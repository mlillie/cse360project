import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class LoginSystem extends JFrame implements ActionListener {


    private JButton login, signUp, exit;

    private JCheckBox rememberMe;

    private JTextField usernameField;

    private JPasswordField passwordField;

    /**
     * The preferred resolution of the frame.
     */
    private static final Dimension PREFERRED_RESOLUTION = new Dimension(350, 170);

    /**
     * This map will hold all the users; Key = username, Value = password
     */
    private Map<String, String> users = new HashMap<>();

    private File usersFile;


    LoginSystem() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Error attempting to set the look and feel to the OS.");
        }

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setPreferredSize(PREFERRED_RESOLUTION);
        setResizable(false);
        setLayout(new BorderLayout());
        setTitle("Java Tutor Deluxe Login System");

        login = new JButton("Login");
        signUp = new JButton("Sign-up");
        exit = new JButton("Exit");
        rememberMe = new JCheckBox("Remember me");

        usernameField = new JTextField(12); // how long
        passwordField = new JPasswordField(12); // how long

        JPanel middlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        middlePanel.add(usernameLabel);
        middlePanel.add(usernameField, gridBagConstraints);
        middlePanel.add(passwordLabel);
        middlePanel.add(passwordField, gridBagConstraints);
        middlePanel.add(rememberMe);
        middlePanel.add(login);
        middlePanel.add(signUp);
        middlePanel.add(exit);
        add(middlePanel, BorderLayout.CENTER);

        login.addActionListener(this);
        exit.addActionListener(this);
        signUp.addActionListener(this);

        //Load all the users from the txt file.
        try {
            usersFile = new File(this.getClass().getResource("/users.txt").toURI());
            try (FileReader fileReader = new FileReader(usersFile)) {
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while((line = bufferedReader.readLine()) != null) {
                    String user = line.split(":")[0];
                    String pass = line.split(":")[1];
                    System.out.println("user: " + user);
                    System.out.println("pass: " + pass);
                    users.put(user, pass);
                }

                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        pack();
        setVisible(true);
    }


    public static void main(String[] args) {
        //Using this to ensure that this will run on the AWT dispatch thread.
        SwingUtilities.invokeLater(LoginSystem::new);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == null) {
            return;
        }
        if(source.equals(login)) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if(username == null || password.length() == 0) {
                JOptionPane.showMessageDialog(this,"You must enter a username and password!");
                return;
            }

            username = username.toLowerCase();

            if(users.containsKey(username)) {
                if(users.get(username).equals(password)) {
                    SwingUtilities.invokeLater(Universe::new);
                    if(rememberMe.isSelected()) {
                        //TODO write locally to the OS, the user and password that has been hashed? and then try to read it on startup
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"Incorrect username or password!");
                }
            } else {
                JOptionPane.showMessageDialog(this,"Incorrect username or password!");
            }

        } else if (source.equals(exit)) {
            dispose();
        } else if (source.equals(signUp)) {
            //TODO
        }
    }
}
