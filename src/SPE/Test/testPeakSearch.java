package SPE.Test;

import SPE.Analyser.PeakSearch.Derivative;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
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
}
