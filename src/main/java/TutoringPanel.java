import javax.swing.*;

/**
 * A panel that is used in the tutoring application. Includes a way to be able to update the panel depending
 * on its state.
 *
 * @author Matt Lillie
 * @version 8/30/2017
 */
public abstract class TutoringPanel extends JPanel {

    /**
     * Sends the update to the panel.
     * @param state The current state of the panel.
     */
    public abstract void update(int state);
}
