package com.company.paywho.repository;

import com.company.paywho.entity.Ahorro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AhorroRepositorio extends JpaRepository<Ahorro, Long> {

    @Query("SELECT a FROM Ahorro a WHERE a.id_usuario =:id_usuario")
    public List<Ahorro> buscarAhorroID(@Param("id_usuario") long id_usuario);

    @Query(value = "SELECT SUM(monto) FROM Ahorro WHERE id_usuario = :id_usuario", nativeQuery = true)
    public Double obtenerSumaTotalPorUsuario(@Param("id_usuario") long idUsuario);

    @Query(value = "SELECT COUNT(*) FROM Ahorro WHERE id_usuario=:id_usuario", nativeQuery = true)
    public Long contarTodosLosAhorros(@Param("id_usuario") long idUsuario);

    @Query(value = """
    SELECT substr(fecha, 4, 2) AS mes, SUM(monto)
    FROM Ahorro
    WHERE id_usuario = :id_usuario
    GROUP BY substr(fecha, 4, 2)
    ORDER BY mes
""", nativeQuery = true)
    List<Object[]> obtenerAhorrosMensuales(@Param("id_usuario") Long idUsuario);

}
