/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Solicitud.
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Stateless
public class SolicitudPersistence {
    private static final Logger LOGGER = Logger.getLogger(SolicitudPersistence.class.getName());

    @PersistenceContext(unitName = "SechPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param solicitudEntity objeto solicitud que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public SolicitudEntity create(SolicitudEntity solicitudEntity) {
        LOGGER.log(Level.INFO, "Creando una solicitud nueva");
        em.persist(solicitudEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una solicitud nueva");
        return solicitudEntity;
    }

    /**
     * Devuelve todas las solicitudes de la base de datos.
     *
     * @return una lista con todas las solicitudes que encuentre en la base de datos.
     */
    public List<SolicitudEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las solicitudes");
        // Se crea un query para buscar todas las solicitudes en la base de datos.
        TypedQuery query = em.createQuery("select u from SolicitudEntity u", SolicitudEntity.class);
        return query.getResultList();
    }

    /**
     * Busca si hay alguna solicitud con el id que se envía de argumento
     *
     * @param solicitudesId: id correspondiente a la solicitud buscada.
     * @return una solicitud.
     */
    public SolicitudEntity find(Long solicitudesId) {
        LOGGER.log(Level.INFO, "Consultando solicitud con id={0}", solicitudesId);
        return em.find(SolicitudEntity.class, solicitudesId);
    }

    /**
     * Actualiza una solicitud.
     *
     * @param solicitudEntity: la solicitud que viene con los nuevos cambios.
     * @return una solicitud con los cambios aplicados.
     */
    public SolicitudEntity update(SolicitudEntity solicitudEntity) {
        LOGGER.log(Level.INFO, "Actualizando solicitud con id = {0}", solicitudEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar la solicitud con id = {0}", solicitudEntity.getId());
        return em.merge(solicitudEntity);
    }

    /**
     * Borra una solicitud de la base de datos recibiendo como argumento el id
     * de la solicitud
     *
     * @param solicitudesId: id correspondiente a la solicitud a borrar.
     */
    public void delete(Long solicitudesId) {
        LOGGER.log(Level.INFO, "Borrando solicitud con id = {0}", solicitudesId);
        SolicitudEntity entity = em.find(SolicitudEntity.class, solicitudesId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la solicitud con id = {0}", solicitudesId);
    }
}
