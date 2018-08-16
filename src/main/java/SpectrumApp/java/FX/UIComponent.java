package SpectrumApp.java.FX;

import SpectrumApp.java.Config.SpringApplicationConfig;
import SpectrumApp.java.SPE.Interfaces.Show;
import SpectrumApp.java.SPE.Read.ReadSpectrumFile;
import SpectrumApp.java.SPE.Read.SpectrumReader;
import SpectrumApp.java.SPE.Spectrum;
import SpectrumApp.java.SPE.lmplementations.PrintSpectrum;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.List;
import java.util.Objects;


public class UIComponent extends Application implements EventHandler<ActionEvent> {

    Button buttonMinus;
    Label label;
    Label labelSpe;
    FileChooser fileChooser;
    Stage globalPrimaryStage;
    File selectedFile;
    Show show;
    ScatterChart<Number,Number> scatterChart;

    private String PATH = "";
    private Spectrum bSpectr;// = new Spectrum();
    private ReadSpectrumFile reader;// = new SpectrumReader(PATH);

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Spring App context
        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(SpringApplicationConfig.class);
        show = applicationContext.getBean(PrintSpectrum.class);

        //Global Stage
        globalPrimaryStage = primaryStage;
        primaryStage.setTitle("Spectrum Analyser");
        StackPane layout = new StackPane();

        // Button browse
        buttonMinus = new Button("Browse File");
        buttonMinus.setTranslateX(10);
        buttonMinus.setTranslateY(70);
//        buttonMinus.setOnAction(e -> fileChooser.showOpenDialog(primaryStage));
        buttonMinus.setOnAction(this);


        //Label
        label = new Label();
        label.setTranslateX(10);
        label.setTranslateY(100);

        //Label
        labelSpe = new Label();
        labelSpe.setTranslateX(10);
        labelSpe.setTranslateY(-200);
        labelSpe.setText("Spectrum");

        //Chart init:
        Chart chart = new Chart("","");
        this.scatterChart = chart.getScatterChart();

        //Add components
        layout.getChildren().add(buttonMinus);
        layout.getChildren().add(label);
        layout.getChildren().add(labelSpe);
//        layout.getChildren().add(scatterChart);

        //Scene deploy
        Scene scene = new Scene(layout, 1000,900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void handle(ActionEvent event) {
        String path = "";

        //button click event
        if (event.getSource() == buttonMinus){ // choose file
            fileChooser = new FileChooser();
            fileChooser.setInitialFileName("*.spe, *.txt");
            selectedFile = fileChooser.showOpenDialog(globalPrimaryStage);
            if (selectedFile != null) {
                path = selectedFile.getAbsolutePath();
            }
        }

        if (!Objects.equals(path, "")){ // if not empty
            setPATH(path); //set global path to file
            readSpectrum(); //read spectrum
        }

    }

    public void readSpectrum(){
        ReadSpectrumFile reader = new SpectrumReader(PATH);
        if (reader.isSpectrumSupported()){ // if supported
            label.setText("File format supported, reading ...");
            bSpectr = reader.read();

            Show spe = new PrintSpectrum();// init
            List<String[]> spectrumList = spe.showSpectrum(bSpectr);// get list for Label
            String spectrumString = convert2String(spectrumList);// prepare output for label
            labelSpe.setText(spectrumString); // set label

            //using xy chart
            List<Integer> miniSpectrum = ((PrintSpectrum) spe).getMiniSpectrum(); // xy chart ..

            label.setText(label.getText() + "\nReading Complete");
        }else { // if not
            label.setText("File format NOT supported");
        }
    }

    public void setPATH(String PATH) {
        this.PATH = PATH;
    }

    private String convert2String(List<String[]> listArr){
        StringBuilder stringBuffer = new StringBuilder();

        for (String[] aS : listArr){

            for (String s :aS) {
              stringBuffer.append(s);
            }
            stringBuffer.append("\n");
        }

        return stringBuffer.toString();
    }
}
