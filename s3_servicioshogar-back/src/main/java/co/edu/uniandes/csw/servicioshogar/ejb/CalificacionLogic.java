/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.CalificacionEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.ServicioPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.SolicitudPersistence;
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
    
    //@Inject
    //private SolicitudPersistence solicitudPersistence;
    
    public CalificacionEntity createCalificacion(Long solicitudId, Long serviciosId, CalificacionEntity calificacionEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de crear calificacion");
        //SolicitudEntity solicitud = solicitudPersistence.find(solicitudId);
        ServicioEntity servicio = servicioPersistence.find(solicitudId ,serviciosId );
        calificacionEntity.setServicio(servicio);
        LOGGER.log(Level.INFO, "Termina proceso de creación del calificacion");
        return persistence.create(calificacionEntity);
    }
    
    public CalificacionEntity getCalificacion(Long serviciosId, Long calificacionId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el calificacion con id = {0} del servicio con id = " + serviciosId, calificacionId);
        return persistence.find(serviciosId, calificacionId);
    }

    public CalificacionEntity updateCalificacion(Long solicitudId, Long serviciosId, CalificacionEntity calificacionEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el calificacion con id = {0} del servicio con id = " + serviciosId, calificacionEntity.getId());
        //SolicitudEntity solicitud = solicitudPersistence.find(solicitudId);
        ServicioEntity servicioEntity = servicioPersistence.find(solicitudId ,serviciosId );
        calificacionEntity.setServicio(servicioEntity);
        persistence.update(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el calificacion con id = {0} del servicio con id = " + serviciosId, calificacionEntity.getId());
        return calificacionEntity;
    }
    
    public void deleteCalificacion(Long serviciosId, Long calificacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el calificacion con id = {0} del servicio con id = " + serviciosId, calificacionId);
        CalificacionEntity old = getCalificacion(serviciosId, calificacionId);
        if (old == null) {
            throw new BusinessLogicException("El calificacion con id = " + calificacionId + " no esta asociado a el servicio con id = " + serviciosId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el calificacion con id = {0} del servicio con id = " + serviciosId, calificacionId);
    }    
}
