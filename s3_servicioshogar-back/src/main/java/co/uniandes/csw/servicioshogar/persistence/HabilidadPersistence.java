/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uniandes.csw.servicioshogar.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.HabilidadEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class HabilidadPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(HabilidadPersistence.class.getName());
    
    @PersistenceContext (unitName = "SechUP")
    protected EntityManager em;
    
    /**
     *Métod para persistir la entidad en la BD
     * @param habilidadEntity Objeto habilidad que se creará en la BD
     * @return La entidad creada con id dado por la BD
     */
    public HabilidadEntity create(HabilidadEntity habilidadEntity){
        
        LOGGER.log(Level.INFO,"Creando una habilidad nueva");
        
        em.persist(habilidadEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una habilidad nueva");
        
        return habilidadEntity;
    }
    
    /**
     * Devuelve todas las habilidades de la BD
     * @return Lista con todas las habilidades de la BD
     */
    public List<HabilidadEntity> findAll(){
        
        LOGGER.log(Level.INFO,"Consultando todas las habilidades");
        
        TypedQuery query = em.createNamedQuery("select u from HabilidadEntity u", HabilidadEntity.class);
        
        return query.getResultList();
    }
    
    /**
     * Busca si hay alguna habilidad con el id dado.
     * @param haibilidadId Id de la habilidad que se quiere buscar
     * @return Una habilidad
     */
    public HabilidadEntity find(Long haibilidadId)
    {
        LOGGER.log(Level.INFO, "Consultando habilidad con id={0}", haibilidadId);
       
        return em.find(HabilidadEntity.class, haibilidadId);
    }
    
    /**
     * Actualiza una habilidad
     * @param habilidadEntity Habilidad que viene con nuevos cambios
     * @return Una habilidad con los cambios aplicados
     */
    public HabilidadEntity update(HabilidadEntity habilidadEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando habilidad con id={0}", habilidadEntity.getId());
        
        LOGGER.log(Level.INFO, "Saliendo de actualizar una habilidad con id={0}", habilidadEntity.getId());
        return em.merge(habilidadEntity);
    }
    
    /**
     * Borra una habilidad de la BD dado su id
     * @param habilidadId Id de la habilidad que se quiere borrar
     */
    public void delete(Long habilidadId)
    {
        LOGGER.log(Level.INFO, "Borrando habilidad con id={0}", habilidadId);
        
        HabilidadEntity habilidad = em.find(HabilidadEntity.class, habilidadId);
        em.remove(habilidad);
        
        LOGGER.log(Level.INFO, "Saliendo de borrar la habilidad con id={0}",habilidadId);
    }
    
    /**
     * Busca si hay una habilidad con el tipo dado por parámetro
     * @param tipo Tipo de la habilidad a buscar
     * @return null si no existe una habilidad con el tipo dado
     * Si existe alguna devuelve la primera.
     */
    public HabilidadEntity findByTipo(String tipo)
    {
        LOGGER.log(Level.INFO, "Consultando habilidad por tipo", tipo);
        
        TypedQuery query = em.createQuery("Select e From HabilidadEntity e where e.tipo = :tipo", HabilidadEntity.class);
        
        query = query.setParameter("tipo", tipo);
        
        List<HabilidadEntity> sameTipo = query.getResultList();
        HabilidadEntity result;
         if (sameTipo == null) {
            result = null;
        } else if (sameTipo.isEmpty()) {
            result = null;
        } else {
            result = sameTipo.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar habilidad por tipo ", tipo);
        return result;
    }
}
