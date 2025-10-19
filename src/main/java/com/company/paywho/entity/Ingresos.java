package com.company.paywho.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Ingresos")
public class Ingresos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_ingreso;
    private long id_C;
    private Date fecha;
    private long id_Usuario;
    private double monto;
    
    public Ingresos(){        
    }

    public Ingresos(long id_C, Date fecha, long id_Usuario, double monto) {        
        this.id_C = id_C;
        this.fecha = fecha;
        this.id_Usuario = id_Usuario;
        this.monto = monto;
    }

    public long getId_ingreso() {
        return id_ingreso;
    }

    public void setId_ingreso(long id_ingreso) {
        this.id_ingreso = id_ingreso;
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

    public long getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(long id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    
    
}
