package SpectrumApp.java.SPE.Classes;

public class EnergyCalibration {
    private double a;
    private double b;
    private boolean energyCalibr;

    public EnergyCalibration() {
        this.a = 0;
        this.b = 0;
        this.energyCalibr = false;
    }

    public void setEnergyCalibr(boolean energyCalibr) {
        this.energyCalibr = energyCalibr;
    }

    public boolean isEnergyCalibr() {
        return this.energyCalibr;
    }

    public double getA() {
        return a;
    }

    public void setCalibrCoeff(double a, double b) {
        this.b = b;
        this.a = a;
        this.energyCalibr = true;
    }

    public double getB() {
        return b;
    }

    public double getEnergy(int channel){
        return this.a * channel + this.b;
    }

}
