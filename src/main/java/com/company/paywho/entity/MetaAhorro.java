package com.company.paywho.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MetaAhorro")
public class MetaAhorro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_meta;
    private long id_usuario;
    private double meta;
    private double monto_logrado;

    public MetaAhorro() {
    }

    public MetaAhorro(long id_usuario, double meta, double monto_logrado) {
        this.id_usuario = id_usuario;
        this.meta = meta;
        this.monto_logrado = monto_logrado;
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

    public double getMonto_logrado() {
        return monto_logrado;
    }

    public void setMonto_logrado(double monto_logrado) {
        this.monto_logrado = monto_logrado;
    }

    
}
