/**
 * A class to keep track of the time that has elapsed in the simulation.
 * Time is measured in hours (24 hour format), days, months, and years
 *
 * @author Colin Billhardt and Thom Treebus
 * @version 2021.03.03
 */
public class Time {

    //Number of hours that have passed in the current day
    private static int hour;
    //Number of days that have passed in the current month
    private int day;
    //Number of months the simulation has been running.
    private int month;


    public Time()
    {
        day = month = 0;
        hour = 6;
    }

    /**
     * Increment the current hour by 1 to signify 1 hour passing.
     */
    public void incrementHour()
    {
        //Check if hour is currently less than 24, otherwise a new day starts
        if(hour < 24) {
            hour++;
        }else{
            hour = 0;
            incrementDay();
        }

    }

    /**
     * Increment the number of days by 1 to signify a day passing.
     */
    public void incrementDay() {
        day++;
    }

    /**
     * @return True if it is currently day time
     */
    public static boolean isDay(){
        //The day starts at sunrise (6 am) and ends at sunset(6 pm)
        return (hour>6)&&(hour<18);
    }

    //Getter methods

    /**
     * @return The number of hours the simulation has been running.
     */
    public int getHour() {
        return hour;
    }

    /**
     * @return The number of days
     */
    public int getDay() {
        return day;
    }

    /**
     * @return The number of months.
     */
    public int getMonth() {
        return month;
    }
}
