import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Is the Universe class for the tutoring project. This will be the frame of the entire project, and will hold all the
 * individual panels within it as well as a slider to be able to change the panels' states.
 *
 * @author Matt Lillie
 * @version 9/8/2017
 */
public class Universe extends JFrame implements ChangeListener {

    /**
     * ArrayList that will hold all the panels within this frame.
     */
    private List<TutoringPanel> tutoringPanels = new ArrayList<>(4);

    /**
     * The Border being used by the panels.
     */
    private static final Border PANEL_BORDER = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
            BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

    /**
     * The preferred resolution of the frame.
     */
    private static final Dimension PREFERRED_RESOLUTION = new Dimension(1280, 720); //720

    /**
     * The maximum resolution of the frame.
     */
    private static final Dimension MAXIMUM_RESOLUTION = new Dimension(3840 , 2160); //4k

    /**
     * The current state of the panels.
     */
    private int panelState = 0;

    /**
     * Creates a new Universe JFrame object.
     */
    Universe() {
        // The look and feel for the frame, which will change depending on the OS.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Error attempting to set the look and feel to the OS.");
        }

        // Setting default options, sizes, title, etc
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setPreferredSize(PREFERRED_RESOLUTION);
        setMaximumSize(MAXIMUM_RESOLUTION);
        setLayout(new BorderLayout());
        setTitle("Java Tutor Deluxe");

        // This middle panel will contain the other 4 panels within it.
        JPanel middlePanel = new JPanel(new GridLayout(2, 2));

        // Create all the other panels with new anim
        TutoringPanel firstPanel = new AnimatedCompanion();
        firstPanel.setBorder(PANEL_BORDER);

        TutoringPanel secondPanel = new Tutor();
        secondPanel.setBorder(PANEL_BORDER);

        TutoringPanel thirdPanel = new Assessor();
        thirdPanel.setBorder(PANEL_BORDER);

        TutoringPanel fourthPanel = new FourthPanel();
        fourthPanel.setBorder(PANEL_BORDER);

        // Add the panels to the list and add all the panels to the middle panel
        tutoringPanels.add(firstPanel);
        tutoringPanels.add(secondPanel);
        tutoringPanels.add(thirdPanel);
        tutoringPanels.add(fourthPanel);
        tutoringPanels.forEach(middlePanel::add);

        // Slider object used to update the panels, will have 5 options (0, 1, 2, 3, 4), defaulted to 0.
        JSlider stateSlider = new JSlider(0, 4, 0);
        stateSlider.addChangeListener(this);
        stateSlider.setMajorTickSpacing(1);
        stateSlider.setPaintTicks(true);
        stateSlider.setLabelTable(stateSlider.createStandardLabels(1));
        stateSlider.setPaintLabels(true);

        // Add the middle panel and the slider to the JFrame.
        add(middlePanel, BorderLayout.CENTER);
        add(stateSlider, BorderLayout.SOUTH);

        // Update the panels now, to ensure they are correctly put to state 0.
        updatePanels(0);

        // Pack the frame and then set it visible.
        pack();
        setVisible(true);
    }

    /**
     * Handles the state changes of the slider.
     * @param event The change event.
     */
    @Override
    public void stateChanged(ChangeEvent event) {
        JSlider source = (JSlider)event.getSource();
        if (!source.getValueIsAdjusting()) {
            if(source.getValue() != panelState) {
                updatePanels(source.getValue());
            }
        }
    }

    /**
     * Sends the state update to the panels, revalidates the components within them.
     * @param panelState The new state to set the panels to.
     */
    private void updatePanels(int panelState) {
        //Update the variable with the new state.
        this.panelState = panelState;

        //Send the update and revalidate each of the panels.
        tutoringPanels.forEach(panel -> {
            panel.update(panelState);
            panel.revalidate();
        });

    }

    public static void main(String[] args) {
        //Using this to ensure that this will run on the AWT dispatch thread.
        SwingUtilities.invokeLater(Universe::new);
    }

}
