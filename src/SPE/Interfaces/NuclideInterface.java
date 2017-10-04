package SPE.Interfaces;

import SPE.Classes.Nuclide;

import java.util.List;

/**
 * Created by Renat Razumilov on 18/09/17.
 */
public interface NuclideInterface {
    boolean isNuclideInTheLibrary(List<Nuclide> library, Nuclide nuclide);

}
