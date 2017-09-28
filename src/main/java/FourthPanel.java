import javax.swing.*;
import java.awt.*;

/**
 * The fourth panel within the tutoring project which only displays a name when the state is 0, otherwise it is blank.
 *
 * @author Matt Lillie
 * @version 9/7/2017
 */
public class FourthPanel extends TutoringPanel {

    /**
     * The name label, containing the name and the alignment of the text.
     */
    private final static JLabel NAME_LABEL = new JLabel("<html> <b>Universe: </b> Matt Lillie </html>", SwingConstants.CENTER);

    /**
     * The color being used for the gradient background.
     */
    private final static Color COLOR = new Color(132, 89, 190);

    /**
     * Creates a new FourthPanel object.
     */
    FourthPanel() {
        setLayout(new GridLayout());
        add(NAME_LABEL);
    }

    /**
     * Determines whether or not the name label needs to be currently visible (if the state is zero), or not.
     * @param state The current state of the panel.
     */
    @Override
    public void update(int state) {
        if(state == 0) {
            NAME_LABEL.setVisible(true);
        } else {
            NAME_LABEL.setVisible(false);
        }
    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onSave() {

    }

    /**
     * Overriding the paint component method in order to add a
     * @param graphics The graphics being used to paint the component.
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        GradientPaint gradientPaint = new GradientPaint(0, 0,
                COLOR.darker(), getWidth(), getHeight(),
                COLOR.brighter());

        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
    }
}
