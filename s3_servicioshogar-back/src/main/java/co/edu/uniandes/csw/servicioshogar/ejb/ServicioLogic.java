/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.ServicioPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.SolicitudPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Stateless
public class ServicioLogic {
    private static final Logger LOGGER = Logger.getLogger(ServicioLogic.class.getName());

    @Inject
    private ServicioPersistence persistence;

    @Inject
    private SolicitudPersistence solicitudPersistence;

    /**
     * Se encarga de crear un Servicio en la base de datos.
     *
     * @param servicioEntity Objeto de ServicioEntity con los datos nuevos
     * @param solicitudesId id del Solicitud el cual sera padre del nuevo Servicio.
     * @return Objeto de ServicioEntity con los datos nuevos y su ID.
     * @throws BusinessLogicException si solicitudesId no es el mismo que tiene el
     * entity.
     *
     */
    public ServicioEntity createServicio(Long solicitudesId, ServicioEntity servicioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear servicio");
        SolicitudEntity solicitud = solicitudPersistence.find(solicitudesId);
        servicioEntity.setSolicitud(solicitud);
        LOGGER.log(Level.INFO, "Termina proceso de creación del servicio");
        return persistence.create(servicioEntity);
    }

    /**
     * Obtiene la lista de los registros de Servicio que pertenecen a un Solicitud.
     *
     * @param solicitudesId id del Solicitud el cual es padre de los Servicios.
     * @return Colección de objetos de ServicioEntity.
     */
    public List<ServicioEntity> getServicios(Long solicitudesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los servicios asociados al solicitud con id = {0}", solicitudesId);
        SolicitudEntity solicitudEntity = solicitudPersistence.find(solicitudesId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los servicios asociados al solicitud con id = {0}", solicitudesId);
        return solicitudEntity.getServicios();
    }

    /**
     * Obtiene los datos de una instancia de Servicio a partir de su ID. La
     * existencia del elemento padre Solicitud se debe garantizar.
     *
     * @param solicitudesId El id del Solicitud buscado
     * @param serviciosId Identificador de la Reseña a consultar
     * @return Instancia de ServicioEntity con los datos del Servicio consultado.
     *
     */
    public ServicioEntity getServicio(Long solicitudesId, Long serviciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el servicio con id = {0} del solicitud con id = " + solicitudesId, serviciosId);
        return persistence.find(solicitudesId, serviciosId);
    }

    /**
     * Actualiza la información de una instancia de Servicio.
     *
     * @param servicioEntity Instancia de ServicioEntity con los nuevos datos.
     * @param solicitudesId id del Solicitud el cual sera padre del Servicio actualizado.
     * @return Instancia de ServicioEntity con los datos actualizados.
     *
     */
    public ServicioEntity updateServicio(Long solicitudesId, ServicioEntity servicioEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el servicio con id = {0} del solicitud con id = " + solicitudesId, servicioEntity.getId());
        SolicitudEntity solicitudEntity = solicitudPersistence.find(solicitudesId);
        servicioEntity.setSolicitud(solicitudEntity);
        persistence.update(servicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el servicio con id = {0} del solicitud con id = " + solicitudesId, servicioEntity.getId());
        return servicioEntity;
    }

    /**
     * Elimina una instancia de Servicio de la base de datos.
     *
     * @param serviciosId Identificador de la instancia a eliminar.
     * @param solicitudesId id del Solicitud el cual es padre del Servicio.
     * @throws BusinessLogicException Si la reseña no esta asociada al solicitud.
     *
     */
    public void deleteServicio(Long solicitudesId, Long serviciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el servicio con id = {0} del solicitud con id = " + solicitudesId, serviciosId);
        ServicioEntity old = getServicio(solicitudesId, serviciosId);
        if (old == null) {
            throw new BusinessLogicException("El servicio con id = " + serviciosId + " no esta asociado a el solicitud con id = " + solicitudesId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el servicio con id = {0} del solicitud con id = " + solicitudesId, serviciosId);
    }
}
