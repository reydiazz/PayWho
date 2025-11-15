package com.company.paywho.repository;

import com.company.paywho.entity.Categoria;
import com.company.paywho.entity.Gasto;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GastoRepositorio extends JpaRepository<Gasto, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Gasto g WHERE g.categoria.id = :idCategoria")
    void deleteByCategoria(@Param("idCategoria") Long idCategoria);

    @Transactional
    @Modifying
    @Query("UPDATE Gasto g SET g.categoria = :categoria, g.monto = :monto WHERE g.id_gasto = :id_gasto")
    public void editarGasto(@Param("categoria") Categoria categoria, @Param("monto") double monto, @Param("id_gasto") long id_gasto);

    @Query("SELECT g FROM Gasto g WHERE g.id_usuario =:id_usuario")
    public List<Gasto> buscarGastoIDUsuario(@Param("id_usuario") long id_usuario);

    @Query(value = "SELECT SUM(monto) FROM Gasto WHERE id_usuario = :id_usuario", nativeQuery = true)
    public Double obtenerSumaTotalPorUsuario(@Param("id_usuario") long idUsuario);

    @Query(value = "SELECT COUNT(*) FROM Gasto WHERE id_usuario=:id_usuario", nativeQuery = true)
    public Long contarTodosLosGastos(@Param("id_usuario") long idUsuario);

    @Query(value = """
    SELECT COUNT(*) 
    FROM Gasto
    WHERE id_usuario = :id_usuario
    AND date(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2)) >= date('now', '-7 day')
""", nativeQuery = true)
    public Long contarGastoUltimaSemana(@Param("id_usuario") Long idUsuario);

    @Query(value = """
        SELECT SUM(monto)
        FROM Gasto
        WHERE id_usuario = :id_usuario
        AND date(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2)) >= date('now', '-7 day')
    """, nativeQuery = true)
    public Double obtenerSumaSemanaActual(@Param("id_usuario") Long idUsuario);

    @Query(value = """
        SELECT SUM(monto)
        FROM Gasto
        WHERE id_usuario = :id_usuario
        AND date(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2)) < date('now', '-7 day')
        AND date(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2)) >= date('now', '-14 day')
    """, nativeQuery = true)
    public Double obtenerSumaSemanaAnterior(@Param("id_usuario") Long idUsuario);

    @Query(value = """
    SELECT substr(fecha, 4, 2) AS mes, SUM(monto)
    FROM Gasto
    WHERE id_usuario = :id_usuario
    GROUP BY substr(fecha, 4, 2)
    ORDER BY mes
""", nativeQuery = true)
    List<Object[]> obtenerGastosMensuales(@Param("id_usuario") Long idUsuario);
}
