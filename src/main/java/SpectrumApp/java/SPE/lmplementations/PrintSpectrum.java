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
    private int fullScale = 30;
    private List<Integer> miniSpectrum;

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
        List<String[]> list = printSpectrum(max, mList);
        return list;
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
