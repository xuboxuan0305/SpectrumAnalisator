package FX;

import SPE.Interfaces.Show;
import SPE.Read.ReadSpectrumFile;
import SPE.Read.SpectrumReader;
import SPE.Spectrum;
import SPE.lmplementations.PrintSpectrum;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;


public class UIComponent extends Application implements EventHandler<ActionEvent> {

    Button buttonMinus;
    Label label;
    FileChooser fileChooser;
    Stage globalPrimaryStage;
    File selectedFile;

    public void setPATH(String PATH) {
        this.PATH = PATH;
    }

    private String PATH = "";
    private Spectrum bSpectr;// = new Spectrum();
    private ReadSpectrumFile reader;// = new SpectrumReader(PATH);

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        globalPrimaryStage = primaryStage;
        primaryStage.setTitle("Spectrum Analyser");
        StackPane layout = new StackPane();


        buttonMinus = new Button("Browse File");
        buttonMinus.setTranslateX(10);
        buttonMinus.setTranslateY(50);
//        buttonMinus.setOnAction(e -> fileChooser.showOpenDialog(primaryStage));
        buttonMinus.setOnAction(this);




        label = new Label();
        label.setTranslateX(10);
        label.setTranslateY(100);

        layout.getChildren().add(buttonMinus);
        layout.getChildren().add(label);

        Scene scene = new Scene(layout, 300,300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void handle(ActionEvent event) {
        String path = "";

        if (event.getSource() == buttonMinus){ // choose file
            fileChooser = new FileChooser();
            fileChooser.setInitialFileName("*.spe, *.txt");
            selectedFile = fileChooser.showOpenDialog(globalPrimaryStage);
            path = selectedFile.getAbsolutePath();
        }

        if (!Objects.equals(path, "")){ // if not empty
            setPATH(path);
            readSpectrum(path);
        }

    }

    public void readSpectrum(String path){
        ReadSpectrumFile reader = new SpectrumReader(PATH);
        if (reader.isSpectrumSupported()){
            label.setText("File format supported");
            bSpectr = reader.read();
            Show spe = new PrintSpectrum();
            spe.showSpectrum(bSpectr);
        }else {
            label.setText("File format NOT supported");
        }
    }
}
