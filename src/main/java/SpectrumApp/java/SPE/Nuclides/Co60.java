package SpectrumApp.java.SPE.Nuclides;

import SpectrumApp.java.SPE.Classes.Nuclide;

import java.util.ArrayList;
import java.util.List;

public class Co60 extends Nuclide {

    @Override
    public List<Double> setEnergies() {
        List<Double> energies = new ArrayList<>();
        energies.add(1173.237);
        energies.add(1332.502);
        return energies;
    }

    @Override
    public String setName() {
        return "Co-60";
    }
}
