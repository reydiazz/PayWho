package com.company.paywho.service;

import com.company.paywho.entity.Usuario;
import com.company.paywho.model.Utilidades;
import com.company.paywho.repository.UsuarioRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    public boolean validarUsuario(String correo_electronico, String contrasena) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreoAndContrasena(correo_electronico, Utilidades.sha256(contrasena));
        return usuario.isPresent();
    }

    public boolean registrarUsuario(String nombre, String apellido, String correo_electronico, String contrasena, Long balance) {
        String sha256Contrasena = Utilidades.sha256(contrasena);
        try {
            Usuario usuario = new Usuario(nombre, apellido, correo_electronico, sha256Contrasena, balance);
            usuarioRepository.save(usuario);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
