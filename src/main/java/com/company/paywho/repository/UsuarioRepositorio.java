package com.company.paywho.repository;

import com.company.paywho.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.correo_electronico = :correo AND u.contrasena = :contrasena")
    Optional<Usuario> findByCorreoAndContrasena(@Param("correo") String correo, @Param("contrasena") String contrasena);
   
}
