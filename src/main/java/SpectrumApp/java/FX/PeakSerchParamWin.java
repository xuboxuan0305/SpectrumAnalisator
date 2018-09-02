package SpectrumApp.java.FX;

import SpectrumApp.java.SPE.Analyser.PeakSearch.PeakSearchDomain;
import SpectrumApp.java.SPE.Spectrum;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PeakSerchParamWin {

    private PeakSearchDomain peakSearchDomain;
    private StackPane layout;
    private Scene sceneSearch, sceneGlobal;
    private Stage stage;

    private TextField textAmpThresh, textSlopeThresh, textLLD, textHLD;
    private Label labelAmpThresh, labelSlopeThresh, labelLLD, labelHLD, labelDATA;
    private Button serchButton, closeButton;

    private Spectrum spectrum;
    private int at, st, lld, hld;

    public PeakSerchParamWin() {
    }

    public boolean isSceneSet() {
        return (this.stage != null) && (this.sceneGlobal != null);
    }

    public void setScene(Stage stageGlobal, Scene sceneGlobal) {
        this.stage = stageGlobal;
        this.sceneGlobal = sceneGlobal;
        this.peakSearchDomain = new PeakSearchDomain();
        this.peakSearchDomain.setSearchParameters(50, 50, 100, 16000);

        this.textAmpThresh = new TextField("50");
        this.textAmpThresh.setTranslateX(-200);
        this.textAmpThresh.setTranslateY(0);
        this.textAmpThresh.setMaxSize(45, 10);

        this.textSlopeThresh = new TextField("50");
        this.textSlopeThresh.setTranslateX(-100);
        this.textSlopeThresh.setTranslateY(0);
        this.textSlopeThresh.setMaxSize(45, 10);

        this.textLLD = new TextField("100");
        this.textLLD.setTranslateX(0);
        this.textLLD.setTranslateY(0);
        this.textLLD.setMaxSize(45, 10);

        this.textHLD = new TextField("16000");
        this.textHLD.setTranslateX(100);
        this.textHLD.setTranslateY(0);
        this.textHLD.setMaxSize(50, 10);

        this.labelAmpThresh = new Label("Amplitude Thresh");
        this.labelAmpThresh.setTranslateX(-200);
        this.labelAmpThresh.setTranslateY(-50);

        this.labelSlopeThresh = new Label("Slope Thresh");
        this.labelSlopeThresh.setTranslateX(-100);
        this.labelSlopeThresh.setTranslateY(-50);

        this.labelLLD = new Label("LLD");
        this.labelLLD.setTranslateX(0);
        this.labelLLD.setTranslateY(-50);

        this.labelHLD = new Label("HLD");
        this.labelHLD.setTranslateX(100);
        this.labelHLD.setTranslateY(-50);

        this.labelDATA = new Label("DATA:");
        this.labelDATA.setTranslateX(0);
        this.labelDATA.setTranslateY(100);

        this.layout = new StackPane();
        this.layout.getChildren().addAll(
                this.labelAmpThresh,
                this.labelSlopeThresh,
                this.labelLLD,
                this.labelHLD,
                this.textAmpThresh,
                this.textSlopeThresh,
                this.textHLD,
                this.textLLD,
                this.labelDATA);
    }

    public void setSearchParam() {
        this.at = Integer.parseInt(this.textAmpThresh.getText());
        this.st = Integer.parseInt(this.textSlopeThresh.getText());
        this.hld = Integer.parseInt(this.textHLD.getText());
        this.lld = Integer.parseInt(this.textLLD.getText());
        this.peakSearchDomain.setSearchParameters(at, st, lld, hld);
    }

    public PeakSearchDomain getPeakSearchDomain() {
        return this.peakSearchDomain;
    }

    public void setLabelDATA(String string) {
        this.labelDATA.setText(string);
    }

    public void setLabelDATA(List<Integer> peaks) {

        if (peaks.size() == 0) {
            this.labelDATA.setText("\nNothing Found");
        }

        if (peaks.size() < 9 && peaks.size() > 0) { //for cobalt60
            this.labelDATA.setText("");
            for (int i = 0; i < peaks.size(); i++) {
                this.labelDATA.setText(this.labelDATA.getText() +
                        "\nPeak #" + i + " Channel: " + peaks.get(i));
            }
        }

        if (peaks.size() >= 9) {
            this.labelDATA.setText("\nToo many peaks found, increase threshold" +
                    "\nPeaks Found: " + peaks.size());
        }

    }

    public void addButtonClose(Button button) {
        if (this.closeButton == null) {
            this.closeButton = button;
            this.closeButton.setOnAction(e -> this.stage.setScene(this.sceneGlobal));
            this.layout.getChildren().add(button);
        }

    }

//    public void addButtonClose(Button button, int x, int y) {
//        button.setTranslateX(x);
//        button.setTranslateY(y);
//        button.setOnAction(e -> this.stage.setScene(this.sceneGlobal));
//        this.layout.getChildren().add(button);
//    }

    public void addButtonSearch(Button button) {
        if (this.serchButton == null) {
            this.serchButton = button;
            this.layout.getChildren().add(button);
        }
    }

    public Scene getSceneSearch() {
        if (this.sceneSearch == null) {
            this.sceneSearch = new Scene(layout, 500, 500);
        }
        return this.sceneSearch;
    }

    public void setDefaultSearchParam() {
        this.peakSearchDomain.setSearchParameters(50, 50, 100, 16000);
    }

    public List<Integer> processSpectrumSearch(Spectrum spectrum) {
        this.peakSearchDomain.setSpectrum(spectrum);
        return this.peakSearchDomain.execute();
    }

    public void setSpectrum(Spectrum spectrum) {
        this.spectrum = spectrum;
    }
}
