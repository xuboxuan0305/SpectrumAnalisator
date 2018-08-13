package FX;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sun.plugin.dom.css.Counter;


public class UIComponent extends Application implements EventHandler<ActionEvent> {
    Button buttonPlus;
    Button buttonMinus;
    Label label;
    int counter = 0;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Spectrum Analyser");
        StackPane layout = new StackPane();

        buttonPlus = new Button("Increment");
        buttonPlus.setTranslateX(10);
        buttonPlus.setTranslateY(0);
        buttonPlus.setOnAction(this);

        buttonMinus = new Button("Decrement");
        buttonMinus.setTranslateX(10);
        buttonMinus.setTranslateY(50);
        buttonMinus.setOnAction(this);

        label = new Label();
        label.setTranslateX(10);
        label.setTranslateY(100);


        layout.getChildren().add(buttonPlus);
        layout.getChildren().add(buttonMinus);
        layout.getChildren().add(label);

        Scene scene = new Scene(layout, 300,250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == buttonPlus){
            label.setText(Integer.toString(counter++));
        }
        else if (event.getSource() == buttonMinus){
            label.setText(Integer.toString(counter--));
        }
    }
}
