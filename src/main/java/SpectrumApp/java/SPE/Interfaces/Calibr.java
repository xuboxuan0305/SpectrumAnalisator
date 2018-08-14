package SpectrumApp.java.SPE.Interfaces;

import SpectrumApp.java.SPE.Classes.EnergyCalibration;

import java.util.List;

public interface Calibr {
    EnergyCalibration calibrLessSquareMethod(
            List<Double> channels,
            List<Double> energy);
}
