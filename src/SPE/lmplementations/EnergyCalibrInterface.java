package SPE.lmplementations;

import SPE.Classes.EnergyCalibration;
import SPE.Interfaces.Calibr;

import java.util.ArrayList;
import java.util.List;

public class EnergyCalibrInterface implements Calibr { // Exception - arrays should be the same size
    public static Double sumArray(List<Double> array) {
        Double sum = 0D;
        for (Double i : array) {
            sum += i;
        }
        return sum;
    }

    public static List<Double> multArrays(List<Double> channels, List<Double> energy) {
        List<Double> mult = new ArrayList<>();
        for (int i = 0; i < channels.size(); i++) {
            mult.add(channels.get(i) * energy.get(i));
        }
        return mult;
    }

    public static Double sumSquare(List<Double> array) {
        Double sum = 0D;
        for (Double i : array) {
            sum += i * i;
        }
        return sum;
    }

    @Override
    public EnergyCalibration calibrLessSquareMethod(List<Double> channels, List<Double> energy) {
        double a, b;

        Double sumChan = sumArray(channels);
        Double sumCounts = sumArray(energy);
        Double sumMult = sumArray(multArrays(channels, energy));
        Double sumSquare = sumSquare(channels);
        int n = channels.size();

        a = (n*sumMult - sumChan*sumCounts)/((n*sumSquare)-(sumChan*sumChan));
        b = (sumCounts - a*sumChan)/n;

        EnergyCalibration calibr = new EnergyCalibration();
        calibr.setCalibrCoeff(a,b);

        return calibr;
    }
}
