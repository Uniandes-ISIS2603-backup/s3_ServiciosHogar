/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uniandes.csw.servicioshogar.persistence;

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
    
    /**
     * Crear una calificacion
     *
     * Crea una nueva calificacion con la información recibida en la entidad.
     *
     * @param calificacionEntity La entidad que representa la nueva calificacion
     * @return La entidad creada
     */
    public CalificacionEntity create(CalificacionEntity calificacionEntity) {
        LOGGER.log(Level.INFO, "Creando una calificacion nuevo");
        em.persist(calificacionEntity);
        LOGGER.log(Level.INFO, "Calificacion creada");
        return calificacionEntity;
    }

    /**
     * Actualizar una calificacion
     *
     * Actualiza la entidad que recibe en la base de datos
     *
     * @param calificacionEntity La entidad actualizada que se desea guardar
     * @return La entidad resultante luego de la acutalización
     */
    public CalificacionEntity update(CalificacionEntity calificacionEntity) {
        LOGGER.log(Level.INFO, "Actualizando calificacion con id = {0}", calificacionEntity.getId());
        return em.merge(calificacionEntity);
    }

    /**
     * Eliminar una calificacion
     *
     * Elimina la calificacion asociada al ID que recibe
     *
     * @param calificacionesId El ID de la calificacion que se desea borrar
     */
    public void delete(Long calificacionesId) {
        LOGGER.log(Level.INFO, "Borrando calificacion con id = {0}", calificacionesId);
        CalificacionEntity calificacionEntity = em.find(CalificacionEntity.class, calificacionesId);
        em.remove(calificacionEntity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la calificacion con id = {0}", calificacionesId);
    }

    /**
     * Buscar una calificacion
     *
     * Busca si hay alguna calificacion asociada a un libro y con un ID específico
     *
     * @param clientesId El ID del libro con respecto al cual se busca
     * @param calificacionesId El ID de la calificacion buscada
     * @return La calificacion encontrada o null. Nota: Si existe una o más calificacions
     * devuelve siempre la primera que encuentra
     */
    public CalificacionEntity find(Long clientesId, Long calificacionesId) {
        LOGGER.log(Level.INFO, "Consultando el calificacion con id = {0} del libro con id = " + clientesId, calificacionesId);
        TypedQuery<CalificacionEntity> q = em.createQuery("select p from CalificacionEntity p where (p.clientes.id = :clientesId) and (p.id = :calificacionesId)", CalificacionEntity.class);
        q.setParameter("clientesId", clientesId);
        q.setParameter("calificacionesId", calificacionesId);
        List<CalificacionEntity> results = q.getResultList();
        CalificacionEntity calificacion = null;
        if (results == null) {
            calificacion = null;
        } else if (results.isEmpty()) {
            calificacion = null;
        } else if (results.size() >= 1) {
            calificacion = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar el calificacion con id = {0} del cliente con id =" + clientesId, calificacionesId);
        return calificacion;
    }
}
