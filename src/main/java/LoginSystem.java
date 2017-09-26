import static java.nio.file.StandardOpenOption.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


/**
 * This class represents the login system to be used for the Intelligent Tutoring System.
 * - Reads users from a text file located in the resources folder of the Maven project.
 * - Sign-up functionality, saves to the users text file.
 * - Remember me reads and writes to the users local documents directory.
 * - Passwords are encoded and decoded in base 64, for a very small amount of security.
 *
 * @author Matt Lillie
 * @version 09/26/17
 */
public class LoginSystem extends JFrame implements ActionListener {


    /**
     * All the components being used in the system.
     */
    private JButton login, signUp, exit;
    private JCheckBox rememberMe;
    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * The preferred resolution of the frame.
     */
    private static final Dimension PREFERRED_RESOLUTION = new Dimension(350, 170);

    /**
     * This map will hold all the users; Key = username, Value = password in base64
     */
    private final Map<String, String> users = new HashMap<>();

    /**
     * The Path to the remember me text file.
     */
    private final static Path REMEMBER_ME_PATH = Paths.get(System.getProperty("user.home") + File.separator + "Documents" +
            File.separator + "jtdremember.txt");

    /**
     * The Path to the users text file.
     */
    private final static Path USERS_PATH = Paths.get(LoginSystem.class.getResource("users.txt").getPath().
            replaceFirst("^/(.:/)", "$1"));

    /**
     * Creation of the login system starts here
     */
    public LoginSystem() {
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

        //Add enter key listener for login.
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    attemptLogin();
                }
            }
        });

        //Add enter key listener for login.
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    attemptLogin();
                }
            }
        });

        // Attempt to read the remember me file, if it exists.
        if(Files.exists(REMEMBER_ME_PATH)) {
            //The remember me file exists, so ensure that the box will be checked.
            rememberMe.setSelected(true);

            try (InputStream in = Files.newInputStream(REMEMBER_ME_PATH);
                 BufferedReader reader =
                         new BufferedReader(new InputStreamReader(in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String user = line.split(":")[0];
                    String pass = line.split(":")[1];

                    usernameField.setText(user);
                    passwordField.setText(new String(Base64.getDecoder().decode(pass)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Load all the users from the txt file.
        if(Files.exists(USERS_PATH)) {
            try (InputStream in = Files.newInputStream(USERS_PATH);
                 BufferedReader reader =
                         new BufferedReader(new InputStreamReader(in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if(line.length() == 0) continue;
                    String user = line.split(":")[0];
                    String pass = line.split(":")[1];
                    System.out.println("user:" + user + " : " + pass);
                    users.put(user.toLowerCase(), pass);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        pack();
        setVisible(true);
    }

    /**
     * Attempts to allow the user to login into the ITS.
     */
    private void attemptLogin() {
        final String initialUsername = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if(initialUsername == null || password.length() == 0) {
            JOptionPane.showMessageDialog(this,"You must enter a username and password!");
            return;
        }

        String base64Password = new String(Base64.getEncoder().encode(password.getBytes()));

        String username = initialUsername.toLowerCase();

        if(users.containsKey(username)) {
            if(users.get(username).equals(base64Password)) {
                //User and password have been authenticated, start the ITS.
                SwingUtilities.invokeLater(() -> new Universe(username));

                //Remember me checks.
                if(rememberMe.isSelected()) {
                    byte[] data = (username + ":" + base64Password).getBytes();

                    try (OutputStream out = new BufferedOutputStream(
                            Files.newOutputStream(REMEMBER_ME_PATH, CREATE, TRUNCATE_EXISTING))) {
                        out.write(data, 0, data.length);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    //If the remember me is not checked, then we can see if the file exists, if so, delete it.
                    if(Files.exists(REMEMBER_ME_PATH)) {
                        try {
                            Files.delete(REMEMBER_ME_PATH);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Incorrect username or password!");
            }
        } else {
            JOptionPane.showMessageDialog(this,"Incorrect username or password!");
        }
    }

    /**
     * Allows for the user to attempt to sign-up to use the ITS.
     */
    private void attemptSignup() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username == null || password.length() == 0) {
            JOptionPane.showMessageDialog(this, "You must enter a username and password to sign-up with!");
            return;
        }

        String base64Password = new String(Base64.getEncoder().encode(password.getBytes()));

        //TODO check validity of the username or password? like invalid characters or inappropriate name?

        if(users.get(username.toLowerCase()) != null) {
            JOptionPane.showMessageDialog(this,"That username already exists!");
            return;
        }

        byte[] data = (System.lineSeparator() + username + ":" + base64Password).getBytes();

        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(USERS_PATH, APPEND))) {
            out.write(data, 0, data.length);
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            users.put(username.toLowerCase(), base64Password);
            JOptionPane.showMessageDialog(this,"Successfully created the user: " + username);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == null) {
            return;
        }
        if(source.equals(login)) {
            attemptLogin();
        } else if (source.equals(signUp)) {
            attemptSignup();
        } else if (source.equals(exit)) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        //Using this to ensure that this will run on the AWT dispatch thread.
        SwingUtilities.invokeLater(LoginSystem::new);
    }

}