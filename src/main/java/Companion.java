import java.awt.*;
import javax.swing.*;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;

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
            this.state = state;
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
					image = ImageIO.read(new File(this.getClass().getResource("/happy.png").toURI()));
					JLabel picLabel = new JLabel(new ImageIcon(image));
					add(picLabel);
				} catch(IOException ex){
					System.out.println("Image doesn't exist");

				} catch (URISyntaxException e) {
					e.printStackTrace();
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
					image = ImageIO.read(new File(this.getClass().getResource("/thinking.png").toURI()));
					JLabel picLabel = new JLabel(new ImageIcon(image));
					add(picLabel);

				} catch(IOException ex)
				{
					System.out.println("Image doesn't exist");
				} catch (URISyntaxException e) {
					e.printStackTrace();
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
					image = ImageIO.read(new File(this.getClass().getResource("/worried.png").toURI()));
					JLabel picLabel = new JLabel(new ImageIcon(image));
					add(picLabel);
				} catch(IOException ex)
				{
					System.out.println("Image doesn't exist");
				} catch (URISyntaxException e) {
					e.printStackTrace();
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
					image = ImageIO.read(new File(this.getClass().getResource("/sorry.png").toURI()));
					JLabel picLabel = new JLabel(new ImageIcon(image));
					add(picLabel);
				} catch(IOException ex){
					System.out.println("Image doesn't exist");
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				this.repaint();
			}
        }
}
