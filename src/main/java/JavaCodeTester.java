import javax.swing.*;
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
 * @version 10/23/2017
 */
public class JavaCodeTester extends TutoringPanel implements ActionListener {

    private static final Path STUDENT_CODE_PATH = Paths.get(System.getProperty("user.home") + File.separator + "JavaTutorDeluxe" +
            File.separator + "StudentCode.java");

    private JEditorPane textPane;
    private JButton compileButton, runButton;

    private ExecutorService service = Executors.newSingleThreadExecutor();

    JavaCodeTester() {
        textPane = new JEditorPane();
        textPane.setFont(new Font("Arial", Font.PLAIN,15));
        textPane.getDocument().putProperty(PlainDocument.tabSizeAttribute, 4);
        JScrollPane scrollPane = new JScrollPane(textPane);
        compileButton = new JButton("Compile");
        runButton = new JButton("Run");
        setLayout(new BorderLayout());

        //What happens when the compile button is hit.
        compileButton.addActionListener(this);

        //What happens when the run button is hit.
        runButton.addActionListener(this);

        //If the student code already exists, load it into the text pane.
        if(Files.exists(STUDENT_CODE_PATH)) {
            textPane.setText(FileUtils.readFile(STUDENT_CODE_PATH));
            textPane.setCaretPosition(0);
        }

        //Arranging everything into the panel.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        buttonPanel.add(compileButton, gridBagConstraints);
        buttonPanel.add(runButton, gridBagConstraints);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void update(int state) {
        //?
    }

    @Override
    public void onLogout() {
        service.shutdown(); //shutdown the service if we have logged out
    }

    @Override
    public void onSave() {
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

            try {
                Files.deleteIfExists(Paths.get(STUDENT_CODE_PATH.toString().substring(0,
                        STUDENT_CODE_PATH.toString().length()-4)+"class"));

                Files.write(STUDENT_CODE_PATH, textPane.getText().getBytes(), CREATE, TRUNCATE_EXISTING);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            service.execute(new RuntimeExec("javac " + STUDENT_CODE_PATH.toString(), true));
        } else if(source.equals(runButton)) {
            service.execute(new RuntimeExec("java -cp " +
                    System.getProperty("user.home") + File.separator + "JavaTutorDeluxe StudentCode", false));
        }
    }

    class RuntimeExec implements Runnable {

        private String command;
        private boolean compile;

        RuntimeExec(String command, boolean compile) {
            this.command = command;
            this.compile = compile;
        }

        @Override
        public void run() {
            try {
                Process process = Runtime.getRuntime().exec(command);

                StreamReader errorReader = new StreamReader(process.getErrorStream(), compile,true);
                StreamReader outputReader = new StreamReader(process.getInputStream(), false,false);

                service.execute(errorReader);
                service.execute(outputReader);

                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    class StreamReader implements Runnable {

        private InputStream inputStream;

        private boolean error, compile;

        StreamReader(InputStream inputStream, boolean compile, boolean error) {
            this.inputStream = inputStream;
            this.compile = compile;
            this.error = error;
        }


        @Override
        public void run() {

            if(inputStream == null) {
                return;
            }

            try(BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;

                StringBuilder builder = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    builder.append(line).append(System.lineSeparator());
                }

                if(builder.length() > 0)
                    JOptionPane.showMessageDialog(JavaCodeTester.this, builder.toString(), error ? "Error" : "Output",
                            error ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
                else if(compile)
                    JOptionPane.showMessageDialog(JavaCodeTester.this, "Compile Successful!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}