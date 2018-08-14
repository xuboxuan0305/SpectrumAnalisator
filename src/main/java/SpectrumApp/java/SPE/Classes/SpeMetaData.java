package SpectrumApp.java.SPE.Classes;

/**
 * Created by Administrator on 7/20/17.
 */
public class SpeMetaData {
    private Date date;
    private Time time;
    private float timeLive;
    private float timeReal;
    private int speChannels;

    public SpeMetaData(Date date,
                       Time time,
                       float timeLive,
                       float timeReal,
                       int speChannels){
        this.date = date;
        this.time = time;
        this.timeLive = timeLive;
        this.timeReal = timeReal;
        this.speChannels = speChannels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpeMetaData that = (SpeMetaData) o;

        if (Float.compare(that.timeLive, timeLive) != 0) return false;
        if (Float.compare(that.timeReal, timeReal) != 0) return false;
        if (speChannels != that.speChannels) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return time != null ? time.equals(that.time) : that.time == null;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (timeLive != +0.0f ? Float.floatToIntBits(timeLive) : 0);
        result = 31 * result + (timeReal != +0.0f ? Float.floatToIntBits(timeReal) : 0);
        result = 31 * result + speChannels;
        return result;
    }

    public Date getDate(){
        return this.date;
    }

    public Time getTime(){
        return this.time;
    }

    public float getTimeLive(){
        return this.timeLive;
    }

    public float getTimeReal(){
        return this.timeReal;
    }
    public int getSpeChannels(){
        return this.speChannels;
    }

    public String toString() {
        return "Date: " + this.date
                + "\n" + "Time: " + this.time
                + "\n" + "TLive: " + this.timeLive
                + "\n" + "TReal: " + this.timeReal
                + "\n" + "Spectrum channels: " + this.speChannels;
    }
}
