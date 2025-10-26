package com.company.paywho.repository;

import com.company.paywho.entity.Meta;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MetaRepositorio extends JpaRepository<Meta, Long> {

    @Query("SELECT m FROM Meta m WHERE m.id_usuario =:id_usuario")
    public Optional<Meta> buscarMetaAhorroIDUsuario(@Param("id_usuario") long id_usuario);

    @Query("SELECT m FROM Meta m WHERE m.id_usuario =:id_usuario")
    public List<Meta> obtenerMetaAhorroIDUsuario(@Param("id_usuario") long id_usuario);

    @Transactional
    @Modifying
    @Query("UPDATE Meta m SET m.monto_actual =:monto_actual WHERE m.id_usuario =:id_usuario")
    public void actualizarMontoMetaAhorro(@Param("monto_actual") double monto_actual, @Param("id_usuario") long id_usuario);

    @Transactional
    @Modifying
    @Query("UPDATE Meta m SET m.nombre =:nombre, m.monto_actual =:monto_actual, m.meta =:meta WHERE m.id_usuario =:id_usuario")
    public void actualizarMetaAhorro(@Param("nombre") String nombre, @Param("monto_actual") double monto_actual, @Param("meta") double meta, @Param("id_usuario") long id_usuario);

}
