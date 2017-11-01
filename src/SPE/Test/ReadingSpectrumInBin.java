package SPE.Test;

import SPE.Classes.Date;
import SPE.Classes.SpeMetaData;
import SPE.Classes.Time;
import SPE.Interfaces.Show;
import SPE.lmplementations.PrintSpectrum;
import SPE.Read.ReadBinarySpectrum;
import SPE.Read.ReadSpectrumFile;
import SPE.Read.SpectrumReader;
import SPE.Spectrum;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Renat Razumilov on 19/09/17.
 */
public class ReadingSpectrumInBin {
    private static final String PATH = "C:\\JAVA\\SpectrumAnalisator\\src\\SPE\\Co60spe\\Co-60 2.spe";
    private  Spectrum bSpectr = new Spectrum();
    private  ReadSpectrumFile reader = new SpectrumReader(PATH);


    @Before
    public void setReader(){
        if (reader.isSpectrumSupported()) {
            bSpectr = reader.read();
        }else {
            System.out.println("Not supported extension of the spectrum file");
            System.exit(0);
        }
    }

    @After
    public void showSpectrum(){
        Show spe = new PrintSpectrum();
        spe.showSpectrum(bSpectr);
    }

    @Test
    public void testGetChannelCounts(){
        assertEquals(bSpectr.getChannelCounts(15197),13);
        assertEquals("We should get 5948 counts in 7126 channel",
                bSpectr.getChannelCounts(7126), 5948);
        assertEquals("We should get 5011 counts in 8091 channel",
                bSpectr.getChannelCounts(8091), 5011);
    }

    @Test
    public void testGetDate(){
        Date date = new Date(30,3,2016);

        assertEquals(bSpectr.getHead().getDate().getDay(),30);
        assertEquals(bSpectr.getHead().getDate().getMonth(),3);
        assertEquals(bSpectr.getHead().getDate().getYear(),2016);
        assertEquals(bSpectr.getHead().getDate(),date);
    }

    @Test
    public void testSpectrumHead(){
        Date date = new Date(30, 3, 2016);
        Time time = new Time(11, 7, 14, "AM");
        SpeMetaData meta = new SpeMetaData(date, time,
                1345.57f,
                1403.75f,
                16384);

        assertEquals("We should get Head of Spectrum",
                    bSpectr.getHead(), meta);
    }
}
