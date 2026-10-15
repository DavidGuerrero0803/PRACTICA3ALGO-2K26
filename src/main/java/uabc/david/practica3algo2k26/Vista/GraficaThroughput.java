package uabc.david.practica3algo2k26.Vista;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import uabc.david.practica3algo2k26.Modelo.Historial;

import java.util.ArrayList;

/**
 * Esta clase grafica la evolución acumulada del Throughput por turno.
 */
public class GraficaThroughput {
    private Historial historial;

    public GraficaThroughput(Historial historial) {
        this.historial = historial;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Throughput");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #d2c8b8;");
        root.setPadding(new Insets(15));

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Turn");

        NumberAxis yAxis = new NumberAxis(0, 70, 10);
        yAxis.setLabel("Throughput");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        ArrayList<Integer> datos = historial.getThroughput();

        for (int i = 0; i < datos.size(); i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i + 1), datos.get(i)));
        }

        barChart.getData().add(series);

        int totalDatos = series.getData().size();
        for (int i = 0; i < totalDatos; i++) {
            XYChart.Data<String, Number> data = series.getData().get(i);
            if (data.getNode() != null) {
                data.getNode().setStyle("-fx-bar-fill: #00aeff;");

                if (i == totalDatos - 1) {
                    Label labelFinal = new Label(String.valueOf(data.getYValue()));
                    labelFinal.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333; -fx-font-size: 14px;");

                    StackPane node = (StackPane) data.getNode();
                    node.getChildren().add(labelFinal);
                }
            }
        }

        root.setCenter(barChart);
        Scene scene = new Scene(root, 850, 500);
        stage.setScene(scene);
        stage.show();
    }
}
