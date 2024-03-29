/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.ReferenciaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniela Rocha Torres
 */
@Stateless
public class ReferenciaPersistence {

    private static final Logger LOGGER = Logger.getLogger(ReferenciaPersistence.class.getName());

    @PersistenceContext(unitName = "SechPU")
    protected EntityManager em;

    public ReferenciaEntity create(ReferenciaEntity referenciaEntity) {
        LOGGER.log(Level.INFO, "Creando una referencia");
        em.persist(referenciaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una referencia");
        return referenciaEntity;
    }

    public List<ReferenciaEntity> findAll(Long hojaDeVidaId) {
        LOGGER.log(Level.INFO, "Consultando todas las referencias");
        TypedQuery query = em.createQuery("select u from ReferenciaEntity u where (u.hojaDeVida.id = :hojadevidaid)", ReferenciaEntity.class);
        query.setParameter("hojadevidaid", hojaDeVidaId);
        return query.getResultList();
    }

    public ReferenciaEntity find(Long hojaDeVidaId, Long idRemitente) {
        Object[] ids = {idRemitente, hojaDeVidaId};
        LOGGER.log(Level.INFO, "Consultando referencia con idRemitente={0} y hojaDevida = {1}", ids);
        TypedQuery<ReferenciaEntity> q = em.createQuery("select p from ReferenciaEntity p where (p.hojaDeVida.id = :hojadevidaid) and (p.id = :remitenteid)", ReferenciaEntity.class);
        q.setParameter("hojadevidaid", hojaDeVidaId);
        q.setParameter("remitenteid", idRemitente);
        List<ReferenciaEntity> results = q.getResultList();
        ReferenciaEntity referencia = null;
        if (!results.isEmpty()) {
            referencia = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la referencia con idRemitente={0} y hojaDevida = {1}", ids);
        return referencia;
    }

    public ReferenciaEntity update(ReferenciaEntity referenciaEntity) {
        LOGGER.log(Level.INFO, "Actualizando referencia con  = {0}", referenciaEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar referencia con id= {0}", referenciaEntity.getId());
        return em.merge(referenciaEntity);
    }

    public void delete(Long idRemitente) {
        LOGGER.log(Level.INFO, "Borrando referencia con idRemitente = {0}", idRemitente);
        ReferenciaEntity entity = em.find(ReferenciaEntity.class, idRemitente);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar referencia con idRemitente = {0}", idRemitente);
    }

}
