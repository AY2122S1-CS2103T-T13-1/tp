package seedu.address.model.module;

import java.time.LocalDate;
import java.util.Comparator;

public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    public static final String MESSAGE_CONSTRAINTS =
            "Input for Day is not formatted correctly.";

    /**
     * Returns true if a given string is a valid Day.
     */
    public static boolean isValidDay(String dayString) {
        try {
            Day.valueOf(dayString.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static int getTodayIntValue() {
        return Math.floorMod(LocalDate.now().getDayOfWeek().getValue() - 1, 7);
    }

    public boolean isToday() {
        return this.ordinal() == getTodayIntValue();
    }

    public static class DayComparator implements Comparator<Day> {
        @Override
        public int compare(Day day, Day dayTwo) {
            // get day of week, shift DayOfWeek by 1 so that Monday is 0 and Sunday is 6.
            int currentDay = getTodayIntValue();

            // get ordinal values of Day enums, shift by currentDay
            int dayNum = Math.floorMod(day.ordinal() - currentDay, 7);
            int dayTwoNum = Math.floorMod(dayTwo.ordinal() - currentDay, 7);

            // compare both day values
            return dayNum - dayTwoNum;
        }
    }
}
