package com.company.paywho.service;

import com.company.paywho.entity.Categoria;
import com.company.paywho.repository.AhorroRepositorio;
import com.company.paywho.repository.CategoriaRepositorio;
import com.company.paywho.repository.GastoRepositorio;
import com.company.paywho.repository.IngresoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServicio {

    private CategoriaRepositorio categoriaRepositorio;
    private IngresoRepositorio ingresoRepositorio;
    private GastoRepositorio gastoRepositorio;
    private AhorroRepositorio ahorroRepositorio;

    @Autowired
    public CategoriaServicio(CategoriaRepositorio categoriaRepositorio, IngresoRepositorio ingresoRepositorio, GastoRepositorio gastoRepositorio, AhorroRepositorio ahorroRepositorio) {
        this.categoriaRepositorio = categoriaRepositorio;
        this.ingresoRepositorio = ingresoRepositorio;
        this.gastoRepositorio = gastoRepositorio;
        this.ahorroRepositorio = ahorroRepositorio;
    }

    public boolean comprobarDisponibilidadNombreCategoria(long idCategoria, String nombre, String tipo) {
        Optional<Categoria> categoria = categoriaRepositorio.buscarCategoriaNombre(idCategoria, nombre, tipo);
        return categoria.isPresent();
    }

    public List<Categoria> obtenerCategoriaSegunID(long id_usuario) {
        List<Categoria> categoria = categoriaRepositorio.buscarCategoriaID(id_usuario);
        return categoria;
    }

    public List<Categoria> obtenerCategoriasSegunTipo(long id_usuario, String tipo) {
        List<Categoria> categoria = categoriaRepositorio.buscarCategoriaTipo(id_usuario, tipo);
        return categoria;
    }

    public boolean guardarCategoria(long id_usuario, String nombre, String tipo) {
        try {
            if (tipo != null) {
                Categoria categoria = new Categoria(id_usuario, nombre, tipo);
                categoriaRepositorio.save(categoria);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean borrarCategoria(Categoria categoria) {
        try {
            if (categoria != null) {
                ingresoRepositorio.deleteByCategoria(categoria.getId_categoria());
                ahorroRepositorio.deleteByCategoria(categoria.getId_categoria());
                gastoRepositorio.deleteByCategoria(categoria.getId_categoria());
                categoriaRepositorio.delete(categoria);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean modificarCategoria(Categoria categoria, String nombre, String tipo) {
        try {
            if (categoria != null) {
                categoriaRepositorio.actualizarCategoria(nombre, tipo, categoria.getId_categoria());
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
