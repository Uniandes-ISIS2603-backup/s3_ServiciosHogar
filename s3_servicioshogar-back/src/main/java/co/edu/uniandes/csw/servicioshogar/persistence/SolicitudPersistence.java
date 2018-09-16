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
        LOGGER.log(Level.INFO, "Creando un solicitud nuevo");
        em.persist(solicitudEntity);
        LOGGER.log(Level.INFO, "Libro creado");
        return solicitudEntity;
    }

    /**
     * Devuelve todos lossolicitudes de la base de datos.
     *
     * @return una lista con todos los solicitudes que encuentre en la base de datos,
     * "select u from SolicitudEntity u" es como un "select * from SolicitudEntity;" -
     * "SELECT * FROM table_name" en SQL.
     */
    public List<SolicitudEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los solicitudes");
        TypedQuery q = em.createQuery("select u from SolicitudEntity u", SolicitudEntity.class);
        return q.getResultList();
    }

    /**
     * Busca si hay algun lubro con el id que se envía de argumento
     *
     * @param solicitudesId: id correspondiente al solicitud buscado.
     * @return un solicitud.
     */
    public SolicitudEntity find(Long solicitudesId) {
        LOGGER.log(Level.INFO, "Consultando el solicitud con id={0}", solicitudesId);
        return em.find(SolicitudEntity.class, solicitudesId);
    }

    /**
     * Actualiza un solicitud.
     *
     * @param solicitudEntity: el solicitud que viene con los nuevos cambios. Por ejemplo
     * el nombre pudo cambiar. En ese caso, se haria uso del método update.
     * @return un solicitud con los cambios aplicados.
     */
    public SolicitudEntity update(SolicitudEntity solicitudEntity) {
        LOGGER.log(Level.INFO, "Actualizando el solicitud con id={0}", solicitudEntity.getId());
        return em.merge(solicitudEntity);
    }

    /**
     *
     * Borra un solicitud de la base de datos recibiendo como argumento el id del
     * solicitud
     *
     * @param solicitudesId: id correspondiente al solicitud a borrar.
     */
    public void delete(Long solicitudesId) {
        LOGGER.log(Level.INFO, "Borrando el solicitud con id={0}", solicitudesId);
        SolicitudEntity solicitudEntity = em.find(SolicitudEntity.class, solicitudesId);
        em.remove(solicitudEntity);
    }
}
