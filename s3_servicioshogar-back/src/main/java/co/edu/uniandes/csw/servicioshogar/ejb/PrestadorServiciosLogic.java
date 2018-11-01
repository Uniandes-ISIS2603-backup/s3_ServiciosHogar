/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.PrestadorPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.ServicioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author María Ocampo
 */
@Stateless
public class PrestadorServiciosLogic {
    
    private final static Logger LOGGER = Logger.getLogger(ServiciosPrestadorLogic.class.getName());
    
    @Inject
    private PrestadorPersistence prestadorPersistence;

    
    public List<ServicioEntity> findAllServices(Long prestadorId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia el proceso de consultar los servicio  de un prestador con id = {0}",prestadorId);
        PrestadorEntity prestadorEntity = prestadorPersistence.find(prestadorId);
        //Agregar servicio al prestador
        if(prestadorEntity ==null)
            throw new BusinessLogicException("El prestador no existe");
        //Fin agregar servicio prestador
       LOGGER.log(Level.INFO, "Termina el proceso de consultar los servicio  de un prestador con id = {0}",prestadorId);
       return prestadorEntity.getServicios();
    }
    
     public ServicioEntity finServices(Long prestadorId, Long servicioId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia el proceso de consultar los servicio  de un prestador con id = {0}",prestadorId);
        PrestadorEntity prestadorEntity = prestadorPersistence.find(prestadorId);
        //Agregar servicio al prestador
        if(prestadorEntity ==null)
            throw new BusinessLogicException("El prestador no existe");
        ServicioEntity buscado = null;
        for(ServicioEntity actual: prestadorEntity.getServicios())
        {
            if(actual.getId().equals(servicioId))
                buscado=actual;
        }
        
        //Fin agregar servicio prestador
       LOGGER.log(Level.INFO, "Termina el proceso de consultar los servicio  de un prestador con id = {0}",prestadorId);
       return buscado;
    }
}
