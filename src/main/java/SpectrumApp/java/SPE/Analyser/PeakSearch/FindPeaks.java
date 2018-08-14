package SpectrumApp.java.SPE.Analyser.PeakSearch;

import java.util.List;

public interface FindPeaks {
    List<Integer> execute();
    void setSearchParameters(int At,int St, int lld, int hld);
}

