package SPE.Classes;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renat Razumilov on 25/09/17.
 */
public class Extensions {
    private List<String> supportedExtensions = new ArrayList<>();

    public Extensions() {
        this.supportedExtensions.add("txt");
        this.supportedExtensions.add("spe");
    }

    public int size() {
        return this.supportedExtensions.size();
    }

    public List<String> getSupportedExtensions() {
        return supportedExtensions;
    }
}
