package com.company.paywho.service;

import com.company.paywho.entity.Ahorro;
import com.company.paywho.entity.Categoria;
import com.company.paywho.model.Utilidades;
import com.company.paywho.repository.AhorroRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AhorroServicio {

    private AhorroRepositorio ahorroRepositorio;

    @Autowired
    public AhorroServicio(AhorroRepositorio ahorroRepositorio) {
        this.ahorroRepositorio = ahorroRepositorio;
    }

    public List<Ahorro> obtenerAhorrosSegunID(long id_usuario) {
        List<Ahorro> ahorros = ahorroRepositorio.findByIDUserSaving(id_usuario);
        for (Ahorro a : ahorros) {
            if (a.getCategoria() == null) {
                ahorros.remove(a);
                ahorroRepositorio.delete(a);
                break;
            }
        }
        return ahorros;
    }

    public boolean guardarAhorro(Categoria categoria, long id_usuario, String montoString) {
        try {
            if (categoria != null) {
                double monto = Double.valueOf(montoString) * (SesionServicio.getUsuarioActual().getPorcentaje_ahorro() / 100);
                if (monto > 0) {
                    Ahorro ahorro = new Ahorro(categoria, Utilidades.obtenerFechaActual(), id_usuario, monto);
                    ahorroRepositorio.save(ahorro);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
