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
    private Usuario usuarioActual;

    public boolean validarUsuario(String correo_electronico, String contrasena) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreoAndContrasena(correo_electronico, Utilidades.sha256(contrasena));
        if (usuario.isPresent()) {
            usuarioActual = usuario.get();
            return true;
        }
        return false;
    }

    public boolean registrarUsuario(String nombre, String apellido, String correo_electronico, String contrasena, String balanceCadena) {
        String sha256Contrasena = Utilidades.sha256(contrasena);
        try {
            long balance = Long.parseLong(balanceCadena);
            Usuario usuario = new Usuario(nombre, apellido, correo_electronico, sha256Contrasena, balance);
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public UsuarioRepositorio getUsuarioRepository() {
        return usuarioRepository;
    }

    public void setUsuarioRepository(UsuarioRepositorio usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

}
