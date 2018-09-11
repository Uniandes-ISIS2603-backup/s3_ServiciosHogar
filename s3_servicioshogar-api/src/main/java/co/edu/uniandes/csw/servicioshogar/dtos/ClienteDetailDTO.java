/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.CalificacionEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Eduardo Robles Quevedo
 */
public class ClienteDetailDTO extends ClienteDTO implements Serializable
{
    private List<CalificacionDTO> calificaciones;
    
    private ClienteDetailDTO(){super();}
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param clienteEntity La entidad de la cual se construye el DTO
     */
    public ClienteDetailDTO(ClienteEntity clienteEntity) 
    {
        super(clienteEntity);
        if (clienteEntity.getCalificaciones() != null) 
        {
            calificaciones = new ArrayList<>();
            for (CalificacionEntity entityCalificacion : clienteEntity.getCalificaciones()) 
                calificaciones.add(new CalificacionDTO(entityCalificacion));
            
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
        if (calificaciones != null) {
            List<CalificacionEntity> calificacionesEntity = new ArrayList<>();
            for (CalificacionDTO dtoReview : getCalificaciones()) 
                calificacionesEntity.add(dtoReview.toEntity());
            
            clienteEntity.setCalificaciones(calificacionesEntity);
        }        
        return clienteEntity;
    }

    /**
     * Devuelve las calificaciones asociadas a este cliente
     *
     * @return Lista de DTOs de Reseñas
     */
    public List<CalificacionDTO> getCalificaciones() {return calificaciones;}

    /**
     * Modifica las calificaciones de este cliente.
     *
     * @param calificaciones Las nuevas calificaciones
     */
    public void setCalificaciones(List<CalificacionDTO> calificaciones) {this.calificaciones = calificaciones;}

}
