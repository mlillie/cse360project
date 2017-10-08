import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A simple class used in relation to file utilities.
 *
 * @author Matt Lillie
 * @version 10/06/17
 */
public class FileUtils {

    /**
     * Used to read an HTML file from the maven resources folder. Uses a StringBuilder object to append each line of HTML code to it, and
     * ultimately returns a string of the entire file.
     *
     * @param htmlFile The file to be read from the resources folder.
     * @return A string of each line of the html file.
     */
    public static String readHtmlFile(String htmlFile) {
        StringBuilder builder = new StringBuilder();
        try (InputStream in = FileUtils.class.getClass().getResourceAsStream("/"+htmlFile);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }


    /**
     * Reads a file at the path given. This should be used to read anything that is not located in the maven resources folder.
     *
     * @param path The path to the file that will be read.
     * @return A string that contains the contents of the file.
     */
    public static String readFile(Path path) {
        StringBuilder builder = new StringBuilder();
        try (InputStream in = Files.newInputStream(path);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
