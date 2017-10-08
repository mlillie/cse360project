import java.util.concurrent.TimeUnit;

/**
 * This class is used to have a separate thread running the animation updating and repainting, etc.
 *
 *
 * @author Matt Lillie
 * @version 09/20/17
 */
public class AnimationTimer implements Runnable {

    /**
     * The thread to run the runnable object on.
     */
    private Thread thread;

    /**
     * The runnable object.
     */
    private Runnable runnable;

    /**
     * How long to sleep in between running.
     */
    private long ms;

    /**
     * Whether or not the the thread should stop(join) and be reset. Volatile to ensure thread safety.
     */
    private volatile boolean running = false;

    /**
     * Creates a new AnimationTimer
     * @param runnable The runnable object to run on the thread.
     * @param ms How long we should sleep in between loops.
     */
    AnimationTimer(Runnable runnable, long ms) {
        this.runnable = runnable;
        this.ms = ms;
    }

    /**
     * Starts the animation timer.
     */
    public void start() {
        if(thread == null){
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Stops the animation timer.
     */
    public void stop() {
        if(thread != null) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread = null;
        }
    }

    /**
     * Determines whether or not this timer is running.
     * @return True if the timer is running otherwise false.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * This is what will be ran inside of the thread; includes the runnable being ran and the sleeping of the thread.
     */
    @Override
    public void run() {
        while (running) {
            runnable.run();
            try {
                TimeUnit.MILLISECONDS.sleep(ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
