/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Eduardo Robles Quevedo
 */
public class ClienteDetailDTO extends ClienteDTO implements Serializable
{
    private List<SolicitudDTO> solicitudes;
    
    private ClienteDetailDTO(){super();}
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param clienteEntity La entidad de la cual se construye el DTO
     */
    public ClienteDetailDTO(ClienteEntity clienteEntity) 
    {
        super(clienteEntity);
        if (clienteEntity.getSolicitudes() != null) 
        {
            solicitudes = new ArrayList<>();
            for (SolicitudEntity entitySolicitud : clienteEntity.getSolicitudes()) 
                solicitudes.add(new SolicitudDTO(entitySolicitud));
            
        }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el cliente.
     */
    @Override
    public ClienteEntity toEntity() {
        ClienteEntity clienteEntity = super.toEntity();
        if (solicitudes != null) {
            List<SolicitudEntity> solicitudesEntity = new ArrayList<>();
            for (SolicitudDTO dtoReview : getSolicitudes()) 
                solicitudesEntity.add(dtoReview.toEntity());
            
            clienteEntity.setSolicitudes(solicitudesEntity);
        }        
        return clienteEntity;
    }

    /**
     * Devuelve las solicitudes asociadas a este cliente
     *
     * @return Lista de DTOs de Reseñas
     */
    public List<SolicitudDTO> getSolicitudes() {return solicitudes;}

    /**
     * Modifica las solicitudes de este cliente.
     *
     * @param solicitudes Las nuevas solicitudes
     */
    public void setSolicitudes(List<SolicitudDTO> solicitudes) {this.solicitudes = solicitudes;}

}
