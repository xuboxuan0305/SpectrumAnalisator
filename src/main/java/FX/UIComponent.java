package FX;

import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.*;

public class UIComponent extends Application {
    Button buttonPlus;
    Button buttonMinus;
    Label  label;

    public void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Spectrum Analyser");


    }
}
