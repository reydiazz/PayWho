package com.company.paywho.service;

import com.company.paywho.entity.Categoria;
import com.company.paywho.entity.Ingreso;
import com.company.paywho.model.Utilidades;
import com.company.paywho.repository.IngresoRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngresoServicio {

    private IngresoRepositorio ingresoRepositorio;

    @Autowired
    public IngresoServicio(IngresoRepositorio ingresoRepositorio) {
        this.ingresoRepositorio = ingresoRepositorio;
    }

    public List<Ingreso> obtenerIngresosSegunID(long id_usuario) {
        List<Ingreso> ingresos = ingresoRepositorio.buscarIngresoIDUsuario(id_usuario);
        for (Ingreso ingreso : ingresos) {
            if (ingreso.getCategoria() == null) {
                ingresos.remove(ingreso);
                ingresoRepositorio.delete(ingreso);
                break;
            }
        }
        return ingresos;
    }

    public boolean guardarIngreso(Categoria categoria, long id_usuario, String montoString) {
        try {
            if (categoria != null) {
                double monto = Double.valueOf(montoString);
                if (monto > 0) {
                    Ingreso ingreso = new Ingreso(categoria, Utilidades.obtenerFechaActual(), id_usuario, monto);
                    ingresoRepositorio.save(ingreso);
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

    public boolean guardarIngresoAhorro(Categoria categoria, long id_usuario, String montoString) {
        try {
            if (categoria != null) {
                double monto = Double.valueOf(montoString);
                double porcentajeAhorro = (SesionServicio.getUsuarioActual().getPorcentaje_ahorro() / 100);
                double parteAhorro = monto * porcentajeAhorro;
                double montoAhorro = monto - parteAhorro;
                if (montoAhorro > 0) {
                    Ingreso ingreso = new Ingreso(categoria, Utilidades.obtenerFechaActual(), id_usuario, montoAhorro);
                    ingresoRepositorio.save(ingreso);
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

    public boolean borrarIngreso(Ingreso ingreso) {
        try {
            if (ingreso != null) {
                ingresoRepositorio.delete(ingreso);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean modificarIngreso(Ingreso ingreso, Categoria categoria, String montoCadena) {
        try {
            if (ingreso != null) {
                double monto = Double.parseDouble(montoCadena);
                ingresoRepositorio.editarIngreso(categoria, monto, ingreso.getId_ingreso());
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Double getSumaTotalPorUsuario(long usuarioId) {
        Double total = ingresoRepositorio.obtenerSumaTotalPorUsuario(usuarioId);
        return total != null ? total : 0.0;
    }

    public Long getCantidadIngresosPorUsuario(Long usuarioId) {
        Long cantidad = ingresoRepositorio.contarTodosLosIngresos(usuarioId);
        return cantidad != null ? cantidad : 0L;
    }

    public Long getCantidadUltimaSemana(Long usuarioId) {
        Long cantidad = ingresoRepositorio.contarIngresosUltimaSemana(usuarioId);
        return cantidad != null ? cantidad : 0L;
    }

    public Double calcularPorcentajeCambio(Long idUsuario) {
        Double actual = ingresoRepositorio.obtenerSumaSemanaActual(idUsuario);
        Double anterior = ingresoRepositorio.obtenerSumaSemanaAnterior(idUsuario);

        actual = (actual != null) ? actual : 0.0;
        anterior = (anterior != null && anterior != 0) ? anterior : 0.0;

        if (anterior == 0) {
            // Evitar división por cero
            return actual > 0 ? 100.0 : 0.0;
        }

        return ((actual - anterior) / anterior) * 100;
    }
}
