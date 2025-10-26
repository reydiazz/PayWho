package com.company.paywho.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Meta")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_meta;
    private long id_usuario;
    private double meta;
    private double monto_actual;
    private String nombre;

    public Meta() {
    }

    public Meta(long id_usuario, double meta, double monto_actual, String nombre) {
        this.id_usuario = id_usuario;
        this.meta = meta;
        this.monto_actual = monto_actual;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getId_meta() {
        return id_meta;
    }

    public void setId_meta(long id_meta) {
        this.id_meta = id_meta;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public double getMeta() {
        return meta;
    }

    public void setMeta(double meta) {
        this.meta = meta;
    }

    public double getMonto_actual() {
        return monto_actual;
    }

    public void setMonto_actual(double monto_actual) {
        this.monto_actual = monto_actual;
    }

}
