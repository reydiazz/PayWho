package com.company.paywho.controller;

import com.company.paywho.entity.Ahorro;
import com.company.paywho.entity.Categoria;
import com.company.paywho.entity.Meta;
import com.company.paywho.model.Utilidades;
import com.company.paywho.service.AhorroServicio;
import com.company.paywho.service.MetaServicio;
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
    private MetaServicio metaAhorroServicio;

    @Autowired
    public AhorroController(AhorroServicio ahorroServicio, MetaServicio metaAhorroServicio) {
        this.ahorroServicio = ahorroServicio;
        this.metaAhorroServicio = metaAhorroServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarTabla();
        inicializarDatos();
        inicializarBotones();
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

    private void inicializarBotones() {
        btn_empezar_meta.setOnAction(evento -> {
            inicializarMetaAhorro();
        });
    }

    private void inicializarMetaAhorro() {
        if (verificarExistenciaMetaAhorro()) {
            if (modificarMetaAhorro()) {
                Utilidades.crearModal("Se modificó correctamente la \nmeta de ahorro.");
            } else {
                Utilidades.crearModal("Error al modificar la meta \nde ahorro.");
            }
        } else {
            if (agregarMetaAhorro()) {
                Utilidades.crearModal("Se registro correctamente la \nmeta de ahorro.");
            }
        }
    }

    private void inicializarDatos() {
        List<Meta> metas = metaAhorroServicio.obtenerMetaAhorro(SesionServicio.getUsuarioActual().getId_usuario());
        if (!metas.isEmpty()) {
            txf_monto_meta.setText(String.valueOf(metas.getFirst().getMeta()));
            txf_nombre_meta.setText(metas.getFirst().getNombre());
        }
    }

    private boolean agregarMetaAhorro() {
        long idUsuario = SesionServicio.getUsuarioActual().getId_usuario();
        String metaString = txf_monto_meta.getText();
        String nombre = txf_nombre_meta.getText();
        return metaAhorroServicio.crearMetaAhorro(idUsuario, metaString, nombre);
    }

    private boolean modificarMetaAhorro() {
        long idUsuario = SesionServicio.getUsuarioActual().getId_usuario();
        String metaString = txf_monto_meta.getText();
        String nombre = txf_nombre_meta.getText();
        return metaAhorroServicio.modificarMetaAhorro(idUsuario, nombre, metaString);
    }

    private boolean verificarExistenciaMetaAhorro() {
        return metaAhorroServicio.validarMetaAhorro(SesionServicio.getUsuarioActual().getId_usuario());
    }

    private ObservableList<Ahorro> solicitarDatosAlServicioAhorro() {
        List<Ahorro> ahorros = ahorroServicio.obtenerAhorrosSegunID(SesionServicio.getUsuarioActual().getId_usuario());
        ObservableList<Ahorro> lista = FXCollections.observableArrayList(ahorros);
        return lista;
    }

    @FXML
    private TextField txf_nombre_meta;
    @FXML
    private TextField txf_monto_meta;
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
