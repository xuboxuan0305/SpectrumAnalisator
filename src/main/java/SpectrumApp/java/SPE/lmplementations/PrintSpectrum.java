package SpectrumApp.java.SPE.lmplementations;

import SpectrumApp.java.SPE.Classes.ChannelsValues;
import SpectrumApp.java.SPE.Interfaces.Show;
import SpectrumApp.java.SPE.Spectrum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class PrintSpectrum implements Show {
    private int fullScale;
    private List<Integer> miniSpectrum;
    private boolean printSpectrumInConsole = false;

    public PrintSpectrum() {
    }

    public PrintSpectrum(int fullScale) {
        this.fullScale = fullScale;
    }

    public void setPrintSpectrumInConsole(boolean printSpectrumInConsole) {
        this.printSpectrumInConsole = printSpectrumInConsole;
    }

    @Override
    public List<String[]> showSpectrum(Spectrum spectrum) {
        List<Integer> sChannelsCounts = Arrays.stream(spectrum.getSpe())
                .map(ChannelsValues::getCounts)
                .collect(toList());
        int max = sChannelsCounts.stream()
                .max(Integer::compare) //.max((i1,i2)-> Integer.compare(i1,i2)
                .get();
        List<Integer> mList = minimize(sChannelsCounts, max);
        max = mList.stream()
                .max(Integer::compare)
                .get();
        if (printSpectrumInConsole) { // print in console if set
            return printSpectrum(max, mList);
        }else {
//          just to ensure we dont get any null pointer error
            List<String[]> dummyList = new ArrayList<>();
            String[] dummStringArr = new String[1];
            dummStringArr[0] = "Flag to print Spectrum in Console is not set";
            dummyList.add(dummStringArr);
            return dummyList;
        }
    }

    private List<Integer> minimize(List<Integer> spe, int max) {
        List<Integer> mSpe = new ArrayList<>();
        int j = 0;
        int lMax = 0;
        float tmp;

        for (Integer i : spe) {
            if (i > lMax) {
                lMax = i;
            }
            j++;
            if (j == 100) {
                tmp = (lMax / (float) max) * fullScale;
                lMax = (int) tmp;
                mSpe.add(lMax);
                j = 0;
                lMax = 0;
            }

        }
        this.miniSpectrum = mSpe;
        return mSpe;
    }

    private List<String[]> printSpectrum(int max, List<Integer> spectr) {
        //String[] aPrint = new String[spectr.size()];
        List<String[]> listSpectrum = new ArrayList<>();
        for (int i = max; i > 0; i--) {
            int k = 0;
            String[] aPrint = new String[spectr.size()];
            for (Integer j : spectr) {

                if (j >= i) {
                    aPrint[k] = "*";
                } else {
                    aPrint[k] = "_";
                }

                System.out.print(aPrint[k]);
                k++;
            }
            listSpectrum.add(aPrint);
            System.out.println();
        }
        return listSpectrum;
    }

    public void setFullScale(int fullScale) {
        this.fullScale = fullScale;
    }

    public List<Integer> getMiniSpectrum() {
        return this.miniSpectrum;
    }
}
