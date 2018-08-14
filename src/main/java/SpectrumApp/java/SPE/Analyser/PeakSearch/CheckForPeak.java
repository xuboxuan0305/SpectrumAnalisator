package SpectrumApp.java.SPE.Analyser.PeakSearch;

public class CheckForPeak {
    public final int DEFAULT_AMPLITUDE_THRESH = 50;
    public final int DEFAULT_SLOPE_THRESH = 50;
    public final int DEFAULT_LLD_THRESH = 100;
    public final int DEFAULT_HLD_THRESH = 16000;
    private int amplitudeThresh;
    private int slopeThresh;
    private int lowLevelDiscriminator;
    private int highLevelDiscriminator;


    public CheckForPeak() {
        this.amplitudeThresh = DEFAULT_AMPLITUDE_THRESH;
        this.slopeThresh = DEFAULT_SLOPE_THRESH;
        this.lowLevelDiscriminator = DEFAULT_LLD_THRESH;
        this.highLevelDiscriminator = DEFAULT_HLD_THRESH;
    }

    public int getAmplitudeThresh() {
        return amplitudeThresh;
    }

    public void setAmplitudeThresh(int amplitudeThresh) {
        this.amplitudeThresh = amplitudeThresh;
    }

    public int getSlopeThresh() {
        return slopeThresh;
    }

    public void setSlopeThresh(int slopeThresh) {
        this.slopeThresh = slopeThresh;
    }

    public int getLowLevelDiscriminator() {
        return lowLevelDiscriminator;
    }

    public void setLowLevelDiscriminator(int lowLevelDiscriminator) {
        this.lowLevelDiscriminator = lowLevelDiscriminator;
    }

    public int getHighLevelDiscriminator() {
        return highLevelDiscriminator;
    }

    public void setHighLevelDiscriminator(int highLevelDiscriminator) {
        this.highLevelDiscriminator = highLevelDiscriminator;
    }

    public boolean foundPeak(int derivA, int derivB, int signalB){
        boolean[] conditions = {false,false,false};
        boolean foundPeak;
        conditions[0] = isSignalHigherThanAmplitudeThresh(signalB);
        conditions[1] = isDerivativeHigherThanSlopeThresh(derivA,derivB);
        conditions[2] = isDownZeroCrossing(derivA,derivB);
        foundPeak = isConditionsMet(conditions);
        return foundPeak;
    }

    public boolean isSignalHigherThanAmplitudeThresh(int signal){
        return signal > this.amplitudeThresh;
    }
    public boolean isDerivativeHigherThanSlopeThresh(int a, int b){
        return a - b > this.slopeThresh;
    }
    public boolean isDownZeroCrossing(int a, int b){
        return (b < 0 && a > 0);
    }
    private boolean isConditionsMet(boolean[] conditions){
        for(boolean b : conditions){ if(!b){ return false;}}
        return true;
    }
}
