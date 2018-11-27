/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.SolicitudPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
public class SolicitudServiciosLogic {
    
     private static final Logger LOGGER = Logger.getLogger(SolicitudServiciosLogic.class.getName());
    
    @Inject
    private SolicitudPersistence solicitudPersistence;

    
    public List<ServicioEntity> findAllServices(Long solicitudId, Long clienteId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia el proceso de consultar los servicio  de un prestador con id = {0}",solicitudId);
        SolicitudEntity solicitudEntity = solicitudPersistence.find(clienteId, solicitudId);
        //Agregar servicio al prestador
        if(solicitudEntity ==null)
            throw new BusinessLogicException("La solicitud no existe");
        //Fin agregar servicio prestador
       LOGGER.log(Level.INFO, "Termina el proceso de consultar los servicio  de un prestador con id = {0}",solicitudId);
       return solicitudEntity.getServicios();
    }
    
     public ServicioEntity finServices(Long clienteId, Long solicitudId, Long servicioId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia el proceso de consultar los servicio  de un prestador con id = {0}",solicitudId);
        SolicitudEntity solicitudEntity = solicitudPersistence.find(clienteId, solicitudId);
        //Agregar servicio al prestador
        if(solicitudEntity ==null)
            throw new BusinessLogicException("El prestador no existe");
        ServicioEntity buscado = null;
        for(ServicioEntity actual: solicitudEntity.getServicios())
        {
            if(actual.getId().equals(servicioId))
                buscado=actual;
        }
        
        //Fin agregar servicio prestador
       LOGGER.log(Level.INFO, "Termina el proceso de consultar los servicio  de un prestador con id = {0}",solicitudId);
       return buscado;
    }
}
