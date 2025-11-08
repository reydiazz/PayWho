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
    private MetaServicio metaServicio;

    @Autowired
    public AhorroServicio(AhorroRepositorio ahorroRepositorio, MetaServicio metaServicio) {
        this.ahorroRepositorio = ahorroRepositorio;
        this.metaServicio = metaServicio;
    }

    public List<Ahorro> obtenerAhorrosSegunID(long id_usuario) {
        List<Ahorro> ahorros = ahorroRepositorio.buscarAhorroID(id_usuario);
        for (Ahorro ahorro : ahorros) {
            if (ahorro.getCategoria() == null) {
                ahorros.remove(ahorro);
                ahorroRepositorio.delete(ahorro);
                break;
            }
        }
        return ahorros;
    }

    public boolean guardarAhorro(Categoria categoria, long id_usuario, String montoString) {
        try {
            if (categoria != null) {
                double monto = Double.valueOf(montoString);
                double montoSegunPorcentaje = SesionServicio.getUsuarioActual().getPorcentaje_ahorro() / 100;
                double montoAhorro = monto * montoSegunPorcentaje;
                if (montoAhorro >= 0) {
                    Ahorro ahorro = new Ahorro(categoria, Utilidades.obtenerFechaActual(), id_usuario, montoAhorro);
                    metaServicio.aumentarMontoMetaAhorro(id_usuario, metaServicio.obtenerMetaAhorro(id_usuario).getFirst().getMonto_actual() + montoAhorro);
                    ahorroRepositorio.save(ahorro);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Double getSumaTotalPorUsuario(long usuarioId) {
        Double total = ahorroRepositorio.obtenerSumaTotalPorUsuario(usuarioId);
        return total != null ? total : 0.0;
    }

    public Long getCantidadAhorrosPorUsuario(Long usuarioId) {
        Long cantidad = ahorroRepositorio.contarTodosLosAhorros(usuarioId);
        return cantidad != null ? cantidad : 0L;
    }

    public List<Object[]> obtenerAhorrosMensuales(Long usuarioId) {
        return ahorroRepositorio.obtenerAhorrosMensuales(usuarioId);
    }

}
