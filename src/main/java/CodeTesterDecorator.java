import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CodeTesterDecorator extends QuestionDecorator implements ActionListener {

    private JButton submitButton;

    private final JavaCodeTester tester;

    public CodeTesterDecorator(JavaCodeTester tester) {
        super(new CodeQuestion(tester));
        this.tester = tester;
        setLayout(new BorderLayout());

        submitButton = new JButton("Submit Code");
        submitButton.setToolTipText("Make sure you have both compiled and ran the code!");
        submitButton.addActionListener(this);

        add(tester, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(submitButton)) {
            if(isComplete()) return;
            if(checkanswer()) {
                long msSpent = ControlCenter.getInstance().getStopwatches()[tester.getAssignmentId()].elapsedTime();
                long secondsSpent = msSpent / 1000;
                msSpent %= 1000;
                long minutesSpent = secondsSpent / 60;
                secondsSpent %= 60;
                submitButton.setText("Completed in "+ minutesSpent + ":" + secondsSpent + ":" + msSpent);
                submitButton.setBackground(Color.GREEN);
            } else {
                submitButton.setBackground(Color.RED);
            }
        }
    }

    @Override
    public int getQuestionId() {
        return 0;
    }
}
