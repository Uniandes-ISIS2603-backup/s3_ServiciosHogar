/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.persistence;

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
    
    private static final Logger LOGGER =  Logger.getLogger(HabilidadPersistence.class.getName());
     
    @PersistenceContext(unitName = "SechPU")
    protected EntityManager em;
    
    public HabilidadEntity create(HabilidadEntity habilidadEntity)
    {
        LOGGER.log(Level.INFO, "Creando una habilidad nueva");
        em.persist(habilidadEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una habilidad nueva");
        return habilidadEntity;
    }
    
    public HabilidadEntity update(HabilidadEntity habilidadEntity) {
        LOGGER.log(Level.INFO, "Actualizando habilidad con id = {0}", habilidadEntity.getId());
        return em.merge(habilidadEntity);
    }
    
     public void delete(Long habilidadId) {
        LOGGER.log(Level.INFO, "Borrando habilidad con id = {0}", habilidadId);
        HabilidadEntity habilidadEntity = em.find(HabilidadEntity.class, habilidadId);
        em.remove(habilidadEntity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la habilidad con id = {0}", habilidadId);
    }
    
    public List<HabilidadEntity> findAll(Long prestadorId)
    {
        LOGGER.log(Level.INFO, "Consultando todas las habilidades");
        
        TypedQuery query = em.createQuery("select u from HabilidadEntity u where (u.prestador.id = :prestadorid)", HabilidadEntity.class);
        query.setParameter("prestadorid", prestadorId);
        return query.getResultList();
    }
    
    public HabilidadEntity find(Long prestadorId, Long habilidadId) {
        LOGGER.log(Level.INFO, "Consultando la habilidad con id = {0} del prestador con id = " + prestadorId, habilidadId);
        TypedQuery<HabilidadEntity> q = em.createQuery("select p from HabilidadEntity p where (p.prestador.id = :prestadorid) and (p.id = :habilidadId)", HabilidadEntity.class);
        q.setParameter("prestadorid", prestadorId);
        q.setParameter("habilidadId", habilidadId);
        List<HabilidadEntity> results = q.getResultList();
        HabilidadEntity review = null;
        if (results == null) {
            review = null;
        } else if (results.isEmpty()) {
            review = null;
        } else if (results.size() >= 1) {
            review = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la habilidad con id = {0} del prestador con id =" + prestadorId, habilidadId);
        return review;
    }
   
    
}
