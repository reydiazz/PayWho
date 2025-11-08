package com.company.paywho.service;

import com.company.paywho.entity.Categoria;
import com.company.paywho.entity.Gasto;
import com.company.paywho.model.Utilidades;
import com.company.paywho.repository.GastoRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GastoServicio {

    private GastoRepositorio gastoRepositorio;

    @Autowired
    public GastoServicio(GastoRepositorio gastoRepositorio) {
        this.gastoRepositorio = gastoRepositorio;
    }

    public List<Gasto> obtenerGastosSegunID(long id_usuario) {
        List<Gasto> gastos = gastoRepositorio.buscarGastoIDUsuario(id_usuario);
        for (Gasto gasto : gastos) {
            if (gasto.getCategoria() == null) {
                gastos.remove(gasto);
                gastoRepositorio.delete(gasto);
                break;
            }
        }
        return gastos;
    }

    public boolean guardarGasto(Categoria categoria, long id_usuario, String montoString) {
        try {
            if (categoria != null) {
                double monto = Double.valueOf(montoString);
                if (monto > 0) {
                    Gasto ingreso = new Gasto(categoria, Utilidades.obtenerFechaActual(), id_usuario, monto);
                    gastoRepositorio.save(ingreso);
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

    public boolean borrarGasto(Gasto gasto) {
        try {
            if (gasto != null) {
                gastoRepositorio.delete(gasto);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean modificarGasto(Gasto gasto, Categoria categoria, String montoCadena) {
        try {
            if (gasto != null) {
                double monto = Double.parseDouble(montoCadena);
                gastoRepositorio.editarGasto(categoria, monto, gasto.getId_gasto());
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
