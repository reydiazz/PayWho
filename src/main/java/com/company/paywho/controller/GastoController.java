package com.company.paywho.controller;

import com.company.paywho.entity.Categoria;
import com.company.paywho.entity.Gasto;
import com.company.paywho.model.Utilidades;
import com.company.paywho.service.AhorroServicio;
import com.company.paywho.service.CategoriaServicio;
import com.company.paywho.service.GastoServicio;
import com.company.paywho.service.SesionServicio;
import com.company.paywho.service.UsuarioServicio;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GastoController implements Initializable {

    private UsuarioServicio usuarioServicio;
    private GastoServicio gastoServicio;
    private CategoriaServicio categoriaServicio;
    private Gasto gastoElegido;

    @Autowired

    public GastoController(GastoServicio gastoServicio, CategoriaServicio categoriaServicio, AhorroServicio ahorroServicio, UsuarioServicio usuarioServicio) {
        this.gastoServicio = gastoServicio;
        this.categoriaServicio = categoriaServicio;
        this.usuarioServicio = usuarioServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarTabla();
        inicializarOpciones();
        inicializarBotones();
        inicializarGastoElegido();
    }

    private void inicializarTabla() {
        tc_monto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        tc_tipo.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tc_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tc_monto.setReorderable(false);
        tc_tipo.setReorderable(false);
        tc_fecha.setReorderable(false);
        tv_gastos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tv_gastos.setItems(solicitarDatosAlServicioGasto());
    }

    private void inicializarOpciones() {
        List<Categoria> tipos = categoriaServicio.obtenerCategoriasSegunTipo(SesionServicio.getUsuarioActual().getId_usuario(), "Gasto");
        ObservableList<Categoria> opciones = FXCollections.observableArrayList(tipos);
        cb_tipo_gasto.setItems(opciones);
    }

    private void inicializarBotones() {
        btn_agregar.setOnAction(evento -> {
            agregarIngreso();
        });
        btn_eliminar.setOnAction(evento -> {
            eliminiarIngreso();
        });
        btn_modificar.setOnAction(evento -> {
            modificarIngreso();
        });
        tv_gastos.getSelectionModel().selectedItemProperty().addListener((observado, antiguoValor, nuevoValor) -> {
            actualizarGastoElegido(nuevoValor);
            actualizarFormularioGastoElegido();
        });
    }

    private void agregarIngreso() {
        if (enviarDatosAlServicioGastoAgregar()) {
            actualizarTabla();
            limpiarFormulario();
            Utilidades.crearModal("Se registro correctamente el gasto.");
        } else {
            Utilidades.crearModal("Error al registrar el gasto.");
        }
    }

    private void modificarIngreso() {
        if (enviarDatosAlServicioGastoModificar()) {
            actualizarTabla();
            limpiarFormulario();
            Utilidades.crearModal("Se actualizo correctamente el gasto.");
        } else {
            Utilidades.crearModal("Error al modificar el gasto.");
        }
    }

    private void eliminiarIngreso() {
        if (enviarDatosAlServicioGastoEliminar()) {
            inicializarGastoElegido();
            actualizarTabla();
            limpiarFormulario();
            Utilidades.crearModal("Se elimino correctamente el gasto.");
        } else {
            Utilidades.crearModal("Error al eliminar el gasto.");
        }
    }

    private boolean enviarDatosAlServicioGastoAgregar() {
        Categoria categoria = cb_tipo_gasto.getValue();
        String monto = txf_monto_gasto.getText();
        return gastoServicio.guardarGasto(categoria, SesionServicio.getUsuarioActual().getId_usuario(), monto) && usuarioServicio.disminuirBalanceUsuario(SesionServicio.getUsuarioActual().getId_usuario(), monto);
    }

    private boolean enviarDatosAlServicioGastoModificar() {
        Categoria categoria = cb_tipo_gasto.getValue();
        String monto = txf_monto_gasto.getText();
        return gastoServicio.modificarGasto(gastoElegido, categoria, monto);
    }

    private boolean enviarDatosAlServicioGastoEliminar() {
        return gastoServicio.borrarGasto(gastoElegido);
    }

    private ObservableList<Gasto> solicitarDatosAlServicioGasto() {
        List<Gasto> gastos = gastoServicio.obtenerGastosSegunID(SesionServicio.getUsuarioActual().getId_usuario());
        ObservableList< Gasto> lista = FXCollections.observableArrayList(gastos);
        return lista;
    }

    private void actualizarTabla() {
        tv_gastos.setItems(solicitarDatosAlServicioGasto());
    }

    private void actualizarGastoElegido(Gasto gastoNuevo) {
        this.gastoElegido = gastoNuevo;
    }

    private void limpiarFormulario() {
        cb_tipo_gasto.getSelectionModel().clearSelection();
        txf_monto_gasto.setText("");
    }

    private void inicializarGastoElegido() {
        gastoElegido = null;
    }

    private void actualizarFormularioGastoElegido() {
        if (gastoElegido != null) {
            txf_monto_gasto.setText(String.valueOf(gastoElegido.getMonto()));
            String categoriaBuscado = gastoElegido.getCategoria().getNombre();
            for (Categoria item : cb_tipo_gasto.getItems()) {
                if (item.getNombre().equals(categoriaBuscado)) {
                    cb_tipo_gasto.getSelectionModel().select(item);
                    break;
                }
            }
        } else {
            limpiarFormulario();
        }
    }

    @FXML
    private ComboBox<Categoria> cb_tipo_gasto;
    @FXML
    private TextField txf_monto_gasto;
    @FXML
    private Button btn_agregar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_modificar;
    @FXML
    private TableView<Gasto> tv_gastos;
    @FXML
    private TableColumn<Categoria, String> tc_tipo;
    @FXML
    private TableColumn<Gasto, Double> tc_monto;
    @FXML
    private TableColumn<Gasto, String> tc_fecha;

}
