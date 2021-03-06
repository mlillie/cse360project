import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static java.nio.file.StandardOpenOption.*;


/**
 * This class represents the authentication system to be used for the Intelligent Tutoring System that is the Java Tutor Deluxe.
 * - Reads users from a text file located in the resources folder of the Maven project.
 * - Sign-up functionality, saves to the users text file.
 * - Created users are saved and read to users home directory/JavaTutorDeluxe
 * - Remember me reads and writes to the users home directory/JavaTutorDeluxe
 * - Usernames and passwords are encoded and decoded in base 64, for a very small amount of security.
 * - Username and password validation (making sure that they are of correct length, and have valid characters)
 * - Different types of Users (students and instructors)
 *
 * @author Matt Lillie
 * @version 10/08/17
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
     * This map will hold all the users; Key = username, Value = User object associated with the username
     */
    private final Map<String, User> users = new HashMap<>();

    /**
     * The Path to the remember me text file.
     */
    private final static Path REMEMBER_ME_PATH = Paths.get(System.getProperty("user.home") + File.separator + "JavaTutorDeluxe" +
            File.separator + "jtdremember.txt");

    /**
     * The Path to the other local users text file.
     */
    private final static Path OTHER_USERS = Paths.get(System.getProperty("user.home") + File.separator + "JavaTutorDeluxe" +
            File.separator + "users.txt");

    /**
     * This is the pattern which is used to see if a username has proper characters in it.
     */
    private final static Pattern USERNAME_PATTERN = Pattern.compile("[A-Za-z0-9_]+"); // just a - z, 0 - 9, and underscores

    /**
     * This is the pattern which is used to see if a password has proper characters in it.
     */
    private final static Pattern PASSWORD_PATTERN = Pattern.compile("[\\w\\Q!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\\E]+"); //allows for many more characters

    /**
     * The minimum length of a username.
     */
    private static final int MINIMUM_USERNAME_LENGTH = 3;

    /**
     * The minimum length of a password.
     */
    private static final int MINIMUM_PASSWORD_LENGTH = 8;

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
        setResizable(true);
        setPreferredSize(calculatePreferredDimension());
        setLayout(new GridLayout());
        setTitle("Java Tutor Deluxe Authentication");

        login = new JButton("Login");
        signUp = new JButton("Sign-up");
        exit = new JButton("Exit");
        rememberMe = new JCheckBox("Remember me");

        login.setToolTipText("Attempt to login.");
        signUp.setToolTipText("Allowed username characters are A-Z, a-z, 0-9 and _");
        exit.setToolTipText("Exit the program.");
        rememberMe.setToolTipText("If selected, your username and password will be saved locally to your home directory.");

        usernameField = new JTextField(12); // how long
        passwordField = new JPasswordField(18); // how long ?

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
        add(middlePanel);

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

                    usernameField.setText(new String(Base64.getDecoder().decode(user)));
                    passwordField.setText(new String(Base64.getDecoder().decode(pass)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Load all the users from the resource users.
        try (InputStream in = getClass().getResourceAsStream("/users.txt");
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() <= 0) continue;
                String[] splitLine = line.split(":");
                String user = splitLine[0];
                String pass = splitLine[1];
                String rights = splitLine[2];
                String realUser = new String(Base64.getDecoder().decode(user.getBytes()));
                String realPass = new String(Base64.getDecoder().decode(pass.getBytes()));
                users.put(user, new User(realUser, realPass, UserRights.getForString(rights)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Load all the users from the other, local, txt file.
        if(Files.exists(OTHER_USERS)) {
            try (InputStream in = Files.newInputStream(OTHER_USERS);
                 BufferedReader reader =
                         new BufferedReader(new InputStreamReader(in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().length() <= 0) continue;
                    String[] splitLine = line.split(":");
                    String user = splitLine[0];
                    String pass = splitLine[1];
                    String rights = splitLine[2];
                    if (users.containsKey(user)) {
                        continue;
                    }
                    String realUser = new String(Base64.getDecoder().decode(user.getBytes()));
                    String realPass = new String(Base64.getDecoder().decode(pass.getBytes()));
                    users.put(user, new User(realUser, realPass, UserRights.getForString(rights)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        pack();
        setVisible(true);
    }

    /**
     * Calculates the proper dimension to use for the Frame, based on the operating system.
     * @return A Dimension, different by OS.
     */
    private Dimension calculatePreferredDimension() {
        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("mac")) {
            return new Dimension(450, 180);
        }

        return new Dimension(380, 180);
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

        String base64Username = new String(Base64.getEncoder().encode(username.getBytes()));

        if(users.containsKey(base64Username)) {
            if(users.get(base64Username).getBase64Password().equalsIgnoreCase(base64Password)) {
                //User and password have been authenticated, start the ITS.
                SwingUtilities.invokeLater(() -> new Universe(users.get(base64Username)));

                //Remember me checks.
                if(rememberMe.isSelected()) {

                    //Create the directory if it does not exist
                    if(!Files.exists(REMEMBER_ME_PATH.getParent())) {
                        try {
                            Files.createDirectory(REMEMBER_ME_PATH.getParent());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    //Write to the remember me file, creating the file if it does not exist
                    //If the file has content within it, delete all the bytes within it and start fresh
                    byte[] data = (base64Username + ":" + base64Password).getBytes();

                    try (OutputStream out = new BufferedOutputStream(
                            Files.newOutputStream(REMEMBER_ME_PATH, CREATE, TRUNCATE_EXISTING))) {
                        out.write(data, 0, data.length);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                } else {
                    //If the remember me is not checked, then we can see if the file exists, if so, delete it.
                    try {
                        Files.deleteIfExists(REMEMBER_ME_PATH);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                //Wont need this frame anymore, so dispose of it
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

        //Check username validity.
        if (!checkUsernameValidity(username)) {
            return;
        }

        //Check password validity.
        if (!checkPasswordValidity(password)) {
            return;
        }

        //Create the directory if it does not exist
        if(!Files.exists(OTHER_USERS.getParent())) {
            try {
                Files.createDirectory(OTHER_USERS.getParent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String base64Username = new String(Base64.getEncoder().encode(username.toLowerCase().getBytes()));

        String base64Password = new String(Base64.getEncoder().encode(password.getBytes()));

        String rights = UserRights.STUDENT.toString(); //TODO how to make it so they can pick what right they have?

        byte[] data = (base64Username + ":" + base64Password + ":" + rights + System.lineSeparator()).getBytes();

        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(OTHER_USERS, CREATE, APPEND))) {
            out.write(data, 0, data.length);
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            users.put(base64Username, new User(username, password));
            JOptionPane.showMessageDialog(this, "Successfully created the user: " + username);
        }

    }



    /**
     * Checks the validity of the username, by making sure it has an appropriate length, does not already exist, and has valid characters.
     * @param username The username to be checked.
     * @return True if the username is valid.
     */
    private boolean checkUsernameValidity(String username) {

        // Username length check.
        if(username.length() < MINIMUM_USERNAME_LENGTH) {
            JOptionPane.showMessageDialog(this, "Your username must contain at least "+ MINIMUM_USERNAME_LENGTH+" characters!");
            return false;
        }

        // Make sure the username has valid characters using Pattern class.
        if(!USERNAME_PATTERN.matcher(username).matches()) {
            JOptionPane.showMessageDialog(this, "Your username must contain valid characters!");
            return false;
        }

        // Make sure username does not already exist.
        if(users.get(new String(Base64.getEncoder().encode(username.toLowerCase().getBytes()))) != null) {
            JOptionPane.showMessageDialog(this,"That username already exists!");
            return false;
        }

        return true;
    }


    /**
     * Checks the validity of the password, by making sure it has an appropriate length, and has valid characters.
     * @param password The password to be checked.
     * @return True if the password is valid.
     */
    private boolean checkPasswordValidity(String password) {
        // Password length check.
        if(password.length() < MINIMUM_PASSWORD_LENGTH) {
            JOptionPane.showMessageDialog(this, "Your password must contain at least "+ MINIMUM_PASSWORD_LENGTH+" characters!");
            return false;
        }

        // Make sure the password has valid characters using Pattern class.
        if(!PASSWORD_PATTERN.matcher(password).matches()) {
            JOptionPane.showMessageDialog(this, "Your password must contain valid characters!");
            return false;
        }

        return true;
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


    /**
     * Represents a user that will have access to the ITS.
     *
     * @author Matt Lillie
     * @version 10/08/17
     */
    static class User {
        //Data associated with the user.
        private String username, password;
        private UserRights userRights;

        /**
         * Creates a new user, default rights set to student.
         * @param username The username of the user.
         * @param password The password of the user.
         */
        User(String username, String password) {
            this.username = username;
            this.password = password;
            this.userRights = UserRights.STUDENT; // default?
        }

        /**
         * Creates a new user.
         * @param username The username of the user.
         * @param password The password of the user.
         * @param userRights The rights of the user.
         */
        User(String username, String password, UserRights userRights) {
            this.username = username;
            this.password = password;
            this.userRights = userRights;
        }

        /**
         * Gets the username of this user.
         * @return The username.
         */
        public String getUsername() {
            return username;
        }

        /**
         * Gets the password for this user.
         * @return The password.
         */
        public String getPassword() {
            return password;
        }

        /**
         * Gets the base64 username for this user.
         * @return The username in base64.
         */
        public String getBase64Username() {
            return new String(Base64.getEncoder().encode(username.getBytes()));
        }

        /**
         * Gets the base64 password for this user.
         * @return The password in base64.
         */
        public String getBase64Password() {
            return new String(Base64.getEncoder().encode(password.getBytes()));
        }

        /**
         * Gets the rights for this user.
         * @return The user's rights.
         */
        public UserRights getUserRights() {
            return userRights;
        }
    }

    /**
     * Enum that holds the different types of rights (Student and Instructor).
     *
     * @author Matt Lillie
     * @version 10/08/17
     */
    enum UserRights {
        STUDENT,
        INSTRUCTOR;

        /**
         * Given a string, it will compare it to this enum's values to see if that string represents a right.
         * @param userRights The right in String form.
         * @return Default return will be STUDENT if it does not find a right from the given string.
         */
        static UserRights getForString(String userRights) {
            for(UserRights rights : values())
                if(rights.toString().equalsIgnoreCase(userRights))
                    return rights;
            return STUDENT;
        }
    }

}