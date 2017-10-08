import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Companion class that displays an image in the universe class depending
 * on slider position
 * Recitation Project 1
 * Completion time: 5 hrs
 *
 * @AshleyGoernitz
 * @9/10/17
 */
public class Companion extends TutoringPanel
{
	static final long serialVersionUID = 1L;
	JPanel panel;
        static int state;

        private BufferedImage image;

        public Companion()
        {

        }


	//updates display according to slider state
        public void update(int state)
        {
            Companion.state = state;
            panel = new JPanel();
            setLayout(new GridBagLayout());
            GridBagConstraints constraint = new GridBagConstraints();

		//displays my name at state 0
            if(state == 0)
            {
                this.removeAll();
                this.revalidate();

                Color newColor = new Color(239, 239, 239);
                JTextField name = new JTextField("Ashley Goernitz");
                name.setEditable(false);
                name.setBackground(newColor);
                name.setBorder(BorderFactory.createEmptyBorder());
                constraint.fill = GridBagConstraints.HORIZONTAL;



                panel.add(name);
                add(panel);

                this.repaint();
            }

            //when state changes to one, display image happy.jpg
            if(state == 1)
            {
				this.removeAll();
                this.revalidate();

                try
                {
					try (InputStream in = getClass().getResourceAsStream("/happy.png")) {
						this.image = ImageIO.read(in);
					}
					JLabel picLabel = new JLabel(new ImageIcon(image));
					add(picLabel);
				} catch(IOException ex){
					System.out.println("Image doesn't exist");

				}
				this.repaint();
            }

            //when state changes to one, display image thinking.jpg
            if(state == 2)
            {
				this.removeAll();
				this.revalidate();

				try
				{
					try (InputStream in = getClass().getResourceAsStream("/thinking.png")) {
						this.image = ImageIO.read(in);
					}
					JLabel picLabel = new JLabel(new ImageIcon(image));
					add(picLabel);

				} catch(IOException ex)
				{
					System.out.println("Image doesn't exist");
				}

				this.repaint();
			}

			//when state changes to one, display image worried.jpg
			if(state == 3)
			{
				this.removeAll();
				this.revalidate();

				try
				{
					try (InputStream in = getClass().getResourceAsStream("/worried.png")) {
						this.image = ImageIO.read(in);
					}
					JLabel picLabel = new JLabel(new ImageIcon(image));
					add(picLabel);
				} catch(IOException ex)
				{
					System.out.println("Image doesn't exist");
				}
				this.repaint();

			}

			//when state changes to one, display image sorry.jpg
			if(state == 4)
			{
				this.removeAll();
				this.revalidate();

				try
				{
					try (InputStream in = getClass().getResourceAsStream("/sorry.png")) {
						this.image = ImageIO.read(in);
					}
					JLabel picLabel = new JLabel(new ImageIcon(image));
					add(picLabel);
				} catch(IOException ex){
					System.out.println("Image doesn't exist");
				}
				this.repaint();
			}
        }

	@Override
	public void onLogout() {

	}

	@Override
	public void onSave() {

	}
}
