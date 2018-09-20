/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.HabilidadEntity;
import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.HabilidadPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.PrestadorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Maria Ocampo
 */
@Stateless
public class HabilidadLogic {
    
    /**
     * 
     */
    private static final Logger LOGGER = Logger.getLogger(HabilidadLogic.class.getName());
    
    /**
     * 
     */
    @Inject
    private HabilidadPersistence persistence;
    
    /**
     * 
     */
    @Inject
    private PrestadorPersistence prestadorPersistence;
    
    /**
     * 
     * @param prstadorId
     * @param habilidadEntity
     * @return
     * @throws BusinessLogicException 
     */
    public HabilidadEntity createHabilidad(Long prstadorId, HabilidadEntity habilidadEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Termina proceso de crear habilidad");
        PrestadorEntity prestador = prestadorPersistence.find(prstadorId);
        if(prestador == null)
            throw new BusinessLogicException("El prestador no existe");
        if(habilidadEntity.getDescripcion() == null || habilidadEntity.getDescripcion().equals(""))
            throw new BusinessLogicException("El prestador no existe");
        if(habilidadEntity.getTipo() == null || habilidadEntity.getTipo().equals(""))
            throw new BusinessLogicException("El prestador no existe");
        habilidadEntity.setPrestador(prestador);
        LOGGER.log(Level.INFO,"Termina proceso de creación de una habilidad");
        return persistence.create(habilidadEntity);
    }
    
    /**
     * 
     * @param prestadorId
     * @return 
     */
    public List<HabilidadEntity> getHabilidades(Long prestadorId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las habilidades aociadas al prestador con id = {0}", prestadorId);
        PrestadorEntity prestador =  prestadorPersistence.find(prestadorId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar las habilidades aociadas al prestador con id = {0}", prestadorId);
        return prestador.getHabilidades();
               
    }
    
    /**
     * 
     * @param prestadorId
     * @param habilidadId
     * @return 
     */
    public HabilidadEntity getHabilidad(Long prestadorId, Long habilidadId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la habilidad con id = {0} del prestador con id = "+prestadorId, habilidadId);
        return persistence.find(prestadorId, habilidadId);
    }
    
    /**
     * 
     * @param prestadorId
     * @param habilidadEntity
     * @return 
     */
    public HabilidadEntity updateHabilidad(Long prestadorId, HabilidadEntity habilidadEntity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la habilidad con id = {0} del prestador con id = " + prestadorId, habilidadEntity.getId());
        PrestadorEntity prestadorEntity = prestadorPersistence.find(prestadorId);
        habilidadEntity.setPrestador(prestadorEntity);
        persistence.update(habilidadEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la habilidad con id = {0} del prestador con id = " + prestadorId, habilidadEntity.getId());
        return habilidadEntity;
    }
    
    /**
     * 
     * @param prestadorId
     * @param habilidadId
     * @throws BusinessLogicException 
     */
    public void deleteHabilidad(Long prestadorId, Long habilidadId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la habilidad con id = {0} del prestador con id = "+ prestadorId, habilidadId);
        HabilidadEntity old = getHabilidad(prestadorId, habilidadId);
        if(old == null)
        {
            throw new BusinessLogicException("La habilidad con id = "+ habilidadId + " no existe");
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la habilidad con id = {0} del prestador con id = " + prestadorId, habilidadId);
        
    }
}
