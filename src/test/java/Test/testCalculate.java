package Test;

import SpectrumApp.java.SPE.Interfaces.Calculate;
import SpectrumApp.java.SPE.Read.SpectrumReader;
import SpectrumApp.java.SPE.Spectrum;
import SpectrumApp.java.SPE.lmplementations.CalculateBackground;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class testCalculate {
    private List<Integer> integerList;
    private Calculate calculate;
    private static final String PATH =
            "C:\\Users\\renatrazumilov\\IdeaProjects\\SpectrumAnalisator\\src\\main\\java\\SpectrumApp\\java\\SPE\\Co60spe\\Co-60 2.spe";

    private final SpectrumReader reader = new SpectrumReader(PATH);
    private Spectrum spectrum;


    @Before
    public void init(){

        //integerList
        if (integerList != null) {
            integerList.clear();
        }else {
            integerList = new ArrayList<>();
        }

        //spectrum
        this.spectrum = reader.read();

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

    @Test
    public void testGetChannels(){
        List<Integer> list = spectrum.getChannels(100,100);
        calculate = new CalculateBackground();
        double average = calculate.calculateAverage(list);
        TestCase.assertEquals(average,274.0D);
    }

    @Test
    public void testGetChannelsNegative(){
        List<Integer> list = spectrum.getChannels(100,-1);
        calculate = new CalculateBackground();
        double average = calculate.calculateAverage(list);
        TestCase.assertEquals(average,0.0);
    }

    @Test
    public void testGetChannelsNegative2(){
        List<Integer> list = spectrum.getChannels(-1,10);
        calculate = new CalculateBackground();
        double average = calculate.calculateAverage(list);
        TestCase.assertEquals(average,0.0);
    }
}
