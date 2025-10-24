package com.company.paywho.controller;

import com.company.paywho.entity.Categoria;
import com.company.paywho.model.Utilidades;
import com.company.paywho.service.CategoriaServicio;
import com.company.paywho.service.SesionServicio;
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
public class CategoriaController implements Initializable {

    CategoriaServicio categoriaServicio;
    Categoria categoriaElegida;

    @Autowired
    public CategoriaController(CategoriaServicio categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarOpcionesCategoria();
        inicializarBotones();
        inicializarTabla();
        inicializarCategoriaElegida();
    }

    private void inicializarBotones() {
        btn_agregar.setOnAction(evento -> {
            agregarCategoria();
        });
        tv_categoria.getSelectionModel().selectedItemProperty().addListener((observado, antiguoValor, nuevoValor) -> {
            actualizarCategoriaElegida(nuevoValor);
            actualizarFormularioCategoriaElegida();
        });
        btn_eliminar.setOnAction(evento -> {
            eliminiarCategoria();
        });
        btn_modificar.setOnAction(evento -> {
            modificarCategoria();
        });

    }

    private void inicializarTabla() {
        tc_categoria.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tc_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tc_categoria.setReorderable(false);
        tc_nombre.setReorderable(false);
        tv_categoria.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tv_categoria.setItems(solicitarDatosAlServicioCategoria());
    }

    private void agregarCategoria() {
        if (verificarDisponibilidadCategoria()) {
            if (enviarDatosAlServicioCategoriaAgregar()) {
                actualizarTabla();
                limpiarFormulario();
                Utilidades.crearModal("Se registro correctamente la categoría.");
            } else {
                Utilidades.crearModal("Error al registrar la categoría.");
            }
        } else {
            Utilidades.crearModal("Categoría ya registrada.");
        }
    }

    private void modificarCategoria() {
        if (enviarDatosAlServicioCategoriaEditar()) {
            actualizarTabla();
            limpiarFormulario();
            Utilidades.crearModal("Se actualizo correctamente la categoría.");
        } else {
            Utilidades.crearModal("Error al modificar la categoría.");
        }
    }

    private void eliminiarCategoria() {
        if (enviarDatosAlServicioCategoriaEliminar()) {
            inicializarCategoriaElegida();
            actualizarTabla();
            limpiarFormulario();
            Utilidades.crearModal("Se elimino correctamente la categoría.");
        } else {
            Utilidades.crearModal("Error al eliminar la categoría.");
        }
    }

    private ObservableList<Categoria> solicitarDatosAlServicioCategoria() {
        List<Categoria> categorias = categoriaServicio.obtenerCategoriaSegunID(SesionServicio.getUsuarioActual().getId_usuario());
        ObservableList<Categoria> lista = FXCollections.observableArrayList(categorias);
        return lista;
    }

    private boolean enviarDatosAlServicioCategoriaEliminar() {
        return categoriaServicio.borrarCategoria(categoriaElegida);
    }

    private boolean enviarDatosAlServicioCategoriaEditar() {
        String nombreNuevo = txf_nombre.getText();
        String tipoNuevo = cb_categoria.getValue();
        return categoriaServicio.modificarCategoria(categoriaElegida, nombreNuevo, tipoNuevo);
    }

    private boolean enviarDatosAlServicioCategoriaAgregar() {
        String nombre = txf_nombre.getText();
        String tipo = cb_categoria.getValue();
        long idUsuario = SesionServicio.getUsuarioActual().getId_usuario();
        return categoriaServicio.guardarCategoria(idUsuario, nombre, tipo);
    }

    private boolean verificarDisponibilidadCategoria() {
        String nombre = txf_nombre.getText();
        String tipo = cb_categoria.getValue();
        long idUsuario = SesionServicio.getUsuarioActual().getId_usuario();
        return !categoriaServicio.comprobarDisponibilidadNombreCategoria(idUsuario, nombre, tipo);
    }

    private void actualizarFormularioCategoriaElegida() {
        if (categoriaElegida != null) {
            String nombreCategoria = categoriaElegida.getNombre();
            String tipoCategoria = categoriaElegida.getTipo();
            txf_nombre.setText(nombreCategoria);
            switch (tipoCategoria) {
                case "Ingreso":
                    cb_categoria.getSelectionModel().select(0);
                    break;
                case "Gasto":
                    cb_categoria.getSelectionModel().select(1);
                    break;
            }
        }
    }

    private void inicializarOpcionesCategoria() {
        cb_categoria.setItems(FXCollections.observableArrayList("Ingreso", "Gasto"));
    }

    private void actualizarTabla() {
        tv_categoria.setItems(solicitarDatosAlServicioCategoria());
    }

    private void inicializarCategoriaElegida() {
        categoriaElegida = null;
    }

    private void actualizarCategoriaElegida(Categoria categoriaElegida) {
        this.categoriaElegida = categoriaElegida;
    }

    private void limpiarFormulario() {
        txf_nombre.setText("");
        cb_categoria.getSelectionModel().clearSelection();
    }
    @FXML
    private ComboBox<String> cb_categoria;
    @FXML
    private TextField txf_nombre;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_agregar;
    @FXML
    private Button btn_modificar;
    @FXML
    private TableView<Categoria> tv_categoria;
    @FXML
    private TableColumn<Categoria, String> tc_nombre;
    @FXML
    private TableColumn<Categoria, String> tc_categoria;
}
