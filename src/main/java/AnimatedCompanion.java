import javax.imageio.ImageIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * This is an animated version of the Companion class, for now the image being displayed will either move in a circle
 * or bounce around off the walls. Images by Ashley Goernitz, though I, Matt Lillie, wrote the entirety of the animation code.
 * 
 * Ashley edited the move image method so that images would move in a fast circle only if the number of correct answers was 
 * less than the number of incorrect. Ashley also edited the graphics2D.drawImage method by having the method draw different 
 * images based on user performance.
 * Rec Project 4
 *
 * @author Matt Lillie, Ashley Goernitz (Edits), Jacqueline Fonseca
 * @version 11/6/2017
 */
public class AnimatedCompanion extends TutoringPanel implements ComponentListener, Observer {


    /**
	 * 
	 */
	private static final long serialVersionUID = 8987772564863058170L;
	/**
     * All the different variables being used.
     */
    private final static int WIDTH = 150, HEIGHT = 150;
    private final static double SPEED = 7.5;
    private final static JLabel AUTHORS_LABEL = new JLabel("<html> <b>Companion: </b> <br> <i>Images by</i>: Ashley Goernitz <br> <i>Animated by</i>: Matt Lillie</html>",
            SwingConstants.CENTER);
    private BufferedImage happyImage, sorryImage, thinkingImage, worriedImage;
    private int panelState = 0, imageState;
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
            try (InputStream in = getClass().getResourceAsStream("/happy.png")) {
                this.happyImage = ImageIO.read(in);
            }
            try (InputStream in = getClass().getResourceAsStream("/sorry.png")) {
                this.sorryImage = ImageIO.read(in);
            }
            try (InputStream in = getClass().getResourceAsStream("/thinking.png")) {
                this.thinkingImage = ImageIO.read(in);
            }
            try (InputStream in = getClass().getResourceAsStream("/worried.png")) {
                this.worriedImage = ImageIO.read(in);
            }
        } catch (IOException e) {
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
    	int totalCorrect = Assessor.totalcorrect;
        int totalWrong = Assessor.q1attempts + Assessor.q2attempts + Assessor.q3attempts + Assessor.q4attempts + Assessor.q5attempts + Assessor.q6attempts + Assessor.q7attempts + Assessor.q8attempts;
        
        if (panelState == 0) {
            return;
        }

        if (totalCorrect < totalWrong) {
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
    
    	private BufferedImage setImage(int state)
    	{
    		if (state == 1)
    			return happyImage;
    		if (state == 2)
    			return thinkingImage;
    		if (state == 3)
    			return worriedImage;
    		return sorryImage;
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
        
        //int totalCorrect = Assessor.totalcorrect;
        //int totalWrong = Assessor.q1attempts + Assessor.q2attempts + Assessor.q3attempts + Assessor.q4attempts + Assessor.q5attempts+ Assessor.q6attempts+ Assessor.q7attempts+Assessor.q8attempts;
        
        //draw whichever image at the updated x and y positions
        graphics2D.drawImage(setImage(this.imageState)
                // == totalWrong ? thinkingImage :
                        // > totalWrong ? happyImage :
                                //totalCorrect < totalWrong && totalCorrect == 0 ? sorryImage :
                                    //    totalCorrect < totalWrong ? worriedImage : null
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
        //RL
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

    @Override
    public void update(Observable o, Object arg) {
    		System.out.println(arg);
    		imageState = (int) arg;
    		
    		if(imageState == 1)//happy
    		{
    			removeAll();
    			BasicCompanion basic = new BasicCompanion();
    			GoodCompanion good = new GoodCompanion();
    			good.add(basic);
    			good.speak(this);
    			revalidate();
    			
    		}
    		if(imageState == 2)//thinking
    		{
    			removeAll();
    			BasicCompanion basic = new BasicCompanion();
    			ThinkingCompanion think = new ThinkingCompanion();
    			think.add(basic);
    			think.speak(this);
    			revalidate();
    		}
    		if(imageState == 3)//worried
    		{
    			removeAll();
    			BasicCompanion basic = new BasicCompanion();
    			WorriedCompanion worry = new WorriedCompanion();
    			worry.add(basic);
    			worry.speak(this);
    			revalidate();
    		}
    		if(imageState == 4)//sorry
    		{
    			removeAll();
    			BasicCompanion basic = new BasicCompanion();
    			BadCompanion bad = new BadCompanion();
    			bad.add(basic);
    			bad.speak(this);
    			revalidate();

    		}
  
    }
    
}
