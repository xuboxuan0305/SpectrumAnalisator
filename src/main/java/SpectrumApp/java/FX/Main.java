package SpectrumApp.java.FX;

import SpectrumApp.java.Config.SpringApplicationConfig;
import SpectrumApp.java.SPE.Analyser.PeakSearch.PeakSearchDomain;
import SpectrumApp.java.SPE.Interfaces.Show;
import SpectrumApp.java.SPE.Read.ReadSpectrumFile;
import SpectrumApp.java.SPE.Read.SpectrumReader;
import SpectrumApp.java.SPE.Spectrum;
import SpectrumApp.java.SPE.lmplementations.PrintSpectrum;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.List;


public class Main extends Application implements EventHandler<ActionEvent> {

    Button buttonBrowse, exitButton, peakSearch, peakSearchAdv;

    Label label, labelHead;

    FileChooser fileChooser;
    Stage globalPrimaryStage;
    File selectedFile;
    Show show;
    LineChart<Number, Number> scatterChart;

    Scene scene;
    TextField textField;

    Chart chart;
    int chartYScaleMax = 1000;

    private String PATH = "";
    private Spectrum bSpectr;// = new Spectrum();
    private ReadSpectrumFile reader;// = new SpectrumReader(PATH);

    PeakSerchParamWin peakSerchParamWin;

    public static void main(String[] args) {
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
        buttonBrowse = new Button("Browse File");
        buttonBrowse.setTranslateX(10);
        buttonBrowse.setTranslateY(90);
//        buttonBrowse.setOnAction(e -> fileChooser.showOpenDialog(primaryStage));
        buttonBrowse.setOnAction(this);

        // Button exit
        exitButton = new Button("Exit");
        exitButton.setTranslateX(100);
        exitButton.setTranslateY(90);
        exitButton.setOnAction(e -> Platform.exit());

        peakSearchAdv = new Button("Search");
        peakSearchAdv.setTranslateX(0);
        peakSearchAdv.setTranslateY(50);
        peakSearchAdv.setOnAction(this);

        // Button peakSearch
        peakSearch = new Button("PeakSearch");
        peakSearch.setTranslateX(-100);
        peakSearch.setTranslateY(90);
        peakSearch.setVisible(false);
        peakSearch.setOnAction(this);
//        peakSerchParamWin = new PeakSerchParamWin(this.scene, this.globalPrimaryStage);
//        peakSearch.setOnAction(e -> primaryStage.setScene(peakSerchParamWin.getSceneSearch()));



        //Label
        label = new Label("Info:");
        label.setTranslateX(10);
        label.setTranslateY(120);

        //Label
        labelHead = new Label("Data:");
        labelHead.setTranslateX(-420);
        labelHead.setTranslateY(-50);


        //Chart init:
        this.chart = new Chart("Channels/100", "Counts [Normalized]", chartYScaleMax, "line");
        this.scatterChart = chart.getScatterChart();
        this.scatterChart.visibleProperty().setValue(false);
        this.scatterChart.setMaxHeight(500);
        this.scatterChart.setMaxWidth(800);
        this.scatterChart.setTranslateY(-200);
        this.scatterChart.setTranslateX(10);

        layoutSpe.getChildren().add(this.scatterChart);
//        backButton.setOnAction(e -> primaryStage.setScene(scene));

        //Add components
        layout.getChildren().addAll(buttonBrowse,
                peakSearch,
                exitButton,
                label,
                labelHead,
                scatterChart);

        //Scene deploy
        this.scene = new Scene(layout, 1000, 900); // main
        this.scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void handle(ActionEvent event) {
        String path = "";

        //button click event
        if (event.getSource() == this.buttonBrowse) { // choose file
            this.fileChooser = new FileChooser();
            this.fileChooser.setInitialFileName("*.spe, *.txt");
            this.fileChooser.setInitialDirectory(
                    new File("C:\\Java\\SpectrumAnalisator\\src\\main\\java\\SpectrumApp\\java\\SPE\\Co60spe"));

            this.selectedFile = this.fileChooser.showOpenDialog(this.globalPrimaryStage);
            if (this.selectedFile != null) {
                path = this.selectedFile.getAbsolutePath();
            }
        }

        if (!path.equals("")) { // if not empty
            setPATH(path); //set global path to file
            readSpectrum(); //read spectrum
            //globalPrimaryStage.setScene(sceneSpe);
        }

        //peak search 2
        if (event.getSource() == this.peakSearchAdv){
            this.peakSerchParamWin.setSearchParam();
            List<Integer> list = this.peakSerchParamWin.processSpectrumSearch(this.bSpectr);
            this.peakSerchParamWin.setLabelDATA(list);
            this.globalPrimaryStage.setScene(peakSerchParamWin.getSceneSearch());
        }

        //peak search
        if (event.getSource() == this.peakSearch) {
            this.peakSerchParamWin =
                    new PeakSerchParamWin(
                    this.globalPrimaryStage,
                    this.scene);

            peakSerchParamWin.setSpectrum(this.bSpectr);
            peakSerchParamWin.addButtonClose(new Button("Close"),-80,50);
            peakSerchParamWin.addButtonSearch(this.peakSearchAdv);

            this.globalPrimaryStage.setScene(peakSerchParamWin.getSceneSearch());


//            if (this.bSpectr == null) {
//                this.label.setText("Error, no spectrum found");
//            } else {
//                PeakSearchDomain peakSearchDomain = new PeakSearchDomain(this.bSpectr);
//                int ampTresh = Integer.parseInt(this.textField.getText());
//                peakSearchDomain.setSearchParameters(ampTresh, 100, 100, 16000);
//                //show new scene with parameters ...
//                //...............................//
//
//
//                List<Integer> peaks = peakSearchDomain.execute();
//                output(peaks);
//            }
        }
    }

    private void output(List<Integer> peaks) {
        if (peaks.size() == 2) { //for cobalt60
            for (int i = 0; i < peaks.size(); i++) {
                this.labelHead.setText(
                        this.labelHead.getText() + "\nPeak #" + i + " Channel: " + peaks.get(i));
            }
        } else if (peaks.size() == 0) {
            this.labelHead.setText(
                    this.labelHead.getText() + "\nNothing Found");
        } else if (peaks.size() > 2) {
            this.labelHead.setText(
                    this.labelHead.getText() + "\nToo many peaks found, \nincrease threshold");
        }

    }

    private void readSpectrum() {
        ReadSpectrumFile reader = new SpectrumReader(PATH);
        if (reader.isSpectrumSupported()) { // if supported
            this.label.setText("File format supported, reading ...");
            this.bSpectr = reader.read();

            Show spe = new PrintSpectrum(this.chartYScaleMax);// init

//            List<String[]> spectrumList = spe.showSpectrum(bSpectr);// get list of values
            spe.showSpectrum(this.bSpectr);// get list of values

            //using xy chart
            this.scatterChart.visibleProperty().setValue(true);
            this.scatterChart.getData().clear(); // clear plot
            List<Integer> miniSpectrum = ((PrintSpectrum) spe).getMiniSpectrum(); // get list of xy
            this.scatterChart.getData().add(this.chart.applyData(miniSpectrum));// apply data

            //set labels
            this.label.setText(this.label.getText() + "\nReading Complete");// set to complete
            this.labelHead.setText(this.bSpectr.getHead().toString());

            //enable peakSearch button & text field (search param)
            this.peakSearch.setVisible(true);


        } else { // if not
            this.label.setText("File format NOT supported");
        }
    }

    private void setPATH(String PATH) {
        this.PATH = PATH;
    }

}
