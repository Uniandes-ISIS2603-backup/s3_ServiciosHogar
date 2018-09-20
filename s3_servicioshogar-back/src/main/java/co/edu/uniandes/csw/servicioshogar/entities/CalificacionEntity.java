/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Carlos Eduardo Robles
 */
@Entity
public class CalificacionEntity  extends BaseEntity implements Serializable
{
    private Double calificacion;
    private String comentario;
    
    @PodamExclude
    @OneToOne
    private ServicioEntity servicio;

    public Double getCalificacion() {return calificacion;}

    public void setCalificacion(Double calificacion) {this.calificacion = calificacion;}

    public String getComentario() {return comentario;}

    public void setComentario(String comentario) {this.comentario = comentario;}

    public ServicioEntity getServicio() {return servicio;}

    public void setServicio(ServicioEntity servicio) {this.servicio = servicio;}
    
}
