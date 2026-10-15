package uabc.david.practica3algo2k26.Vista;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import uabc.david.practica3algo2k26.Modelo.Historial;

import java.util.ArrayList;

/**
 * Esta clase grafica la evolución del Number In System (WIP).
 */
public class GraficaNumberInSystem {
    private Historial historial;

    public GraficaNumberInSystem(Historial historial) {
        this.historial = historial;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Number in system");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #d2c8b8;");
        root.setPadding(new Insets(15));

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Turn");

        NumberAxis yAxis = new NumberAxis(0, 100, 10);
        yAxis.setLabel("Number in system");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        int inventarioInicialTotal = 36;
        series.getData().add(new XYChart.Data<>("0", inventarioInicialTotal));

        ArrayList<Integer> datos = historial.getNumberInSystem();
        for (int i = 0; i < datos.size(); i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i + 1), datos.get(i)));
        }

        barChart.getData().add(series);

        for (XYChart.Data<String, Number> data : series.getData()) {
            if (data.getNode() != null) {
                if ("0".equals(data.getXValue())) {
                    data.getNode().setStyle("-fx-bar-fill: #808080;");
                } else {
                    data.getNode().setStyle("-fx-bar-fill: #00aeff;");
                }
            }
        }

        root.setCenter(barChart);
        Scene scene = new Scene(root, 850, 500);
        stage.setScene(scene);
        stage.show();
    }
}
