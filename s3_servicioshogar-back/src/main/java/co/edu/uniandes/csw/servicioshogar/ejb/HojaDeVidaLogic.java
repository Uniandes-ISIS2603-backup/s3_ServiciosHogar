/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.HojaDeVidaEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.HojaDeVidaPersistence;
import java.util.List;
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
    

   
    public HojaDeVidaEntity createHojaDeVida(HojaDeVidaEntity hojaDeVidaEntity) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la hoja de vida");
        
        if(persistence.findByEmail(hojaDeVidaEntity.getEmail())!=null){
            throw new BusinessLogicException("Ya existe una hoja de vida asociada al email: "+hojaDeVidaEntity.getEmail());
        }
        // Invoca la persistencia para crear el servicio
        persistence.create(hojaDeVidaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la hoja de vida");
        return hojaDeVidaEntity;
    }

 
    public List<HojaDeVidaEntity> getHojasDeVida() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las hojas de vida");
        List<HojaDeVidaEntity> hojasDeVida = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las hojas de vida");
        return hojasDeVida;
    }

    
    public HojaDeVidaEntity getHojaDeVida(Long telPrestador) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar hoja de vida asociada con telefono= {0}", telPrestador);
        HojaDeVidaEntity hojaDeVidaEntity = persistence.find(telPrestador);
        if (hojaDeVidaEntity == null)
            LOGGER.log(Level.SEVERE, "La hoja de vida asociada con telefono= {0}", telPrestador);
        LOGGER.log(Level.INFO, "Termina proceso de consultar hoja de vida asociada con telefono= {0}", telPrestador);
        return hojaDeVidaEntity;
    }

 

    
    public HojaDeVidaEntity updateHojaDeVida(Long telPrestador, HojaDeVidaEntity hojaDeVidaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar hoja de vida asociada con telefono= {0}", telPrestador);
        HojaDeVidaEntity newEntity = persistence.update(hojaDeVidaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar hoja de vida asociada con telefono= {0}", hojaDeVidaEntity.getId());
        return newEntity;
    }

  
    public void deleteHojaDeVida(Long telPrestador) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar hoja de vida asociada con telefono= {0}", telPrestador);
        persistence.delete(telPrestador);
        LOGGER.log(Level.INFO, "Termina proceso de borrar hoja de vida asociada con telefono= {0}", telPrestador);
    }

  
    
}
