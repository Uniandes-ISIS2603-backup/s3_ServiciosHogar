/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.HojaDeVidaEntity;
import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.HojaDeVidaPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.PrestadorPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniela Rocha Torres
 */
@Stateless
public class HojaDeVidaLogic {
    
       private static final Logger LOGGER = Logger.getLogger(HojaDeVidaLogic.class.getName());
    
         /*Inyecta las dependencias*/
    @Inject
    private HojaDeVidaPersistence persistence;
    
    /**
     * 
     */
    @Inject
    private PrestadorPersistence prestadorPersistence;

   
    public HojaDeVidaEntity createHojaDeVida(Long prestadorId,HojaDeVidaEntity hojaDeVidaEntity) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la hoja de vida");
        PrestadorEntity prestador = prestadorPersistence.find(prestadorId);
        if(prestador == null)
            throw new BusinessLogicException("El prestador no existe");
        if(persistence.findByEmail(hojaDeVidaEntity.getEmail())!=null){
            throw new BusinessLogicException("Ya existe una hoja de vida asociada al email: "+hojaDeVidaEntity.getEmail());
        }
        // Invoca la persistencia para crear el servicio
        hojaDeVidaEntity.setPrestador(prestador);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la hoja de vida");
        return persistence.create(hojaDeVidaEntity);
    }


    public HojaDeVidaEntity getHojaDeVida(Long prestadorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar hoja de vida asociada con telefono= {0}", prestadorId);
        HojaDeVidaEntity hojaDeVidaEntity = persistence.find(prestadorId);
        if (hojaDeVidaEntity == null)
            LOGGER.log(Level.SEVERE, "La hoja de vida asociada con telefono= {0}", prestadorId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar hoja de vida asociada con telefono= {0}", prestadorId);
        
        
        return hojaDeVidaEntity;
    }
    
        public HojaDeVidaEntity getHojaDeVidaById(Long hojaDeVidaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar hoja de vida asociada con telefono= {0}", hojaDeVidaId);
        HojaDeVidaEntity hojaDeVidaEntity = persistence.findById(hojaDeVidaId);
        if (hojaDeVidaEntity == null)
            LOGGER.log(Level.SEVERE, "La hoja de vida asociada con telefono= {0}", hojaDeVidaId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar hoja de vida asociada con telefono= {0}", hojaDeVidaId);
        return hojaDeVidaEntity;
    }

 

    
    public HojaDeVidaEntity updateHojaDeVida(Long prestadorId, HojaDeVidaEntity hojaDeVidaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar hoja de vida asociada con id= {0}", prestadorId);
        PrestadorEntity prestadorEntity = prestadorPersistence.find(prestadorId);
        hojaDeVidaEntity.setPrestador(prestadorEntity);
        HojaDeVidaEntity newEntity = persistence.update(hojaDeVidaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar hoja de vida asociada con id= {0}", hojaDeVidaEntity.getId());
        return newEntity;
    }

  
    public void deleteHojaDeVida(Long prestadorId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar hoja de vida asociada con id= {0}", prestadorId);
        HojaDeVidaEntity old = getHojaDeVida(prestadorId);
         if(old == null)
        {
            throw new BusinessLogicException("La hoja de vida del prestador con id = "+prestadorId+ " no existe");
        }
         
        persistence.delete(old.getId());
        
        LOGGER.log(Level.INFO, "Termina proceso de borrar hoja de vida asociada con id= {0}", prestadorId);
    }
      
}
