/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import java.io.Serializable;

/**
 *
 * @author Carlos Eduardo Robles
 */
public class ServicioDetailDTO extends ServicioDTO implements Serializable 
{
    private CalificacionDTO calificacion;
    
    public ServicioDetailDTO(){super();}
    
    public ServicioDetailDTO(ServicioEntity servicioEntity)
    {
        super(servicioEntity);
        if(servicioEntity.getCalificacion() != null)
            this.calificacion = new CalificacionDTO(servicioEntity.getCalificacion());
    }

    public CalificacionDTO getCalificacion() { return calificacion;}

    public void setCalificacion(CalificacionDTO calificacion) {this.calificacion = calificacion;}   
    
    @Override
    public ServicioEntity toEntity() {
        ServicioEntity entity = super.toEntity();
        if (getCalificacion() != null) {
            entity.setCalificacion(getCalificacion().toEntity());
        }
        return entity;
    }
}
