package com.company.paywho.service;

import com.company.paywho.entity.Usuario;
import com.company.paywho.model.Utilidades;
import com.company.paywho.repository.UsuarioRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public boolean validarUsuario(String correo_electronico, String contrasena) {
        Optional<Usuario> usuario = usuarioRepositorio.findByCorreoAndContrasena(correo_electronico, Utilidades.sha256(contrasena));
        if (usuario.isPresent()) {
            SesionServicio.iniciarSesion(usuario.get());
            return true;
        }
        return false;
    }

    public boolean registrarUsuario(String nombre, String apellido, String correo_electronico, String contrasena, String balanceCadena) {
        String sha256Contrasena = Utilidades.sha256(contrasena);
        try {
            long balance = Long.parseLong(balanceCadena);
            Usuario usuario = new Usuario(nombre, apellido, correo_electronico, sha256Contrasena, balance);
            usuarioRepositorio.save(usuario);
            validarUsuario(correo_electronico, contrasena);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
