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

    public Double getSumaTotalPorUsuario(long usuarioId) {
        Double total = gastoRepositorio.obtenerSumaTotalPorUsuario(usuarioId);
        return total != null ? total : 0.0;
    }

    public Long getCantidadGastosPorUsuario(Long usuarioId) {
        Long cantidad = gastoRepositorio.contarTodosLosGastos(usuarioId);
        return cantidad != null ? cantidad : 0L;
    }

    public Long getCantidadUltimaSemana(Long usuarioId) {
        Long cantidad = gastoRepositorio.contarGastoUltimaSemana(usuarioId);
        return cantidad != null ? cantidad : 0L;
    }

    public Double calcularPorcentajeCambio(Long idUsuario) {
        Double actual = gastoRepositorio.obtenerSumaSemanaActual(idUsuario);
        Double anterior = gastoRepositorio.obtenerSumaSemanaAnterior(idUsuario);

        actual = (actual != null) ? actual : 0.0;
        anterior = (anterior != null && anterior != 0) ? anterior : 0.0;

        if (anterior == 0) {
            // Evitar división por cero
            return actual > 0 ? 100.0 : 0.0;
        }

        return ((actual - anterior) / anterior) * 100;
    }

    public List<Object[]> obtenerGastosMensuales(Long usuarioId) {
        return gastoRepositorio.obtenerGastosMensuales(usuarioId);
    }
}
