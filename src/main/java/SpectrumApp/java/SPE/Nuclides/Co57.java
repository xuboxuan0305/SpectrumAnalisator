package SpectrumApp.java.SPE.Nuclides;

import SpectrumApp.java.SPE.Classes.Nuclide;

import java.util.ArrayList;
import java.util.List;

public class Co57 extends Nuclide {
    @Override
    public String setName() {
        return "Co-57";
    }

    @Override
    public List<Double> setEnergies() {
        List<Double> energies = new ArrayList<>();
        energies.add(121.36);
        energies.add(136.05);
        return energies;
    }
}
