/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.CalificacionEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Carlos Eduardo Robles
 */
@Stateless
public class CalificacionPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(CalificacionPersistence.class.getName());

    @PersistenceContext(unitName = "SechPU")
    protected EntityManager em;
    
    public CalificacionEntity create(CalificacionEntity calificacionEntity)
    {
        LOGGER.log(Level.INFO, "Creando un calificacion nuevo");
        em.persist(calificacionEntity);
        LOGGER.log(Level.INFO, "Calificacion creado");
        return calificacionEntity;
    }
    
    public CalificacionEntity update(CalificacionEntity calificacionEntity) 
    {
        LOGGER.log(Level.INFO, "Actualizando calificacion con id = {0}", calificacionEntity.getId());
        return em.merge(calificacionEntity);
    }
    
    public void delete(Long calificacionId) 
    {
        LOGGER.log(Level.INFO, "Borrando calificacion con id = {0}", calificacionId);
        CalificacionEntity calificacionEntity = em.find(CalificacionEntity.class, calificacionId);
        em.remove(calificacionEntity);
        LOGGER.log(Level.INFO, "Saliendo de borrar El calificacion con id = {0}", calificacionId);
    }
    
    public CalificacionEntity find(Long serviciosId, Long calificacionId) 
    {
        LOGGER.log(Level.INFO, "Consultando el calificacion con id = {0} del libro con id = " + serviciosId, calificacionId);
        TypedQuery<CalificacionEntity> q = em.createQuery("select p from CalificacionEntity p where (p.servicio.id = :serviciosId) and (p.id = :calificacionId)", CalificacionEntity.class);
        q.setParameter("serviciosId", serviciosId);
        q.setParameter("calificacionId", calificacionId);
        List<CalificacionEntity> results = q.getResultList();
        CalificacionEntity calificacion = null;
        if (results == null) 
            calificacion = null;
        else if (results.isEmpty()) 
            calificacion = null;
        else if (results.size() >= 1) 
            calificacion = results.get(0);
        
        LOGGER.log(Level.INFO, "Saliendo de consultar el calificacion con id = {0} del servicio con id =" + serviciosId, calificacionId);
        return calificacion;
    }
}
