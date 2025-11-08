package com.company.paywho.controller;

import com.company.paywho.service.AhorroServicio;
import com.company.paywho.service.GastoServicio;
import com.company.paywho.service.IngresoServicio;
import com.company.paywho.service.MetaServicio;
import com.company.paywho.service.SesionServicio;
import com.company.paywho.service.UsuarioServicio;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InicioController implements Initializable {

    private IngresoServicio ingresoServicio;
    private GastoServicio gastoServicio;
    private AhorroServicio ahorroServicio;
    private UsuarioServicio usuarioServicio;
    private MetaServicio metaServicio;

    @Autowired
    public InicioController(IngresoServicio ingresoServicio, GastoServicio gastoServicio, UsuarioServicio usuarioServicio, AhorroServicio ahorroServicio, MetaServicio metaServicio) {
        this.ingresoServicio = ingresoServicio;
        this.gastoServicio = gastoServicio;
        this.usuarioServicio = usuarioServicio;
        this.ahorroServicio = ahorroServicio;
        this.metaServicio = metaServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarGraficos();
    }

    private void inicializarGraficos() {
        inicializarGraficoPastel();
        inicializarGraficoBarras();
        inicializarCartillaIngresos();
        inicializarCartillaGastos();
        inicializarCartillaAhorros();
    }

    private void inicializarCartillaIngresos() {
        lbl_monto_ingreso.setText("$" + String.valueOf(ingresoServicio.getSumaTotalPorUsuario(SesionServicio.getUsuarioActual().getId_usuario())));
        lbl_numero_ingreso.setText(String.valueOf(ingresoServicio.getCantidadIngresosPorUsuario(SesionServicio.getUsuarioActual().getId_usuario()) + " numero de ingresos"));
        lbl_numero_monto_semanal_ingreso.setText("+" + String.valueOf(ingresoServicio.getCantidadUltimaSemana(SesionServicio.getUsuarioActual().getId_usuario()) + " esta semana"));
        lbl_porcentaje_semanal_ingreso.setText("+" + String.valueOf(ingresoServicio.calcularPorcentajeCambio(SesionServicio.getUsuarioActual().getId_usuario())) + "%");
    }

    private void inicializarCartillaGastos() {
        lbl_monto_gasto.setText("$" + String.valueOf(gastoServicio.getSumaTotalPorUsuario(SesionServicio.getUsuarioActual().getId_usuario())));
        lbl_numero_gasto.setText(String.valueOf(gastoServicio.getCantidadGastosPorUsuario(SesionServicio.getUsuarioActual().getId_usuario()) + " numero de ingresos"));
        lbl_numero_monto_semanal_gasto.setText("+" + String.valueOf(gastoServicio.getCantidadUltimaSemana(SesionServicio.getUsuarioActual().getId_usuario()) + " esta semana"));
        lbl_porcentaje_semanal_gasto.setText("+" + String.valueOf(gastoServicio.calcularPorcentajeCambio(SesionServicio.getUsuarioActual().getId_usuario())) + "%");
    }

    private void inicializarCartillaAhorros() {
        lbl_porcentaje_ahorro.setText(String.valueOf(usuarioServicio.obtenerUsuarioID(SesionServicio.getUsuarioActual().getId_usuario()).getPorcentaje_ahorro()) + "% de cada ingreso");
        lbl_monto_ahorro.setText("$" + String.valueOf(ahorroServicio.getSumaTotalPorUsuario(SesionServicio.getUsuarioActual().getId_usuario())));
        double progreso = metaServicio.obtenerMetaAhorro(SesionServicio.getUsuarioActual().getId_usuario()).getFirst().getMonto_actual() / metaServicio.obtenerMetaAhorro(SesionServicio.getUsuarioActual().getId_usuario()).getFirst().getMeta();
        pb_meta_ahorro.setProgress(progreso);
    }

    private void inicializarGraficoPastel() {
        lbl_balance_actual.setText("$" + String.valueOf(usuarioServicio.obtenerUsuarioID(SesionServicio.getUsuarioActual().getId_usuario()).getSaldo()));
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList(
                new PieChart.Data("Ingresos", ingresoServicio.getCantidadIngresosPorUsuario(SesionServicio.getUsuarioActual().getId_usuario())),
                new PieChart.Data("Gastos", gastoServicio.getCantidadGastosPorUsuario(SesionServicio.getUsuarioActual().getId_usuario())),
                new PieChart.Data("Ahorros", ahorroServicio.getCantidadAhorrosPorUsuario(SesionServicio.getUsuarioActual().getId_usuario()))
        );
        pc_pastel.setLabelsVisible(false);
        pc_pastel.setData(datos);
    }

    private void inicializarGraficoBarras() {
        XYChart.Series<String, Number> ingresos = new XYChart.Series<>();
        XYChart.Series<String, Number> gastos = new XYChart.Series<>();
        XYChart.Series<String, Number> ahorros = new XYChart.Series<>();

        ingresos.setName("Ingresos");
        gastos.setName("Gastos");
        ahorros.setName("Ahorros");

        Long idUsuario = SesionServicio.getUsuarioActual().getId_usuario();

        List<Object[]> datosIngresos = ingresoServicio.obtenerIngresosMensuales(idUsuario);
        List<Object[]> datosGastos = gastoServicio.obtenerGastosMensuales(idUsuario);
        List<Object[]> datosAhorros = ahorroServicio.obtenerAhorrosMensuales(idUsuario);

        String[] meses = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        Map<String, String> nombreMes = Map.ofEntries(
                Map.entry("01", "Ene"),
                Map.entry("02", "Feb"),
                Map.entry("03", "Mar"),
                Map.entry("04", "Abr"),
                Map.entry("05", "May"),
                Map.entry("06", "Jun"),
                Map.entry("07", "Jul"),
                Map.entry("08", "Ago"),
                Map.entry("09", "Sep"),
                Map.entry("10", "Oct"),
                Map.entry("11", "Nov"),
                Map.entry("12", "Dic")
        );

        Map<String, Double> mapaIngresos = convertirAMapa(datosIngresos);
        Map<String, Double> mapaGastos = convertirAMapa(datosGastos);
        Map<String, Double> mapaAhorros = convertirAMapa(datosAhorros);

        for (String mes : meses) {
            String nombre = nombreMes.get(mes);
            ingresos.getData().add(new XYChart.Data<>(nombre, mapaIngresos.getOrDefault(mes, 0.0)));
            gastos.getData().add(new XYChart.Data<>(nombre, mapaGastos.getOrDefault(mes, 0.0)));
            ahorros.getData().add(new XYChart.Data<>(nombre, mapaAhorros.getOrDefault(mes, 0.0)));
        }

        CategoryAxis ejeX = (CategoryAxis) sbc_barras.getXAxis();
        ejeX.setCategories(FXCollections.observableArrayList(
                "Ene", "Feb", "Mar", "Abr", "May", "Jun",
                "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
        ));

        sbc_barras.getData().clear();
        sbc_barras.getData().addAll(ingresos, gastos, ahorros);

    }

    private Map<String, Double> convertirAMapa(List<Object[]> datos) {
        Map<String, Double> mapa = new HashMap<>();
        for (Object[] fila : datos) {
            if (fila != null && fila[0] != null && fila[1] != null) {
                String mes = fila[0].toString().replaceAll("\\D", "");
                if (mes.length() == 1) {
                    mes = "0" + mes;
                }
                Double monto = ((Number) fila[1]).doubleValue();
                mapa.put(mes, monto);
                System.out.println("Mes limpio: " + mes + " -> " + monto);
            }
        }
        return mapa;
    }

    @FXML
    private StackedBarChart sbc_barras;
    @FXML
    private PieChart pc_pastel;
    @FXML
    private Label lbl_balance_actual;

    // Ingresos
    @FXML
    private Label lbl_numero_ingreso;
    @FXML
    private Label lbl_monto_ingreso;
    @FXML
    private Label lbl_porcentaje_semanal_ingreso;
    @FXML
    private Label lbl_numero_monto_semanal_ingreso;

    //Gastos
    @FXML
    private Label lbl_numero_gasto;
    @FXML
    private Label lbl_monto_gasto;
    @FXML
    private Label lbl_porcentaje_semanal_gasto;
    @FXML
    private Label lbl_numero_monto_semanal_gasto;

    //Ahorros
    @FXML
    private Label lbl_porcentaje_ahorro;
    @FXML
    private Label lbl_monto_ahorro;
    @FXML
    private ProgressBar pb_meta_ahorro;

}
