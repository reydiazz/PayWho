package com.company.paywho.service;

import com.company.paywho.entity.Categoria;
import com.company.paywho.repository.CategoriaRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServicio {

    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    public CategoriaServicio(CategoriaRepositorio categoriaRepositorio) {
        this.categoriaRepositorio = categoriaRepositorio;
    }

    public boolean comprobarDisponibilidadNombreCategoria(long idCategoria, String nombre, String tipo) {
        Optional<Categoria> categoria = categoriaRepositorio.findByNameCategory(idCategoria, nombre, tipo);
        return categoria.isPresent();
    }

    public List<Categoria> obtenerCategoriaSegunID(long id_usuario) {
        List<Categoria> categoria = categoriaRepositorio.findByIDCategory(id_usuario);
        return categoria;
    }

    public List<Categoria> obtenerCategoriasSegunTipo(long id_usuario, String tipo) {
        List<Categoria> categoria = categoriaRepositorio.findByTypeCategory(id_usuario, tipo);
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
                categoriaRepositorio.updateCategory(nombre, tipo, categoria.getId_categoria());
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
