package com.company.paywho.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Ahorro")
public class Ahorro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_ahorro;
    private long id_C;
    private Date fecha;
    private long id_usuario;
    private double monto;

    public Ahorro() {
    }

    public Ahorro(long id_C, Date fecha, long id_usuario, double monto) {
        this.id_C = id_C;
        this.fecha = fecha;
        this.id_usuario = id_usuario;
        this.monto = monto;
    }

    public long getId_ahorro() {
        return id_ahorro;
    }

    public void setId_ahorro(long id_ahorro) {
        this.id_ahorro = id_ahorro;
    }

    public long getId_C() {
        return id_C;
    }

    public void setId_C(long id_C) {
        this.id_C = id_C;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
