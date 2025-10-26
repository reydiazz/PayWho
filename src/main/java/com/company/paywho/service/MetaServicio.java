package com.company.paywho.service;

import com.company.paywho.entity.Meta;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.paywho.repository.MetaRepositorio;

@Service
public class MetaServicio {

    MetaRepositorio metaAhorroRepositorio;

    @Autowired
    public MetaServicio(MetaRepositorio metaAhorroRepositorio) {
        this.metaAhorroRepositorio = metaAhorroRepositorio;
    }

    public boolean validarMetaAhorro(long idUsuario) {
        Optional<Meta> metaAhorro = metaAhorroRepositorio.buscarMetaAhorroIDUsuario(idUsuario);
        return metaAhorro.isPresent();
    }

    public List<Meta> obtenerMetaAhorro(long idUsuario) {
        List<Meta> metaAhorro = metaAhorroRepositorio.obtenerMetaAhorroIDUsuario(idUsuario);
        return metaAhorro;
    }

    public boolean crearMetaAhorro(long idUsuario, String metaString, String nombre) {
        try {
            double meta = Double.parseDouble(metaString);
            double montoLogrado = 0;
            Meta metaAhorro = new Meta(idUsuario, meta, montoLogrado, nombre);
            metaAhorroRepositorio.save(metaAhorro);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean aumentarMontoMetaAhorro(long idUsuario, double montoNuevo) {
        try {
            metaAhorroRepositorio.actualizarMontoMetaAhorro(montoNuevo, idUsuario);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean modificarMetaAhorro(long idUsuario, String nuevoNombre, String nuevaMetaString) {
        try {
            double nuevaMeta = Double.parseDouble(nuevaMetaString);
            double montoLogrado = 0;
            metaAhorroRepositorio.actualizarMetaAhorro(nuevoNombre, montoLogrado, nuevaMeta, idUsuario);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
