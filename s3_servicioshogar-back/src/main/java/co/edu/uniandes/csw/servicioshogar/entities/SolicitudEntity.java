/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

/**
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
public class SolicitudEntity extends BaseEntity{
    
    private Integer noSolicitud;
    private String fecha, direccion;

    public Integer getNoSolicitud() {
        return noSolicitud;
    }

    public void setNoSolicitud(Integer noSolicitud) {
        this.noSolicitud = noSolicitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
