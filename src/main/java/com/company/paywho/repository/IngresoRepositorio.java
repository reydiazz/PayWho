package com.company.paywho.repository;

import com.company.paywho.entity.Categoria;
import com.company.paywho.entity.Ingreso;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IngresoRepositorio extends JpaRepository<Ingreso, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Ingreso i WHERE i.categoria.id = :idCategoria")
    void deleteByCategoria(@Param("idCategoria") Long idCategoria);

    @Transactional
    @Modifying
    @Query("UPDATE Ingreso i SET i.categoria = :categoria, i.monto = :monto WHERE i.id_ingreso = :id_ingreso")
    public void editarIngreso(@Param("categoria") Categoria categoria, @Param("monto") double monto, @Param("id_ingreso") long id_ingreso);

    @Query("SELECT i FROM Ingreso i WHERE i.id_usuario =:id_usuario")
    public List<Ingreso> buscarIngresoIDUsuario(@Param("id_usuario") long id_usuario);

    @Query(value = "SELECT SUM(monto) FROM Ingreso WHERE id_usuario = :id_usuario", nativeQuery = true)
    public Double obtenerSumaTotalPorUsuario(@Param("id_usuario") long idUsuario);

    @Query(value = "SELECT COUNT(*) FROM Ingreso WHERE id_usuario=:id_usuario", nativeQuery = true)
    public Long contarTodosLosIngresos(@Param("id_usuario") long idUsuario);

    @Query(value = """
    SELECT COUNT(*) 
    FROM Ingreso
    WHERE id_usuario = :id_usuario
    AND date(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2)) >= date('now', '-7 day')
""", nativeQuery = true)
    public Long contarIngresosUltimaSemana(@Param("id_usuario") Long idUsuario);

    @Query(value = """
        SELECT SUM(monto)
        FROM Ingreso
        WHERE id_usuario = :id_usuario
        AND date(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2)) >= date('now', '-7 day')
    """, nativeQuery = true)
    public Double obtenerSumaSemanaActual(@Param("id_usuario") Long idUsuario);

    @Query(value = """
        SELECT SUM(monto)
        FROM Ingreso
        WHERE id_usuario = :id_usuario
        AND date(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2)) < date('now', '-7 day')
        AND date(substr(fecha, 7, 4) || '-' || substr(fecha, 4, 2) || '-' || substr(fecha, 1, 2)) >= date('now', '-14 day')
    """, nativeQuery = true)
    public Double obtenerSumaSemanaAnterior(@Param("id_usuario") Long idUsuario);

    @Query(value = """
    SELECT substr(fecha, 4, 2) AS mes, SUM(monto)
    FROM Ingreso
    WHERE id_usuario = :id_usuario
    GROUP BY substr(fecha, 4, 2)
    ORDER BY mes
""", nativeQuery = true)
    List<Object[]> obtenerIngresosMensuales(@Param("id_usuario") Long idUsuario);

}
