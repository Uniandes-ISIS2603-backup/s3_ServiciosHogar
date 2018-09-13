/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
public class SolicitudDetailDTO extends SolicitudDTO implements Serializable{
        /*
    * Esta lista de tipo ServicioDTO contiene los servicios que estan asociados a una solicitud
     */
    private List<ServicioDTO> servicios;

    /**
     * Constructor por defecto
     */
    public SolicitudDetailDTO() {
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param solicitudEntity La entidad de la solicitud para transformar a DTO.
     */
    public SolicitudDetailDTO(SolicitudEntity solicitudEntity) {
        super(solicitudEntity);
        if (solicitudEntity != null) {
            if (solicitudEntity.getServicios() != null) {
                servicios = new ArrayList<>();
                for (ServicioEntity entityServicio : solicitudEntity.getServicios()) {
                    servicios.add(new ServicioDTO(entityServicio));
                }
            }
        }
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la solicitud para transformar a Entity
     */
    @Override
    public SolicitudEntity toEntity() {
        SolicitudEntity solicitudEntity = super.toEntity();
        if (servicios != null) {
            List<ServicioEntity> serviciosEntity = new ArrayList<>();
            for (ServicioDTO dtoServicio : servicios) {
                serviciosEntity.add(dtoServicio.toEntity());
            }
            solicitudEntity.setServicios(serviciosEntity);
        }
        return solicitudEntity;
    }

    /**
     * Devuelve la lista de libros de la solicitud.
     *
     * @return the servicios
     */
    public List<ServicioDTO> getServicios() {
        return servicios;
    }

    /**
     * Modifica la lista de libros de la solicitud.
     *
     * @param servicios the servicios to set
     */
    public void setServicios(List<ServicioDTO> servicios) {
        this.servicios = servicios;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
