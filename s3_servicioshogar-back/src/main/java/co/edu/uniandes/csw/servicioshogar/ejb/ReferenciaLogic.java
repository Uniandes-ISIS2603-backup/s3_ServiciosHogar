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
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la referencia");
        // Invoca la persistencia para crear el servicio
        persistence.create(referenciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la referencia");
        return referenciaEntity;
    }

 
    public List<ReferenciaEntity> getReferencias() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las referencias");
        List<ReferenciaEntity> referencias = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las referencias");
        return referencias;
    }

    
    public ReferenciaEntity getReferencia(Long idRemitente) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar referencia con idRemitente= {0}", idRemitente);
        ReferenciaEntity referenciaEntity = persistence.find(idRemitente);
        if (referenciaEntity == null)
            LOGGER.log(Level.SEVERE, "La referencia con idRemitente= {0}", idRemitente);
        LOGGER.log(Level.INFO, "Termina proceso de consultar referencia con idRemitente= {0}", idRemitente);
        return referenciaEntity;
    }

  
    public ReferenciaEntity updateReferencia(Long idRemitente, ReferenciaEntity referenciaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar referencia con id= {0}", idRemitente);
        ReferenciaEntity newEntity = persistence.update(referenciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar referencia con id= {0}", referenciaEntity.getIdRemitente());
        return newEntity;
    }

  
    public void deleteReferencia(Long idRemitente) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar referencia con idRemitente= {0}", idRemitente);
        persistence.delete(idRemitente);
        LOGGER.log(Level.INFO, "Termina proceso de borrar referencia con idRemitente= {0}", idRemitente);
    }

    
}
