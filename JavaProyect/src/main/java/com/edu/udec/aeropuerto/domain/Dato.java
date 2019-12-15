package com.edu.udec.aeropuerto.domain;

import com.edu.udec.aeropuerto.domain.enums.RangoRuido;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "datos")
public class Dato {


    @Transient
    public static final String SEQUENCE_NAME = "datos_sequence";

    @Id
    private long id;
    private double decibeles;
    private RangoRuido clasificacionRuido;
    private Date fecha_creacion;

    public Dato() {
    }

    public Dato(Double valueOf, Date date) {
        this.decibeles = valueOf;
        this.fecha_creacion = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getDecibeles() {
        return decibeles;
    }

    public void setDecibeles(double decibeles) {
        this.decibeles = decibeles;
    }

    public RangoRuido getClasificacionRuido() {
        return clasificacionRuido;
    }

    public void setClasificacionRuido(RangoRuido clasificacionRuido) {
        this.clasificacionRuido = clasificacionRuido;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    @Override
    public String toString() {
        return "Dato{" +
                "id=" + id +
                ", decibeles=" + decibeles +
                ", clasificacionRuido=" + clasificacionRuido +
                ", fecha_creacion=" + fecha_creacion +
                '}';
    }
}
