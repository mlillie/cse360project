import javax.swing.*;
import java.awt.*;

class CodeQuestion extends JPanel implements Question {

    private JavaCodeTester javaCodeTester;
    private boolean complete = false;

    public CodeQuestion(JavaCodeTester javaCodeTester) {
        this.javaCodeTester = javaCodeTester;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public int getAttempts() {
        return ControlCenter.getInstance().getCorrectAnswers()[getQuestionId()] +
                ControlCenter.getInstance().getIncorrectAnswers()[getQuestionId()];
    }

    @Override
    public boolean checkanswer() {
        if(complete) {
            return true;
        } else if(javaCodeTester.checkAnswerCorrect()) {
            complete = true;
            ControlCenter.getInstance().getStopwatches()[getQuestionId()].stop();
            ControlCenter.getInstance().getCorrectAnswers()[getQuestionId()]++;
            return true;
        } else {
            ControlCenter.getInstance().getIncorrectAnswers()[getQuestionId()]++;
            return false;
        }
    }


    @Override
    public void addtoQuestion(Component c) {
        add(c);
    }

    @Override
    public void setAttempt(int answerKey) {
    }

    @Override
    public void setAnswer(int answerKey) {
    }

    @Override
    public void setQuestion(String question) { }

    @Override
    public void pause() {
        ControlCenter.getInstance().getStopwatches()[getQuestionId()].stop();
    }

    @Override
    public void unpause() {
        ControlCenter.getInstance().getStopwatches()[getQuestionId()].start();
    }

    @Override
    public int getQuestionId() {
        return javaCodeTester.getAssignmentId();
    }
}