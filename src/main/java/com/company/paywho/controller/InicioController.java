package com.company.paywho.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import org.springframework.stereotype.Controller;

@Controller
public class InicioController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarGraficos();
    }

    private void inicializarGraficos() {
        inicializarGraficoPastel();
        inicializarGraficoBarras();
    }

    private void inicializarGraficoPastel() {
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList(
                new PieChart.Data("Ingresos", 70),
                new PieChart.Data("Gastos", 30),
                new PieChart.Data("Ahorros", 10)
        );
        pc_pastel.setLabelsVisible(false);
        pc_pastel.setData(datos);
    }

    private void inicializarGraficoBarras() {
        // Serie de Gastos
        XYChart.Series<String, Number> gastos = new XYChart.Series<>();
        gastos.setName("Gastos");
        gastos.getData().add(new XYChart.Data<>("Enero", 500));
        gastos.getData().add(new XYChart.Data<>("Febrero", 300));
        gastos.getData().add(new XYChart.Data<>("Marzo", 450));

        // Serie de Ahorros
        XYChart.Series<String, Number> ahorros = new XYChart.Series<>();
        ahorros.setName("Ahorros");
        ahorros.getData().add(new XYChart.Data<>("Enero", 200));
        ahorros.getData().add(new XYChart.Data<>("Febrero", 250));
        ahorros.getData().add(new XYChart.Data<>("Marzo", 300));
        ahorros.getData().add(new XYChart.Data<>("Abril", 300));
        ahorros.getData().add(new XYChart.Data<>("Junio", 300));
        ahorros.getData().add(new XYChart.Data<>("Julio", 300));
        ahorros.getData().add(new XYChart.Data<>("Agosto", 300));
        ahorros.getData().add(new XYChart.Data<>("Septiembre", 300));
        ahorros.getData().add(new XYChart.Data<>("Octubre", 300));
        ahorros.getData().add(new XYChart.Data<>("Noviembre", 300));
        ahorros.getData().add(new XYChart.Data<>("Diciembre", 300));

        // Serie de Ingresos
        XYChart.Series<String, Number> ingresos = new XYChart.Series<>();
        ingresos.setName("Ingresos");
        ingresos.getData().add(new XYChart.Data<>("Enero", 800));
        ingresos.getData().add(new XYChart.Data<>("Febrero", 700));
        ingresos.getData().add(new XYChart.Data<>("Marzo", 900));

        // Agregar todas las series
        sbc_barras.getData().addAll(gastos, ahorros, ingresos);
    }
    
    @FXML
    private StackedBarChart sbc_barras;
    @FXML
    private PieChart pc_pastel;
}
