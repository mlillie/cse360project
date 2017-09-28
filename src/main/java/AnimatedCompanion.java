import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * This is an animated version of the Companion class, for now the image being displayed will either move in a circle
 * or bounce around off the walls. Images by Ashley Goernitz, though I, Matt Lillie, wrote the entirety of the animation code.
 *
 * @author Matt Lillie
 * @version 09/22/2017
 */
public class AnimatedCompanion extends TutoringPanel implements ComponentListener {


    /**
     * All the different variables being used.
     */
    private final static int WIDTH = 150, HEIGHT = 150;
    private final static double SPEED = 7.5;
    private final static JLabel AUTHORS_LABEL = new JLabel("<html> <b>Companion: </b> <br> <i>Images by</i>: Ashley Goernitz <br> <i>Animated by</i>: Matt Lillie</html>",
            SwingConstants.CENTER);
    private BufferedImage happyImage, sorryImage, thinkingImage, worriedImage;
    private int panelState = 0;
    private int x = 0, y = 0;
    private int originX = 0, originY = 0;
    private double theta = 0, radius = 22;
    private int moveX = 0, moveY = 0;
    private AnimationTimer animationTimer;

    /**
     * Creating AnimatedCompanion and instantiating certain variables.
     */
    AnimatedCompanion() {
        //Load all the images
        try {
            this.happyImage = ImageIO.read(new File(this.getClass().getResource("/happy.png").toURI()));
            this.sorryImage = ImageIO.read(new File(this.getClass().getResource("/sorry.png").toURI()));
            this.thinkingImage = ImageIO.read(new File(this.getClass().getResource("/thinking.png").toURI()));
            this.worriedImage = ImageIO.read(new File(this.getClass().getResource("/worried.png").toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        //Used fort the author label, to ensure it will be centered.
        setLayout(new GridLayout());

        //Animation forces this to run on its own thread separate from the regular GUI (runs at 40 ms interval)
        this.animationTimer = new AnimationTimer(() -> {
            moveImage();
            repaint();
        }, 40);

        //author label
        add(AUTHORS_LABEL);

        //Add component listener to be able to listen to whenever the Frame is resized.
        addComponentListener(this);

        //little randomness so that whenever the program starts, the direction the image is moving is not always the same.
        Random random  = new Random();

        this.moveX = (random.nextBoolean() ? 1 : -1);
        this.moveY = (random.nextBoolean() ? -1 : 1);
    }


    /**
     * Moves the image by updating its x and y position on the panel.
     */
    private void moveImage() {
        if (panelState == 0) {
            return;
        }

        if (panelState % 2 == 0) {
            //Goes in a circle
            x = (int) (originX + Math.cos(Math.toRadians(theta)) * radius);
            y = (int) (originY + Math.sin(Math.toRadians(theta)) * radius);
            theta += Math.PI * 2;
            if (theta >= 360) theta -= 360;
        } else {
            //Will move across the screen and 'bounce' off the walls
            if (x > getWidth() - WIDTH || x < 0) {
                moveX = -moveX;
            }

            if (y > getHeight() - HEIGHT || y < 0) {
                moveY = -moveY;
            }

            x += SPEED * moveX;
            y += SPEED * moveY;
        }

    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (panelState == 0) {
            return;
        }

        Graphics2D graphics2D = (Graphics2D) graphics.create();

        //Setting rendering for antialiasing to be enabled and for quality to be of higher priority than performance
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        //draw whichever image at the updated x and y positions
        graphics2D.drawImage(
                panelState == 1 ? happyImage :
                        panelState == 2 ? sorryImage :
                                panelState == 3 ? thinkingImage :
                                        panelState == 4 ? worriedImage : null
                , x, y, WIDTH, HEIGHT, this);

        graphics2D.dispose();
    }


    @Override
    public void update(int state) {
        this.panelState = state;
        //Stopping and starting the timer, and updating the visibility of the label.
        if (panelState == 0) {
            animationTimer.stop();
            AUTHORS_LABEL.setVisible(true);
            repaint();
        } else if(!animationTimer.isRunning()){
            animationTimer.start();
            AUTHORS_LABEL.setVisible(false);
        }
    }

    @Override
    public void onLogout() {
        //Have to make sure the timer stops on logout other wise the thread will still be running in the background.
        animationTimer.stop();
    }

    @Override
    public void onSave() {

    }

    @Override
    public void componentResized(ComponentEvent e) {
        //Resizing the window will require us to update the origin and radius variables.
        this.x = this.originX = getWidth() / 2 - (WIDTH / 2);
        this.y = this.originY = getHeight() / 2 - (HEIGHT / 2);
        this.radius = ((getWidth() + getHeight()) / 2) / 6;
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
