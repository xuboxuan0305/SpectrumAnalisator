package SPE.lmplementations;

import SPE.Classes.ExtensionValidation;
import SPE.Classes.Extensions;
import SPE.Interfaces.ValidateExtension;

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
