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

    private static final Logger LOGGER = Logger.getLogger(HabilidadPersistence.class.getName());

    @PersistenceContext(unitName = "SechPU")
    protected EntityManager em;

    /**
     * Método para persistir la entidad en la BD.
     * @param habilidadEntity. Objeto Habilidad que se creará en la BD.
     * @return La entidad creada (Habilidad) con un id dado por la BD.
     */
    public HabilidadEntity create(HabilidadEntity habilidadEntity) {
        LOGGER.log(Level.INFO, "Creando una habilidad nueva");
        em.persist(habilidadEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una habilidad nueva");
        return habilidadEntity;
    }

    /**
     *  Modifica la habilidad con id dado por parámetro
     * @param habilidadEntity. Habilidad que viene con al modificaciones que se desean realizar
     * @return La habilidad con los cambios aplicados
     */
    public HabilidadEntity update(HabilidadEntity habilidadEntity) {
        LOGGER.log(Level.INFO, "Actualizando habilidad con id = {0}", habilidadEntity.getId());
        return em.merge(habilidadEntity);
    }

    /**
     * Borra la habilidad con el id dado por parámetro
     * @param habilidadId. El id de la habildad que se desea eliminar
     */
    public void delete(Long habilidadId) {
        LOGGER.log(Level.INFO, "Borrando habilidad con id = {0}", habilidadId);
        HabilidadEntity habilidadEntity = em.find(HabilidadEntity.class, habilidadId);
        em.remove(habilidadEntity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la habilidad con id = {0}", habilidadId);
    }

    /**
     * Devuelve la lista con todas las habilidades asociadas al prestador dado por parámetro
     * @param prestadorId. El id del prestador asociado a las habilidades
     * @return Una lista con todas las habilidades que se encuetran en la BD
     */
    public List<HabilidadEntity> findAll(Long prestadorId) {
        LOGGER.log(Level.INFO, "Consultando todas las habilidades");

        TypedQuery query = em.createQuery("select u from HabilidadEntity u where (u.prestador.id = :prestadorid)", HabilidadEntity.class);
        query.setParameter("prestadorid", prestadorId);
        return query.getResultList();
    }

    /**
     * Devuelve una habilidad con el id dado por parámetro
     * @param prestadorId. El id del prestador asociado a la habilidad
     * @param habilidadId. El id de la habilidad buscada
     * @return La entidad (Habilidad) encontrada.
     */
    public HabilidadEntity find(Long prestadorId, Long habilidadId) {
        Object[] ids = {habilidadId, prestadorId};
        LOGGER.log(Level.INFO, "Consultando la habilidad con id = {0} del prestador con id = {1} ", ids);
        TypedQuery<HabilidadEntity> q = em.createQuery("select p from HabilidadEntity p where (p.prestador.id = :prestadorid) and (p.id = :habilidadId)", HabilidadEntity.class);
        q.setParameter("prestadorid", prestadorId);
        q.setParameter("habilidadId", habilidadId);
        List<HabilidadEntity> results = q.getResultList();
        HabilidadEntity review = null;
        if (!results.isEmpty()) {
            review = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la habilidad con id = {0} del prestador con id = {1}", ids);
        return review;
    }

}
