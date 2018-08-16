package SpectrumApp.java.FX;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

public class Chart {
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private ScatterChart<Number, Number> scatterChart;

    public Chart(String xName, String yName) {
        this.xAxis = new NumberAxis(0, 168, 3);
        xAxis.setLabel(xName);
        this.yAxis = new NumberAxis(0, 30, 3);
        xAxis.setLabel(yName);
        this.scatterChart = new ScatterChart<>(xAxis, yAxis);
    }

    public void applyData(){
        XYChart.Series series = new XYChart.Series();
    }
}
