package com.company.paywho.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ingreso")
public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_ingreso;
    private long id_usuario;
    private String fecha;
    private double monto;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    public Ingreso() {
    }

    public Ingreso(Categoria categoria, String fecha, long id_usuario, double monto) {
        this.categoria = categoria;
        this.fecha = fecha;
        this.id_usuario = id_usuario;
        this.monto = monto;
    }

    public long getId_ingreso() {
        return id_ingreso;
    }

    public void setId_ingreso(long id_ingreso) {
        this.id_ingreso = id_ingreso;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public long getId_Usuario() {
        return id_usuario;
    }

    public void setId_Usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

}
