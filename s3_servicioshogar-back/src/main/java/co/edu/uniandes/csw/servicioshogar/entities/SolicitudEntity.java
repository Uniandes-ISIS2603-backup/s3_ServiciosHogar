/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 * Clase que representa una solicitud en la persistencia y permite su
 * serialización.
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Entity
public class SolicitudEntity extends BaseEntity implements Serializable{
    
    private String fecha, direccion;

    /**
     * Devuelve la fecha de la solicitud.
     * @return fecha de la solicitud.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * modifica la fecha de la solicitud.
     * @param fecha. Nueva fecha.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve la direccion de la solicitud.
     * @return direccion de la solicitud.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Modifica la direccion de la solicitud.
     * @param direccion.Nueva direccion.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
