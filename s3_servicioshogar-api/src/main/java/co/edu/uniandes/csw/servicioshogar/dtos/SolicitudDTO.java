/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class SolicitudDTO {
    
    private Long id;
    private Integer noSolicitud;
    private String fecha, direccion;

    public SolicitudDTO() {
    }
    
    public SolicitudDTO(SolicitudEntity solicitudEntity) {
        if (solicitudEntity != null) {
            this.id = solicitudEntity.getId();
            this.noSolicitud = solicitudEntity.getNoSolicitud();
            this.fecha = solicitudEntity.getFecha();
            this.direccion = solicitudEntity.getDireccion();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
    public SolicitudEntity toEntity() {
        SolicitudEntity solicitudEntity = new SolicitudEntity();
        solicitudEntity.setId(this.id);
        solicitudEntity.setNoSolicitud(this.noSolicitud);
        solicitudEntity.setDireccion(this.fecha);
        solicitudEntity.setDireccion(this.direccion);
        return solicitudEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
