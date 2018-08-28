/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
public class ServicioDTO {
    
    private Long id;
    private String descripcion, requerimientos;
    private Double calificacion;
    
    public ServicioDTO() {
    }
    
    public ServicioDTO(ServicioEntity servicioEntity){
        if(servicioEntity!=null){
            this.id=servicioEntity.getId();
            this.descripcion=servicioEntity.getDescripcion();
            this.requerimientos=servicioEntity.getRequerimientos();
            this.calificacion=servicioEntity.getCalificacion();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(String requerimientos) {
        this.requerimientos = requerimientos;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
    
    public ServicioEntity toenEntity(){
        ServicioEntity servicioEntity = new ServicioEntity();
        servicioEntity.setId(this.id);
        servicioEntity.setDescripcion(this.descripcion);
        servicioEntity.setRequerimientos(this.requerimientos);
        servicioEntity.setCalificacion(calificacion);
        return servicioEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}