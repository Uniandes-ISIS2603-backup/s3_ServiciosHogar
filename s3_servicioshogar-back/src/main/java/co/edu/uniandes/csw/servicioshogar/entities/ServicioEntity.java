/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un servicio en la persistencia y permite su
 * serialización.
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Entity
public class ServicioEntity extends BaseEntity implements Serializable{
    
    private String descripcion, requerimientos;
    
    @PodamExclude
    @ManyToOne
    private SolicitudEntity solicitud;

    /**
     * Devuelve la descripcion del servicio.
     * @return descripcion del servicio.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripcion del servicio.
     * @param descripcion. Nueva descripcion.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve los requerimientos del servicio.
     * @return los requerimientos del servio.
     */
    public String getRequerimientos() {
        return requerimientos;
    }

    /**
     * Modifica los requerimientos del servicio.
     * @param requerimientos. Nuevos requerimientos.
     */
    public void setRequerimientos(String requerimientos) {
        this.requerimientos = requerimientos;
    }
    
    /**
     * Devuelve la solicitud a la que pertenece el servicio.
     *
     * @return Una entidad de solicitud.
     */
    public SolicitudEntity getSolicitud() {
        return solicitud;
    }

    /**
     * Modifica la solicitud a la que pertenece el servicio.
     *
     * @param solicitudEntity La nueva solicitud.
     */
    public void setSolicitud(SolicitudEntity solicitudEntity) {
        this.solicitud = solicitudEntity;
    }
}