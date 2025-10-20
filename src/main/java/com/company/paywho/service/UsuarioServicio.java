package com.company.paywho.service;

import com.company.paywho.entity.Usuario;
import com.company.paywho.model.ArchivoServicio;
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
    
    public boolean editarImagenUsuario(long idUsuario, String rutaImg) {
        try {
            usuarioRepositorio.updateImgRute(rutaImg, idUsuario);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean validarUsuarioContrasena(long idUsuario, String contrasena) {
        Optional<Usuario> usuario = usuarioRepositorio.findByIDAndContrasena(idUsuario, Utilidades.sha256(contrasena));
        if (usuario.isPresent()) {
            return true;
        }
        return false;
    }
    
    public boolean editarUsuario(String nombre, String apellido, String correo_electronico, String saldoCadena, long id_usuario) {
        try {
            long saldo = Long.parseLong(saldoCadena);
            usuarioRepositorio.updateUser(nombre, apellido, correo_electronico, saldo, id_usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean registrarUsuario(String nombre, String apellido, String correo_electronico, String contrasena, String balanceCadena) {
        String sha256Contrasena = Utilidades.sha256(contrasena);
        try {
            long balance = Long.parseLong(balanceCadena);
            Usuario usuario = new Usuario(nombre, apellido, correo_electronico, sha256Contrasena, balance, ArchivoServicio.getInstancia().getRuta("img_perfil_predeterminado"));
            usuarioRepositorio.save(usuario);
            validarUsuario(correo_electronico, contrasena);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
}
