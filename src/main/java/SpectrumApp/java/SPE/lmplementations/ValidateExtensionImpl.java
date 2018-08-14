package SpectrumApp.java.SPE.lmplementations;

import SpectrumApp.java.SPE.Classes.ExtensionValidation;
import SpectrumApp.java.SPE.Classes.Extensions;
import SpectrumApp.java.SPE.Interfaces.ValidateExtension;

import java.util.List;

/**
 * Created by Renat Razumilov on 25/09/17.
 */
public class ValidateExtensionImpl implements ValidateExtension {
    @Override
    public ExtensionValidation isExtensionValid(String extension) {
        ExtensionValidation validation = new ExtensionValidation();
        Extensions sExt = new Extensions();
        List<String> s = sExt.getSupportedExtensions();

        for (int i = 0; i < sExt.size(); i++) {
            String ext = s.get(i);
            if (ext.equals(extension)) {
                validation.setValidation();
            }
        }
        return validation;
    }
}
