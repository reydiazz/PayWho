package com.company.paywho.repository;

import com.company.paywho.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.correo_electronico = :correo AND u.contrasena = :contrasena")
    public Optional<Usuario> buscarUsuarioContrasenaCorreo(@Param("correo") String correo, @Param("contrasena") String contrasena);

    @Query("SELECT u FROM Usuario u WHERE u.id_usuario = :id AND u.contrasena = :contrasena")
    public Optional<Usuario> buscarUsuarioIDContrasena(@Param("id") long id, @Param("contrasena") String contrasena);

    @Query("SELECT u FROM Usuario u WHERE u.correo_electronico = :correo")
    public Optional<Usuario> buscarUsuarioCorreo(@Param("correo") String correo);

    @Transactional
    @Modifying
    @Query("UPDATE Usuario u SET u.nombre = :nombre, u.apellido = :apellido, u.correo_electronico = :correo_electronico, u.saldo = :saldo, u.porcentaje_ahorro = :porcentaje_ahorro WHERE u.id_usuario = :id_usuario")
    public void editarUsuario(@Param("nombre") String nombre, @Param("apellido") String apellido, @Param("correo_electronico") String correo_electronico, @Param("saldo") long saldo, @Param("id_usuario") long id_usuario , @Param("porcentaje_ahorro") double porcentaje_ahorro);

    @Transactional
    @Modifying
    @Query("UPDATE Usuario u SET u.ruta_img = :ruta_img WHERE u.id_usuario = :id_usuario")
    public void editarUsuarioImagenRuta(@Param("ruta_img") String ruta_img, @Param("id_usuario") long id_usuario);

}
