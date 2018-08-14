package SpectrumApp.java.SPE.Read;

import SpectrumApp.java.SPE.Spectrum;

public interface ReadSpectrumFile {
    Spectrum read();
    boolean isSpectrumSupported();
}
