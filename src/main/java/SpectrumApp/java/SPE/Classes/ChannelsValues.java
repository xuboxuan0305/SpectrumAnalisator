package SpectrumApp.java.SPE.Classes;

/**
 * Created by Administrator on 7/21/17.
 */
public class ChannelsValues {
    private int channel;
    private int counts;

    public ChannelsValues(int channel,
                          int counts){
        this.channel = channel;
        this.counts = counts;
    }

    public String toString() {
        return "Channel: " + this.channel
                + " Counts: " + this.counts;
    }

    public int getCounts(){
        return this.counts;
    }
}
