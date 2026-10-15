package uabc.david.practica3algo2k26.Vista;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import uabc.david.practica3algo2k26.Modelo.Historial;

/**
 * Esta clase representa la interfaz interactiva de Activity.
 */
public class GraficaActivity {
    private Historial historial;
    private BarChart<String, Number> barChart;
    private Label promedio;
    private boolean mostrarMoved = true;
    private int jugadorSeleccionado = -1;

    public GraficaActivity(Historial historial) {
        this.historial = historial;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Activity");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #d2c8b8;");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Turn");

        NumberAxis yAxis = new NumberAxis(0, 36, 2);
        yAxis.setLabel("Number");

        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);

        root.setCenter(barChart);

        VBox panelDerecho = new VBox(15);
        panelDerecho.setAlignment(Pos.CENTER);
        panelDerecho.setPadding(new Insets(10));

        Button botonMoved = new Button("Moved");
        Button botonRolled = new Button("Rolled");

        botonMoved.setPrefSize(80, 35);
        botonRolled.setPrefSize(80, 35);

        promedio = new Label("Average\n0.0");
        promedio.setStyle("-fx-font-size: 14px; -fx-text-alignment: center; -fx-font-weight: bold;");

        botonMoved.setOnAction(e -> {
            mostrarMoved = true;
            actualizarGrafica();
        });

        botonRolled.setOnAction(e -> {
            mostrarMoved = false;
            actualizarGrafica();
        });

        panelDerecho.getChildren().addAll(botonMoved, botonRolled, promedio);
        root.setRight(panelDerecho);

        HBox panelInferior = new HBox(8);
        panelInferior.setAlignment(Pos.CENTER);
        panelInferior.setPadding(new Insets(15));

        Label jugador = new Label("Player");
        jugador.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        panelInferior.getChildren().add(jugador);

        for (int i = 1; i <= 10; i++) {
            final int idJugador = i - 1;
            Button botonJugador = new Button(String.valueOf(i));
            botonJugador.setStyle("-fx-background-radius: 20; -fx-min-width: 35px; -fx-min-height: 35px;");
            botonJugador.setOnAction(e -> {
                jugadorSeleccionado = idJugador;
                actualizarGrafica();
            });
            panelInferior.getChildren().add(botonJugador);
        }

        Button botonAll = new Button("all");
        botonAll.setStyle("-fx-background-radius: 20; -fx-min-width: 45px; -fx-min-height: 35px; -fx-font-weight: bold;");
        botonAll.setOnAction(e -> {
            jugadorSeleccionado = -1;
            actualizarGrafica();
        });

        panelInferior.getChildren().addAll(botonAll);
        root.setBottom(panelInferior);

        actualizarGrafica();

        Scene scene = new Scene(root, 850, 500);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Recalcula los valores y redibuja las barras según la combinación de filtros seleccionada.
     */
    private void actualizarGrafica() {
        barChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        int rondas = historial.getRonda();
        int estaciones = historial.getEstaciones();

        double sumaTotalMuestra = 0;
        int puntosTotales = 0;

        for (int ronda = 0; ronda < rondas; ronda++) {
            double valorTurno = 0;

            if (jugadorSeleccionado == -1) {
                double sumaRonda = 0;
                for (int persona = 0; persona < estaciones; persona++) {
                    sumaRonda += mostrarMoved ? historial.getMovimiento(ronda, persona) : historial.getTirada(ronda, persona);
                }
                valorTurno = sumaRonda / estaciones;
            } else {
                try {
                    valorTurno = mostrarMoved ?
                            historial.getMovimiento(ronda, jugadorSeleccionado) :
                            historial.getTirada(ronda, jugadorSeleccionado);
                } catch (Exception e) {
                    valorTurno = 0;
                }
            }

            series.getData().add(new XYChart.Data<>(String.valueOf(ronda + 1), valorTurno));
            sumaTotalMuestra += valorTurno;
            puntosTotales++;
        }

        barChart.getData().add(series);

        for (XYChart.Data<String, Number> data : series.getData()) {
            if (data.getNode() != null) {
                data.getNode().setStyle("-fx-bar-fill: #00aeff;");
            }
        }

        double promedioGlobal = (puntosTotales > 0) ? (sumaTotalMuestra / puntosTotales) : 0.0;
        promedio.setText(String.format("Average\n%.1f", promedioGlobal));
    }
}
