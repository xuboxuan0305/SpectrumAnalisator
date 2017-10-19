package SPE;

import SPE.Classes.*;
import SPE.Interfaces.NuclideInterface;
import SPE.Interfaces.ValidateExtension;
import SPE.Read.ChooseMethodToReadFile;
import SPE.lmplementations.ValidateExtensionImpl;
import SPE.lmplementations.checkNuclidesForExistance;


import java.util.ArrayList;
import java.util.List;

import static SPE.Read.ReadSpectrumTxt.CHANNELS;
import static SPE.StaticMethods.getChnValues;
import static SPE.StaticMethods.getSpeHead;

public class Spectrum {
    private String path; // move to separate class "reader"
    private SpeMetaData head;
    private ChannelsValues[] spe;
    private EnergyCalibration energyCalibration;
    private List<Nuclide> nLibrary;
    private ChooseMethodToReadFile method; // move to separate class "reader"
    private ExtensionValidation validExt; // move to separate class "reader"

    public ExtensionValidation getValidExt() {
        return validExt;
    }

    public Spectrum(String path) {
        this.path = path;
        check();
        this.nLibrary = new ArrayList<>();
        this.energyCalibration = new EnergyCalibration();
        this.method = new ChooseMethodToReadFile(this.path);
        read();
    }
    private void check(){
        ValidateExtension validateExtension = new ValidateExtensionImpl();
        this.validExt = validateExtension.isExtensionValid(getExtension());
    }

    private String getExtension(){
        String[] pExtension = this.path.split("\\.");
        int size = pExtension.length;
        String ext = pExtension[size-1];
        return ext;
    }

    private void read(){
        if (validExt.getValidation()) {
            setHead();
            setSpe();
        }else {
            System.out.println(validExt.getError());
        }
    }

    public int nuclideLibSize() {
        return this.nLibrary.size();
    }

    public void addNuclide(Nuclide nuclide) {
        NuclideInterface checkNuclide = new checkNuclidesForExistance();
        if (!(checkNuclide.isNuclideInTheLibrary(this.nLibrary, nuclide))) {
            this.nLibrary.add(nuclide);
        }
    }

    public void setNuclideLibrary(List<Nuclide> newLibrary) {
        NuclideInterface checkNuclide = new checkNuclidesForExistance();

        for (Nuclide nNew : newLibrary) {
            if (!(checkNuclide.isNuclideInTheLibrary(this.nLibrary, nNew))) {
                this.nLibrary.add(nNew);
            } else {
                System.out.println(nNew.getName() + " Already in the list");
            }

        }
    }

    public List<Double> getNuclideEnergies(String name) {
        boolean match = false;
        List<Double> en = new ArrayList<>();
        if (!(this.nLibrary.size() == 0)) {
            for (Nuclide n : this.nLibrary) {
                if (n.getName().equals(name)) {
                    en = n.getEnergies();
                    match = true;
                }
            }
        } else {
            throw new SpectrumException("Library is empty");
        }
        if (match) {
            return en;
        } else {
            throw new SpectrumException("No Match");
        }
    }

    public List<Double> getNuclideEnergies(int pos) {
        if (!(this.nLibrary.size() == 0)) {
            if (pos < (this.nLibrary.size() + 1)) {
                return this.nLibrary.get(pos - 1).getEnergies();
            } else if ((pos < 1)) {
                throw new SpectrumException("Index out of bounds");
            } else {
                throw new SpectrumException("Index out of bounds");
            }
        } else {
            throw new SpectrumException("Library is empty");
        }
    }

    public void setEnergyCalibration(EnergyCalibration energyCalibration) {
        this.energyCalibration = energyCalibration;
        energyCalibration.setEnergyCalibr(true);
    }

    public double getEnergy(int channel) {
        boolean energyCalibr = this.energyCalibration.isEnergyCalibr();
        if (energyCalibr) {
            return this.energyCalibration.getEnergy(channel);
        } else {
            throw new SpectrumException("Energy calibration is absent");
        }
    }

    public void printChannels() {
        for (ChannelsValues i : spe) {
            System.out.println(i);
        }
    }

    public void printLibrary() {
        int x = 0;
        for (Nuclide n : this.nLibrary) {
            System.out.println("#" + ++x + " :");
            n.showEnergies();
        }
    }

    public ChannelsValues[] getSpe() {
        return spe;
    }

    public SpeMetaData getHead() {
        return head;
    }

    public void printHead() {
        System.out.println(head);
    }

    public int getChannelCounts(int channelNumber) {
        return spe[channelNumber - 1].getCounts();
    }

    private void setHead() {
        String[] sHead = method.ReadWithApropriateMethodHead();
        this.head = getSpeHead(sHead);
    }

    private void setSpe() {
        String[] speVal = method.ReadWithApropriateMethodChannels();
        this.spe = getChnValues(speVal, CHANNELS);
    }


}
