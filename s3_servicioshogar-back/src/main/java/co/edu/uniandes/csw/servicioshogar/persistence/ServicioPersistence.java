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
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Stateless
public class ServicioPersistence {
    private static final Logger LOGGER = Logger.getLogger(ServicioPersistence.class.getName());

    @PersistenceContext(unitName = "SechPU")
    protected EntityManager em;

    /**
     * Crear una reseña
     *
     * Crea una nueva reseña con la información recibida en la entidad.
     *
     * @param servicioEntity La entidad que representa la nueva reseña
     * @return La entidad creada
     */
    public ServicioEntity create(ServicioEntity servicioEntity) {
        LOGGER.log(Level.INFO, "Creando un servicio nuevo");
        em.persist(servicioEntity);
        LOGGER.log(Level.INFO, "Servicio creado");
        return servicioEntity;
    }

    /**
     * Actualizar una reseña
     *
     * Actualiza la entidad que recibe en la base de datos
     *
     * @param servicioEntity La entidad actualizada que se desea guardar
     * @return La entidad resultante luego de la acutalización
     */
    public ServicioEntity update(ServicioEntity servicioEntity) {
        LOGGER.log(Level.INFO, "Actualizando servicio con id = {0}", servicioEntity.getId());
        return em.merge(servicioEntity);
    }

    /**
     * Eliminar una reseña
     *
     * Elimina la reseña asociada al ID que recibe
     *
     * @param serviciosId El ID de la reseña que se desea borrar
     */
    public void delete(Long serviciosId) {
        LOGGER.log(Level.INFO, "Borrando servicio con id = {0}", serviciosId);
        ServicioEntity servicioEntity = em.find(ServicioEntity.class, serviciosId);
        em.remove(servicioEntity);
        LOGGER.log(Level.INFO, "Saliendo de borrar El servicio con id = {0}", serviciosId);
    }
    
    /**
     * Busca si hay algun lubro con el id que se envía de argumento
     *
     * @param serviciosId: id correspondiente al servicio buscado.
     * @return un servicio.
     */
    public ServicioEntity find(Long serviciosId) {
        LOGGER.log(Level.INFO, "Consultando el servicio con id={0}", serviciosId);
        return em.find(ServicioEntity.class, serviciosId);
    }

    /**
     * Buscar una reseña
     *
     * Busca si hay alguna reseña asociada a un solicitud y con un ID específico
     *
     * @param solicitudesId El ID del solicitud con respecto al cual se busca
     * @param serviciosId El ID de la reseña buscada
     * @return La reseña encontrada o null. Nota: Si existe una o más reseñas
     * devuelve siempre la primera que encuentra
     */
    public ServicioEntity findBySolicitud(Long solicitudesId, Long serviciosId) {
        LOGGER.log(Level.INFO, "Consultando el servicio con id = {1} del solicitud con id ={0} ", new Long[]{solicitudesId, serviciosId});
        TypedQuery<ServicioEntity> q = em.createQuery("select p from ServicioEntity p where (p.solicitud.id = :solicitudid) and (p.id = :serviciosId)", ServicioEntity.class);
        q.setParameter("solicitudid", solicitudesId);
        q.setParameter("serviciosId", serviciosId);
        List<ServicioEntity> results = q.getResultList();
        ServicioEntity servicio = null;
        if (!(results == null&&results.isEmpty()))
            servicio=results.get(0);
        LOGGER.log(Level.INFO, "Saliendo de consultar el servicio con id = {0} del solicitud con id =" + solicitudesId, serviciosId);
        return servicio;
    }
    
    /**
     * Buscar una reseña
     *
     * Busca si hay alguna reseña asociada a un solicitud y con un ID específico
     *
     * @param serviciosId El ID de la reseña buscada
     * @return La reseña encontrada o null. Nota: Si existe una o más reseñas
     * devuelve siempre la primera que encuentra
     */
    public ServicioEntity findByPrestador(Long prestadorId, Long serviciosId) {
        LOGGER.log(Level.INFO, "Consultando el servicio con id = {1} del prestador con id ={0} ",new Long[] {prestadorId, serviciosId});
        TypedQuery<ServicioEntity> q = em.createQuery("select p from ServicioEntity p where (p.prestador.id = :prestadorId) and (p.id = :serviciosId)", ServicioEntity.class);
        q.setParameter("prestadorId", prestadorId);
        q.setParameter("serviciosId", serviciosId);
        List<ServicioEntity> results = q.getResultList();
        ServicioEntity servicio = null;
        if (!(results == null&&results.isEmpty()))
            servicio= results.get(0);
        LOGGER.log(Level.INFO, "Saliendo de consultar el servicio con id = {1} del prestador con id ={0}" ,new Long[] {prestadorId, serviciosId});
        return servicio;
    }
}
