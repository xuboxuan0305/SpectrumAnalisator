package Test;

import SpectrumApp.java.SPE.Interfaces.Calculate;
import SpectrumApp.java.SPE.lmplementations.CalculateBackground;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class testCalculate {
    private List<Integer> integerList;
    private Calculate calculate;

    @Before
    public void init(){
        if (integerList != null) {
            integerList.clear();
        }else {
            integerList = new ArrayList<>();
        }

        for (int i = 0; i <= 11; i++ ){
          integerList.add(i);
        }
    }

    @Test
    public void testPositive(){
        calculate = new CalculateBackground();
        double average = calculate.calculateAverage(this.integerList);
        TestCase.assertEquals(average,5.5D);
    }

    @Test
    public void testNegative(){
        calculate = new CalculateBackground();
        double average = calculate.calculateAverage(this.integerList);
        TestCase.assertNotSame(average,6D);
    }
}
