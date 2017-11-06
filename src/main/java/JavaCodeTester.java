import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;


/**
 * This is currently a panel, that takes in Java code, has the ability to compile and then run that java code.
 * - Saves the code locally.
 * - Displays errors
 * - Displays output
 *
 * @author Matt Lillie
 * @version 10/26/2017
 */
public class JavaCodeTester extends TutoringPanel implements ActionListener {

    //The path to the student code Java file.
    private static final Path STUDENT_CODE_PATH = Paths.get(System.getProperty("user.home") + File.separator + "JavaTutorDeluxe" +
            File.separator + "StudentCode.java");

    //The text pane for the student to code in.
    private final JEditorPane textPane;

    //The compile and run buttons.
    private final JButton compileButton, runButton;

    //The executor service to run the Runtime executions.
    private final ExecutorService service;

    //The string to check if the output
    private final String outputCheck;

    //The output that has been ran by the user.
    private String ranOutput = "";

    /**
     * New JavaCodeTester object, initializing variables, setting up the panel, etc
     */
    JavaCodeTester(String outputCheck) {
        this.outputCheck = outputCheck;
        //Create new new single threaded executor service to run the runtime exec and reading the streams.
        service = Executors.newSingleThreadExecutor();
        setLayout(new BorderLayout());
        //Creating the pane.
        textPane = new JEditorPane();
        textPane.setFont(new Font("Arial", Font.PLAIN,15));
        textPane.getDocument().putProperty(PlainDocument.tabSizeAttribute, 4);
        JScrollPane scrollPane = new JScrollPane(textPane);
        compileButton = new JButton("Compile");
        runButton = new JButton("Run");

        //What happens when the compile button is hit.
        compileButton.addActionListener(this);

        //What happens when the run button is hit.
        runButton.addActionListener(this);

        //If the student code already exists, load it into the text pane.
        if(Files.exists(STUDENT_CODE_PATH)) {
            textPane.setText(FileUtils.readFile(STUDENT_CODE_PATH));
            textPane.setCaretPosition(0);
        }

        JPanel northPanel = new JPanel();
        JLabel toDoLabel = new JLabel();
        toDoLabel.setText("<html>Generate the output of: <i>" + outputCheck + "</i>"+
                "<br>Name of the class <b>MUST</b> be StudentCode."+
                "You <b>MUST</b> compile then run the file before submission!</html>");

        northPanel.add(toDoLabel);
        northPanel.setBorder(BorderFactory.createEtchedBorder());

        //Arranging everything into the panel.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonPanel.add(compileButton, gridBagConstraints);
        buttonPanel.add(runButton, gridBagConstraints);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        centerPanel.setBorder(BorderFactory.createEtchedBorder());
        add(centerPanel, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);
    }

    public int getAssignmentId() {
        return 6;
    }

    public boolean checkAnswerCorrect() {
        return outputCheck.trim().equalsIgnoreCase(ranOutput.trim());
    }

    @Override
    public void update(int state) {

    }

    @Override
    public void onLogout() {
        service.shutdown(); //shutdown the service if we have logged out
    }

    @Override
    public void onSave() {
        //Write the file to the path if save is issued.
        if(textPane != null ) {
            if(textPane.getText().trim().length() <= 0) {
                return;
            }
            try {
                Files.write(STUDENT_CODE_PATH, textPane.getText().getBytes(), CREATE, TRUNCATE_EXISTING);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == null) {
            return;
        }

        if(source.equals(compileButton)) {
            if(textPane.getText().trim().length() <= 0) {
                return;
            }

            //Delete the class file and then write the new java file.
            try {
                Files.deleteIfExists(Paths.get(STUDENT_CODE_PATH.toString().substring(0,
                        STUDENT_CODE_PATH.toString().length()-4)+"class"));

                Files.write(STUDENT_CODE_PATH, textPane.getText().getBytes(), CREATE, TRUNCATE_EXISTING);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            //Submit the compile execution to the ExecutorService
            service.execute(new RuntimeExec("javac " + STUDENT_CODE_PATH.toString(), true));
        } else if(source.equals(runButton)) {
            //Submit the run execution to the ExecutorService
            service.execute(new RuntimeExec("java -cp " +
                    System.getProperty("user.home") + File.separator + "JavaTutorDeluxe StudentCode", false));
        }
    }

    /**
     *
     * An implementation of the Runnable interface, that will execute a runtime command.
     *
     * @author Matt Lillie
     * @version 10/26/2017
     */
    class RuntimeExec implements Runnable {

        //Command to be ran.
        private final String command;

        //Whether or not the command is a compile command.
        private final boolean compile;

        /**
         * Creates a new RuntimeExec object to be ran through an ExecutorService.
         *
         * @param command The command to be ran.
         * @param compile Whether or not the command is a compile command.
         */
        RuntimeExec(String command, boolean compile) {
            this.command = command;
            this.compile = compile;
        }

        @Override
        public void run() {
            try {
                //Execute the command.
                Process process = Runtime.getRuntime().exec(command);

                //Create each of the stream readers.
                StreamReader errorReader = new StreamReader(process.getErrorStream(), compile,true);
                StreamReader outputReader = new StreamReader(process.getInputStream(), false,false);

                //Execute each of the stream readers by sending them to the executor service.
                service.execute(errorReader);
                service.execute(outputReader);

                //Wait for the process to be finished.
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * An implementation of the Runnable interface, that will read a stream and display a message based on that stream.
     *
     * @author Matt Lillie
     * @version 10/26/2017
     */
    class StreamReader implements Runnable {

        //The InputStream to read.
        private final InputStream inputStream;

        //Booleans that indicate whether it is an error message, or if compiling.
        private final boolean error, compile;

        /**
         * Creates a new StreamReader class that will be executed by an ExecutorService
         *
         * @param inputStream The InputStream to read.
         * @param compile Whether or not we are reading a compile message.
         * @param error Whether or not we are reading an error message.
         */
        StreamReader(InputStream inputStream, boolean compile, boolean error) {
            this.inputStream = inputStream;
            this.compile = compile;
            this.error = error;
        }

        @Override
        public void run() {
            //Try resource block; ensures readers are closed.
            try(BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;

                StringBuilder builder = new StringBuilder();

                //Read each line and append it to the builder.
                while ((line = in.readLine()) != null) {
                    builder.append(line).append(System.lineSeparator());
                }

                //Display the message.
                if(builder.length() > 0) {
                    if(!error) {
                        JOptionPane.showMessageDialog(JavaCodeTester.this, builder.toString(), "Output",
                                JOptionPane.INFORMATION_MESSAGE);
                        ranOutput = builder.toString();
                    } else {
                        JOptionPane.showMessageDialog(JavaCodeTester.this, builder.toString(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }
                else if(compile)
                    JOptionPane.showMessageDialog(JavaCodeTester.this, "Compile Successful!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}