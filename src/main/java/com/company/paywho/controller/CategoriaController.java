package com.company.paywho.controller;

import com.company.paywho.entity.Categoria;
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
        inicializarComboBox();
        inicializarBotones();
        inicializarTabla();
        inicializarCategoriaElegida();
    }

    private void inicializarBotones() {
        btn_agregar.setOnAction(evento -> {
            agregarCategoria();
        });
        tb_categoria.getSelectionModel().selectedItemProperty().addListener((observado, antiguoValor, nuevoValor) -> {
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
        tb_categoria.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tb_categoria.setItems(solicitarDatosAlServicioCategoria());
    }

    private void agregarCategoria() {
        if (verificarDisponibilidadCategoria()) {
            if (enviarDatosAlServicioCategoriaAgregar()) {
                System.out.println("Se guardo correctamente");
                actualizarTabla();
            } else {
                System.out.println("Error al registrar");
            }
        } else {
            System.out.println("Categoria ya registrada.");
        }
    }

    private void modificarCategoria() {
        if (enviarDatosAlServicioCategoriaEditar()) {
            System.out.println("Se actualizo correctamente.");
            actualizarTabla();
        } else {
            System.out.println("Error al modificar");
        }
    }

    private void eliminiarCategoria() {
        if (enviarDatosAlServicioCategoriaEliminar()) {
            System.out.println("Se elimino correctamente");
            inicializarCategoriaElegida();
            actualizarTabla();
        } else {
            System.out.println("Error al eliminar");
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
            txf_nombre.setText(categoriaElegida.getNombre());
            switch (categoriaElegida.getTipo()) {
                case "Ingreso":
                    cb_categoria.getSelectionModel().select(0);
                    break;
                case "Gasto":
                    cb_categoria.getSelectionModel().select(1);
                    break;
                default:
                    throw new AssertionError();
            }
        } else {
            limpiarFormulario();
        }
    }

    private void inicializarComboBox() {
        cb_categoria.setItems(FXCollections.observableArrayList("Ingreso", "Gasto"));
    }

    private void actualizarTabla() {
        tb_categoria.setItems(solicitarDatosAlServicioCategoria());
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
    private TableView<Categoria> tb_categoria;
    @FXML
    private TableColumn<Categoria, String> tc_nombre;
    @FXML
    private TableColumn<Categoria, String> tc_categoria;
}
