/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.CalificacionEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.uniandes.csw.servicioshogar.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.ClientePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Carlos Eduardo Robles
 */
@Stateless
public class CalificacionLogic
{
    private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());

    @Inject
    private CalificacionPersistence persistence;

    @Inject
    private ClientePersistence bookPersistence;
    
    /**
     * Se encarga de crear una calificacion en la base de datos.
     * @param clientesId Id del cliente.
     * @param calificacionEntity Entidad de tipo calificacion a insertar en el cliente
     * @return Objeto CalificacionEntity
     * @throws BusinessLogicException si clientesId no es el mismo que tiene el
     * entity.
     */
    public CalificacionEntity createCalificacion(Long clientesId, CalificacionEntity calificacionEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de crear calificacion");
        ClienteEntity cliente = bookPersistence.find(clientesId);
        calificacionEntity.setCliente(cliente);
        LOGGER.log(Level.INFO, "Termina proceso de creación del review");
        return persistence.create(calificacionEntity);
    }
    
    /**
     * 
     * @param clientesId
     * @return 
     */
    public List<CalificacionEntity> getCalificaciones(Long clientesId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las calificaciones asociados al cliente con id = {0}", clientesId);
        ClienteEntity clienteEntity = bookPersistence.find(clientesId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar las calificaciones asociados al cliente con id = {0}", clientesId);
        return clienteEntity.getCalificaciones();
    }

    /**
     * Obtiene los datos de una instancia de Calificacion a partir de su ID. La
     * existencia del elemento padre Book se debe garantizar.
     *
     * @param clientesId El id del Cliente buscado
     * @param calificacionId Identificador de la Calificacion a consultar
     * @return Instancia de CalificacionEntity con los datos del Calificacion consultado.
     *
     */
    public CalificacionEntity getCalificacion(Long clientesId, Long calificacionId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la calificacion con id = {0} del cliente con id = " + clientesId, calificacionId);
        return persistence.find(clientesId, calificacionId);
    }

    /**
     * Actualiza la información de una instancia de Calificacion.
     *
     * @param calificacionEntity Instancia de CalificacionEntity con los nuevos datos.
     * @param clientesId id del Cliente el cual sera padre del Calificacion actualizado.
     * @return Instancia de CalificacionEntity con los datos actualizados.
     *
     */
    public CalificacionEntity updateCalificacion(Long clientesId, CalificacionEntity calificacionEntity) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la calificacion con id = {0} del cliente con id = " + clientesId, calificacionEntity.getId());
        ClienteEntity clienteEntity = bookPersistence.find(clientesId);
        calificacionEntity.setCliente(clienteEntity);
        persistence.update(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la calificacion con id = {0} del cliente con id = " + clientesId, calificacionEntity.getId());
        return calificacionEntity;
    }

    /**
     * Elimina una instancia de Calificacion de la base de datos.
     *
     * @param calificacionId Identificador de la instancia a eliminar.
     * @param clientesId id del Cliente el cual es padre del Calificacion.
     * @throws BusinessLogicException Si la reseña no esta asociada al libro.
     *
     */
    public void deleteCalificacion(Long clientesId, Long calificacionId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la calificacion con id = {0} del cliente con id = " + clientesId, calificacionId);
        CalificacionEntity old = getCalificacion(clientesId, calificacionId);
        if (old == null) {
            throw new BusinessLogicException("La calificacion con id = " + calificacionId + " no esta asociado a el cliente con id = " + clientesId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la calificacion con id = {0} del cliente con id = " + clientesId, calificacionId);
    }
}
