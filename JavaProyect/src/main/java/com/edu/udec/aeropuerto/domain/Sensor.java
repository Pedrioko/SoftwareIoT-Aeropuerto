/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.udec.aeropuerto.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PedroD
 */
@Document(collection = "sensors")
public class Sensor {


    @Transient
    public static final String SEQUENCE_NAME = "sensors_sequence";
    @Id
    private Long id;
    private String nombre;
    private String descripcion;
    private String puerto;
    private List<Dato> historial = new ArrayList<>();

    public Sensor() {
        this.id = -1L;
    }

    public Sensor(String nombre, String descripcion, String puerto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puerto = puerto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public List<Dato> getHistorial() {
        return historial;
    }

    public void setHistorial(List<Dato> historial) {
        this.historial = historial;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", puerto='" + puerto + '\'' +
                ", historial=" + historial +
                '}';
    }
}
