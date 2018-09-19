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
    private ServicioPersistence servicioPersistence;
    
    public CalificacionEntity createCalificacion(Long serviciosId, CalificacionEntity calificacionEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de crear calificacion");
        ServicioEntity servicio = servicioPersistence.find(serviciosId);
        calificacionEntity.setServicio(servicio);
        LOGGER.log(Level.INFO, "Termina proceso de creación del calificacion");
        return persistence.create(calificacionEntity);
    }
    
    /*public List<CalificacionEntity> getCalificacions(Long serviciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los calificacions asociados al servicio con id = {0}", serviciosId);
        ServicioEntity servicioEntity = servicioPersistence.find(serviciosId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los calificacions asociados al servicio con id = {0}", serviciosId);
        return servicioEntity.getCalificacions();
    }*/
    
    public CalificacionEntity getCalificacion(Long serviciosId, Long calificacionsId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el calificacion con id = {0} del servicio con id = " + serviciosId, calificacionsId);
        return persistence.find(serviciosId, calificacionsId);
    }

    public CalificacionEntity updateCalificacion(Long serviciosId, CalificacionEntity calificacionEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el calificacion con id = {0} del servicio con id = " + serviciosId, calificacionEntity.getId());
        ServicioEntity servicioEntity = servicioPersistence.find(serviciosId);
        calificacionEntity.setServicio(servicioEntity);
        persistence.update(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el calificacion con id = {0} del servicio con id = " + serviciosId, calificacionEntity.getId());
        return calificacionEntity;
    }
    
    public void deleteCalificacion(Long serviciosId, Long calificacionsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el calificacion con id = {0} del servicio con id = " + serviciosId, calificacionsId);
        CalificacionEntity old = getCalificacion(serviciosId, calificacionsId);
        if (old == null) {
            throw new BusinessLogicException("El calificacion con id = " + calificacionsId + " no esta asociado a el servicio con id = " + serviciosId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el calificacion con id = {0} del servicio con id = " + serviciosId, calificacionsId);
    }    
}
