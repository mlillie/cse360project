/**
 *
 * ControlCenter class that uses the Singleton design pattern to be able to track the number of correct and incorrect answers
 * of each lesson. Also this will track the time spent on each question of each lesson and calculate the current student's status.
 *
 * @author Matt Lillie
 * @version 10/23/2017
 */
public class ControlCenter {

    //The control center instance.
    private static ControlCenter instance;

    //The total number of lessons there will be.
    public static int NUMBER_OF_LESSONS = 10; // TODO HOW MANY?

    //The arrays associated with the lessons.
    private final int[] correctAnswers;
    private final int[] incorrectAnswers;
    private final long[] timeSpent;

    private ControlCenter() {
        correctAnswers = new int[NUMBER_OF_LESSONS];
        incorrectAnswers = new int[NUMBER_OF_LESSONS];
        timeSpent = new long[NUMBER_OF_LESSONS];
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
     * Gets the time spent on each question array.
     * @return The time spent array.
     */
    public long[] getTimeSpent() {
        return timeSpent;
    }

    /**
     * Calculates the status of the student by going through each array and deciding whether or not the student is doing well or
     * not. TODO
     * @return A StudentStatus that will be either: worried, happy, thinking, or sorry.
     */
    public StudentStatus calculateStatus() {
        return null;
    }


    /**
     * An enum used to represent the current status of the student.
     */
    enum StudentStatus {
        WORRIED, HAPPY, THINKING, SORRY;
    }


}
