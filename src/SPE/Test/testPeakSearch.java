package SPE.Test;

import SPE.Analyser.PeakSearch.CheckForPeak;
import SPE.Analyser.PeakSearch.Derivative;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class testPeakSearch {
    private Derivative x;

    @Test
    public void testSmoothDerivative(){
        int[] testArray = {1,1,1,1,10,1,1,1,1,1,1};
        x = new Derivative(testArray);
        int xDerv = x.getDerivative();
        assertEquals(xDerv,-9);
    }

    @Test
    public void testDerivativeMatrixNotMatchException(){
        boolean thrown = false;
        int[] testArray = {1,1,1,1,1,1,1,1,1,1};
        try {
            x = new Derivative(testArray);
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
}
