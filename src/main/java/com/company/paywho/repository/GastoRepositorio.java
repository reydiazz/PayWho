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

    @Transactional
    @Modifying
    @Query("UPDATE Gasto g SET g.categoria = :categoria, g.monto = :monto WHERE g.id_gasto = :id_gasto")
    public void editarGasto(@Param("categoria") Categoria categoria, @Param("monto") double monto, @Param("id_gasto") long id_gasto);

    @Query("SELECT g FROM Gasto g WHERE g.id_usuario =:id_usuario")
    public List<Gasto> buscarGastoIDUsuario(@Param("id_usuario") long id_usuario);
}
