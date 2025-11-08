package com.company.paywho.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;

@Entity
@Table(name = "Gasto")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_gasto;
    private long id_usuario;
    private String fecha;
    private double monto;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    public Gasto() {
    }

    public Gasto(Categoria categoria, String fecha, long id_usuario, double monto) {
        this.categoria = categoria;
        this.fecha = fecha;
        this.id_usuario = id_usuario;
        this.monto = monto;
    }

    public long getId_gasto() {
        return id_gasto;
    }

    public void setId_gasto(long id_gasto) {
        this.id_gasto = id_gasto;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    
    
    
}
