package SpectrumApp.java.SPE.Analyser.PeakSearch;

import SpectrumApp.java.SPE.Classes.ChannelsValues;
import SpectrumApp.java.SPE.Spectrum;

import java.util.ArrayList;
import java.util.List;

public class PeakSearchDomain implements FindPeaks {
    public final int SHIFT = 5;
    private CheckForPeak checkForPeak;
    private Derivative derivative;
    private Spectrum spectrum;

    public PeakSearchDomain(Spectrum spectrum) {
        this.checkForPeak = new CheckForPeak();
        this.derivative = new Derivative();
        this.spectrum = spectrum;
    }

    @Override
    public List<Integer> execute() {
        int[] signal = getSignal();
        int[] derivative = getDerivative(signal);
        return getPeaks(derivative,signal);
    }

    @Override
    public void setSearchParameters(int At, int St, int lld, int hld) {
        this.checkForPeak.setAmplitudeThresh(At);
        this.checkForPeak.setSlopeThresh(St);
        this.checkForPeak.setLowLevelDiscriminator(lld);
        this.checkForPeak.setHighLevelDiscriminator(hld);
    }

    private List<Integer> getPeaks(int[] deriv, int[] sig){
        List<Integer> peaksChannels = new ArrayList<>();
        for (int i = this.checkForPeak.getLowLevelDiscriminator(); i < this.checkForPeak.getHighLevelDiscriminator(); i++){
         if (this.checkForPeak.foundPeak(deriv[i],deriv[i+1],sig[i+1])){
             peaksChannels.add(i+1);
         }
        }
        return peaksChannels;
    }

    private int[] getDerivative(int[] signal) {
        int[] deriv = new int[signal.length];
        int lld = this.checkForPeak.getLowLevelDiscriminator();
        int hld = this.checkForPeak.getHighLevelDiscriminator();
        for (int i = lld; i < hld; i++) {
            int[] input = getInput(signal,i);
            this.derivative.setInput(input);
            deriv[i] = this.derivative.getDerivative();
        }
        return deriv;
    }

    private int[] getInput(int[] signal, int i) {
        int[] tempReturn = new int[11];
        int hld = 16384 - SHIFT;
        if (i <= SHIFT) {
            switch (i) {
                case 0:
                    tempReturn = new int[]{0, 0, 0, 0, 0, signal[i], signal[i + 1], signal[i + 2], signal[i + 3], signal[i + 4], signal[i + 5]};
                    break;
                case 1:
                    tempReturn = new int[]{0, 0, 0, 0, signal[i - 1], signal[i], signal[i + 1], signal[i + 2], signal[i + 3], signal[i + 4], signal[i + 5]};
                    break;
                case 2:
                    tempReturn = new int[]{0, 0, 0, signal[i - 2], signal[i - 1], signal[i], signal[i + 1], signal[i + 2], signal[i + 3], signal[i + 4], signal[i + 5]};
                    break;
                case 3:
                    tempReturn = new int[]{0, 0, signal[i - 3], signal[i - 2], signal[i - 1], signal[i], signal[i + 1], signal[i + 2], signal[i + 3], signal[i + 4], signal[i + 5]};
                    break;
                case 4:
                    tempReturn = new int[]{0, signal[i - 4], signal[i - 3], signal[i - 2], signal[i - 1], signal[i], signal[i + 1], signal[i + 2], signal[i + 3], signal[i + 4], signal[i + 5]};
                    break;
                case 5:
                    tempReturn = new int[]{signal[i - 5], signal[i - 4], signal[i - 3], signal[i - 2], signal[i - 1], signal[i], signal[i + 1], signal[i + 2], signal[i + 3], signal[i + 4], signal[i + 5]};
                    break;
            }
        } else if (i < hld){
            tempReturn = new int[]{signal[i - 5], signal[i - 4], signal[i - 3], signal[i - 2], signal[i - 1], signal[i], signal[i + 1], signal[i + 2], signal[i + 3], signal[i + 4], signal[i + 5]};
        } else if (i >= hld) {
            tempReturn = new int[]{signal[i - 5], signal[i - 4], signal[i - 3], signal[i - 2], signal[i - 1], signal[i], 0, 0, 0, 0, 0};
        }

        return tempReturn;
    }

    private int[] getSignal() {
        ChannelsValues[] channelsValues = this.spectrum.getSpe();
        int size = channelsValues.length;
        int[] signal = new int[size];
        for (int i = 0; i < size; i++) {
            signal[i] = channelsValues[i].getCounts();
        }
        return signal;
    }
}
