package com.company.paywho.repository;

import com.company.paywho.entity.Ahorro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AhorroRepositorio extends JpaRepository<Ahorro, Long> {

    @Query("SELECT a FROM Ahorro a WHERE a.id_usuario =:id_usuario")
    public List<Ahorro> findByIDUserSaving(@Param("id_usuario") long id_usuario);

}
