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
    private double m_meta;
    private double m_logrado;

    public MetaAhorro() {
    }

    public MetaAhorro(long id_usuario, double m_meta, double m_logrado) {
        this.id_usuario = id_usuario;
        this.m_meta = m_meta;
        this.m_logrado = m_logrado;
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

    public double getM_meta() {
        return m_meta;
    }

    public void setM_meta(double m_meta) {
        this.m_meta = m_meta;
    }

    public double getM_logrado() {
        return m_logrado;
    }

    public void setM_logrado(double m_logrado) {
        this.m_logrado = m_logrado;
    }
}
