package SPE.lmplementations;

import SPE.Classes.Nuclide;
import SPE.Interfaces.NuclideInterface;

import java.util.List;

/**
 * Created by Renat Razumilov on 18/09/17.
 */
public class checkNuclidesForExistance implements NuclideInterface {

    @Override
    public boolean isNuclideInTheLibrary(List<Nuclide> library, Nuclide nuclide) {
        boolean match = false;
        if (library.size() == 0) {
            return false;
        } else {
            for (Nuclide n : library) {
                if (nuclide.equals(n)) {
                    match = true;
                }
            }
        }
        return match;
    }
}
