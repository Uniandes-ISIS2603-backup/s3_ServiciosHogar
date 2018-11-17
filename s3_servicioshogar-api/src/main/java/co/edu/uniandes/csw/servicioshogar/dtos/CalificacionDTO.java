/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.CalificacionEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 *
 * @author Carlos Eduardo Robles
 */
public class CalificacionDTO implements Serializable
{
    private Long id;
    private String comentario;
    private Double calificacion;
    
    private ServicioDTO servicio;
    
    public CalificacionDTO(){}
    
    public CalificacionDTO(CalificacionEntity calificacionEntity)
    {
        if(calificacionEntity != null)
        {
            this.id = calificacionEntity.getId();
            this.calificacion = calificacionEntity.getCalificacion();
            this.comentario = calificacionEntity.getComentario();

        }
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getComentario() {return comentario;}

    public void setComentario(String comentario) {this.comentario = comentario;}

    public Double getCalificacion() {return calificacion;}

    public void setCalificacion(Double calificacion) {this.calificacion = calificacion;}

    public ServicioDTO getServicio() {return servicio;}

    public void setServicio(ServicioDTO servicio) {this.servicio = servicio;}      
    
    public CalificacionEntity toEntity() 
    {
        CalificacionEntity calificacionEntity = new CalificacionEntity();
        calificacionEntity.setId(this.id);
        calificacionEntity.setComentario(this.comentario);
        calificacionEntity.setCalificacion(this.calificacion);

        return calificacionEntity;
    }
    
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    } 
    
}
