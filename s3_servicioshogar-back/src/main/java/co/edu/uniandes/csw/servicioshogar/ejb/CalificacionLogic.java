/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.CalificacionEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.ServicioPersistence;
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
    //------------------------------------------
    //-----------------Atributos----------------
    //------------------------------------------
    private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());
    
    /**
     * Inyecta las dependencias.
     */
    @Inject
    private CalificacionPersistence persistence;

    /**
     * Inyecta las dependencias.
     */
    @Inject
    private ServicioPersistence servicioPersistence;   

    //------------------------------------------
    //------------------Metodos-----------------
    //------------------------------------------  
    /**
     * Crea una calificacion en la persistencia.
     * @param solicitudesId - Id de la solicitud al que pertenece el servicio.
     * @param serviciosId - Id del servicio al que pertenece la calificacion.
     * @param calificacionEntity - Entidad de la calificacion a ser persistida.
     * @return Entidad persistida.
     * @throws BusinessLogicException - Si la calificacion ya existe
     */
    public CalificacionEntity createCalificacion(Long solicitudesId, Long serviciosId, CalificacionEntity calificacionEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de crear calificacion");        
        ServicioEntity servicio = servicioPersistence.findBySolicitud(solicitudesId ,serviciosId );
        if(servicio.getCalificacion() != null)
            throw new BusinessLogicException("El servicio con id = " + serviciosId + " ya tiene calificacion");
        else if(calificacionEntity.getCalificacion() > 5 || calificacionEntity.getCalificacion() < 1 )        
            throw new BusinessLogicException("La calificacion debe estar entre 1.0 y 5.0");        
        else
        {        
            calificacionEntity.setServicio(servicio);            
            LOGGER.log(Level.INFO, "Termina proceso de creación del calificacion"); 
        }        
        return persistence.create(calificacionEntity);
    }
    
    /**
     * Obtener una calificacion identificada con el 'id' ingresado por parametro.
     * @param serviciosId - Id del servcio al que pertenece la calificacion.
     * @param calificacionId - Id de la calificacion a obtener.
     * @return calificacion solicitada.
     */
    public CalificacionEntity getCalificacion(Long serviciosId, Long calificacionId) 
    {
        Long[] ids ={calificacionId, serviciosId};
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el calificacion con id = {0} del servicio con id = {0}",ids);
        return persistence.find(serviciosId, calificacionId);
    }

    /**
     * Modifica la informacion de una calificacion ingresada por parametro.
     * @param solicitudesId - Id de la solicitud del servicio.
     * @param serviciosId - Id del servicio al que pertenece la calificacion.
     * @param calificacionEntity - Entidad con los cambios.
     * @return calificacion con los cambios actualizados en la BD.
     */
    public CalificacionEntity updateCalificacion(Long solicitudesId, Long serviciosId, CalificacionEntity calificacionEntity) {
         Long[] ids = {calificacionEntity.getId(), serviciosId};
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el calificacion con id = {0} del servicio con id = {1}", ids);
        ServicioEntity servicioEntity = servicioPersistence.findBySolicitud(solicitudesId ,serviciosId );
        calificacionEntity.setServicio(servicioEntity);
        persistence.update(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el calificacion con id = {0} del servicio con id = {1}" , ids);
        return calificacionEntity;
    }
    
    /**
     * Borra una calificacion buscada por el 'id' ingresado por parametro.
     * @param serviciosId - Id del servicio al que pertenece la calificacion.
     * @param calificacionId - Id de la calificacion a borrar.
     * @throws BusinessLogicException - Si no existe el servicio o la calificacion.
     */
    public void deleteCalificacion(Long serviciosId, Long calificacionId) throws BusinessLogicException {
        Long[] ids = {calificacionId, serviciosId};
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el calificacion con id = {0} del servicio con id = {1}", ids);
        CalificacionEntity old = getCalificacion(serviciosId, calificacionId);
        if (old == null) {
            throw new BusinessLogicException("El calificacion con id = " + calificacionId + " no esta asociado a el servicio con id = " + serviciosId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el calificacion con id = {0} del servicio con id = {1}", ids);
    }    
}
