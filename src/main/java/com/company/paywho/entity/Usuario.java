package com.company.paywho.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_usuario;
    private String nombre;
    private String apellido;
    private String correo_electronico;
    private String contrasena;
    private double saldo;
    private String ruta_img;
    private double porcentaje_ahorro;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String correo_electronico, String contrasena, long saldo, String ruta_img, double porcentaje_ahorro) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo_electronico = correo_electronico;
        this.contrasena = contrasena;
        this.saldo = saldo;
        this.ruta_img = ruta_img;
        this.porcentaje_ahorro = porcentaje_ahorro;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }

    public String getNombreApellido() {
        return nombre + " " + apellido;
    }

    public String getRuta_img() {
        return ruta_img;
    }

    public void setRuta_img(String ruta_img) {
        this.ruta_img = ruta_img;
    }

    public double getPorcentaje_ahorro() {
        return porcentaje_ahorro;
    }

    public void setPorcentaje_ahorro(double porcentaje_ahorro) {
        this.porcentaje_ahorro = porcentaje_ahorro;
    }

}
