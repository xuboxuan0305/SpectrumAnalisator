package SPE.Test;

import SPE.Classes.ExtensionValidation;
import SPE.Read.ChooseMethodToReadFile;
import SPE.Read.SpectrumReader;
import SPE.Spectrum;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Renat Razumilov on 21/09/17.
 */


public class TestWithMocks {
    @Test
    public void testGetExtension() {
        SpectrumReader spe = mock(SpectrumReader.class);
        ExtensionValidation eValid = new ExtensionValidation();
        eValid.setValidation();
        when(spe.getValidExt()).thenReturn(eValid);
        assertEquals(spe.getValidExt(), eValid);
        //
    }
}
