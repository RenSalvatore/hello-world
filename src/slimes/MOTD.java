package slimes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * this class gets the message of the day.
 * @author Sam
 */

public class MOTD {

    private static final String PUZZLE_URL = "http://cswebcat.swansea.ac.uk/puzzle";
    private static final String SOLUTION_URL = "http://cswebcat.swansea.ac.uk/message?solution=";

    /**
     * this gets the message of the day.
     * @return the message of the day.
     */
    public static String getMOTD() {
        try {
            String puzzle = getPuzzleString();
            String solution = solvePuzzle(puzzle);
            String message = getMessage(solution);
            return message;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * this sends a get request to the website.
     *
     * @param urlString the url you want to send to.
     * @return what the website returns back.
     * @throws IOException
     */
    private static String sendRequest(String urlString) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();
        while (line != null ) {
            result.append(line);
            line = bufferedReader.readLine();
        }
        return result.toString();
    }

    /**
     * this gets the puzzle string.
     *
     * @return the puzzle string.
     * @throws IOException
     */
    private static String getPuzzleString() throws IOException {
        return sendRequest(PUZZLE_URL);
    }

    /**
     * this solves the puzzle with the puzzle string.
     *
     * @param puzzle the puzzle string.
     * @return the solution.
     */
    private static String solvePuzzle(String puzzle) {
        String output = "";
        int i = 1;
        for(char car : puzzle.toCharArray()) {
            if(i % 2 == 0) {
                output += shiftChar(car, i);
            } else {
                output += shiftChar(car, i * -1);
            }

            i++;
        }

        output += "CS-230";
        output = output.length() + output;

        return output;
    }

    /**
     * this shifts a char by an amount in the alphabet.
     * it also warps around.
     *
     * @param chacater a char between A and Z (it has to be uppercase).
     * @param shiftAmount the amount you want to shift it by.
     * @return the shifted char.
     */
    private static char shiftChar(char chacater, int shiftAmount) {
        int asscciValue = chacater;
        if(chacater >= 'A' && chacater <= 'Z') {
            int alphabetIndex = chacater - 65;
            alphabetIndex += shiftAmount;

            alphabetIndex = Math.floorMod(alphabetIndex, 26);


            return (char) (alphabetIndex + 65);
        }

        return ' ';
    }

    /**
     * this gets the message with the solution string.
     *
     * @param solution the souluion string.
     * @return the message of the day.
     * @throws IOException
     */
    private static String getMessage(String solution) throws IOException {
        String message = sendRequest(SOLUTION_URL + solution);
        return message;
    }
}
