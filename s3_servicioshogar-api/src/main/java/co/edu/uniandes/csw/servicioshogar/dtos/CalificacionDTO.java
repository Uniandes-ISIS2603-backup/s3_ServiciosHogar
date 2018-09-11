/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.CalificacionEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Carlos Eduardo Robles
 */
public class CalificacionDTO implements Serializable
{
    private Long id;
    private Double calificacion;
    private String comentario;
    
    private ClienteDTO cliente;
    
    public CalificacionDTO (){}
    
    public CalificacionDTO(CalificacionEntity calificacionEntity)
    {
        if(calificacionEntity != null)
        {
            this.id = calificacionEntity.getId();
            this.calificacion = calificacionEntity.getCalificacion();
            this.comentario = calificacionEntity.getComentario();
            if(calificacionEntity.getCliente() != null)
                this.cliente = new ClienteDTO(calificacionEntity.getCliente());
            else
                this.cliente = null;
        }
    }
    
    public CalificacionEntity toEntity() {
        CalificacionEntity calificacionEntity = new CalificacionEntity();
        calificacionEntity.setId(this.id);
        calificacionEntity.setCalificacion(this.calificacion);
        calificacionEntity.setComentario(this.comentario);
        if (this.cliente != null) 
            calificacionEntity.setCliente(this.cliente.toEntity());
        
        return calificacionEntity;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Double getCalificacion() {return calificacion;}

    public void setCalificacion(Double calificacion) {this.calificacion = calificacion;}

    public String getComentario() {return comentario;}

    public void setComentario(String comentario) {this.comentario = comentario;}

    public ClienteDTO getCliente() {return cliente;}

    public void setCliente(ClienteDTO cliente) {this.cliente = cliente;}   
    
    @Override
    public String toString() {return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);}
}
