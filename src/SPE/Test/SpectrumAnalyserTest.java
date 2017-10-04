package SPE.Test;


import SPE.Classes.*;
import SPE.Interfaces.Show;
import SPE.Nuclides.Co57;
import SPE.lmplementations.EnergyCalibrInterface;
import SPE.Interfaces.Calibr;
import SPE.Nuclides.Co60;
import SPE.Spectrum;
import SPE.SpectrumException;
import SPE.lmplementations.PrintSpectrum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static SPE.lmplementations.EnergyCalibrInterface.multArrays;
import static SPE.lmplementations.EnergyCalibrInterface.sumArray;
import static SPE.lmplementations.EnergyCalibrInterface.sumSquare;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.*;


/**
 * Created by Administrator on 7/20/17.
 */
public class SpectrumAnalyserTest {
    private static final String PATH = "C:\\JAVA\\Projects\\src\\SPE\\Co60spe\\Co-60 2.spe";
    private static final Spectrum spectrum = new Spectrum(PATH);

    @Test
    public void testChannelValue() {
        if (spectrum.getValidExt().getValidation()) {
            assertEquals("We should get 5948 counts in 7126 channel",
                    spectrum.getChannelCounts(7126),
                    5948);
            assertEquals(spectrum.getChannelCounts(8091), 5011);
        }else{
            System.out.println(spectrum.getValidExt().getError());
        }
    }

    @Test
    public void testHead() {
        if (spectrum.getValidExt().getValidation()) {
            assertEquals("We should get the day of the Date",
                    spectrum.getHead().getDate().getDay(),
                    30);
        }else{
            System.out.println(spectrum.getValidExt().getError());
        }
    }

    @Test
    public void testHead1() {
        Date date = new Date(30, 3, 2016);
        if (spectrum.getValidExt().getValidation()) {
            assertEquals("We should get the Date",
                    spectrum.getHead().getDate(), date);
        }else{
            System.out.println(spectrum.getValidExt().getError());
        }
    }

    @Test
    public void testHead2() {
        Date date = new Date(30, 3, 2016);
        Time time = new Time(11, 7, 14, "AM");
        SpeMetaData meta = new SpeMetaData(date, time, 1345.57f, 1403.75f, 16384);
        if (spectrum.getValidExt().getValidation()) {
            assertEquals("We should get Head of Spectrum",
                    spectrum.getHead(), meta);
        }else{
            System.out.println(spectrum.getValidExt().getError());
        }
    }

    @Test
    public void testCalibr() {
        List<Double> arrayA = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
            arrayA.add((double) i);
        }

        assertEquals(arrayA.size(), 3);

        double sum = sumArray(arrayA);
        assertEquals(sum, 6.0);

        List<Double> sumArray = multArrays(arrayA, arrayA);
        sum = sumArray(sumArray);
        assertEquals(sum, 14.0);

        sum = sumSquare(arrayA);
        assertEquals(sum, 14.0);

        EnergyCalibration calibr;
        Calibr energyCalibr = new EnergyCalibrInterface();
        calibr = energyCalibr.calibrLessSquareMethod(arrayA, arrayA);

        assertEquals(calibr.getA(), 1.0);
        assertEquals(calibr.getB(), 0.0);
        assertEquals(calibr.getEnergy(4), 4.0);

    }

    @Test
    public void testNuclide() {
        boolean exception = false;

        Nuclide co60 = new Co60();
        assertEquals(co60.getSize(), 2);

        co60.addEnergy(1460.25);
        assertEquals(co60.getSize(), 3);

        //co60.showEnergies();

        assertEquals(co60.getEnergy(1), 1173.237);
        assertEquals(co60.getEnergy(2), 1332.502);
        assertEquals(co60.getEnergy(3), 1460.25);

        try {
            co60.getEnergy(4);
        } catch (SpectrumException e) {
            System.out.println(e.getMessage());
            exception = true;
        }
        assertEquals(exception, true);
    }

    @Test
    public void testCalibrProcedure() {

        Nuclide co60 = new Co60();
        //System.out.println(co60.getName());

        List<Double> aEnergies;
        List<Double> aChannels = new ArrayList<>();

        aEnergies = co60.getEnergies();
        aChannels.add(7126D);
        aChannels.add(8091D);

        assertEquals(aEnergies.size(), 2);
        assertEquals(aChannels.size(), 2);

        EnergyCalibration calibr;
        Calibr energyCalibr = new EnergyCalibrInterface();
        calibr = energyCalibr.calibrLessSquareMethod(aChannels, aEnergies);

        System.out.printf("%10.3f", calibr.getEnergy(7126));
        System.out.println();
        System.out.printf("%14.3f", calibr.getEnergy(8091));
        System.out.println();

        assertEquals(calibr.getEnergy(7126), 1173.2369999999992);


    }

    @Test
    public void testCalibrProcedure2() {
        Nuclide co60 = new Co60();
        List<Double> aEnergies;
        List<Double> aChannels = new ArrayList<>();

        aEnergies = co60.getEnergies();
        aChannels.add(7126D);
        aChannels.add(8091D);

        Calibr energyCalibr = new EnergyCalibrInterface();
        spectrum.setEnergyCalibration(energyCalibr.calibrLessSquareMethod(aChannels, aEnergies));

        double energy = spectrum.getEnergy(7126);
        assertEquals(energy, 1173.2369999999992);

        energy = spectrum.getEnergy(8091);
        assertEquals(energy, 1332.5020000000006);
    }

    @Test
    public void testCalibrProcedure3() {
        List<Nuclide> nLib = new ArrayList<>();
        nLib.add(new Co60());
        nLib.add(new Co57());
        spectrum.setNuclideLibrary(nLib);
        //spectrum.printLibrary();
        assertEquals(spectrum.nuclideLibSize(), 2);

        List<Double> aChannels = new ArrayList<>();
        List<Double> aEnergies;

        aEnergies = spectrum.getNuclideEnergies(1);
        aChannels.add(7126D);
        aChannels.add(8091D);

        Calibr energyCalibr = new EnergyCalibrInterface();
        spectrum.setEnergyCalibration(energyCalibr.calibrLessSquareMethod(aChannels, aEnergies));

        double energy = spectrum.getEnergy(7126);
        assertEquals(energy, 1173.2369999999992);

        energy = spectrum.getEnergy(8091);
        assertEquals(energy, 1332.5020000000006);
    }

    @Test
    public void testCalibrProcedure4() {
        List<Nuclide> nLib = new ArrayList<>();
        nLib.add(new Co60());
        nLib.add(new Co57());
        spectrum.setNuclideLibrary(nLib);
        spectrum.printLibrary();
        assertEquals(spectrum.nuclideLibSize(), 2);

        List<Double> aChannels = new ArrayList<>();
        aChannels.add(7126D);
        aChannels.add(8091D);

        Calibr energyCalibr = new EnergyCalibrInterface();
        spectrum.setEnergyCalibration
                (energyCalibr.calibrLessSquareMethod
                        (aChannels, spectrum.getNuclideEnergies("Co-60")));

        double energy = spectrum.getEnergy(7126);
        assertEquals(energy, 1173.2369999999992);

        energy = spectrum.getEnergy(8091);
        assertEquals(energy, 1332.5020000000006);
    }

    @Test
    public void testCalibrProcedureException() {
        boolean thrown = false;

        try {
            double energy = spectrum.getEnergy(7126);
            assertEquals(energy, 1173.2369999999992);
        } catch (SpectrumException e) {
            thrown = true;
            String str = e.getMessage();
            System.out.println(str);
        }
        assertEquals(thrown, true);
    }

    @Test
    public void testPrintSpectrum() {
        if (spectrum.getValidExt().getValidation()) {
            Show spe = new PrintSpectrum();
            spe.showSpectrum(spectrum);
        }else{
            System.out.println(spectrum.getValidExt().getError());
        }
    }

    @Test
    public void testNuclideLib() {
        spectrum.addNuclide(new Co60());
        assertEquals(spectrum.nuclideLibSize(), 1);
        List<Nuclide> nLib = new ArrayList<>();
        nLib.add(new Co60());
        nLib.add(new Co57());
        spectrum.setNuclideLibrary(nLib);
        //spectrum.printLibrary();
        assertEquals(spectrum.nuclideLibSize(), 2);
    }

    @Test
    public void testMocks() {
        Spectrum spectrumMock = mock(Spectrum.class);
        when(spectrumMock.nuclideLibSize()).thenReturn(5);
        assertEquals(spectrumMock.nuclideLibSize(), 5);
        spectrumMock.addNuclide(new Co60());
        verify(spectrumMock, times(1)).addNuclide(new Co60());
        verify(spectrumMock, times(1)).nuclideLibSize();

    }
}
