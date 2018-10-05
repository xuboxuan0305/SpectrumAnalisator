package Test;


import SpectrumApp.java.SPE.Classes.EnergyCalibration;
import SpectrumApp.java.SPE.Classes.Nuclide;
import SpectrumApp.java.SPE.Interfaces.Show;
import SpectrumApp.java.SPE.Nuclides.Co57;
import SpectrumApp.java.SPE.Read.SpectrumReader;
import SpectrumApp.java.SPE.lmplementations.EnergyCalibrInterface;
import SpectrumApp.java.SPE.Interfaces.Calibr;
import SpectrumApp.java.SPE.Nuclides.Co60;
import SpectrumApp.java.SPE.Spectrum;
import SpectrumApp.java.SPE.Exceptions.SpectrumException;
import SpectrumApp.java.SPE.lmplementations.PrintSpectrum;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static SpectrumApp.java.SPE.lmplementations.EnergyCalibrInterface.multArrays;
import static SpectrumApp.java.SPE.lmplementations.EnergyCalibrInterface.sumArray;
import static SpectrumApp.java.SPE.lmplementations.EnergyCalibrInterface.sumSquare;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.*;


/**
 * Created by Administrator on 7/20/17.
 */
public class SpectrumAnalyserTest {
    private static final String PATH =
            "C:\\Users\\renatrazumilov\\IdeaProjects\\SpectrumAnalisator\\src\\main\\java\\SpectrumApp\\java\\SPE\\Co60spe\\Co-60 2.spe";

    private final SpectrumReader reader = new SpectrumReader(PATH);
    private Spectrum spectrum = new Spectrum();


    @Before
    public void readSpectrum(){
        this.spectrum = this.reader.read();
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
            Show spe = new PrintSpectrum(50);
            spe.showSpectrum(spectrum);

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
