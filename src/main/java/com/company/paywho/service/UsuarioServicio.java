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

    public boolean registrarUsuario(String nombre, String apellido, String correo_electronico, String contrasena, String balanceCadena) {
        try {
            String contrasenaHash = Utilidades.sha256(contrasena);
            long balance = Long.parseLong(balanceCadena);
            if (balance > 0) {
                Usuario usuario = new Usuario(nombre, apellido, correo_electronico, contrasenaHash, balance, "SIN DEFINIR", 0);
                usuarioRepositorio.save(usuario);
                validarUsuario(correo_electronico, contrasena);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean editarUsuario(String nombre, String apellido, String correo_electronico, String saldoCadena, long id_usuario, String porcentajeCadena) {
        try {
            double saldo = Double.parseDouble(saldoCadena);
            double porcentajeAhorro = Double.parseDouble(porcentajeCadena);
            if (saldo > 0 && porcentajeAhorro >= 0 && porcentajeAhorro <= 100) {
                usuarioRepositorio.editarUsuario(nombre, apellido, correo_electronico, saldo, id_usuario, porcentajeAhorro);
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

    public boolean aumentarBalanceUsuario(long idUsuario, String montoAumentarString) {
        try {
            double montoAumentar = Double.parseDouble(montoAumentarString);
            double nuevoMonto = SesionServicio.getUsuarioActual().getSaldo() + montoAumentar;
            usuarioRepositorio.editarSaldoUsuario(nuevoMonto, idUsuario);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean disminuirBalanceUsuario(long idUsuario, String montoAumentarString) {
        try {
            double montoAumentar = Double.parseDouble(montoAumentarString);
            double nuevoMonto = SesionServicio.getUsuarioActual().getSaldo() - montoAumentar;
            usuarioRepositorio.editarSaldoUsuario(nuevoMonto, idUsuario);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
