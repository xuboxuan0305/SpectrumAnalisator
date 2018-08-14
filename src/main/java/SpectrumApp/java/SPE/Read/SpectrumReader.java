package SpectrumApp.java.SPE.Read;

import SpectrumApp.java.SPE.Classes.ExtensionValidation;
import SpectrumApp.java.SPE.Interfaces.ValidateExtension;
import SpectrumApp.java.SPE.Spectrum;
import SpectrumApp.java.SPE.lmplementations.ValidateExtensionImpl;
import SpectrumApp.java.SPE.StaticMethods;


public class SpectrumReader implements ReadSpectrumFile {
    private Spectrum spectrum;
    private String path;
    private ChooseMethodToReadFile method;
    private ExtensionValidation validExt;

    public SpectrumReader(String path) {
        this.spectrum = new Spectrum();
        this.path = path;
        check();
        this.method = new ChooseMethodToReadFile(this.path);
        read();
    }

    public ExtensionValidation getValidExt() {
        return validExt;
    }

    @Override
    public boolean isSpectrumSupported() {
        return validExt.getValidation();
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

    public Spectrum read(){
        if (validExt.getValidation()) {
            setHead();
            setSpe();
        }else {
            System.out.println(validExt.getError());
        }
        return this.spectrum;
    }

    private void setHead() {
        String[] sHead = method.ReadWithApropriateMethodHead();
        this.spectrum.setHead(StaticMethods.getSpeHead(sHead));

    }

    private void setSpe() {
        String[] speVal = method.ReadWithApropriateMethodChannels();
        this.spectrum.setSpe(StaticMethods.getChnValues(speVal, ReadSpectrumTxt.CHANNELS)) ;
    }

}
