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
        String contrasenaHash = Utilidades.sha256(contrasena);
        Optional<Usuario> usuario = usuarioRepositorio.buscarUsuarioContrasenaCorreo(correo_electronico, contrasenaHash);
        if (usuario.isPresent()) {
            SesionServicio.iniciarSesion(usuario.get());
            return true;
        }
        return false;
    }

    public Usuario obtenerUsuarioID(long id_usuario) {
        return usuarioRepositorio.findById(id_usuario).get();
    }

    public boolean validarUsuarioContrasena(long idUsuario, String contrasena) {
        Optional<Usuario> usuario = usuarioRepositorio.buscarUsuarioIDContrasena(idUsuario, Utilidades.sha256(contrasena));
        return usuario.isPresent();
    }

    public boolean validarCorreoElectronico(String correo) {
        Optional<Usuario> usuario = usuarioRepositorio.buscarUsuarioCorreo(correo);
        return usuario.isPresent();
    }

    public boolean registrarUsuario(String nombre, String apellido, String correo_electronico, String contrasena) {
        try {
            String contrasenaHash = Utilidades.sha256(contrasena);
            Usuario usuario = new Usuario(nombre, apellido, correo_electronico, contrasenaHash, "SIN DEFINIR", 0);
            usuarioRepositorio.save(usuario);
            validarUsuario(correo_electronico, contrasena);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean editarUsuario(String nombre, String apellido, String correo_electronico, long id_usuario, String porcentajeCadena) {
        try {
            double porcentajeAhorro = Double.parseDouble(porcentajeCadena);
            if (porcentajeAhorro >= 0 && porcentajeAhorro <= 100) {
                usuarioRepositorio.editarUsuario(nombre, apellido, correo_electronico, id_usuario, porcentajeAhorro);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean editarImagenUsuario(long idUsuario, String rutaImg) {
        try {
            usuarioRepositorio.editarUsuarioImagenRuta(rutaImg, idUsuario);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
