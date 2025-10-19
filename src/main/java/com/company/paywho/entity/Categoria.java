package com.company.paywho.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_C;
    private long id_usuario;
    private String nombre;
    private String tipo;

    public Categoria() {
    }

    public Categoria(long id_usuario, String nombre, String tipo) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public long getId_C() {
        return id_C;
    }

    public void setId_C(long id_C) {
        this.id_C = id_C;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
