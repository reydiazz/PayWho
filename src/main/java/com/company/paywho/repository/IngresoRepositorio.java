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

    @Transactional
    @Modifying
    @Query("UPDATE Ingreso i SET i.categoria = :categoria, i.monto = :monto WHERE i.id_ingreso = :id_ingreso")
    public void updateEarning(@Param("categoria") Categoria categoria, @Param("monto") double monto, @Param("id_ingreso") long id_ingreso);

    @Query("SELECT i FROM Ingreso i WHERE i.id_usuario =:id_usuario")
    public List<Ingreso> findByIDUserEarning(@Param("id_usuario") long id_usuario);

}
