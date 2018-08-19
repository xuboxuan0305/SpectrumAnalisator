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
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.List;
import java.util.Objects;


public class UIComponent extends Application implements EventHandler<ActionEvent> {

    Button buttonMinus;
    Label label;
    FileChooser fileChooser;
    Stage globalPrimaryStage;
    File selectedFile;
    Show show;
    LineChart<Number,Number> scatterChart;
    Scene scene;
    Chart chart;
    int chartYScaleMax = 1000;

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
        StackPane layoutSpe = new StackPane();

        // Button browse
        buttonMinus = new Button("Browse File");
        buttonMinus.setTranslateX(10);
        buttonMinus.setTranslateY(90);
//        buttonMinus.setOnAction(e -> fileChooser.showOpenDialog(primaryStage));
        buttonMinus.setOnAction(this);

        //Label
        label = new Label("Data");
        label.setTranslateX(10);
        label.setTranslateY(120);


        //Chart init:
        this.chart = new Chart("Channels/100","Counts [Normalized]",chartYScaleMax, "line");
        this.scatterChart = chart.getScatterChart();
        this.scatterChart.visibleProperty().setValue(false);
        this.scatterChart.setMaxHeight(500);
        this.scatterChart.setMaxWidth(800);
        this.scatterChart.setTranslateY(-200);
        this.scatterChart.setTranslateX(10);

        layoutSpe.getChildren().add(this.scatterChart);
//        backButton.setOnAction(e -> primaryStage.setScene(scene));

        //Add components
        layout.getChildren().addAll(buttonMinus,label,scatterChart);

        //Scene deploy
        this.scene = new Scene(layout, 1000,900); // main
        this.scene.getStylesheets().add("style.css");
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
            //globalPrimaryStage.setScene(sceneSpe);
        }

    }

    public void readSpectrum(){
        ReadSpectrumFile reader = new SpectrumReader(PATH);
        if (reader.isSpectrumSupported()){ // if supported
            label.setText("File format supported, reading ...");
            bSpectr = reader.read();

            Show spe = new PrintSpectrum(chartYScaleMax);// init

//            List<String[]> spectrumList = spe.showSpectrum(bSpectr);// get list of values
            spe.showSpectrum(bSpectr);// get list of values

            //using xy chart
            this.scatterChart.visibleProperty().setValue(true);
            this.scatterChart.getData().clear(); // clear plot
            List<Integer> miniSpectrum = ((PrintSpectrum) spe).getMiniSpectrum(); // get list of xy
            this.scatterChart.getData().add(this.chart.applyData(miniSpectrum));// apply data

            label.setText(label.getText() + "\nReading Complete");// set to complete
        }else { // if not
            label.setText("File format NOT supported");
        }
    }

    public void setPATH(String PATH) {
        this.PATH = PATH;
    }

}
