/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.ReferenciaEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.ReferenciaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniela Rocha Torres
 */
public class ReferenciaLogic {
    
       private static final Logger LOGGER = Logger.getLogger(ReferenciaLogic.class.getName());
    
         /*Inyecta las dependencias*/
    @Inject
    private ReferenciaPersistence persistence;
    
    
    public ReferenciaEntity createReferencia(ReferenciaEntity referenciaEntity){
        LOGGER.log(Level.INFO, "Inicia proceso de creación del servicio");
        // Invoca la persistencia para crear el servicio
        persistence.create(referenciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del servicio");
        return referenciaEntity;
    }

 
    public List<ReferenciaEntity> getReferencias() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los servicios");
        List<ReferenciaEntity> referencias = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los servicios");
        return referencias;
    }

    
    public ReferenciaEntity getReferencia(Long idRemitente) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar hoja de vida asociada con telefono= {0}", idRemitente);
        ReferenciaEntity referenciaEntity = persistence.find(idRemitente);
        if (referenciaEntity == null)
            LOGGER.log(Level.SEVERE, "El servicio con hoja de vida asociada con telefono= {0}", idRemitente);
        LOGGER.log(Level.INFO, "Termina proceso de consultar hoja de vida asociada con telefono= {0}", idRemitente);
        return referenciaEntity;
    }

  
    public ReferenciaEntity updateReferencia(Long telPrestador, ReferenciaEntity hojaDeVidaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar hoja de vida asociada con telefono= {0}", telPrestador);
        ReferenciaEntity newEntity = persistence.update(hojaDeVidaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar hoja de vida asociada con telefono= {0}", hojaDeVidaEntity.getId());
        return newEntity;
    }

  
    public void deleteReferencia(Long idRemitente) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar hoja de vida asociada con telefono= {0}", idRemitente);
        persistence.delete(idRemitente);
        LOGGER.log(Level.INFO, "Termina proceso de borrar hoja de vida asociada con telefono= {0}", idRemitente);
    }

    
}
