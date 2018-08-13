package SPE.Read;

import SPE.Spectrum;

public interface ReadSpectrumFile {
    Spectrum read();
    boolean isSpectrumSupported();
}
