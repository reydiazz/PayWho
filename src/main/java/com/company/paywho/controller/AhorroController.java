package com.company.paywho.controller;

import com.company.paywho.entity.Ahorro;
import com.company.paywho.entity.Categoria;
import com.company.paywho.service.AhorroServicio;
import com.company.paywho.service.SesionServicio;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AhorroController implements Initializable {

    private AhorroServicio ahorroServicio;

    @Autowired
    public AhorroController(AhorroServicio ahorroServicio) {
        this.ahorroServicio = ahorroServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarTabla();
    }

    private void inicializarTabla() {
        tc_monto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        tc_proviene.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tc_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tc_monto.setReorderable(false);
        tc_proviene.setReorderable(false);
        tc_fecha.setReorderable(false);
        tv_registro_ahorros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tv_registro_ahorros.setItems(solicitarDatosAlServicioAhorro());
    }

    private ObservableList<Ahorro> solicitarDatosAlServicioAhorro() {
        List<Ahorro> ahorros = ahorroServicio.obtenerAhorrosSegunID(SesionServicio.getUsuarioActual().getId_usuario());
        ObservableList<Ahorro> lista = FXCollections.observableArrayList(ahorros);
        return lista;
    }

    @FXML
    private TextField txf_nombre_meta;
    @FXML
    private TextField txf_monto_menta;
    @FXML
    private Button btn_empezar_meta;
    @FXML
    private TableView<Ahorro> tv_registro_ahorros;
    @FXML
    private TableColumn<Ahorro, Categoria> tc_proviene;
    @FXML
    private TableColumn<Ahorro, String> tc_fecha;
    @FXML
    private TableColumn<Ahorro, Double> tc_monto;

}
