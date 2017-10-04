package SPE.Classes;

import SPE.SpectrumException;

import java.util.List;

public abstract class Nuclide {
    private String name;
    private List<Double> energies;

    public  Nuclide(){
        this.name = setName();
        this.energies = setEnergies();
    }

    public  List<Double> getEnergies(){
        return this.energies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nuclide nuclide = (Nuclide) o;
        if (!nuclide.getName().equals(nuclide.getName())) return false;
        return getEnergies().equals(nuclide.getEnergies());
    }

    public void addEnergy(Double energy){
        this.energies.add(energy);
    }

    public Double getEnergy(int i){
        if (i > this.energies.size()){
            throw new SpectrumException("Index out of " + this.name + " energies list size, \n" +
                    "List size is " + this.energies.size());
        }else {
            return this.energies.get(i - 1);
        }
    }

    public String getName() {
        return name;
    }

    public void showEnergies(){
        System.out.println("Nuclide: " + this.name);
        int j = 1;
        for (double i:this.energies) {
            System.out.println(j + ": " + i + ";");
            j++;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getSize(){
        return this.energies.size();
    }

    public abstract List<Double> setEnergies();
    public abstract String setName();
}
