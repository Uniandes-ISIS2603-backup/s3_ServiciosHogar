/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.persistence.PrestadorPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.ServicioPersistence;
import java.util.ArrayList;
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
public class ServiciosPrestadorLogic {
 
    private final static Logger LOGGER = Logger.getLogger(ServiciosPrestadorLogic.class.getName());
    
    @Inject
    private PrestadorPersistence prestadorPersistence;
    
    @Inject
    private ServicioPersistence servicioPersistence;
    
    public ServicioEntity replacePrestador(Long prestadorId, Long solicitudId,Long servicioId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un servicio con id = {0}",servicioId);
        PrestadorEntity prestadorEntity = prestadorPersistence.find(prestadorId);
        ServicioEntity servicioEntity = servicioPersistence.findBySolicitud(solicitudId, servicioId);
        servicioEntity.setPrestador(prestadorEntity);
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
