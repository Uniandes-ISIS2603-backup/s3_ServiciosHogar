/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.persistence.ServicioPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.SolicitudPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ServiciosSolicitudLogic {
    
        private static final Logger LOGGER = Logger.getLogger(ServiciosPrestadorLogic.class.getName());
    
    @Inject
    private SolicitudPersistence prestadorPersistence;
    
    @Inject
    private ServicioPersistence servicioPersistence;
    
    public ServicioEntity replacePrestador(Long clienteId, Long prestadorId, Long servicioId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un servicio con id = {0}",servicioId);
        SolicitudEntity prestadorEntity = prestadorPersistence.find(clienteId, prestadorId);
        ServicioEntity servicioEntity = servicioPersistence.find(servicioId);
        servicioEntity.setSolicitud(prestadorEntity);
        //Agregar servicio al prestador
        List<ServicioEntity> servicios = prestadorEntity.getServicios();
        servicios.add(servicioEntity);
        prestadorEntity.setServicios(servicios);
        prestadorPersistence.update(prestadorEntity);
        //Fin agregar servicio prestador
       LOGGER.log(Level.INFO, "Termina el proceso de actualizar el servicio con id= {0}",servicioId);
       return servicioEntity;
    }
}
