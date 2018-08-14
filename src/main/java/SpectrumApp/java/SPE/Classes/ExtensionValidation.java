package SpectrumApp.java.SPE.Classes;


/**
 * Created by Renat Razumilov on 21/09/17.
 */
public class ExtensionValidation{
    private Boolean validation;
    private String error;

    public ExtensionValidation() {
        this.validation = false;
        this.error = "Not valid extension";
    }

    public String getError() {
        return this.error;
    }

    public void setValidation() {
        this.validation = true;
    }

    public Boolean getValidation() {
        return validation;
    }
}
