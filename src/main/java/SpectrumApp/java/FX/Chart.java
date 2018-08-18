package SpectrumApp.java.FX;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.util.List;

public class Chart {
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private LineChart<Number, Number> scatterChart;

    public Chart(String xName, String yName, int upperBound, String graphType) {
        switch (graphType){
            case "line":
                this.xAxis = new NumberAxis(0, 163, 3);
                xAxis.setLabel(xName);
                this.yAxis = new NumberAxis(0, upperBound, 3);
                xAxis.setLabel(yName);
                this.scatterChart = new LineChart(xAxis, yAxis);
                scatterChart.setLegendVisible(false);
                break;
            case "bar":
                break;
        }

    }

    public LineChart<Number, Number> getScatterChart() {
        return scatterChart;
    }

    public LineChart.Series<Number, Number> applyData(List<Integer> dataList) {
        LineChart.Series series = new XYChart.Series();
        for (int i = 0; i < dataList.size(); i++) {
            series.getData().add(new XYChart.Data(i, dataList.get(i)));
        }
        return series;
    }

}
