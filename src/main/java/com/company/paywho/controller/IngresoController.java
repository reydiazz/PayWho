package com.company.paywho.controller;

import com.company.paywho.entity.Categoria;
import com.company.paywho.entity.Ingreso;
import com.company.paywho.model.Utilidades;
import com.company.paywho.service.AhorroServicio;
import com.company.paywho.service.CategoriaServicio;
import com.company.paywho.service.IngresoServicio;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class IngresoController implements Initializable {

    private UsuarioServicio usuarioServicio;
    private IngresoServicio ingresoServicio;
    private CategoriaServicio categoriaServicio;
    private AhorroServicio ahorroServicio;
    private Ingreso ingresoElegido;

    @Autowired

    public IngresoController(IngresoServicio ingresoServicio, CategoriaServicio categoriaServicio, AhorroServicio ahorroServicio, UsuarioServicio usuarioServicio) {
        this.ingresoServicio = ingresoServicio;
        this.categoriaServicio = categoriaServicio;
        this.ahorroServicio = ahorroServicio;
        this.usuarioServicio = usuarioServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarTabla();
        inicializarOpciones();
        inicializarBotones();
        inicializarIngresoElegido();
    }

    private void inicializarTabla() {
        tc_monto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        tc_tipo.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tc_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tc_monto.setReorderable(false);
        tc_tipo.setReorderable(false);
        tc_fecha.setReorderable(false);
        tv_ingresos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tv_ingresos.setItems(solicitarDatosAlServicioIngreso());
    }

    private void inicializarOpciones() {
        List<Categoria> tipos = categoriaServicio.obtenerCategoriasSegunTipo(SesionServicio.getUsuarioActual().getId_usuario(), "Ingreso");
        ObservableList<Categoria> opciones = FXCollections.observableArrayList(tipos);
        cb_tipo_ingreso.setItems(opciones);
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
        tv_ingresos.getSelectionModel().selectedItemProperty().addListener((observado, antiguoValor, nuevoValor) -> {
            actualizarIngresoElegido(nuevoValor);
            actualizarFormularioIngresoElegido();
        });
    }

    private void agregarIngreso() {
        if (cx_ahorrar.isSelected()) {
            if (usuarioServicio.obtenerUsuarioID(SesionServicio.getUsuarioActual().getId_usuario()).getPorcentaje_ahorro() != 0) {
                if (enviarDatosAlServicioIngresoAgregarAhorro() && enviarDatosAlServicioAhorroAgregar()) {
                    actualizarTabla();
                    limpiarFormulario();
                    Utilidades.crearModal("Se registro correctamente el ingreso.");
                } else {
                    Utilidades.crearModal("Error al registrar el ingreso.");
                }
            } else {
                Utilidades.crearModal("El porcentaje de ahorro tiene que ser mayor a cero.");
            }
        } else {
            if (enviarDatosAlServicioIngresoAgregar()) {
                actualizarTabla();
                limpiarFormulario();
                Utilidades.crearModal("Se registro correctamente el ingreso.");
            } else {
                Utilidades.crearModal("Error al registrar el ingreso.");
            }
        }
    }

    private void modificarIngreso() {
        if (enviarDatosAlServicioIngresoModificar()) {
            actualizarTabla();
            limpiarFormulario();
            Utilidades.crearModal("Se actualizo correctamente el ingreso.");
        } else {
            Utilidades.crearModal("Error al modificar el ingreso.");
        }
    }

    private void eliminiarIngreso() {
        if (enviarDatosAlServicioIngresoEliminar()) {
            inicializarIngresoElegido();
            actualizarTabla();
            limpiarFormulario();
            Utilidades.crearModal("Se elimino correctamente el ingreso.");
        } else {
            Utilidades.crearModal("Error al eliminar el ingreso.");
        }
    }

    private boolean enviarDatosAlServicioIngresoAgregar() {
        Categoria categoria = cb_tipo_ingreso.getValue();
        String monto = txf_monto_ingreso.getText();
        return ingresoServicio.guardarIngreso(categoria, SesionServicio.getUsuarioActual().getId_usuario(), monto);
    }

    private boolean enviarDatosAlServicioIngresoAgregarAhorro() {
        Categoria categoria = cb_tipo_ingreso.getValue();
        String monto = txf_monto_ingreso.getText();
        return ingresoServicio.guardarIngresoAhorro(categoria, SesionServicio.getUsuarioActual().getId_usuario(), monto);
    }

    private boolean enviarDatosAlServicioAhorroAgregar() {
        Categoria categoria = cb_tipo_ingreso.getValue();
        String monto = txf_monto_ingreso.getText();
        return ahorroServicio.guardarAhorro(categoria, SesionServicio.getUsuarioActual().getId_usuario(), monto);
    }

    private boolean enviarDatosAlServicioIngresoModificar() {
        Categoria categoria = cb_tipo_ingreso.getValue();
        String monto = txf_monto_ingreso.getText();
        return ingresoServicio.modificarIngreso(ingresoElegido, categoria, monto);
    }

    private boolean enviarDatosAlServicioIngresoEliminar() {
        return ingresoServicio.borrarIngreso(ingresoElegido);
    }

    private ObservableList<Ingreso> solicitarDatosAlServicioIngreso() {
        List<Ingreso> ingresos = ingresoServicio.obtenerIngresosSegunID(SesionServicio.getUsuarioActual().getId_usuario());
        ObservableList< Ingreso> lista = FXCollections.observableArrayList(ingresos);
        return lista;
    }

    private void actualizarTabla() {
        tv_ingresos.setItems(solicitarDatosAlServicioIngreso());
    }

    private void actualizarIngresoElegido(Ingreso ingresoNuevo) {
        this.ingresoElegido = ingresoNuevo;
    }

    private void limpiarFormulario() {
        cb_tipo_ingreso.getSelectionModel().clearSelection();
        txf_monto_ingreso.setText("");
        cx_ahorrar.setSelected(false);
    }

    private void inicializarIngresoElegido() {
        ingresoElegido = null;
    }

    private void actualizarFormularioIngresoElegido() {
        if (ingresoElegido != null) {
            txf_monto_ingreso.setText(String.valueOf(ingresoElegido.getMonto()));
            String categoriaBuscado = ingresoElegido.getCategoria().getNombre();
            for (Categoria item : cb_tipo_ingreso.getItems()) {
                if (item.getNombre().equals(categoriaBuscado)) {
                    cb_tipo_ingreso.getSelectionModel().select(item);
                    break;
                }
            }
        } else {
            limpiarFormulario();
        }
    }

    @FXML
    private ComboBox<Categoria> cb_tipo_ingreso;
    @FXML
    private TextField txf_monto_ingreso;
    @FXML
    private CheckBox cx_ahorrar;
    @FXML
    private Button btn_agregar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_modificar;
    @FXML
    private TableView<Ingreso> tv_ingresos;
    @FXML
    private TableColumn<Categoria, String> tc_tipo;
    @FXML
    private TableColumn<Ingreso, Double> tc_monto;
    @FXML
    private TableColumn<Ingreso, String> tc_fecha;

}
