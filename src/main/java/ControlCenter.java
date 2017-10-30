import java.util.concurrent.TimeUnit;

/**
 *
 * ControlCenter class that uses the Singleton design pattern to be able to track the number of correct and incorrect answers
 * of each lesson. Also this will track the time spent on each question of each lesson and calculate the current student's status.
 *
 * @author Matt Lillie
 * @version 10/29/2017
 */
public class ControlCenter {

    //The control center instance.
    private static ControlCenter instance;

    //The total number of lessons there will be.
    public static int NUMBER_OF_LESSONS = 10; // TODO HOW MANY?

    //The arrays associated with the lessons.
    private final int[] correctAnswers;
    private final int[] incorrectAnswers;
    private final Stopwatch[] stopwatches;

    /**
     * Constructs new ControlCenter and initializes variables
     */
    private ControlCenter() {
        correctAnswers = new int[NUMBER_OF_LESSONS];
        incorrectAnswers = new int[NUMBER_OF_LESSONS];
        stopwatches = new Stopwatch[NUMBER_OF_LESSONS];
        for(int i = 0; i < NUMBER_OF_LESSONS; i ++) {
            stopwatches[i] = new Stopwatch(TimeUnit.SECONDS);
        }
    }

    /**
     * Gets the singleton variable used to represent this ControlCenter.
     * @return The ControlCenter instance.
     */
    public static ControlCenter getInstance() {
        if(instance == null) {
            instance = new ControlCenter();
        }
        return instance;
    }

    /**
     * Gets the correct answers array.
     * @return The array of correct answers.
     */
    public int[] getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * Gets the incorrect answers array.
     * @return The array of incorrect answers.
     */
    public int[] getIncorrectAnswers() {
        return incorrectAnswers;
    }

    /**
     * Gets the stopwatches.
     * @return The stopwatches array.
     */
    public Stopwatch[] getStopwatches() {
        return stopwatches;
    }

    /**
     * Calculates the status of the student by going through each array and deciding whether or not the student is doing well or
     * not.
     * @return A StudentStatus that will be either: worried, happy, thinking, or sorry.
     */
    public StudentStatus calculateStatus() {
        int totalCorrect = 0, totalWrong = 0;
        long totalTime = 0;

        for(int correct : correctAnswers) {
            totalCorrect += correct;
        }

        for(int wrong : incorrectAnswers) {
            totalWrong += wrong;
        }

        for(Stopwatch stopwatch : stopwatches) {
            totalTime += stopwatch.elapsedTime(); // in seconds
        }

        if(totalWrong > totalCorrect) { // More wrong than correct
            if(totalTime > 300) { // 5 minutes in seconds
                return StudentStatus.WORRIED;
            } else {
                return StudentStatus.SORRY;
            }
        } else if(totalWrong < totalCorrect) { // More correct than wrong
            if(totalTime > 300) { // 5 minutes in seconds
                return StudentStatus.THINKING;
            } else {
                return StudentStatus.HAPPY;
            }
        } else { // Same amount correct as wrong
            return StudentStatus.THINKING;
        }
    }


    /**
     * An enum used to represent the current status of the student.
     */
    enum StudentStatus {
        WORRIED, HAPPY, THINKING, SORRY
    }

    /**
     * Represents a stopwatch to be used to track how long a student has been doing a lesson.
     *
     * @author Matt Lillie
     * @version 10/29/2017
     */
    class Stopwatch {

        //Variables
        final TimeUnit timeUnit;
        boolean running = false;
        long startTime = 0, endTime = 0;

        /**
         * Stopwatch constructor, takes in the time unit to be measured in.
         * @param timeUnit The time unit that this stopwatch should be measured in.
         */
        Stopwatch(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
        }

        /**
         * Starts the stopwatch.
         */
        public void start() {
            if(running) {
                return;
            }
            this.running = true;
            this.startTime = System.currentTimeMillis();
        }

        /**
         * Stops the stopwatch.
         */
        public void stop() {
            if(!running) {
                return;
            }
            this.running = false;
            this.endTime = System.currentTimeMillis();
        }

        /**
         * Resets the stopwatch.
         */
        public void reset() {
            this.running = false;
            this.endTime = this.startTime = 0;
        }

        /**
         * Gets the elapsed time of the stopwatch.
         * @return The elapsed time of the stopwatch in the given TimeUnit.
         */
        public long elapsedTime() {
            if(!running) {
                return TimeUnit.MILLISECONDS.convert(endTime - startTime, timeUnit);
            }
            return TimeUnit.MILLISECONDS.convert(System.currentTimeMillis() - startTime, timeUnit);
        }
    }

}
