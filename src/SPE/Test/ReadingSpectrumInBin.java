package SPE.Test;

import SPE.Read.ReadBinarySpectrum;
import SPE.Spectrum;
import org.junit.Test;

/**
 * Created by Renat Razumilov on 19/09/17.
 */
public class ReadingSpectrumInBin {
    private static final String PATH = "C:\\JAVA\\Projects\\src\\SPE\\Co60spe\\Co60_10cm.spe";
    private static final Spectrum bSpectr = new Spectrum(PATH);

    @Test
    public void test(){
     //bSpectr.readHead();
     //bSpectr.readChannels();
        bSpectr.printChannels();
    }

}
