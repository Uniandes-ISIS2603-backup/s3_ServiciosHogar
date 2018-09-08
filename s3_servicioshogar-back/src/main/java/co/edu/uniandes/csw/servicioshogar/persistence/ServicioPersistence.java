/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
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
public class ServicioPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(ServicioPersistence.class.getName());

    @PersistenceContext(unitName = "SechPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param servicioEntity objeto servicio que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ServicioEntity create(ServicioEntity servicioEntity) {
        LOGGER.log(Level.INFO, "Creando un servicio nuevo");
        em.persist(servicioEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un servicio nuevo");
        return servicioEntity;
    }

    /**
     * Devuelve todos los servicios de la base de datos.
     *
     * @return una lista con todos los servicios que encuentre en la base de datos.
     */
    public List<ServicioEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los servicios");
        // Se crea un query para buscar todas las servicios en la base de datos.
        TypedQuery query = em.createQuery("select u from ServicioEntity u", ServicioEntity.class);
        return query.getResultList();
    }

    /**
     * Busca si hay alguna servicio con el id que se envía de argumento
     *
     * @param serviciosId: id correspondiente al servicio buscado.
     * @return una servicio.
     */
    public ServicioEntity find(Long serviciosId) {
        LOGGER.log(Level.INFO, "Consultando servicio con id={0}", serviciosId);
        return em.find(ServicioEntity.class, serviciosId);
    }

    /**
     * Actualiza un servicio.
     *
     * @param servicioEntity: el servicio que viene con los nuevos cambios.
     * @return un servicio con los cambios aplicados.
     */
    public ServicioEntity update(ServicioEntity servicioEntity) {
        LOGGER.log(Level.INFO, "Actualizando servicio con id = {0}", servicioEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar el servicio con id = {0}", servicioEntity.getId());
        return em.merge(servicioEntity);
    }

    /**
     * Borra un servicio de la base de datos recibiendo como argumento el id
     * del servicio
     *
     * @param serviciosId: id correspondiente al servicio a borrar.
     */
    public void delete(Long serviciosId) {
        LOGGER.log(Level.INFO, "Borrando servicio con id = {0}", serviciosId);
        ServicioEntity entity = em.find(ServicioEntity.class, serviciosId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el servicio con id = {0}", serviciosId);
    }
}
