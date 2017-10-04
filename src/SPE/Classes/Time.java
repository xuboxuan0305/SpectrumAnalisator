package SPE.Classes;

/**
 * Created by Administrator on 7/20/17.
 */
public class Time {
    private int hours;
    private int minutes;
    private int seconds;
    private String amPm;

    public Time(int hours,
             int minutes,
             int seconds,
             String amPm){
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.amPm = amPm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time time = (Time) o;

        if (hours != time.hours) return false;
        if (minutes != time.minutes) return false;
        if (seconds != time.seconds) return false;
        return amPm != null ? amPm.equals(time.amPm) : time.amPm == null;
    }

    @Override
    public int hashCode() {
        int result = hours;
        result = 31 * result + minutes;
        result = 31 * result + seconds;
        result = 31 * result + (amPm != null ? amPm.hashCode() : 0);
        return result;
    }

    public int getHours(){
        return this.hours;
    }

    public int getMinutes(){
        return this.minutes;
    }

    public int getSeconds(){
        return this.seconds;
    }

    public String getAmPm(){
        return this.amPm;
    }

    public String toString(){
        return this.hours + ":" + this.minutes + ":" + this.seconds + " " + this.amPm;
    }
}
