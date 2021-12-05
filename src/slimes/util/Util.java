package slimes.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * extra methods that dont fall in the catagory of the other utility classes
 */

public class Util {

    /**
     * this takes a normal number and turns in to a readable ordinal number.
     * for example
     * 2 _> 2nd
     * 37 -> 37th
     * 151 -> 151st
     *
     * @param number the number you want to turn in to an ordinal.
     * @return the ordinal number.
     */
    public static String toOrdinalNumber(int number) {
        if(number < 0) {
            return "-" + toOrdinalNumber(number * -1);
        }

        if(number % 100 > 10 && number % 100 < 20) {
            return number + "th";
        } else if(number % 10 == 1) {
            return number + "st";
        } else if(number % 10 == 2) {
            return number + "nd";
        } else if(number % 10 == 3) {
            return number + "rd";
        } else {
            return number + "th";
        }
    }

    /**
     * this formates time nicly.
     *
     * @param time the number you want to format.
     * @return the formatted time.
     */
    public static String formatTime(int time) {
        if(time < 0) {
            return "000";
        } else if (time > 999) {
            return "999";
        } else {
            NumberFormat numberFormat = new DecimalFormat("000");
            return numberFormat.format(time);
        }
    }
}
