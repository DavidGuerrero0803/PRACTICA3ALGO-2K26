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
 * Esta clase muestra el tiempo (turnos) que permaneció en el sistema
 * cada cliente que cruzó la línea completa.
 */
public class GraficaTimeInSystem {
    private Historial historial;

    public GraficaTimeInSystem(Historial historial) {
        this.historial = historial;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Time in system");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #d2c8b8;");
        root.setPadding(new Insets(15));

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Order of arrival");

        NumberAxis yAxis = new NumberAxis(0, 20, 1);
        yAxis.setLabel("Time in system");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        ArrayList<Integer> tiempos = historial.getTimeInSystem();

        for (int i = 0; i < tiempos.size(); i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i + 1), tiempos.get(i)));
        }

        barChart.getData().add(series);

        for (XYChart.Data<String, Number> data : series.getData()) {
            if (data.getNode() != null) {
                data.getNode().setStyle("-fx-bar-fill: #00aeff;");
            }
        }

        root.setCenter(barChart);
        Scene scene = new Scene(root, 850, 500);
        stage.setScene(scene);
        stage.show();
    }
}