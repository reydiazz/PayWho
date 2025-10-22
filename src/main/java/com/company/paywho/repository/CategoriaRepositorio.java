package com.company.paywho.repository;

import com.company.paywho.entity.Categoria;
import com.company.paywho.entity.Usuario;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

    @Query("SELECT c FROM Categoria c WHERE c.id_usuario =:id_usuario")
    public List<Categoria> findByIDCategory(@Param("id_usuario") long id_usuario);

    @Query("SELECT c FROM Categoria c WHERE c.id_usuario =:id_usuario AND c.nombre =:nombre AND c.tipo =:tipo")
    public Optional<Categoria> findByNameCategory(@Param("id_usuario") long id_usuario, @Param("nombre") String nombre, @Param("tipo") String tipo);

    @Transactional
    @Modifying
    @Query("UPDATE Categoria c SET c.nombre = :nombre, c.tipo = :tipo WHERE c.id_categoria = :id_categoria")
    public void updateCategory(@Param("nombre") String nombre, @Param("tipo") String tipo, @Param("id_categoria") long id_categoria);
}
