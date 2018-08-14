package SpectrumApp.java.SPE.Read;

import SpectrumApp.java.SPE.Classes.Extensions;
import SpectrumApp.java.SPE.Interfaces.ReadSpectrum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renat Razumilov on 21/09/17.
 */
public class ChooseMethodToReadFile {
    private ArrayList<ReadSpectrum> methods;
    private String path;
    private String ext;
    private Extensions sExt;
    private int methodNumber;

    public ChooseMethodToReadFile(String path) {
        this.path = path;
        this.methods = new ArrayList<>();
        initializeMethods();
        this.ext = getExtension();
        this.sExt = new Extensions();
        getMethodNumber();
    }

    private void getMethodNumber() {
        List<String> supported = this.sExt.getSupportedExtensions();

        for (int i = 0; i < sExt.size(); i++) {
            String sExtension = supported.get(i);
            if (sExtension.equals(this.ext)) {
                this.methodNumber = i;
            }
        }
    }

    private void initializeMethods() {
        this.methods.add(new ReadSpectrumTxt(path));
        this.methods.add(new ReadBinarySpectrum(path));
    }


    public String[] ReadWithApropriateMethodChannels() {
        int mNumber = this.methodNumber;
        return methods.get(mNumber).readChannels();
    }

    public String[] ReadWithApropriateMethodHead() {
        int mNumber = this.methodNumber;
        return methods.get(mNumber).readHead();
    }

    public String getExtension() {
        String[] pExtension = this.path.split("\\.");
        int size = pExtension.length;
        String ext = pExtension[size - 1];
        return ext;
    }
}
