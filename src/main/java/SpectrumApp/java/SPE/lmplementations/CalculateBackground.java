package SpectrumApp.java.SPE.lmplementations;

import SpectrumApp.java.SPE.Interfaces.Calculate;

import java.util.List;

public class CalculateBackground implements Calculate {
    @Override
    public double calculateAverage(List<Integer> integerList) {
        return integerList.stream()
                .mapToDouble(Integer::intValue).average().getAsDouble();
    }
}
