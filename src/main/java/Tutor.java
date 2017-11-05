import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Seth Turnage
 * @version 9/8/17
 */
public class Tutor extends TutoringPanel {

	private JEditorPane mypane;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7304905427511551369L;
	/**
     * The name label, containing the name and the alignment of the text.
     */
    private final static JLabel NAME_LABEL = new JLabel("<html> <b>Tutor: </b> Seth Turnage </html>", SwingConstants.CENTER);

    /**
     * Creates a new Tutor object.
     */
    Tutor() {
        setLayout(new CardLayout());
		JPanel labelPanel = new JPanel(new GridLayout());
       	labelPanel.add(NAME_LABEL);

		JPanel htmlPanel = new JPanel(new BorderLayout());
        mypane = new JEditorPane();

        mypane.setEditable(false);
        mypane.setContentType("text/html");

		htmlPanel.add(new JScrollPane(mypane), BorderLayout.CENTER);
        add(labelPanel, "Label Panel");
        add(htmlPanel, "HTML Panel");
    }

    /**
     * Determines whether or not the name label needs to be currently visible (if the state is zero), or not.
     * @param state The current state of the panel.
     */
    @Override
    public void update(int state) {
		CardLayout layout = (CardLayout) getLayout();
		switch(state) {
			case 0:
				layout.show(this, "Label Panel");
				break;
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				layout.show(this, "HTML Panel");
				display_HTML("P"+state+".html");
				break;
			default:
				System.out.println("Invalid state sent to Tutor");
				break;
		}
    }

	@Override
	public void onLogout() {

	}

	@Override
	public void onSave() {

	}

	/**
	 * this reads a string from the filename parameter, creates a document with the string, and uses HTMLEditorKit to display it.
	 * @param filename - string location of file.
	 */
	
	private void display_HTML(String filename) {
		String content = FileUtils.readHtmlFile(filename); //read the file
		mypane.setText(content); // Document text is provided below.
		mypane.setCaretPosition(0); //Making sure the slider is at the top if it is visible
	}
}