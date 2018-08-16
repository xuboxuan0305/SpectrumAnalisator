package SpectrumApp.java.FX;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.util.List;

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

    public ScatterChart<Number, Number> getScatterChart() {
        return scatterChart;
    }

    public ScatterChart<Number, Number> applyData(List<Integer> dataList){
        XYChart.Series series = new XYChart.Series();
        for (Integer i: dataList) {
            series.getData().add(new XYChart.Data(i,dataList.get(i)));
        }
        this.scatterChart.getData().addAll(series);
        return this.scatterChart;
    }

}
