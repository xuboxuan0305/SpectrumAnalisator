package Test;

import SPE.Analyser.PeakSearch.CheckForPeak;
import SPE.Analyser.PeakSearch.Derivative;
import SPE.Analyser.PeakSearch.PeakSearchDomain;
import SPE.Interfaces.Calibr;
import SPE.Nuclides.Co60;
import SPE.Read.SpectrumReader;
import SPE.Spectrum;
import SPE.lmplementations.EnergyCalibrInterface;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class testPeakSearch {
    private Derivative x;
    private Spectrum spectrum = new Spectrum();
    private static final String PATH =
            "C:\\Java\\SpectrumAnalisator\\src\\main\\java\\SPE\\Co60spe\\Co-60 2.spe";
    private final SpectrumReader reader = new SpectrumReader(PATH);


    @Test
    public void testSmoothDerivative(){
        int[] testArray = {1,1,1,1,10,1,1,1,1,1,1};
        x = new Derivative();
        x.setInput(testArray);
        int xDerv = x.getDerivative();
        assertEquals(xDerv,-9);
    }

    @Test
    public void testDerivativeMatrixNotMatchException(){
        boolean thrown = false;
        int[] testArray = {1,1,1,1,1,1,1,1,1,1};
        try {
            x = new Derivative();
            x.setInput(testArray);
        }catch (ArrayIndexOutOfBoundsException e){
            thrown = true;
        }
        assertTrue("Exception should be thrown",thrown);
    }

    @Test
    public void testConditionAllFalse(){
        int derivA = 50;
        int derivB = 60;
        int signalB = 50;

        CheckForPeak isSignal = new CheckForPeak();

        boolean condition1 = isSignal.isSignalHigherThanAmplitudeThresh(signalB);
        boolean condition2 = isSignal.isDerivativeHigherThanSlopeThresh(derivA,derivB);
        boolean condition3 = isSignal.isDownZeroCrossing(derivA,derivB);

        assertFalse(condition1);
        assertFalse(condition2);
        assertFalse(condition3);

        boolean foundPeak = isSignal.foundPeak(derivA,derivB,signalB);
        assertFalse("all conditions false",foundPeak);
    }

    @Test
    public void testConditionAmplitudeThreshIsTrue(){
        int derivA = 50;
        int derivB = 60;
        int signalB = 60;

        CheckForPeak isSignal = new CheckForPeak();

        boolean condition1 = isSignal.isSignalHigherThanAmplitudeThresh(signalB);
        boolean condition2 = isSignal.isDerivativeHigherThanSlopeThresh(derivA,derivB);
        boolean condition3 = isSignal.isDownZeroCrossing(derivA,derivB);

        assertTrue(condition1);
        assertFalse(condition2);
        assertFalse(condition3);

        boolean foundPeak = isSignal.foundPeak(derivA,derivB,signalB);
        assertFalse("two conditions false, aThresh - true",foundPeak);
    }

    @Test
    public void testConditionSlopeThreshIsTrue(){
        int derivA = 120;
        int derivB = 60;
        int signalB = 60;

        CheckForPeak isSignal = new CheckForPeak();


        boolean condition1 = isSignal.isSignalHigherThanAmplitudeThresh(signalB);
        boolean condition2 = isSignal.isDerivativeHigherThanSlopeThresh(derivA,derivB);
        boolean condition3 = isSignal.isDownZeroCrossing(derivA,derivB);

        assertTrue(condition1);
        assertTrue(condition2);
        assertFalse(condition3);

        boolean foundPeak = isSignal.foundPeak(derivA,derivB,signalB);
        assertFalse("one condition false," +
                "aThresh - true," +
                "sThresh - true",foundPeak);
    }

    @Test
    public void testConditionDownCrossZero(){
        int derivA = 120;
        int derivB = -1;
        int signalB = 60;

        CheckForPeak isSignal = new CheckForPeak();

        boolean condition1 = isSignal.isSignalHigherThanAmplitudeThresh(signalB);
        boolean condition2 = isSignal.isDerivativeHigherThanSlopeThresh(derivA,derivB);
        boolean condition3 = isSignal.isDownZeroCrossing(derivA,derivB);

        assertTrue(condition1);
        assertTrue(condition2);
        assertTrue(condition3);

        boolean foundPeak = isSignal.foundPeak(derivA,derivB,signalB);
        assertTrue("all conditions true",foundPeak);
    }

    @Test
    public void testCheckForPeakIsTrue(){
        int derivA = 120;
        int derivB = -1;
        int signalB = 60;

        CheckForPeak isSignal = new CheckForPeak();
        boolean foundPeak = isSignal.foundPeak(derivA,derivB,signalB);
        assertTrue("all conditions true",foundPeak);
    }

    @Test
    public void testCheckForPeakIsFalse(){
        int derivA = 50;
        int derivB = 50;
        int signalB = 50;

        CheckForPeak isSignal = new CheckForPeak();
        boolean foundPeak = isSignal.foundPeak(derivA,derivB,signalB);
        assertFalse("all conditions true",foundPeak);
    }

    @Test
    public void testPeakSearchProcedureWithCalibr(){
        this.spectrum = reader.read();
        PeakSearchDomain peakSearchDomain = new PeakSearchDomain(this.spectrum);
        List<Integer> peaks = peakSearchDomain.execute();
        assertEquals(peaks.size(),845);

        peakSearchDomain.setSearchParameters(1000,100,100,16000);
        peaks = peakSearchDomain.execute();
        assertEquals(peaks.size(),2);
        int result = peaks.get(0);
        assertEquals(result,7126);
        result = peaks.get(1);
        assertEquals(result,8091);

        Calibr energyCalibr = new EnergyCalibrInterface();
        List<Double> channels = convertToDoubleList(peaks);
        this.spectrum.addNuclide(new Co60());
        this.spectrum.setEnergyCalibration
                (energyCalibr.calibrLessSquareMethod
                        (channels,this.spectrum.getNuclideEnergies
                                ("Co-60")));
        double energy = spectrum.getEnergy(peaks.get(0));
        assertEquals(energy, 1173.2369999999992);

        energy = spectrum.getEnergy(peaks.get(1));
        assertEquals(energy, 1332.5020000000006);
    }

    private List<Double> convertToDoubleList(List<Integer> integerList){
        List<Double> channels = new ArrayList<>();
        for (Integer i : integerList){
            channels.add(Double.valueOf(i));
        }
        return channels;
    }
}
