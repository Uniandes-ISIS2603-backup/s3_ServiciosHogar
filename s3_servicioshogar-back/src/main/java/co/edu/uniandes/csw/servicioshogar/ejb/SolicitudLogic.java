/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
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
public class SolicitudLogic {
    private static final Logger LOGGER = Logger.getLogger(SolicitudLogic.class.getName());

    @Inject
    private SolicitudPersistence persistence;

    /**
     * Guardar un nuevo solicitud
     *
     * @param solicitudEntity La entidad de tipo solicitud del nuevo solicitud a persistir.
     * @return La entidad luego de persistirla
     * persistencia.
     */
    public SolicitudEntity createSolicitud(SolicitudEntity solicitudEntity){
        LOGGER.log(Level.INFO, "Inicia proceso de creación del solicitud");
        persistence.create(solicitudEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del solicitud");
        return solicitudEntity;
    }

    /**
     * Devuelve todos los solicitudes que hay en la base de datos.
     *
     * @return Lista de entidades de tipo solicitud.
     */
    public List<SolicitudEntity> getSolicitudes() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los solicitudes");
        List<SolicitudEntity> solicitudes = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los solicitudes");
        return solicitudes;
    }

    /**
     * Busca un solicitud por ID
     *
     * @param solicitudesId El id del solicitud a buscar
     * @return El solicitud encontrado, null si no lo encuentra.
     */
    public SolicitudEntity getSolicitud(Long solicitudesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el solicitud con id = {0}", solicitudesId);
        SolicitudEntity solicitudEntity = persistence.find(solicitudesId);
        if (solicitudEntity == null) {
            LOGGER.log(Level.SEVERE, "El solicitud con el id = {0} no existe", solicitudesId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el solicitud con id = {0}", solicitudesId);
        return solicitudEntity;
    }

    /**
     * Actualizar un solicitud por ID
     *
     * @param solicitudesId El ID del solicitud a actualizar
     * @param solicitudEntity La entidad del solicitud con los cambios deseados
     * @return La entidad del solicitud luego de actualizarla
     */
    public SolicitudEntity updateSolicitud(Long solicitudesId, SolicitudEntity solicitudEntity){
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el solicitud con id = {0}", solicitudesId);
        SolicitudEntity newEntity = persistence.update(solicitudEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el solicitud con id = {0}", solicitudEntity.getId());
        return newEntity;
    }
    
    /**
     * Eliminar un solicitud por ID
     *
     * @param solicitudesId El ID del solicitud a eliminar
     */
    public void deleteSolicitud(Long solicitudesId){
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el solicitud con id = {0}", solicitudesId);
        persistence.delete(solicitudesId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el solicitud con id = {0}", solicitudesId);
    }
}
