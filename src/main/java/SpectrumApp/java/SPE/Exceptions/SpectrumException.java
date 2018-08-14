package SpectrumApp.java.SPE.Exceptions;

public class SpectrumException extends RuntimeException {
    private String str;

    public SpectrumException(String str){
        this.str = str;
    }

    @Override
    public String getMessage() {
        return this.str;
    }
}
