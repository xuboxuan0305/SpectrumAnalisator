package SPE.Read;

import SPE.Classes.ExtensionValidation;
import SPE.Interfaces.ValidateExtension;
import SPE.Spectrum;
import SPE.lmplementations.ValidateExtensionImpl;

import static SPE.Read.ReadSpectrumTxt.CHANNELS;
import static SPE.StaticMethods.getChnValues;
import static SPE.StaticMethods.getSpeHead;


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
        this.spectrum.setHead(getSpeHead(sHead));

    }

    private void setSpe() {
        String[] speVal = method.ReadWithApropriateMethodChannels();
        this.spectrum.setSpe(getChnValues(speVal, CHANNELS)) ;
    }

}
