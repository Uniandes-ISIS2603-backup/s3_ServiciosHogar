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

    private static final Logger LOGGER = Logger.getLogger(HabilidadLogic.class.getName());
    
    /**
     * Inyección de dependencias
     */
    @Inject
    private HabilidadPersistence persistence;
    
    /**
     * Inyección de dependencias
     */
    @Inject
    private PrestadorPersistence prestadorPersistence;
    
    /**
     * Crea una habilidad en la persistencia
     * @param prstadorId. El id del prestador asociado a la habilidad
     * @param habilidadEntity. La entidad que representa la habilidad a persistir
     * @return Entidad de la habilidad luego de ser persistida
     */
    public HabilidadEntity createHabilidad(Long prstadorId, HabilidadEntity habilidadEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Termina proceso de crear habilidad");
        PrestadorEntity prestador = prestadorPersistence.find(prstadorId);
        if(prestador == null)
            throw new BusinessLogicException("El prestador no existe");
        if(habilidadEntity.getDescripcion() == null || habilidadEntity.getDescripcion().equals(""))
            throw new BusinessLogicException("La habilidad no tiene descripción");
        if(habilidadEntity.getTipo() == null || habilidadEntity.getTipo().equals(""))
            throw new BusinessLogicException("La habilidad no tiene tipo");
        habilidadEntity.setPrestador(prestador);
        LOGGER.log(Level.INFO,"Termina proceso de creación de una habilidad");
        return persistence.create(habilidadEntity);
    }
    
    /**
     * Obtener todas la habilidades del prestador dado por parámetro
     * @param prestadorId. El id del prestador del cual se quieren consultar sus habilidades
     * @return La lista con todas la habilidades asociadas al prestador dado
     */
    public List<HabilidadEntity> getHabilidades(Long prestadorId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las habilidades aociadas al prestador con id {0}", prestadorId);
        PrestadorEntity prestador =  prestadorPersistence.find(prestadorId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar las habilidades aociadas al prestador con id = {0}", prestadorId);
        return prestador.getHabilidades();
               
    }
    
    /**
     * Obtener una habildiad identificada con el id dado por parámetro
     * @param prestadorId. El id del prestador asociado a la habilidad
     * @param habilidadId. El id de la habildiad a buscar
     * @return La habilidad solicitada.
     */
    public HabilidadEntity getHabilidad(Long prestadorId, Long habilidadId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la habilidad con id = {0} del prestador con id = "+prestadorId, habilidadId);
        return persistence.find(prestadorId, habilidadId);
    }
    
    /**
     * Modifica la información de la habilidad dada por parámetro
     * @param prestadorId. El id del prestador asociado a la habilidad
     * @param habilidadEntity. La habilidad con los cambios que se quieren realizar.
     * @return  La habilidad con los cambios actualizados en la BD.
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
     * Borra una habilidad con el id dado por parámetro
     * @param prestadorId. El id del prestador asociado a la habilidad
     * @param habilidadId. El id de la habilidad que se desea eliminar
     * @throws BusinessLogicException. Si la habilidad no existe
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
